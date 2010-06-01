/* 
* OriginHost.java
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
 * The Origin-Host AVP (AVP Code 264) is of type DiameterIdentity, and
 * MUST be present in all Diameter messages.  This AVP identifies the
 * endpoint that originated the Diameter message.  Relay agents MUST NOT
 * modify this AVP.
 *
 * The value of the Origin-Host AVP is guaranteed to be unique within a
 * single host.
 *
 * Note that the Origin-Host AVP may resolve to more than one address as
 * the Diameter peer may support more than one address.
 *
 * This AVP SHOULD be placed as close to the Diameter header as
 * possible. 6.10
 * 
 * RFC 3588
 * 
 * @author mhappenhofer
 *
 */
public class OriginHost extends DiameterIdentity implements IIETFConstants{

	public static final int AVP_CODE = 264;

	public OriginHost() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @param octetString
	 */
	public OriginHost(String octetString) {
		super(AVP_CODE,true,VENDOR_ID);
		this.setOctetString(octetString);
	}

}
