/* 
* FramedIPAddress.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.base.OctetString;

/**
 * The Framed-IP-Address AVP (AVP Code 8) [RADIUS] is of type
 * OctetString and contains an IPv4 address of the type specified in the
 * attribute value to be configured for the user.  It MAY be used in an
 * authorization request as a hint to the server that a specific address
 * is desired, but the server is not required to honor the hint in the
 * corresponding response.
 *
 * Two values have special significance: 0xFFFFFFFF and 0xFFFFFFFE.  The
 * value 0xFFFFFFFF indicates that the NAS should allow the user to
 * select an address (i.e., negotiated).  The value 0xFFFFFFFE indicates
 * that the NAS should select an address for the user (e.g., assigned
 * from a pool of addresses kept by the NAS).
 *
 * RFC4005
 * 
 * @author mhappenhofer
 *
 */
public class FramedIPAddress extends OctetString implements IIETFConstants{

	
	public static final int AVP_CODE=8;

	public FramedIPAddress() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @param _framedIPAddress
	 */
	public FramedIPAddress(String _framedIPAddress) {
		super(AVP_CODE,true,VENDOR_ID);
		this.setOctetString(_framedIPAddress);
	}

}
