/* 
* DiameterRequest.java
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
package at.ac.tuwien.ibk.biqini.diameter.messages;

import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.AuthApplicationId;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.DestinationHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.DestinationRealm;
import de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage;

/**
 * @author Christoph Egger
 *
 */
public class DiameterRequest extends DiameterMessage {
	
	public static final boolean REQUEST_FLAG = true; 
	
	public DiameterRequest(int _CommandCode, int _ApplicationId) {
		super(_CommandCode, true, _ApplicationId);
	}
	
	public DiameterRequest(DiameterMessage _msg){
		super(_msg.commandCode, _msg.flagRequest, _msg.flagProxiable, _msg.applicationID, _msg.hopByHopID, _msg.endToEndID);
		this.avps = _msg.avps;
	}
	
	/**
	 * Searches for the Destination-Host AVP inside a message
	 * @return the found Destination-Host AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public DestinationHost getDestinationHost() {
		return (DestinationHost)findAVP(DestinationHost.AVP_CODE);
	}
	/**
	 * sets the Destination-Host and overrides a existing one
	 * @param _destinationHost	the new DestinationHost
	 * 
	 * @author mhappenhofer
	 */
	public void setDestinationHost(DestinationHost _destinationHost)	{
		this.setSingleAVP( _destinationHost);
	}
	
	/**
	 * Searches for the Destination-Realm AVP inside a message
	 * @return the found Destination-Realm AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public DestinationRealm getDestinationRealm() {
		return (DestinationRealm)findAVP(DestinationRealm.AVP_CODE);
	}
	/**
	 * sets the Destination-Realm and overrides a existing one
	 * @param _destinationRealm	the new DestinationRealm
	 * 
	 * @author mhappenhofer
	 */
	public void setDestinationRealm(DestinationRealm _destinationRealm)	{
		this.setSingleAVP( _destinationRealm);
	}
	
	/**
	 * Searches for the AuthApplicationId AVP inside a message
	 * @return the found AuthApplicationId AVP, null if not found
	 * 
	 * @author cegger
	 */
	public AuthApplicationId getAuthApplicationId() {
		return (AuthApplicationId)findAVP(AuthApplicationId.AVP_CODE);
	}
	/**
	 * sets the AuthApplicationId and overrides a existing one
	 * @param _authApplicationId the new AuthApplicationId
	 * 
	 * @author cegger
	 */
	public void setAuthApplicationId(AuthApplicationId _authApplicationId)	{
		this.setSingleAVP(_authApplicationId);
	}
}
