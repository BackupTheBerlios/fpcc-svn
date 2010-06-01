/* 
* Command.java
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
package at.ac.tuwien.ibk.biqini.af;

import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.DestinationHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.DestinationRealm;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.FramedIPAddress;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginRealm;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.TerminationCause;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowNumber;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowStatus;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowUsage;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthUL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaComponentDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaSubComponent;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaType;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ServiceInfoStatus;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxAAR;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxSTR;
import de.fhg.fokus.diameter.DiameterPeer.DiameterPeer;
import de.fhg.fokus.diameter.DiameterPeer.transaction.TransactionListener;

/**
 * @author mhappenhofer
 *
 */
public class Command {
	
	public static final int PREPRELIMINARY = 0;
	public static final int FINAL = 1;
	public static final int TERMINATE = 2;

	private SessionDescription sd;
	private int command;
	/**
	 * 
	 */
	public Command(SessionDescription _sd, int action) {
		this.sd = _sd;
		this.command = action;
	}
	
	public void execute(DiameterPeer _diameterPeer, TransactionListener _transactionListener)	{
		RxAAR aar= null;
		RxSTR str = null;
		switch(command)
		{
		case PREPRELIMINARY:
			System.out.println("Can not commit("+sd.getSessionID()+")!");
			aar = getRxAAR(_diameterPeer);
			aar.setServiceInfoStatus(new ServiceInfoStatus(ServiceInfoStatus.PRELIMINARY_SERVICE_INFORMATION));
			System.out.println(aar.toString());
			_diameterPeer.sendRequestTransactional("127.0.0.1", aar, _transactionListener);
			break;
		case FINAL:
			System.out.println("Can commit("+sd.getSessionID()+")!");
			aar = getRxAAR(_diameterPeer);
			aar.setServiceInfoStatus(new ServiceInfoStatus(ServiceInfoStatus.FINAL_SERVICE_INFORMATION));
			System.out.println(aar.toString());
			_diameterPeer.sendRequestTransactional(aar, _transactionListener);
			break;
		case TERMINATE:
			System.out.println("Tertminate("+sd.getSessionID()+")!");
			str = getRxSTR(_diameterPeer);
			System.out.println(str.toString());
			_diameterPeer.sendRequestTransactional(str, _transactionListener);
			break;
		default:
			
		}
	}
	
	public RxSTR getRxSTR(DiameterPeer _diameterPeer)	{
		RxSTR str = new RxSTR();
		str.hopByHopID=_diameterPeer.getNextHopByHopId();
		str.endToEndID=_diameterPeer.getNextEndToEndId();
		str.setOriginHost(new OriginHost(_diameterPeer.FQDN));
		str.setOriginRealm(new OriginRealm(_diameterPeer.Realm));
		str.setDestinationHost(new DestinationHost("192.168.1.51"));
		str.setDestinationRealm(new DestinationRealm("open-ims.test"));
		str.setSessionId(sd.getSessionID());
		str.setTerminationCause(new TerminationCause(TerminationCause.DIAMETER_LOGOUT));
		return str;
	}
	
	public RxAAR getRxAAR(DiameterPeer _diameterPeer)	{
		RxAAR aar = new RxAAR();
		aar.hopByHopID=_diameterPeer.getNextHopByHopId();
		aar.endToEndID=_diameterPeer.getNextEndToEndId();
		aar.setOriginHost(new OriginHost(_diameterPeer.FQDN));
		aar.setOriginRealm(new OriginRealm(_diameterPeer.Realm));
		aar.setDestinationHost(new DestinationHost("192.168.1.51"));
		aar.setDestinationRealm(new DestinationRealm("open-ims.test"));
		aar.setSessionId(sd.getSessionID());
		aar.setFramedIPAddress(new FramedIPAddress(sd.getOffer().ipAddress));
		aar.addSubscriptionId(sd.getSubscriptionId());
		SSDP offer = sd.getOffer();
		SSDP answer = sd.getAnswer();
		String ipA = null;
		Media aAudio = null;
		Media aVideo = null;
		if(answer!=null)
		{	
			ipA = answer.ipAddress;
			aAudio = answer.audio;
			aVideo = answer.video;
		}
		String ipO = offer.ipAddress;
		Media oAudio = offer.audio;
		Media oVideo = offer.video;
		MediaComponentDescription audioMCD = getMediaComponentDescriptionByMedia(1, oAudio, aAudio, ipO, ipA,MediaType.AUDIO);
		if(audioMCD != null)
			aar.addMediaComponentDescription(audioMCD);
		MediaComponentDescription videoMCD = getMediaComponentDescriptionByMedia(1, oVideo, aVideo, ipO, ipA,MediaType.VIDEO);
		if(videoMCD != null)
			aar.addMediaComponentDescription(videoMCD);
		return aar;
	}
	
	public MediaComponentDescription getMediaComponentDescriptionByMedia(long _id, Media _offer, Media _answer, String ipOfferer, String ipAnswerer, int _mediaType)	{
		MediaComponentDescription mcd = new MediaComponentDescription(_id);
		if(_offer!=null)
		{
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
			if(_answer==null)
				mcd.setFlowStatus(new FlowStatus(FlowStatus.DISABLED));
			else
				mcd.setFlowStatus(new FlowStatus(FlowStatus.ENABLED));
			mcd.setMediaType(new MediaType(_mediaType));
		} else 
			mcd = null;
			
		return mcd;
	}
	
	public String ipfilter(String ipOfferer, String ipAnswerer, int rcvPort, boolean in){
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
