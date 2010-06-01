/* 
* RAA.java
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
 * The Re-Auth-Answer (RAA), indicated by the Command-Code set to 258
 * and the message flags' 'R' bit clear, is sent in response to the RAR.
 * The Result-Code AVP MUST be present, and indicates the disposition of
 * the request.
 *
 * A successful RAA message MUST be followed by an application-specific
 * authentication and/or authorization message.
 *
 * Message Format
 *
 *    <RAA>  ::= < Diameter Header: 258, PXY >
 *               < Session-Id >
 *               { Result-Code }
 *               { Origin-Host }
 *               { Origin-Realm }
 *               [ User-Name ]
 *               [ Origin-State-Id ]
 *               [ Error-Message ]
 *               [ Error-Reporting-Host ]
 *             * [ Failed-AVP ]
 *             * [ Redirect-Host ]
 *               [ Redirect-Host-Usage ]
 *               [ Redirect-Host-Cache-Time ]
 *             * [ Proxy-Info ]
 *             * [ AVP ]
 *
 * RFC3588
 *
 * 
 * @author Christoph Egger
 *
 */
public class RAA extends DiameterAnswer implements ICommonDiameterConstants{

	public final static int MESSAGE_CODE = RA_CODE;
	
	public RAA(int _ApplicationId) {
		super(MESSAGE_CODE, _ApplicationId);
	}
}
