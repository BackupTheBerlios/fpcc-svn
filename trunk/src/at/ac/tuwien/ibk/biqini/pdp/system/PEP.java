/* 
* PEP.java
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
package at.ac.tuwien.ibk.biqini.pdp.system;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AcceptableServiceInfo;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthUL;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxAAR;
import at.ac.tuwien.ibk.biqini.pdp.session.PDPSession;

/**
 * @author Christoph Egger
 *
 */
public class PEP {
	private String inetAddress;
	private String realm;
	private Hashtable<String, PDPSession> sessions;
	private final long maxBandwidthUP;// = 20000000;
	private final long maxBandwidthDOWN;// = 20000000;
	
	public PEP(String _addr, String _realm, long _upbw, long _dobw){
		sessions = new Hashtable<String, PDPSession>();
		inetAddress = _addr;
		realm = _realm;
		maxBandwidthDOWN = _dobw;
		maxBandwidthUP = _upbw;
	}
	
	public void registerSession(PDPSession _pdpSession){
		sessions.put(_pdpSession.getSessionID(), _pdpSession);
	}
	
	public String getAddress(){
		return inetAddress;
	}
	public String getRealm(){
		return realm;
	}
	
	//TODO zurï¿½ckliefern von OoSClassIdentifier
	public void checkAndReserveBandwidth(RxAAR _req) throws NotEnoughBandwidthFreeException{
		long currentBandwidthUP = 0;
		long currentBandwidthDOWN = 0;
		long acceptableBWUP = 0;
		long acceptableBWDOWN = 0;
		//check what we have until now
		Iterator<PDPSession> pdpIt = sessions.values().iterator();
		while (pdpIt.hasNext()){
			PDPSession sess = pdpIt.next();
			currentBandwidthDOWN += sess.getCurrentBandwidthDOWN();
			currentBandwidthUP += sess.getCurrentBandwidthUP();
		}
		//check if session already exists
		String sessionID = _req.getSessionId().getUTF8String();
		PDPSession sess = sessions.get(sessionID);
		if (sess == null){
			//if no, create session, add bandwidth value 
			sess = new PDPSession(sessionID, _req.getFramedIPAddress().getOctetString(), _req.getOriginHost().getOctetString());
			sessions.put(sessionID, sess);
		}else{
			//if yes, subtract bandwidth included and add new value
			currentBandwidthDOWN -= sess.getCurrentBandwidthDOWN();
			currentBandwidthUP -= sess.getCurrentBandwidthUP();
			acceptableBWDOWN = maxBandwidthDOWN - currentBandwidthDOWN;
			acceptableBWUP = maxBandwidthUP - currentBandwidthUP;
		}
		currentBandwidthDOWN += sess.getCurrentBandwidthDOWN();
		currentBandwidthUP += sess.getCurrentBandwidthUP();
		//check if we still have enough
		if (currentBandwidthUP >= maxBandwidthUP) {
			sess.setCurrentBandwidthDOWN(0);
			throw new NotEnoughBandwidthFreeException("Not enough UP bandwith", getAcceptableServiceInfo(acceptableBWUP, acceptableBWDOWN));
		}
		if (currentBandwidthDOWN >= maxBandwidthDOWN){
			sess.setCurrentBandwidthUP(0);
			throw new NotEnoughBandwidthFreeException("Not enough DOWN bandwith", getAcceptableServiceInfo(acceptableBWUP, acceptableBWDOWN));		
		}
	}
	
	public void killSession(String _sessionID){
		sessions.remove(_sessionID);
	}
	
	private AcceptableServiceInfo getAcceptableServiceInfo(long _freeBandwidthUP, long _freeBandwidthDOWN){
		AcceptableServiceInfo acceptableServiceInfo = new AcceptableServiceInfo();
		acceptableServiceInfo.setMaxRequestedBandwidthDL(new MaxRequestedBandwidthDL(_freeBandwidthDOWN));
		acceptableServiceInfo.setMaxRequestedBandwidthUL(new MaxRequestedBandwidthUL(_freeBandwidthUP));
		return acceptableServiceInfo;
	}

	public String getFreeBandwidth() {
		long currBWDown = 0;
		long currBWUp = 0;
		Enumeration<PDPSession> sessEnum = sessions.elements();
		while (sessEnum.hasMoreElements()){
			PDPSession se = sessEnum.nextElement();
			currBWDown+= se.getCurrentBandwidthDOWN();
			currBWUp += se.getCurrentBandwidthUP();
		}
		long freeBWD = maxBandwidthDOWN-currBWDown;
		long freeBWU = maxBandwidthUP-currBWUp;
		String returnString = "PEP "+inetAddress+": "+freeBWD+"D "+freeBWU+"U";
		return returnString;
	}

	/**
	 * @return the maxBandwidthUP
	 */
	public long getMaxBandwidthUP() {
		return maxBandwidthUP;
	}

	/**
	 * @return the maxBandwidthDOWN
	 */
	public long getMaxBandwidthDOWN() {
		return maxBandwidthDOWN;
	}
}
	

