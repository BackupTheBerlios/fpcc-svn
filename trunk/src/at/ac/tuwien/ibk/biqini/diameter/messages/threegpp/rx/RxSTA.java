/* 
* RxSTA.java
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
package at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx;

import at.ac.tuwien.ibk.biqini.diameter.messages.common.STA;

/**
 * The STA command, indicated by the Command-Code field set to 275 and the 'R' bit cleared in the Command Flags field, is sent by the PCRF to the AF in response to the STR command.
 * Message Format:
 * <ST-Answer>  ::= < Diameter Header: 275, PXY >
 * 				 < Session-Id >
 * 				 { Origin-Host }
 * 				 { Origin-Realm }
 * 				 [ Result-Code ]
 * 				 [ Experimental-Result ]
 * 				 [ Error-Message ]
 * 				 [ Error-Reporting-Host ]
 * 				*[ Failed-AVP ]
 * 				 [ Origin-State-Id ]
 * 				*[ Class ]
 * 				*[ Redirect-Host ]
 * 				 [ Redirect-Host-Usage ]
 * 				 [ Redirect-Max-Cache-Time ]
 * 				*[ Proxy-Info ]
 * 				*[ AVP ]
 * 
 * TS 29.214
 *   
 * @author Christoph Egger
 *
 */
public class RxSTA extends STA implements I3GPPRXInterface{

	public RxSTA() {
		super(APPLICATION_ID);
	}
}
