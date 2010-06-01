/* 
* FlowStatus.java
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
 * The Flow-Status AVP (AVP code 511) is of type Enumerated, and describes 
 * whether the IP flow(s) are enabled or disabled.
 * 
 * TS 29.214
 *  
 * @author mhappenhofer
 *
 */
public class FlowStatus extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE = 511;
	/**
	 * This value shall be used to enable associated uplink IP flow(s) and to 
	 * disable associated downlink IP flow(s). 
	 */
	public static final int ENABLED_UPLINK = 0;
	/**
	 * This value shall be used to enable associated downlink IP flow(s) and to 
	 * disable associated uplink IP flow(s).
	 */
	public static final int ENABLED_DOWNLINK = 1;
	/**
	 * This value shall be used to enable all associated IP flow(s) in both 
	 * directions.
	 */
	public static final int ENABLED = 2;
	/**
	 * This value shall be used to disable all associated IP flow(s) in both 
	 * directions.
	 */
	public static final int DISABLED = 3;
	/**
	 * This value shall be used to remove all associated IP flow(s). The IP 
	 * Filters for the associated IP flow(s) shall be removed. The associated 
	 * IP flows shall not be taken into account when deriving the authorized QoS.
	 */
	public static final int REMOVED = 4;
	

	public FlowStatus() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	/**
	 * 
	 * @param _flowStatus
	 */
	public FlowStatus(int _flowStatus) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(_flowStatus);
		init();
	}

	private void init()	{
		mapping.put(0, "ENABLED_UPLINK");
		mapping.put(1, "ENABLED_DOWNLINK");
		mapping.put(2, "ENABLED");
		mapping.put(3, "DISABLED");
		mapping.put(4, "REMOVED");
		
	}
}
