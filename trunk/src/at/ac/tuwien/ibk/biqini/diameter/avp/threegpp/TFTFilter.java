/* 
* TFTFilter.java
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
 * The TFT-Filter AVP (AVP code 1012) is of type IPFilterRule, and it 
 * contains the flow filter for one TFT packet filter. The TFT-Filter 
 * AVP is derived from the Traffic Flow Template (TFT) defined in 3GPP 
 * TS 24.008 [13]. The following information shall be sent:
 * -	Action shall be set to "permit".
 * -	Direction shall be set to "out".
 * -	Protocol shall be set to the value provided within the TFT 
 * 		packet filter parameter "Protocol Identifier/Next Header Type". 
 * 		If the TFT packet filter parameter "Protocol Identifier/Next 
 * 		Header Type" is not provided within the TFT packet filter, 
 * 		Protocol shall be set to "ip".
 * -	Source IP address (possibly masked). The source IP address 
 * 		shall be derived from TFT packet filter parameters "Source 
 * 		address" and "Subnet Mask". The source IP address shall be 
 * 		set to "any", if no such information is provided in the TFT 
 * 		packet filter.
 * -	Source and destination port (single value, list or ranges). 
 * 		The information shall be derived from the corresponding TFT 
 * 		packet filter parameters. Source and/or destination port(s) 
 * 		shall be omitted if such information is not provided in the 
 * 		TFT packet filter.
 * -	The Destination IP address shall be set to "assigned".
 * 
 * The IPFilterRule type shall be used with the following restrictions:
 * -	No options shall be used.
 * -	The invert modifier "!" for addresses shall not be used.
 * The direction "out" refers to downlink direction.
 * 
 * TS 29.212
 *
 * @author mhappenhofer
 *
 */
public class TFTFilter extends IPFilterRule implements I3GPPConstants {

	public static final int AVP_CODE = 1012;

	public TFTFilter() {
		super(AVP_CODE,true,VENDOR_ID);
	}
	
	/**
	 * @param octetString
	 */
	public TFTFilter(String octetString) {
		super(AVP_CODE,true,VENDOR_ID);
		setIPFilterRule(octetString);
	}

}
