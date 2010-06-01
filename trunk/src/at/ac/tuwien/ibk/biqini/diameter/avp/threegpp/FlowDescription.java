/* 
* FlowDescription.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.base.IPFilterRule;

/**
 * The Flow-Description AVP (AVP code 507) is of type IPFilterRule, and 
 * defines a packet filter for an IP flow with the following information:
 * -	Direction (in or out).
 * -	Source and destination IP address (possibly masked).
 * -	Protocol.
 * -	Source and destination port (The Source Port may be omitted to 
 * 		indicate that any source port is allowed. For the Rx interface, 
 * 		lists or ranges shall not be used.).
 * NOTE:	For TCP protocol, destination port may also be omitted.
 * The IPFilterRule type shall be used with the following restrictions:
 * -	Only the Action "permit" shall be used.
 * -	No "options" shall be used.
 * -	The invert modifier "!" for addresses shall not be used.
 * -	The keyword "assigned" shall not be used.
 * If any of these restrictions is not observed by the AF, the server shall 
 * send an error response to the AF containing the Experimental-Result-Code 
 * AVP with value FILTER_RESTRICTIONS. 
 * For the Rx interface, the Flow description AVP shall be used to describe 
 * a single IP flow.
 * The direction "in" refers to uplink IP flows, and the direction "out" 
 * refers to downlink IP flows.
 *  
 * TS 29.214
 * @author mhappenhofer
 *
 */
public class FlowDescription extends IPFilterRule implements I3GPPConstants {

	public static final int AVP_CODE = 507;
	
	public FlowDescription() {
		super(AVP_CODE,true,VENDOR_ID);

	}

	/**
	 * @param octetString
	 */
	public FlowDescription(String octetString) {
		super(AVP_CODE,true,VENDOR_ID);
		setOctetString(octetString);
	}

}
