/* 
* CCR.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.CalledStationId;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SubscriptionId;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.TerminationCause;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.UserEquipmentInfo;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterRequest;

/**
 * The Credit-Control-Request message (CCR) is indicated by the
 * command-code field being set to 272 and the 'R' bit being set in the
 * Command Flags field.  It is used between the Diameter credit-control
 * client and the credit-control server to request credit authorization
 * for a given service.
 * 
 * The Auth-Application-Id MUST be set to the value 4, indicating the
 * Diameter credit-control application.
 * 
 * Message Format
 * 
 *    <Credit-Control-Request> ::= < Diameter Header: 272, REQ, PXY >
 *                                 < Session-Id >
 *                                 { Origin-Host }
 *                                 { Origin-Realm }
 *                                 { Destination-Realm }
 *                                 { Auth-Application-Id }
 *                                 { Service-Context-Id }
 *                                 { CC-Request-Type }
 *                                 { CC-Request-Number }
 *                                 [ Destination-Host ]
 *                                 [ User-Name ]
 *                                 [ CC-Sub-Session-Id ]
 *                                 [ Acct-Multi-Session-Id ]
 *                                 [ Origin-State-Id ]
 *                                 [ Event-Timestamp ]
 *                                *[ Subscription-Id ]
 *                                 [ Service-Identifier ]
 *                                 [ Termination-Cause ]
 *                                 [ Requested-Service-Unit ]
 *                                 [ Requested-Action ]
 *                                *[ Used-Service-Unit ]
 *                                 [ Multiple-Services-Indicator ]
 *                                *[ Multiple-Services-Credit-Control ]
 *                                *[ Service-Parameter-Info ]
 *                                 [ CC-Correlation-Id ]
 *                                 [ User-Equipment-Info ]
 *                                *[ Proxy-Info ]
 *                                *[ Route-Record ]
 *                                *[ AVP ]
 * 
 * RFC4006
 * 
 * @author mhappenhofer
 *
 */
public class CCR extends DiameterRequest implements ICommonDiameterConstants {

	public static final int MESSAGE_CODE = CC_CODE;
	
	public CCR(int applicationId) {
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

	/**
	 * Searches for the SubscriptionId AVP inside a message
	 * @return the found SubscriptionId AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public SubscriptionId getSubscriptionId() {
		return (SubscriptionId)findAVP(SubscriptionId.AVP_CODE);
	}
	/**
	 * sets the SubscriptionId and overrides a existing one
	 * @param _destinationRealm	the new SubscriptionId
	 * 
	 * @author mhappenhofer
	 */
	public void setSubscriptionId(SubscriptionId _SubscriptionId)	{
		this.setSingleAVP( _SubscriptionId);
	}
	
	/**
	 * Searches for the TerminationCause AVP inside a message
	 * @return the found TerminationCause AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public TerminationCause getTerminationCause() {
		return (TerminationCause)findAVP(TerminationCause.AVP_CODE);
	}
	/**
	 * sets the TerminationCause and overrides a existing one
	 * @param _destinationRealm	the new TerminationCause
	 * 
	 * @author mhappenhofer
	 */
	public void setTerminationCause(TerminationCause _TerminationCause)	{
		this.setSingleAVP( _TerminationCause);
	}
	/**
	 * Searches for the UserEquipmentInfo AVP inside a message
	 * @return the found UserEquipmentInfo AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public UserEquipmentInfo getUserEquipmentInfo() {
		return (UserEquipmentInfo)findAVP(UserEquipmentInfo.AVP_CODE);
	}
	/**
	 * sets the UserEquipmentInfo and overrides a existing one
	 * @param _destinationRealm	the new UserEquipmentInfo
	 * 
	 * @author mhappenhofer
	 */
	public void setUserEquipmentInfo(UserEquipmentInfo _UserEquipmentInfo)	{
		this.setSingleAVP( _UserEquipmentInfo);
	}
	/**
	 * Searches for the CalledStationId AVP inside a message
	 * @return the found CalledStationId AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public CalledStationId getCalledStationId() {
		return (CalledStationId)findAVP(CalledStationId.AVP_CODE);
	}
	/**
	 * sets the CalledStationId and overrides a existing one
	 * @param _destinationRealm	the new CalledStationId
	 * 
	 * @author mhappenhofer
	 */
	public void setCalledStationId(CalledStationId _CalledStationId)	{
		this.setSingleAVP( _CalledStationId);
	}
	
}
