/* 
* PDPSession.java
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
package at.ac.tuwien.ibk.biqini.pdp.session;

import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import at.ac.tuwien.ibk.biqini.common.Codec;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.CodecData;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowUsage;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthUL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaComponentDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaSubComponent;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterAnswer;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterRequest;
import at.ac.tuwien.ibk.biqini.diameter.messages.IfMessageReceiver;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxAAR;
import at.ac.tuwien.ibk.biqini.pdp.state.StateMachine;

/**
 * @author Christoph Egger
 *
 */
public class PDPSession implements IfMessageReceiver{

	public static final int RTCP_BANDWIDTH = 8000;
	
	
	private static final Logger LOGGER = Logger.getLogger(PDPSession.class.getName());
	
	private StateMachine stateMachine;
	private String sessionID;
	private long currentBandwidthUP = 0;
	private long currentBandwidthDOWN = 0;
	private String framedIPAddress;
	private String fqdnOfAF;
	private Vector<MediaComponentDescription> mediaComponents;
	
	private boolean bandwidthValid = false;
	
	public PDPSession(String _sessionID, String _framedIPAddress, String _fqdnOfAF) {
		super();
		sessionID = _sessionID;
		framedIPAddress = _framedIPAddress;
		fqdnOfAF = _fqdnOfAF;
		stateMachine = new StateMachine(this);
		mediaComponents = new Vector<MediaComponentDescription>();
		
	}
	
	public void recvdAnswer(String _fqdn, DiameterRequest _req,
			DiameterAnswer _answ) {
		stateMachine.recvdAnswer(_fqdn, _req, _answ);
		
	}

	public void recvdRequest(String _fqdn, DiameterRequest _req) {
		if (_req instanceof RxAAR){
				framedIPAddress = ((RxAAR)_req).getFramedIPAddress().getOctetString();
				Iterator<MediaComponentDescription> medIt = ((RxAAR)_req).getMediaComponentDescriptionIterator();
				mediaComponents = new Vector<MediaComponentDescription>();
				bandwidthValid=false;
				while (medIt.hasNext()){
					//FIXME update implementieren!!!
					mediaComponents.add(medIt.next());
				}
		}
		stateMachine.recvdRequest(_fqdn, _req);
	}


	/**
	 * @return the sessionID
	 */
	public String getSessionID() {
		return sessionID;
	}

	/**
	 * @return the currentBandwidthUP
	 */
	public long getCurrentBandwidthUP() {
		if(!bandwidthValid)
			calculateNewBandwidthValue();
		return currentBandwidthUP;
	}

	/**
	 * @param currentBandwidthUP the currentBandwidthUP to set
	 */
	/*public void setCurrentBandwidthUP(long currentBandwidthUP) {
		
		this.currentBandwidthUP = currentBandwidthUP;
	}*/

	/**
	 * @return the currentBandwidthDOWN
	 */
	public long getCurrentBandwidthDOWN() {
		if(!bandwidthValid)
			calculateNewBandwidthValue();
		return currentBandwidthDOWN;
	}

	/**
	 * @param currentBandwidthDOWN the currentBandwidthDOWN to set
	 */
	/*public void setCurrentBandwidthDOWN(long currentBandwidthDOWN) {
		this.currentBandwidthDOWN = currentBandwidthDOWN;
	}*/

	/**
	 * @return the framedIPAddress of the ua
	 */
	public String getFramedIPAddress() {
		return framedIPAddress;
	}

