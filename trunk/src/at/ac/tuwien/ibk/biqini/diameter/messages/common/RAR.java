/* 
* RAR.java
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
package at.ac.tuwien.ibk.biqini.diameter.messages.common;

import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.AuthApplicationId;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ReAuthRequestType;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterRequest;

/**
 * The Re-Auth-Request (RAR), indicated by the Command-Code set to 258
 * and the message flags' 'R' bit set, may be sent by any server to the
 * access device that is providing session service, to request that the
 * user be re-authenticated and/or re-authorized.
 *
 * Message Format
 *
 *    <RAR>  ::= < Diameter Header: 258, REQ, PXY >
 *               < Session-Id >
 *               { Origin-Host }
 *               { Origin-Realm }
 *               { Destination-Realm }
 *               { Destination-Host }
 *               { Auth-Application-Id }
 *               { Re-Auth-Request-Type }
 *               [ User-Name ]
 *               [ Origin-State-Id ]
 *             * [ Proxy-Info ]
 *             * [ Route-Record ]
 *             * [ AVP ]
 *
 * RFC3588
 *
 * 
 * @author Christoph Egger
 *
 */
public class RAR extends DiameterRequest implements ICommonDiameterConstants{

	public final static int MESSAGE_CODE = RA_CODE;
	
	public RAR(int _ApplicationId) {
		super(MESSAGE_CODE, _ApplicationId);
	}
	
	/**
	 * Searches for the AuthApplicationId AVP inside a message
	 * @return the found AuthApplicationId AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public AuthApplicationId getAuthApplicationId() {
		return (AuthApplicationId)findAVP(AuthApplicationId.AVP_CODE);
	}
	/**
	 * sets the AuthApplicationId and overrides a existing one
	 * @param _destinationRealm	the new AuthApplicationId
	 * 
	 * @author mhappenhofer
	 */
	public void setAuthApplicationId(AuthApplicationId _authApplicationId)	{
		this.setSingleAVP( _authApplicationId);
	}
	
	/**
	 * Searches for the ReAuthRequestType AVP inside a message
	 * @return the found ReAuthRequestType AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public ReAuthRequestType getReAuthRequestType() {
		return (ReAuthRequestType)findAVP(ReAuthRequestType.AVP_CODE);
	}
	/**
	 * sets the ReAuthRequestType and overrides a existing one
	 * @param _reAuthRequestType the new ReAuthRequestType
	 * 
	 * @author mhappenhofer
	 */
	public void setReAuthRequestType(ReAuthRequestType _reAuthRequestType)	{
		this.setSingleAVP( _reAuthRequestType);
	}


}
