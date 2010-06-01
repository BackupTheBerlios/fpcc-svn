/* 
* RAI.java
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
package at.ac.tuwien.ibk.biqini.diameter.avp.threegpp;

import at.ac.tuwien.ibk.biqini.diameter.I3GPPConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.UTF8String;

/**
 * The RAI AVP (AVP Code 909) is of type UTF8String, and contains the 
 * Routing Area Identity of the SGSN where the UE is registered. RAI 
 * use and structure is specified in 3GPP TS 23.003 [40].
 * 
 * TS 29.061
 * 
 * @author mhappenhofer
 *
 */
public class RAI extends UTF8String implements I3GPPConstants {

	public static final int AVP_CODE = 909;

	public RAI() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @param string
	 */
	public RAI(String string) {
		super(AVP_CODE,true,VENDOR_ID);
		setOctetString(string);
	}

}
