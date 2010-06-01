/* 
* ChargingRuleName.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.base.OctetString;

/**
 * The Charging-Rule-Name AVP (AVP code 1005) is of type OctetString, 
 * and it defines a name for PCC rule.  For PCC rules provided by the 
 * PCRF it uniquely identifies a PCC rule within one IP CAN session. 
 * For PCC rules pre-defined at the PCEF it uniquely identifies a PCC 
 * rule within the PCEF.
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class ChargingRuleName extends OctetString implements I3GPPConstants {

	public static final int AVP_CODE = 1005;

	public ChargingRuleName() {
		super(AVP_CODE,true,VENDOR_ID);
	
	}

	/**
	 * @param octetString
	 */
	public ChargingRuleName(String octetString) {
		super(AVP_CODE,true,VENDOR_ID);
		setOctetString(octetString);
	}

}
