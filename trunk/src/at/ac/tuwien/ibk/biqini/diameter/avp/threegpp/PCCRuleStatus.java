/* 
* PCCRuleStatus.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Enumerated;

/**
 * The PCC-Rule-Status AVP (AVP code 1019) is of type Enumerated, and 
 * describes the status of one or a group of  PCC Rules.
 * 
 * TS 29.212
 *
 * @author mhappenhofer
 *
 */
public class PCCRuleStatus extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE = 1019;
	
	/**
	 * This value is used to indicate that the PCC rule(s) are 
	 * successfuly installed (for those provisioned from PCRF) 
	 * or activated (for those pre-provisioned in PCEF)
	 */
	public static final int ACTIVE = 0;
	/**
	 * This value is used to indicate that the PCC rule(s) are removed 
	 * (for those provisioned from PCRF) or inactive (for those 
	 * pre-provisioned in PCEF)
	 */
	public static final int INACTIVE = 1;
	/**
	 * This value is used to indicate that, for some reason (e.g. 
	 * loss of bearer), already installed or activated PCC rules are 
	 * temporary disabled.
	 */
	public static final int TEMPORARY_INACTIVE = 2;
	

	public PCCRuleStatus() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	/**
	 * @param enumerated
	 */
	public PCCRuleStatus(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}
	
	private void init()	{
		mapping.put(0, "ACTIVE");
		mapping.put(1, "INACTIVE");
		mapping.put(2, "TEMPORARY_INACTIVE");
	}

}
