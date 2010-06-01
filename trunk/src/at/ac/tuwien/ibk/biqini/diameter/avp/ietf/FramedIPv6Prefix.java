/* 
* FramedIPv6Prefix.java
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
 * The Framed-IPv6-Prefix AVP (AVP Code 97) is of type OctetString and
 * contains the IPv6 prefix to be configured for the user.  One or more
 * AVPs MAY be used in authorization requests as a hint to the server
 * that specific IPv6 prefixes are desired, but the server is not
 * required to honor the hint in the corresponding response.
 *
 * RFC4005
 * 
 * @author mhappenhofer
 *
 */
public class FramedIPv6Prefix extends OctetString implements IIETFConstants{

	public static final int AVP_CODE=97;
	
	public FramedIPv6Prefix() {
		super(AVP_CODE,true,VENDOR_ID);
	}


	/**
	 * @param _framedIPv6Prefix
	 */
	public FramedIPv6Prefix(String _framedIPv6Prefix) {
		super(AVP_CODE,true,VENDOR_ID);
		this.setOctetString(_framedIPv6Prefix);
	}
}
