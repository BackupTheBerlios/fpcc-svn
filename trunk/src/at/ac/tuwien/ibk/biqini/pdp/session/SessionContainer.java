/* 
* SessionContainer.java
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

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author Christoph Egger
 *
 */
public class SessionContainer {
	private Hashtable<String, PDPSession> sessionHashtable;
	
	public SessionContainer(){
		sessionHashtable = new Hashtable<String, PDPSession>();
	}
	public void addSession(PDPSession _session){
		
		sessionHashtable.put(_session.getSessionID(), _session);
	}
	public PDPSession getSession(String _sessionID){
		 return sessionHashtable.get(_sessionID);
	}
	public String getAFToSession(String _sessionID){
		return sessionHashtable.get(_sessionID).getFqdnOfAF();
	}
	
	public String toString(){
		int n = 0;
		String resultString = "";
		Enumeration<PDPSession> en = sessionHashtable.elements();
		while (en.hasMoreElements()){
			PDPSession sess = en.nextElement();
			resultString += "("+(n+1)+")"+sess.toString()+"\n";
			n++;
		}
		resultString += "Total count of sessions: "+n;
		return resultString;
	}
	public PDPSession killSession(int _which) {
		int n = 0;
		Enumeration<String> en = sessionHashtable.keys();
		while (en.hasMoreElements()){
			String sessID = en.nextElement();
			if (n==_which){
				PDPSession sess = sessionHashtable.get(sessID);
				sessionHashtable.remove(sessID);
				return sess;
			}
			n++;
		}
		return null;
		
	}
	
	public int getSize(){
		return sessionHashtable.size();
	}
	

}