	public void calculateNewBandwidthValue() {
		currentBandwidthDOWN = 0;
		currentBandwidthUP = 0;
		Iterator<MediaComponentDescription> medCompIt = this.mediaComponents.iterator();
		while (medCompIt.hasNext()){
			MediaComponentDescription medComp = medCompIt.next();
			long mediaComponentDL=0;
			long mediaComponentUL=0;
			long SDPUL=0;
			long SDPDL=0;
			MaxRequestedBandwidthUL medMRBU = medComp.getMaxRequestedBandwidthUL();
			if(medMRBU!=null)
				SDPUL=medMRBU.getUnsigned32();
			MaxRequestedBandwidthDL medMRBD = medComp.getMaxRequestedBandwidthDL();
			if(medMRBD!=null)
				SDPDL=medMRBD.getUnsigned32();	
			Iterator<MediaSubComponent> medSubCompIt = medComp.getMediaSubComponentIterator();
			while(medSubCompIt.hasNext())	{
				MediaSubComponent mediaSubComponent = medSubCompIt.next();
				MaxRequestedBandwidthDL dl = mediaSubComponent.getMaxRequestedBandwidthDL();
				MaxRequestedBandwidthUL ul = mediaSubComponent.getMaxRequestedBandwidthUL();
				FlowUsage fU = mediaSubComponent.getFlowUsage();
				
				/*
				 * commented out that we look into the media desctiption sent by the AF
				 * we only look into the codebook!!!
				 * 
				 * 
				 */
				/*if(SDPDL!=0 && (fU==null || fU.getEnumerated()==FlowUsage.NO_INFORMATION))	{
					// use Bandwidth of the MediaComponent if it exists !
					mediaSubComponent.setMaxRequestedBandwidthDL(new MaxRequestedBandwidthDL(SDPDL));
				} else {
					// try to estimate a bandwidth !
					if(dl == null)*/
						mediaSubComponent.setMaxRequestedBandwidthDL(new MaxRequestedBandwidthDL(getMaxRequestedBandwidth(fU,medComp,false)));
				/*}
				if(SDPUL!=0 && (fU==null || fU.getEnumerated()==FlowUsage.NO_INFORMATION))	{
					mediaSubComponent.setMaxRequestedBandwidthUL(new MaxRequestedBandwidthUL(SDPUL));
				} else {
					if(ul ==null)*/
						mediaSubComponent.setMaxRequestedBandwidthUL(new MaxRequestedBandwidthUL(getMaxRequestedBandwidth(fU,medComp,true)));
				//}
				mediaComponentDL += mediaSubComponent.getMaxRequestedBandwidthDL().getUnsigned32();
				mediaComponentUL += mediaSubComponent.getMaxRequestedBandwidthUL().getUnsigned32();
				
			}
			MaxRequestedBandwidthDL dl = medComp.getMaxRequestedBandwidthDL();
			MaxRequestedBandwidthUL ul = medComp.getMaxRequestedBandwidthUL();
			// check if bandwidth information is in MeidaComponent
			if(dl == null || mediaComponentDL>dl.getUnsigned32()){
				medComp.setMaxRequestedBandwidthDL(new MaxRequestedBandwidthDL(mediaComponentDL));
			}
			if(ul == null || mediaComponentUL>ul.getUnsigned32()){
				medComp.setMaxRequestedBandwidthUL(new MaxRequestedBandwidthUL(mediaComponentUL));
			}
			
			currentBandwidthDOWN += medComp.getMaxRequestedBandwidthDL().getUnsigned32();
			currentBandwidthUP += medComp.getMaxRequestedBandwidthUL().getUnsigned32();
		}
	}
	/**
	 * calucates the maxRequested Bandwidth for a certain MediaSubcomponent
	 * @param fU		Usage of this flow (RTCP or no Information )
	 * @param medComp	MeidComponent the mediaSubcomponent belongs to
	 * @param uplink	claculate the upl√∂ink ?
	 * @return			
	 */
	private long getMaxRequestedBandwidth(FlowUsage fU, MediaComponentDescription medComp, boolean uplink)	{
		long maxRequestedBandwidth=0;
			if(fU==null || fU.getEnumerated()==FlowUsage.NO_INFORMATION)	{
				// OK RTP stream lets have a look in the code Book
				Iterator<CodecData> codecDataIt = medComp.getCodecDataIterator();
				// search for the correct codec Data
				while(codecDataIt.hasNext())	{
					CodecData cd = codecDataIt.next();
					try {
						cd.parseCodecData();
					} catch (Exception e) {
						LOGGER.error("Could not identify the direction of Codec Data!"+e.getMessage());
					}
					if(!uplink && cd.isDownlink() || uplink && cd.isUplink())		{
						// found correct codecData
						Iterator<Codec> codecIt = cd.getMediaCodecs().iterator();
						while(codecIt.hasNext()){
							Codec currentCodec = codecIt.next();
							long bndw = currentCodec.getBandwidth();
							// ok we have no information in the codecbook, so let us try to estaimate
							if(bndw == -1)	{
								currentCodec.estimateBandwidth();
								bndw = currentCodec.getBandwidth();
							}
							if(bndw>maxRequestedBandwidth)
								maxRequestedBandwidth=bndw;
						}
					}
				}
				// ok store the bandwidth information we have found
				return maxRequestedBandwidth;
			}
			else	{
				// stream is for RTCP
				return RTCP_BANDWIDTH;
			}
	}

	/**
	 * @return the fqdnOfAF
	 */
	public String getFqdnOfAF() {
		return fqdnOfAF;
	}

	/**
	 * @return the mediaComponents
	 */
	public Vector<MediaComponentDescription> getMediaComponents() {
		return mediaComponents;
	}
	
	public String toString(){
		return " SessionID="+sessionID+"\n IPOfClient="+framedIPAddress+ "\n Status="+stateMachine.getStateName();
	}

	public void setCurrentBandwidthDOWN(long currentBandwidthDOWN) {
		this.currentBandwidthDOWN = currentBandwidthDOWN;
	}

	public void setCurrentBandwidthUP(long currentBandwidthUP) {
		this.currentBandwidthUP = currentBandwidthUP;
	}
}
