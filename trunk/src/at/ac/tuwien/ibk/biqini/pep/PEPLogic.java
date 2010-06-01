/* 
* PEPLogic.java
* Marco Happenhofer
* $Revision$
* 
* Copyright (C) 2010 FTW (Telecommunications Research Center Vienna)
* 
*
* This file is part of BIQINI, a free Policy and Charging Control Function
* for session-based services.
*
* BIQINI is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version
*
* For a license to use the BIQINI software under conditions
* other than those described here, or to purchase support for this
* software, please contact FTW by e-mail at the following addresses:
* fpcc@ftw.at   
*
* BIQINI is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License 
* along with this program; if not, write to the Free Software 
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package at.ac.tuwien.ibk.biqini.pep;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import org.apache.log4j.Logger;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginRealm;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ResultCode;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SessionID;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleDefinition;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSInformation;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterAnswer;
import at.ac.tuwien.ibk.biqini.diameter.messages.IIETFResponseCodes;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxRAA;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxRAR;
import at.ac.tuwien.ibk.biqini.pep.Exception.ChargingRuleInstallException;
import at.ac.tuwien.ibk.biqini.pep.Exception.ConfigurationMissingException;
import at.ac.tuwien.ibk.biqini.pep.Exception.TrafficControlException;
import de.fhg.fokus.diameter.DiameterPeer.DiameterPeer;
import de.fhg.fokus.diameter.DiameterPeer.EventListener;
import de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage;

/**
 * Main Logic for PEP (PCEF and/or RCEF) component
 * 
 * Processes Diameter messages, stores in Session and process these sessions
 * 
 * @author Marco Happenhofer
 * 
 */
public class PEPLogic implements EventListener {

	public static final int PRINT_DEBUG = 0x1;

	public static final int PRINT_INFO = 0x2;

	/**The logger*/
	public static final Logger LOGGER = Logger.getLogger(PEPApplication.class);

	/**all active PEP Sessions*/
	private Hashtable<String, PEPSession> pepSessions;

	/**dimeterPeer for executing diameter commands*/
	private DiameterPeer diameterPeer;

	/**configured max Bandwidth uplink*/
	private long bandwidthUp;

	/**configured max Bandwidth downlink*/
	private long bandwidthDown;

	/**actual free uplink bandwidth*/
	private long freeUp;

	/**actual free downlink bandwidth*/
	private long freeDown;

	/**level of logging report*/
	private int logLevel;

	/**
	 * 
	 */
	public PEPLogic(DiameterPeer diameterPeer, long up, long down) {
		super();
		this.diameterPeer = diameterPeer;
		PEPSession.pepLogic=this;
		pepSessions = new Hashtable<String, PEPSession>();
		bandwidthDown = down;
		bandwidthUp = up;
		freeDown = down;
		freeUp = up;
		logLevel = 0x0;
	}

	/**
	 * installs all Traffic Control queues
	 * further all predefined and actived
	 * 
	 * @throws TrafficControlException
	 */
	public void setupTrafficControl() throws TrafficControlException {
		TrafficControl.getInstance().init();
		PEPXMLConfig config;
		try {
			config = PEPXMLConfig.getInstance();
			Collection<QoSRule> rules = config.getAllStaticRules();
			Iterator <QoSRule> it = rules.iterator();
			while(it.hasNext()){
				QoSRule q = it.next();
				boolean hasToActived=q.isActive();
				q.setActive(false);
				PEPSession.addStaticQoSRule(q);
				if(hasToActived)	{
					try {
						PEPSession.installStaticRule(q.getName());
					} catch (ChargingRuleInstallException e) {
						LOGGER.error("Could not active static Rule "+q.getName()+" ("+e.getMessage()+")");
					}
				}
			}
		} catch (ConfigurationMissingException e) {
			LOGGER.error("Could not access Config, abort static Rules load!");
		}
	}

	/**
	 * removes all Traffic Control queues
	 */
	public void clearTrafficControl() throws TrafficControlException {
		TrafficControl.getInstance().terminate();
	}

	/**
	 * configures which details are logged to the console
	 * 
	 * @param loglevel
	 *            PRINT_INFO, PRINT_DEBUG
	 */
	public void configureLogging(int loglevel) {
		logLevel = loglevel;
	}
	
	/**
	 * set certain logging options
	 * @param info		true id info messages should be logged
	 * @param message	true if all diameter messages should be logged
	 */
	public void configureLogging(boolean info, boolean message) {
		int temp=0x00;
		if(info)
			temp=temp+0x02;
		if(message)
			temp=temp+0x01;
		logLevel = temp;
	}

	/**
	 * queries the current logging level
	 * @return	current logginglevel
	 */
	public int getcurrentLogLevel() {
		return logLevel;
	}

