/* 
* DestinationRealm.java
* Marco Happenhofer
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
package at.ac.tuwien.ibk.biqini.diameter.avp.ietf;

import at.ac.tuwien.ibk.biqini.diameter.IIETFConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.DiameterIdentity;

/**
 * The Destination-Realm AVP (AVP Code 283) is of type DiameterIdentity,
 * and contains the realm the message is to be routed to.  The
 * Destination-Realm AVP MUST NOT be present in Answer messages.
 * Diameter Clients insert the realm portion of the User-Name AVP.
 * Diameter servers initiating a request message use the value of the
 * Origin-Realm AVP from a previous message received from the intended
 * target host (unless it is known a priori).  When present, the
 * Destination-Realm AVP is used to perform message routing decisions.
 *
 * Request messages whose ABNF does not list the Destination-Realm AVP
 * as a mandatory AVP are inherently non-routable messages.
 *
 * This AVP SHOULD be placed as close to the Diameter header as
 * possible.
 * 
 * RFC 3588
 * 
 * @author mhappenhofer
 *
 */
public class DestinationRealm extends DiameterIdentity implements IIETFConstants{

	public static final int AVP_CODE = 283;

	public DestinationRealm() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @param octetString
	 */
	public DestinationRealm(String octetString) {
		super(AVP_CODE,true,VENDOR_ID);
		setOctetString(octetString);
	}

}
