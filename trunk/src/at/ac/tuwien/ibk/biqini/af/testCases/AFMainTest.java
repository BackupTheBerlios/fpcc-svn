/* 
* AFMainTest.java
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
* fpcc@ftw.at ÊÊ
*
* BIQINI is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. ÊSee the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License 
* along with this program; if not, write to the Free Software 
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA Ê02111-1307 ÊUSA
*/
package at.ac.tuwien.ibk.biqini.af.testCases;

import java.io.File;

import org.apache.log4j.Logger;

import at.ac.tuwien.ibk.biqini.af.AFApplication;
import at.ac.tuwien.ibk.biqini.af.Media;
import at.ac.tuwien.ibk.biqini.af.SSDP;
import at.ac.tuwien.ibk.biqini.af.SessionDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.DestinationHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.DestinationRealm;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginRealm;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.CodecData;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowNumber;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowStatus;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowUsage;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthUL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaComponentDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaSubComponent;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaType;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterAnswer;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterRequest;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxAAR;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxSTR;
import de.fhg.fokus.diameter.DiameterPeer.DiameterPeer;
import de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage;

public abstract class AFMainTest implements AFTestCase {

	protected static final boolean DEBUG = false;
	
	protected static final Logger LOGGER = Logger.getLogger(AFApplication.class);
	protected DiameterPeer diameterPeer;
	protected String afName;
	protected String realm;
	protected int hbhID;
	protected int eteID;
	protected String localFQDN;
	protected String localRealm;
	
	protected boolean localStack = false;
	
	protected SessionDescription sessionDescription;
	protected String defaultUser ="sip:goodUser@domain.at";
	
	public void init(String filename, String AF, String realm) throws Exception	{
		localStack = true;
		afName=AF;
		this.realm=realm;
		if(DEBUG){
			LOGGER.info(this.TestName()+" Starting... in Debug");
			hbhID = 0123;
			eteID = 456;
			localFQDN="localHost";
			localRealm="open-ims.test";
			return;
		}
		LOGGER.info(this.TestName()+" Starting...");
		
		// start diameter peer
		String xmlFilename = filename;
		File xml = new File(xmlFilename);
		if(!xml.exists())
		{
			LOGGER.error("Can not find File "+xml.getAbsoluteFile());
			throw new Exception("Can not find File "+xml.getAbsoluteFile());
		}
		diameterPeer = new DiameterPeer();
		diameterPeer.configure(xmlFilename, true);
		diameterPeer.enableTransactions(10,1);
		hbhID = diameterPeer.getNextHopByHopId();
		eteID = diameterPeer.getNextEndToEndId();
		localFQDN=diameterPeer.FQDN;
		localRealm=diameterPeer.Realm;
		
		Thread.sleep(1000);
	}
	
