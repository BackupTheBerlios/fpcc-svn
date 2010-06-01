/* 
* UserEquipmentInfoType.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Enumerated;

/**
 * The User-Equipment-Info-Type AVP is of type Enumerated  (AVP Code
 * 459) and defines the type of user equipment information contained in
 * the User-Equipment-Info-Value AVP.
 *
 * This specification defines the following user equipment types.
 * However, new User-Equipment-Info-Type values can be assigned by an
 * IANA designated expert, as defined in section 12.
 *
 * RFC4006
 *
 * @author mhappenhofer
 *
 */
public class UserEquipmentInfoType extends Enumerated implements IIETFConstants{

	public static final int AVP_CODE = 459;
	
	/**
	 * The identifier contains the International Mobile Equipment
     * Identifier and Software Version in the international IMEISV format
     * according to 3GPP TS 23.003 [3GPPIMEI].
	 */
	public static final int IMEISV = 0;
	/**
	 * The 48-bit MAC address is formatted as described in [RAD802.1X].
	 */
	public static final int MAC = 1;
	/**
	 * The 64-bit identifier used to identify hardware instance of the
     * product, as defined in [EUI64].
	 */
	public static final int EUI64 = 2;
	/**
	 * There are a number of types of terminals that have identifiers
     * other than IMEI, IEEE 802 MACs, or EUI-64.  These identifiers can
     * be converted to modified EUI-64 format as described in [IPv6Addr]
     * or by using some other methods referred to in the service-specific
     * documentation.
	 */
	public static final int MODIFIED_EUI64 = 3;

	public UserEquipmentInfoType() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}
	
	/**
	 * @param _userEquipmentInfoType
	 */
	public UserEquipmentInfoType(int _userEquipmentInfoType) {
		super(AVP_CODE,true,VENDOR_ID);
		this.setInteger32(_userEquipmentInfoType);
		init();
	}
	
	private void init()	{
		mapping.put(0,"IMEISV");
		mapping.put(1, "MAC");
		mapping.put(2, "EUI64");
	}


}
