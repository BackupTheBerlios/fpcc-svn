/* 
* Precedence.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Unsigned32;

/**
 * The Precedence AVP (AVP code 1010) is of type Unsigned32.
 * Within the Charging Rule Definition AVP, the Precedence AVP 
 * determines the order, in which the service data flow templates are 
 * applied at service data flow detection at the PCEF. A PCC rule with 
 * the Precedence AVP with lower value shall be applied before a PCC 
 * rule with the Precedence AVP with higher value.
 * The Precedence AVP is also used within the 
 * TFT-Packet-Filter-Information AVP to indicate the evaluation 
 * precedence of the Traffic Mapping Information filters (for GPRS the 
 * TFT packet filters) as received from the UE. The PCEF shall assign a 
 * lower value in the corresponding Precedence AVP to a Traffic Mapping 
 * Information filter with a higher evaluation precedence than to a 
 * Traffic Mapping Information filter with a lower evaluation 
 * precedence.
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class Precedence extends Unsigned32 implements I3GPPConstants {

	public static final int AVP_CODE = 1010;

	public Precedence() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @param unsigned32
	 */
	public Precedence(long unsigned32) {
		super(AVP_CODE,true,VENDOR_ID);
		setUnsigned32(unsigned32);
	}

}