	public void init(DiameterPeer diameterPeer,String afName, String realm) throws Exception	{
		this.diameterPeer=diameterPeer;
		hbhID = diameterPeer.getNextHopByHopId();
		eteID = diameterPeer.getNextEndToEndId();
		localFQDN=diameterPeer.FQDN;
		localRealm=diameterPeer.Realm;
		this.afName = afName;
		this.realm= realm;
	}
	
	
	public void exit()	{
		if(DEBUG) return;
		if(!localStack) return;
		diameterPeer.shutdown();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected int sendBlocking(DiameterRequest request, DiameterAnswer response){
		if(DEBUG){
			System.out.println(request.toString());
			return 2001;
		}
		DiameterMessage dma = diameterPeer.sendRequestBlocking(afName,request);
		if (dma==null) {
			LOGGER.error(TestName()+ " SendBlocking timed-out!");
			return -1;
		}
		if (dma instanceof DiameterAnswer) {
			DiameterAnswer ret = (DiameterAnswer) dma;
			response = ret;
		}else	{
			LOGGER.error(TestName()+ " Response was not an Answer");
			return -1;
		}
			
		LOGGER.debug(TestName()+ " SendBlocking answer: "+dma.toString());
		if(!response.getResultCode().isSuccess())
		{
			LOGGER.debug(TestName()+ " Did not Receive 2001 as Result code!");
		}
		return response.getResultCode().getIntData();
	}
	
	
	protected RxSTR getRxSTR()	{
		RxSTR str = new RxSTR();
		str.hopByHopID=hbhID;
		str.endToEndID=eteID;
		str.setOriginHost(new OriginHost(localFQDN));
		str.setOriginRealm(new OriginRealm(localRealm));
		str.setDestinationHost(new DestinationHost(afName));
		str.setDestinationRealm(new DestinationRealm(realm));
		return str;
	}
	
	protected RxAAR getRxAAR()	{
		RxAAR aar = new RxAAR();
		aar.hopByHopID=hbhID;
		aar.endToEndID=eteID;
		aar.setOriginHost(new OriginHost(localFQDN));
		aar.setOriginRealm(new OriginRealm(localRealm));
		aar.setDestinationHost(new DestinationHost(afName));
		aar.setDestinationRealm(new DestinationRealm(realm));
		return aar;
	}
	
	protected void addMedia(RxAAR aar, SessionDescription sessionDescription){
		aar.setServiceInfoStatus(sessionDescription.getServiceInfoStatus());
		aar.addSubscriptionId(sessionDescription.getSubscriptionId());
		aar.setFramedIPAddress(sessionDescription.getLocalIP());
		aar.setSessionId(sessionDescription.getSessionID());
		
		SSDP offer = sessionDescription.getOffer();
		SSDP answer = sessionDescription.getAnswer();
		
		Media audioOffer = offer.audio;
		Media audioAnswer = answer.audio;
		
		Media videoOffer = offer.video;
		Media videoAnswer = answer.video;
		
		String lIP = sessionDescription.getLocalIP().getOctetString();
		String rIP = sessionDescription.getRemoteIP().getOctetString();
		
		if(audioOffer!=null)	{
			aar.addMediaComponentDescription(createMediaComponentDescription(1, audioOffer, audioAnswer, lIP, rIP, MediaType.AUDIO));
		}
		if(videoOffer!=null)	{
			aar.addMediaComponentDescription(createMediaComponentDescription(2, videoOffer, videoAnswer, lIP, rIP, MediaType.VIDEO));
		}
	}
	
	private static MediaComponentDescription createMediaComponentDescription(long _id, Media _offer, Media _answer, String ipOfferer, String ipAnswerer, int _mediaType) {
		MediaComponentDescription mcd = new MediaComponentDescription(_id);
		//media
		MediaSubComponent msc = new MediaSubComponent();
		msc.setFlowNumber(new FlowNumber(1));
		msc.setMaxRequestedBandwidthDL(new MaxRequestedBandwidthDL(_offer.bandwidth));
		if(_answer!=null)
			msc.setMaxRequestedBandwidthUL(new MaxRequestedBandwidthUL(_answer.bandwidth));
		msc.setFlowUsage(new FlowUsage(FlowUsage.NO_INFORMATION));
		int rcvPort =-1;
		if( _answer!=null)
			rcvPort=_answer.recvPort;
		msc.setFlowDescriptionIn(new FlowDescription(ipfilter(ipOfferer, ipAnswerer, rcvPort, true)));
		rcvPort = _offer.recvPort;
		msc.setFlowDescriptionOut(new FlowDescription(ipfilter(ipOfferer, ipAnswerer, rcvPort, false)));
		mcd.addMediaSubComponent(msc);
		
		//RTCP
		MediaSubComponent rtcp = new MediaSubComponent();
		rtcp.setFlowNumber(new FlowNumber(2));
		rtcp.setMaxRequestedBandwidthDL(new MaxRequestedBandwidthDL(8000));
		if(_answer!=null)
			rtcp.setMaxRequestedBandwidthUL(new MaxRequestedBandwidthUL(8000));
		rtcp.setFlowUsage(new FlowUsage(FlowUsage.RTCP));
		rcvPort =-1;
		if( _answer!=null)
			rcvPort=_answer.recvPort;
		rtcp.setFlowDescriptionIn(new FlowDescription(ipfilter(ipOfferer, ipAnswerer, rcvPort+1, true)));
		rcvPort = _offer.recvPort;
		rtcp.setFlowDescriptionOut(new FlowDescription(ipfilter(ipOfferer, ipAnswerer, rcvPort+1, false)));
		mcd.addMediaSubComponent(rtcp);
		CodecData cdOffer = new CodecData("uplink\noffer\n"+_offer.toString());
		mcd.addCodecData(cdOffer);
		if(_answer==null)
			mcd.setFlowStatus(new FlowStatus(FlowStatus.DISABLED));
		else	{
			if(_offer.status!=Media.INACTIVE)
				mcd.setFlowStatus(new FlowStatus(FlowStatus.ENABLED));
			else
				mcd.setFlowStatus(new FlowStatus(FlowStatus.DISABLED));
			CodecData cdAnswer = new CodecData("downlink\nanswer\n"+_answer.toString());
			mcd.addCodecData(cdAnswer);
		}
		mcd.setMediaType(new MediaType(_mediaType));
		return mcd;
	}
	
	
	private static String ipfilter(String ipOfferer, String ipAnswerer, int rcvPort, boolean in){
		StringBuffer sbf = new StringBuffer();
		sbf.append("permit ");
		if(in)
			sbf.append("in");
		else
			sbf.append("out");
		sbf.append(" 17 from ");
		if(in)
			sbf.append(ipOfferer);
		else
		{
			if(ipAnswerer!=null)
				sbf.append(ipAnswerer);
			else
				sbf.append("0.0.0.0");
		}
		sbf.append(" to ");
		if(in)
		{
			if(ipAnswerer!=null)
				sbf.append(ipAnswerer);
			else
				sbf.append("0.0.0.0");
		}
		else
			sbf.append(ipOfferer);
		if(rcvPort>0)
		{
			sbf.append(" "+rcvPort);
		}
		return sbf.toString();
	}

}
