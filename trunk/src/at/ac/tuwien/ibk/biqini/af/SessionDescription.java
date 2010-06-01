/* 
* SessionDescription.java
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
package at.ac.tuwien.ibk.biqini.af;

import at.ac.tuwien.ibk.biqini.diameter.avp.base.DiameterIdentity;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.FramedIPAddress;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SessionID;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SubscriptionId;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SubscriptionIdData;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SubscriptionIdType;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ServiceInfoStatus;

public class SessionDescription {
	private SessionID sessionID;
	private SubscriptionId subscriptionId;
	private ServiceInfoStatus serviceInfoStatus;
	
	//private MediaComponentDescription audio;
	//private MediaComponentDescription video;
	private FramedIPAddress localIP;
	private FramedIPAddress remoteIP;
	
	private SSDP offer;
	private SSDP answer;
	
	private boolean terminate;
	/**
	 * 
	 */
	public SessionDescription(String fqdn, String sipURI) {
		super();
		this.sessionID = new SessionID(new DiameterIdentity(fqdn),sipURI);
		this.subscriptionId = new SubscriptionId(new SubscriptionIdType(SubscriptionIdType.END_USER_SIP_URI),new SubscriptionIdData(sipURI));
		this.serviceInfoStatus = new ServiceInfoStatus(ServiceInfoStatus.PRELIMINARY_SERVICE_INFORMATION);
		this.terminate = false;
	}
	
	public void setServiceInfoStatus(int _status)	{
		this.serviceInfoStatus.setEnumerated(_status);
	}
	
	public void setSDPOffer(SSDP _offer)	{
		this.offer = _offer;
	}
	
	public void setSDPAnswer(SSDP _answer)	{
		this.answer = _answer;
	}
	
	public void terminate()	{
		this.terminate = true;
	}
	
	public boolean terminated()	{
		return terminate;
	}
	
	public SessionID getSessionID()	{
		return this.sessionID;
	}

	public boolean answered()	{
		return answer != null;
	}
	
	public boolean offered()	{
		return offer != null;
	}

	/**
	 * @return the offer
	 */
	public SSDP getOffer() {
		return offer;
	}

	/**
	 * @return the answer
	 */
	public SSDP getAnswer() {
		return answer;
	}

	/**
	 * @param offer the offer to set
	 */
	public void setOffer(SSDP offer) {
		this.offer = offer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(SSDP answer) {
		this.answer = answer;
	}

	public ServiceInfoStatus getServiceInfoStatus() {
		return serviceInfoStatus;
	}

	public SubscriptionId getSubscriptionId() {
		return subscriptionId;
	}

	public FramedIPAddress getLocalIP() {
		return localIP;
	}

	public void setLocalIP(FramedIPAddress localIP) {
		this.localIP = localIP;
	}

	public FramedIPAddress getRemoteIP() {
		return remoteIP;
	}

	public void setRemoteIP(FramedIPAddress remoteIP) {
		this.remoteIP = remoteIP;
	}
	
 
}
