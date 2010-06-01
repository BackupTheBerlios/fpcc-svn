/* 
* AAA.java
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

import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterAnswer;

/**
 * The AA-Answer (AAA) message is indicated by setting the Command-Code
 * field to 265 and clearing the 'R' bit in the Command Flags field.  It
 * is sent in response to the AA-Request (AAR) message.  If
 * authorization was requested, a successful response will include the
 * authorization AVPs appropriate for the service being provided, as
 * defined in section 6.
 *
 * For authentication exchanges requiring more than a single round trip,
 * the server MUST set the Result-Code AVP to DIAMETER_MULTI_ROUND_AUTH.
 * An AAA message with this result code MAY include one Reply-Message or
 * more and MAY include zero or one State AVPs.
 *
 * If the Reply-Message AVP was present, the network access server
 * SHOULD send the text to the user's client to display to the user,
 * instructing the client to prompt the user for a response.  For
 * example, this capability can be achieved in PPP via PAP.  If the
 * access client is unable to prompt the user for a new response, it
 * MUST treat the AA-Answer (AAA) with the Reply-Message AVP as an error
 * and deny access.
 *
 * Message Format
 *
 *    <AA-Answer> ::= < Diameter Header: 265, PXY >
 *                    < Session-Id >
 *                    { Auth-Application-Id }
 *                    { Auth-Request-Type }
 *                    { Result-Code }
 *                    { Origin-Host }
 *                    { Origin-Realm }
 *                    [ User-Name ]
 *                    [ Service-Type ]
 *                  * [ Class ]
 *                  * [ Configuration-Token ]
 *                    [ Acct-Interim-Interval ]
 *                    [ Error-Message ]
 *                    [ Error-Reporting-Host ]
 *                  * [ Failed-AVP ]
 *                    [ Idle-Timeout ]
 *                    [ Authorization-Lifetime ]
 *                    [ Auth-Grace-Period ]
 *                    [ Auth-Session-State ]
 *                    [ Re-Auth-Request-Type ]
 *                    [ Multi-Round-Time-Out ]
 *                    [ Session-Timeout ]
 *                    [ State ]
 *                  * [ Reply-Message ]
 *                    [ Origin-AAA-Protocol ]
 *                    [ Origin-State-Id ]
 *                  * [ Filter-Id ]
 *                    [ Password-Retry ]
 *                    [ Port-Limit ]
 *                    [ Prompt ]
 *                    [ ARAP-Challenge-Response ]
 *                    [ ARAP-Features ]
 *                    [ ARAP-Security ]
 *                  * [ ARAP-Security-Data ]
 *                    [ ARAP-Zone-Access ]
 *                    [ Callback-Id ]
 *                    [ Callback-Number ]
 *                    [ Framed-Appletalk-Link ]
 *                  * [ Framed-Appletalk-Network ]
 *                    [ Framed-Appletalk-Zone ]
 *                  * [ Framed-Compression ]
 *                    [ Framed-Interface-Id ]
 *                    [ Framed-IP-Address ]
 *                  * [ Framed-IPv6-Prefix ]
 *                    [ Framed-IPv6-Pool ]
 *                  * [ Framed-IPv6-Route ]
 *                    [ Framed-IP-Netmask ]
 *                  * [ Framed-Route ]
 *                    [ Framed-Pool ]
 *                    [ Framed-IPX-Network ]
 *                    [ Framed-MTU ]
 *                    [ Framed-Protocol ]
 *                    [ Framed-Routing ]
 *                  * [ Login-IP-Host ]
 *                  * [ Login-IPv6-Host ]
 *                    [ Login-LAT-Group ]
 *                    [ Login-LAT-Node ]
 *                    [ Login-LAT-Port ]
 *                    [ Login-LAT-Service ]
 *                    [ Login-Service ]
 *                    [ Login-TCP-Port ]
 *                  * [ NAS-Filter-Rule ]
 *                  * [ QoS-Filter-Rule ]
 *                  * [ Tunneling ]
 *                  * [ Redirect-Host ]
 *                    [ Redirect-Host-Usage ]
 *                    [ Redirect-Max-Cache-Time ]
 *                  * [ Proxy-Info ]
 *                  * [ AVP ]
 *
 * RFC 4005
 *
 * @author Christoph Egger
 *
 */
public class AAA extends DiameterAnswer implements ICommonDiameterConstants{
	
	public final static int MESSAGE_CODE = AA_CODE;
	
	
	public AAA(int _ApplicationId){
		super(MESSAGE_CODE, _ApplicationId);
	}
}
