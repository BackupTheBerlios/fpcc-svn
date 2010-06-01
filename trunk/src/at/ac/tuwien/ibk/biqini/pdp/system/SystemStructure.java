/* 
* SystemStructure.java
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
package at.ac.tuwien.ibk.biqini.pdp.system;

import java.util.Vector;

import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxAAR;
import at.ac.tuwien.ibk.biqini.pdp.session.PDPSession;

/**
 * @author Christoph Egger
 *
 */
public class SystemStructure {

	private PEPContainer PEPList;
	private static SystemStructure INSTANCE = null;

	private SystemStructure(String cfgFile){
		PEPList = new PEPContainer(cfgFile);
	}

	public static SystemStructure getInstance(){
		/*if (INSTANCE == null){
			INSTANCE = new SystemStructure();
		}*/
		return INSTANCE;
	}
	
	public static void createInstance(String cfgFile){
		INSTANCE = new SystemStructure(cfgFile);
	}

	public void checkAndReserveBandwidth(RxAAR _req) throws NotEnoughBandwidthFreeException, NoCorrespondingPEPFoundException{
		Vector<PEP> pepList = PEPList.getPEPs(_req.getFramedIPAddress().getOctetString());
		if (pepList != null){
			for (int i = 0; i<pepList.size(); i++){
				pepList.elementAt(i).checkAndReserveBandwidth(_req);
			}
		}else throw new NoCorrespondingPEPFoundException("checkAvailability: no PEP for IP "+_req.getFramedIPAddress().getOctetString());

	}
	
	public void addSession(PDPSession _sess) throws NoCorrespondingPEPFoundException{
		Vector<PEP> pepList = PEPList.getPEPs(_sess.getFramedIPAddress());
		if (pepList != null){
			for (int i = 0; i<pepList.size(); i++){
				pepList.elementAt(i).registerSession(_sess);
			}
		}else throw new NoCorrespondingPEPFoundException("addSession: no PEP for IP "+_sess.getFramedIPAddress());

	}
	
	public Vector<PEP> getPEPsForIP(String _IPAddress){
		return PEPList.getPEPs(_IPAddress);
	}

	public void killSession(PDPSession _sess) {
		Vector<PEP> pepList = PEPList.getPEPs(_sess.getFramedIPAddress());
		if (pepList != null){
			for (int i = 0; i<pepList.size(); i++){
				pepList.elementAt(i).killSession(_sess.getSessionID());
			}
		}		
	}
	
	public String getFreeBandwidth(){
		return PEPList.getFreeBandwidth();
	}
	
	public void printConfig(){
		PEPList.printConfig();
	}
}