	public void recvMessage(String FQDN, DiameterMessage msg) {
		synchronized (this) {
			DiameterMessage response = null;
			if ((logLevel & PRINT_DEBUG) != 0) {
				LOGGER.debug("Received a Diameter request!\n"+msg.toString());
			}
			if (msg instanceof GxRAR) {
				GxRAR rar = (GxRAR) msg;

				SessionID sessionID = rar.getSessionId();
				PEPSession pep = null;
				if (!pepSessions.containsKey(sessionID.getUTF8String())) {
					// a new Session ID so create a new PEP Session
					pep = new PEPSession(sessionID);
					pepSessions.put(sessionID.getUTF8String(), pep);
				} else
					pep = pepSessions.get(sessionID.getUTF8String());
				response = pep.updateSession(rar);
				pep.addDiameterMessage(rar);
				pep.addDiameterMessage(response);
				//TODO modify as inactive to ensure that all sessions can be observed later
				if (pep.getNumberofActiveRules() == 0)
					pepSessions.remove(sessionID.getUTF8String());

			} else {
				// We do not care about all other message Types as RAR
				response = newGenericResponse(msg,
						IIETFResponseCodes.DIAMETER_APPLICATION_UNSUPPORTED);
			}
			if ((logLevel & PRINT_DEBUG) != 0) {
				LOGGER.debug("Send a Diameter response!\n"+response.toString());
			}
			diameterPeer.sendMessage(FQDN, response);
		}
	}

	/**
	 * allocate the resources for a Charging Rule Definition
	 * 
	 * @param crd
	 *            the Charging Rule definition for which the resources should be
	 *            allocated
	 * @throws ChargingRuleInstallException
	 *             throws if not enough resources are free
	 * @throws NullPointerException
	 *             throws if there is no QoS Information inside this
	 *             CharginingRuleDefintion
	 */
	public synchronized void allocateResources(ChargingRuleDefinition crd)
			throws ChargingRuleInstallException, NullPointerException {
		QoSInformation qosInformation = crd.getQoSInformation();
		long downlink=0;
		long uplink=0;
		try {
			downlink = qosInformation.getMaxRequestedBandwidthDL()
					.getUnsigned32();
		} catch (NullPointerException e) {
		}
		try {
			uplink = qosInformation.getMaxRequestedBandwidthUL()
					.getUnsigned32();
		} catch (NullPointerException e) {
		}
		
		if (freeDown < downlink && freeUp < uplink)
			throw new ChargingRuleInstallException(
					"Too few Resources for uplink and downlink!");
		if (freeDown < downlink)
			throw new ChargingRuleInstallException(
					"Too few Resources for downlink!");
		if (freeUp < uplink)
			throw new ChargingRuleInstallException(
					"Too few Resources for uplink!");
		freeDown = freeDown - downlink;
		freeUp = freeUp - uplink;
	}

	/**
	 * free the resources for a Charging Rule Definition
	 * 
	 * @param crd
	 * @throws NullPointerException
	 *             throws if no Bandwidth information is inside the Charging
	 *             Rule Definition
	 */
	public synchronized void freeResources(ChargingRuleDefinition crd)
			throws NullPointerException {
		QoSInformation qosInformation = crd.getQoSInformation();
		try {
			long downlink = qosInformation.getMaxRequestedBandwidthDL()
					.getUnsigned32();
			freeDown = Math.min(freeDown + downlink, this.bandwidthDown);
		} catch (NullPointerException e) {
		}
		try {
			long uplink = qosInformation.getMaxRequestedBandwidthUL()
					.getUnsigned32();
			freeUp = Math.min(freeUp + uplink, this.bandwidthUp);
		} catch (NullPointerException e) {
		}
		
	}

	/**
	 * create a template response based on the GxRAR request
	 * @param _request			request on that base the response should be created
	 * @param _responseCode		the response code for the response
	 * @return		a correct response on that request
	 */
	public GxRAA newGxRAA(GxRAR _request, int _responseCode) {
		GxRAA raa = new GxRAA();
		raa.endToEndID = _request.endToEndID;
		raa.hopByHopID = _request.hopByHopID;
		raa.setOriginHost(new OriginHost(diameterPeer.FQDN));
		raa.setOriginRealm(new OriginRealm(diameterPeer.Realm));
		raa.setResultCode(new ResultCode(_responseCode));
		raa.setSessionId(_request.getSessionId());
		return raa;
	}
	
	/**
	 * creates a response for an unknown/unsupported Request type
	 * @param _request			request on that base the response should be created
	 * @param _responseCode		the response code for the response
	 * @return		a correct response on that request
	 */
	public DiameterAnswer newGenericResponse(DiameterMessage _request,
			int _responseCode) {
		DiameterAnswer da = new DiameterAnswer(_request.commandCode,
				_request.applicationID);
		da.endToEndID = _request.endToEndID;
		da.hopByHopID = _request.hopByHopID;
		da.setOriginHost(new OriginHost(diameterPeer.FQDN));
		da.setOriginRealm(new OriginRealm(diameterPeer.Realm));
		da.setSessionId(_request.getSessionId());
		da.setResultCode(new ResultCode(_responseCode));
		return da;
	}

	/**
	 * @param diameterPeer
	 *            the diameterPeer to set
	 */
	public void setDiameterPeer(DiameterPeer diameterPeer) {
		this.diameterPeer = diameterPeer;
	}
	/**
	 * provides an Array with all current active Sessions	
	 * @return	an array with all current sessions
	 */
	public PEPSession[] getAllSessions() {
		PEPSession ret[];
		synchronized (pepSessions) {
			ret = new PEPSession[pepSessions.size()];
			int i = 0;
			Iterator<PEPSession> sessions = pepSessions.values().iterator();
			while (sessions.hasNext()) {
				ret[i++] = sessions.next();
			}
		}
		return ret;
	}
	/**
	 * queries the actual free uplink bitrate
	 * @return	free uplink bitrate
	 */
	public long getFreeUp() {
		return freeUp;
	}
	/**
	 * queries the actual free downlink bitrate
	 * @return	free downlink bitrate
	 */
	public long getFreeDown() {
		return freeDown;
	}

}
