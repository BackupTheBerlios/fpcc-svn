/* 
* AAR.java
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

import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterRequest;

/**
 * The AA-Request (AAR), which is indicated by setting the Command-Code
 * field to 265 and the 'R' bit in the Command Flags field, is used to
 * request authentication and/or authorization for a given NAS user.
 * The type of request is identified through the Auth-Request-Type AVP
 * [BASE].  The recommended value for most RADIUS interoperabily
 * situations is AUTHORIZE_AUTHENTICATE.
 *
 * If Authentication is requested, the User-Name attribute SHOULD be
 * present, as well as any additional authentication AVPs that would
 * carry the password information.  A request for authorization SHOULD
 * only include the information from which the authorization will be
 * performed, such as the User-Name, Called-Station-Id, or Calling-
 * Station-Id AVPs.  All requests SHOULD contain AVPs uniquely
 * identifying the source of the call, such as Origin-Host and NAS-Port.
 * Certain networks MAY use different AVPs for authorization purposes.
 * A request for authorization will include some AVPs defined in section
 * 6.
 *
 * It is possible for a single session to be authorized first and then
 * for an authentication request to follow.
 *
 * This AA-Request message MAY be the result of a multi-round
 * authentication exchange, which occurs when the AA-Answer message is
 * received with the Result-Code AVP set to DIAMETER_MULTI_ROUND_AUTH.
 * A subsequent AAR message SHOULD be sent, with the User-Password AVP
 * that includes the user's response to the prompt, and MUST include any
 * State AVPs that were present in the AAA message.
 *
 * Message Format
 *    <AA-Request> ::= < Diameter Header: 265, REQ, PXY >
 *                     < Session-Id >
 *                     { Auth-Application-Id }
 *                     { Origin-Host }
 *                     { Origin-Realm }
 *                     { Destination-Realm }
 *                     { Auth-Request-Type }
 *                     [ Destination-Host ]
 *                     [ NAS-Identifier ]
 *                     [ NAS-IP-Address ]
 *                     [ NAS-IPv6-Address ]
 *                     [ NAS-Port ]
 *                     [ NAS-Port-Id ]
 *                     [ NAS-Port-Type ]
 *                     [ Origin-AAA-Protocol ]
 *                     [ Origin-State-Id ]
 *                     [ Port-Limit ]
 *                     [ User-Name ]
 *                     [ User-Password ]
 *                     [ Service-Type ]
 *                     [ State ]
 *                     [ Authorization-Lifetime ]
 *                     [ Auth-Grace-Period ]
 *                     [ Auth-Session-State ]
 *                     [ Callback-Number ]
 *                     [ Called-Station-Id ]
 *                     [ Calling-Station-Id ]
 *                     [ Originating-Line-Info ]
 *                     [ Connect-Info ]
 *                     [ CHAP-Auth ]
 *                     [ CHAP-Challenge ]
 *                   * [ Framed-Compression ]
 *                     [ Framed-Interface-Id ]
 *                     [ Framed-IP-Address ]
 *                   * [ Framed-IPv6-Prefix ]
 *                     [ Framed-IP-Netmask ]
 *                     [ Framed-MTU ]
 *                     [ Framed-Protocol ]
 *                     [ ARAP-Password ]
 *                     [ ARAP-Security ]
 *                   * [ ARAP-Security-Data ]
 *                   * [ Login-IP-Host ]
 *                   * [ Login-IPv6-Host ]
 *                     [ Login-LAT-Group ]
 *                     [ Login-LAT-Node ]
 *                     [ Login-LAT-Port ]
 *                     [ Login-LAT-Service ]
 *                   * [ Tunneling ]
 *                   * [ Proxy-Info ]
 *                   * [ Route-Record ]
 *                   * [ AVP ]
 *
 * RFC 4005
 *
 * 
 * @author Christoph Egger
 *
 */
public class AAR extends DiameterRequest implements ICommonDiameterConstants{
	
	public final static int MESSAGE_CODE = AA_CODE;
	
	
	public AAR(int _ApplicationId){
		super(MESSAGE_CODE, _ApplicationId);
	}
	public void setAuthApplicationID(){
		
	}
}
