/* 
* RxSTR.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.AuthApplicationId;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.TerminationCause;
import at.ac.tuwien.ibk.biqini.diameter.messages.common.STR;

/**
 * The STR command, indicated by the Command-Code field set to 275 and the 'R' bit set in the Command Flags field, is sent by the AF to inform the PCRF that an established session shall be terminated.
 * Message Format:
 * <ST-Request> ::= < Diameter Header: 275, REQ, PXY >
 * 				 < Session-Id >
 * 				 { Origin-Host }
 * 				 { Origin-Realm }
 * 				 { Destination-Realm }
 * 				 { Auth-Application-Id }
 * 				 { Termination-Cause }
 * 				 [ Destination-Host ]
 * 				*[ Class ]
 * 				 [ Origin-State-Id ]
 * 				*[ Proxy-Info ]
 * 				*[ Route-Record ]
 * 				*[ AVP ]
 * 
 *  TS 29.214
 *  
 * @author Christoph Egger
 *
 */
public class RxSTR extends STR implements I3GPPRXInterface{

	public RxSTR() {
		super(APPLICATION_ID);
		this.setAuthApplicationId(new AuthApplicationId(RxSTR.APPLICATION_ID));
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
	 * @param _TerminationCause	the new TerminationCause
	 * 
	 * @author mhappenhofer
	 */
	public void setTerminationCause(TerminationCause _TerminationCause)	{
		this.setSingleAVP( _TerminationCause);
	}
}
