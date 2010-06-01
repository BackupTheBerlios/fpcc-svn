/* 
* PDPGx.java
* Christoph Egger
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
package at.ac.tuwien.ibk.biqini.pdp;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import at.ac.tuwien.ibk.biqini.diameter.DiameterHelper;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.DestinationHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.DestinationRealm;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginRealm;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ReAuthRequestType;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SessionID;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleDefinition;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleInstall;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleRemove;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowNumber;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowStatus;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowUsage;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Flows;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthUL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaComponentDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaComponentNumber;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaSubComponent;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSClassIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSInformation;
import at.ac.tuwien.ibk.biqini.diameter.exceptions.DiameterHelperNotConfiguredException;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterAnswer;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterRequest;
import at.ac.tuwien.ibk.biqini.diameter.messages.IfMessageReceiver;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxRAR;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.I3GPPGxInterface;
import at.ac.tuwien.ibk.biqini.pdp.session.PDPSession;
import at.ac.tuwien.ibk.biqini.pdp.system.PEP;
import at.ac.tuwien.ibk.biqini.pdp.system.SystemStructure;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowDescription;

public class PDPGx implements IfPDPtoPEP, IfMessageReceiver, I3GPPGxInterface{

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(PDPGx.class);
	private int whatToDo = 0;
	private final int INSTALL = 1;
	private final int REMOVE = 2;
	private final int UPDATE = 3;
	//private Hashtable<DiameterRequest, DiameterAnswer> diamAnswers;
	private DiameterHelper diameterHelper;
	private AVPBuilder avpBuilder;
	//private DiameterRequest currentRequest;
	private PDPSession currentSession;
	private SystemStructure systemStructure;
	private IfMessageReceiver ifMessageReceiver;

	private static PDPGx INSTANCE = null;

	public static PDPGx getInstance(IfMessageReceiver _pdpLogic){
		if (INSTANCE == null){
			INSTANCE = new PDPGx(_pdpLogic);
		}
		return INSTANCE;
	}
	public static PDPGx getInstance(){
		return INSTANCE;
	}

	private PDPGx(IfMessageReceiver _pdpLogic){
		ifMessageReceiver = _pdpLogic;
		avpBuilder = new AVPBuilder();
		try {
			diameterHelper = DiameterHelper.getInstance();
		} catch (DiameterHelperNotConfiguredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		diameterHelper.registerApp(APPLICATION_ID, this);
		//diamAnswers = new Hashtable<DiameterRequest, DiameterAnswer>();
		systemStructure = SystemStructure.getInstance();
	}

	public void installSession(PDPSession _sess){
		synchronized(this){
			currentSession = _sess;
			whatToDo = INSTALL;
			this.notify();
		}
	}

	public void removeSession(PDPSession _sess) {
		synchronized(this){
			currentSession = _sess;
			whatToDo = REMOVE;
			this.notify();
		}
	}

	public void updateSession(PDPSession _sess) {
		synchronized(this){

			whatToDo = UPDATE;
			this.notify();
		}
	}
	/*
	public String getLastAnswer(){
		return diamAnswer;
	}
	public void shutdownPeer(){
		synchronized(this){
			whatToDo = SHUTDOWN;
			this.notify();
		}
	}*/

	private synchronized void install(){
		Vector<PEP> currentPEPList = systemStructure.getPEPsForIP(currentSession.getFramedIPAddress());
		Iterator<PEP> PEPit = currentPEPList.iterator();
		while (PEPit.hasNext()){
			GxRAR rar = buildGxRAR(INSTALL, PEPit.next());
			diameterHelper.sendRequestTransactional(rar);
		}
		whatToDo = 0;
	}

	private synchronized void remove(){
		Vector<PEP> currentPEPList = systemStructure.getPEPsForIP(currentSession.getFramedIPAddress());
		Iterator<PEP> PEPit = currentPEPList.iterator();
		while (PEPit.hasNext()){
			GxRAR rar = buildGxRAR(REMOVE, PEPit.next());
			diameterHelper.sendRequestTransactional(rar);
		}
		whatToDo = 0;
	}
	private synchronized void update(){


		whatToDo = 0;
	}

	public void recvdAnswer(String _fqdn, DiameterRequest _req, DiameterAnswer _answ) {
		ifMessageReceiver.recvdAnswer(_fqdn, _req, _answ);
	}

	public void recvdRequest(String _fqdn, DiameterRequest _req) {
		// TODO not implemented yet, because in the first version we are only client for Gx

	}
	/*
	private void shutdown(){
		diameterPeer.shutdown();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	 */
	public void run() {
		synchronized(this){
			while(true){
				switch(whatToDo){
				case INSTALL:
					install();
					break;
				case REMOVE:
					remove();
					break;
				case UPDATE:
					update();
					break;
				default:
					break;
				}
				try{
					this.wait();
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}

	private GxRAR buildGxRAR(int _what, PEP _pep){
		GxRAR rar = new GxRAR();
		rar.hopByHopID = diameterHelper.getNextHopByHopId();
		rar.endToEndID = diameterHelper.getNextEndToEndId();
		rar.setOriginHost(new OriginHost(diameterHelper.getPeerFQDN()));
		rar.setOriginRealm(new OriginRealm(diameterHelper.getPeerRealm()));
		rar.setDestinationHost(new DestinationHost(_pep.getAddress()));
		rar.setDestinationRealm(new DestinationRealm(_pep.getRealm()));
		rar.setReAuthRequestType(new ReAuthRequestType(ReAuthRequestType.AUTHORIZE_ONLY));
		rar.setSessionId(new SessionID(currentSession.getSessionID()));
		switch (_what){
		case INSTALL:
			rar.addChargingRuleInstall(avpBuilder.getChargingRuleInstall());
			break;
		case REMOVE:
			rar.addChargingRuleRemove(avpBuilder.getChargingRuleRemove());
			break;
		}
		return rar;
	}

	private class AVPBuilder{

		private ChargingRuleInstall getChargingRuleInstall(){
			ChargingRuleInstall c = new ChargingRuleInstall();
			Iterator<MediaComponentDescription> medIt = currentSession.getMediaComponents().iterator();
			while (medIt.hasNext()){
				MediaComponentDescription mediaComponentDescription = medIt.next();
				MaxRequestedBandwidthDL maxDL = mediaComponentDescription.getMaxRequestedBandwidthDL();
				MaxRequestedBandwidthUL maxUL = mediaComponentDescription.getMaxRequestedBandwidthUL();
				Iterator<MediaSubComponent> subIt = mediaComponentDescription.getMediaSubComponentIterator();
				while (subIt.hasNext()){
					c.addChargingRuleDefinition(getChargingRuleDefinition(subIt.next(), mediaComponentDescription.getMediaComponentNumber(), mediaComponentDescription.getFlowStatus(), maxDL, maxUL));
				}

			}
			return c;
		}

		private ChargingRuleRemove getChargingRuleRemove(){//MediaSubComponent _mediaSubComponent, MediaComponentNumber _mediaComponentNumber)	{
			ChargingRuleRemove c = new ChargingRuleRemove();
			Iterator<MediaComponentDescription> medIt = currentSession.getMediaComponents().iterator();
			while (medIt.hasNext()){
				MediaComponentDescription mediaComponentDescription = medIt.next();
				Iterator<MediaSubComponent> subIt = mediaComponentDescription.getMediaSubComponentIterator();
				while (subIt.hasNext()){
					MediaSubComponent mediaSubComponent = subIt.next();
					c.addChargingRuleName(new ChargingRuleName(generateChargingRuleName(currentSession.getSessionID(), mediaComponentDescription.getMediaComponentNumber(), mediaSubComponent.getFlowNumber())));
				}

			}
			return c;
		}

		private ChargingRuleDefinition getChargingRuleDefinition(MediaSubComponent _mediaSubComponent, MediaComponentNumber _mediaComponentNumber, FlowStatus _flowStatus, MaxRequestedBandwidthDL _maxDL, MaxRequestedBandwidthUL _maxUL)	{
			ChargingRuleDefinition chargingRuleDefinition = new ChargingRuleDefinition();
			chargingRuleDefinition.setChargingRuleName(new ChargingRuleName(generateChargingRuleName(currentSession.getSessionID(), _mediaComponentNumber, _mediaSubComponent.getFlowNumber())));
			chargingRuleDefinition.setFlowStatus(_flowStatus);
			QoSInformation q = new QoSInformation();
		//	q.setMaxRequestedBandwidthDL(new MaxRequestedBandwidthDL(15000000));//_mediaSubComponent.getMaxRequestedBandwidthDL().getUnsigned32()));
		//	q.setMaxRequestedBandwidthUL(new MaxRequestedBandwidthUL(15000000));//_mediaSubComponent.getMaxRequestedBandwidthUL().getUnsigned32()));
			long bla = _mediaSubComponent.getMaxRequestedBandwidthDL().getUnsigned32();
			long bla2 = _mediaSubComponent.getMaxRequestedBandwidthUL().getUnsigned32();
			q.setMaxRequestedBandwidthDL(new MaxRequestedBandwidthDL(_mediaSubComponent.getMaxRequestedBandwidthDL().getUnsigned32()));
			q.setMaxRequestedBandwidthUL(new MaxRequestedBandwidthUL(_mediaSubComponent.getMaxRequestedBandwidthUL().getUnsigned32()));
			q.setQoSClassIdentifier(new QoSClassIdentifier(QoSClassIdentifier.CONVERSATIONAL_SPEECH));
			chargingRuleDefinition.setQoSInformation(q);
			Flows flows = new Flows(_mediaComponentNumber);
			flows.addFlowNumber(_mediaSubComponent.getFlowNumber());
			chargingRuleDefinition.addFlows(flows);
			FlowDescription flowIn = _mediaSubComponent.getFlowDescriptionIn();
			FlowDescription flowOut = _mediaSubComponent.getFlowDescriptionOut();
			if (flowIn != null)
				chargingRuleDefinition.addFlowDescription(flowIn);
			if (flowOut != null)
				chargingRuleDefinition.addFlowDescription(flowOut);
			return chargingRuleDefinition;
		}

		private String generateChargingRuleName(String _sessionID, MediaComponentNumber _mediaComponentNumber, FlowNumber _flowNumber){
			return _sessionID+";"+_mediaComponentNumber.getUnsigned32()+";"+_flowNumber.getUnsigned32();
		}
	}


}
