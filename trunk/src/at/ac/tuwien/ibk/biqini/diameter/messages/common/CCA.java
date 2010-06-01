/* 
* CCA.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.CCRequestNumber;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.CCRequestType;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterAnswer;

/**
 * The Credit-Control-Answer message (CCA) is indicated by the command-
 * code field being set to 272 and the 'R' bit being cleared in the
 * Command Flags field.  It is used between the credit-control server
 * and the Diameter credit-control client to acknowledge a Credit-
 * Control-Request command.
 *
 *  Message Format
 *
 *    <Credit-Control-Answer> ::= < Diameter Header: 272, PXY >
 *                                < Session-Id >
 *                                { Result-Code }
 *                                { Origin-Host }
 *                                { Origin-Realm }
 *                                { Auth-Application-Id }
 *                                { CC-Request-Type }
 *                                { CC-Request-Number }
 *                                [ User-Name ]
 *                                [ CC-Session-Failover ]
 *                                [ CC-Sub-Session-Id ]
 *                                [ Acct-Multi-Session-Id ]
 *                                [ Origin-State-Id ]
 *                                [ Event-Timestamp ]
 *                                [ Granted-Service-Unit ]
 *                               *[ Multiple-Services-Credit-Control ]
 *                                [ Cost-Information]
 *                                [ Final-Unit-Indication ]
 *                                [ Check-Balance-Result ]
 *                                [ Credit-Control-Failure-Handling ]
 *                                [ Direct-Debiting-Failure-Handling ]
 *                                [ Validity-Time]
 *                               *[ Redirect-Host]
 *                                [ Redirect-Host-Usage ]
 *                                [ Redirect-Max-Cache-Time ]
 *                               *[ Proxy-Info ]
 *                               *[ Route-Record ]
 *                               *[ Failed-AVP ]
 *                               *[ AVP ]
 *
 * RFC4006
 *
 * @author mhappenhofer
 *
 */
public class CCA extends DiameterAnswer implements ICommonDiameterConstants {

	public static final int MESSAGE_CODE = CC_CODE;
	
	public CCA(int applicationId) {
		super(MESSAGE_CODE, applicationId);
		
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
	 * Searches for the CCRequestType AVP inside a message
	 * @return the found CCRequestType AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public CCRequestType getCCRequestType() {
		return (CCRequestType)findAVP(CCRequestType.AVP_CODE);
	}
	/**
	 * sets the CCRequestType and overrides a existing one
	 * @param _destinationRealm	the new CCRequestType
	 * 
	 * @author mhappenhofer
	 */
	public void setCCRequestType(CCRequestType _CCRequestType)	{
		this.setSingleAVP( _CCRequestType);
	}
	
	/**
	 * Searches for the CCRequestNumber AVP inside a message
	 * @return the found CCRequestNumber AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public CCRequestNumber getCCRequestNumber() {
		return (CCRequestNumber)findAVP(CCRequestNumber.AVP_CODE);
	}
	/**
	 * sets the CCRequestNumber and overrides a existing one
	 * @param _destinationRealm	the new CCRequestNumber
	 * 
	 * @author mhappenhofer
	 */
	public void setCCRequestNumber(CCRequestNumber _CCRequestNumber)	{
		this.setSingleAVP( _CCRequestNumber);
	}
}
