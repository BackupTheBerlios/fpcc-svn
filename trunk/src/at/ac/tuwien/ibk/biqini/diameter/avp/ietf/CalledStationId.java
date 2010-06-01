/* 
* CalledStationId.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.base.UTF8String;

/**
 * The Called-Station-Id AVP (AVP Code 30) is of type UTF8String and
 * allows the NAS to send the ASCII string describing the layer 2
 * address the user contacted in the request.  For dialup access, this
 * can be a phone number obtained by using Dialed Number Identification
 * (DNIS) or a similar technology.  Note that this may be different from
 * the phone number the call comes in on.  For use with IEEE 802 access,
 * the Called-Station-Id MAY contain a MAC address formatted as
 * described in [RAD802.1X].  It SHOULD only be present in
 * authentication and/or authorization requests.
 *
 * If the Auth-Request-Type AVP is set to authorization-only and the
 * User-Name AVP is absent, the Diameter Server MAY perform
 * authorization based on this field.  This can be used by a NAS to
 * request whether a call should be answered based on the DNIS.
 *
 * The codification of this field's allowed usage range is outside the
 * scope of this specification.
 *
 * RFC4005
 *
 * @author mhappenhofer
 *
 */
public class CalledStationId extends UTF8String implements IIETFConstants{

	public static final int AVP_CODE=30;

	public CalledStationId() {
		super(AVP_CODE,true,VENDOR_ID);
	}
	
	/**
	 * @param _calledStationId
	 */
	public CalledStationId(String _callStationId) {
		super(AVP_CODE,true,VENDOR_ID);
		this.setUTF8String(_callStationId);
	}
}
