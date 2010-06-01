/* 
* IPCANType.java
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
 * The IP-CAN-Type AVP (AVP code 1027) is of type Enumerated, and it 
 * shall indicate the type of Connectivity Access Network in which the 
 * user is connected. 
 * The IP-CAN-Type AVP shall always be present during the IP-CAN 
 * session establishment. During an IP-CAN session modification, this 
 * AVP shall be present when there has been a change in the IP-CAN type 
 * and the PCRF requested to be informed of this event. The 
 * Event-Trigger AVP with value IP-CAN CHANGE shall be provided 
 * together with the IP-CAN-Type AVP.
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class IPCANType extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE = 1027;
	
	/**
	 * This value shall be used to indicate that the IP-CAN is associated 
	 * with a 3GPP access and is further detailed by the RAT-Type AVP.
	 */
	public static final int ThreeGPP = 0;
	/**
	 * This value shall be used to indicate that the IP-CAN is associated 
	 * with a DOCSIS access.
	 */
	public static final int DOCSIS = 1;
	/**
	 * This value shall be used to indicate that the IP-CAN is associated 
	 * with an xDSL access.
	 */
	public static final int xDSL = 2;
	/**
	 * This value shall be used to indicate that the IP-CAN is associated 
	 * with a WiMAX access (IEEE 802.16).
	 */
	public static final int WiMAX = 3;
	/**
	 * This value shall be used to indicate that the IP-CAN is associated 
	 * with a 3GPP2 access and is further detailed by the RAT-Type AVP.
	 */
	public static final int ThreeGPP2 = 4;
	

	public IPCANType() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	/**
	 * @param enumerated
	 */
	public IPCANType(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}

	private void init()	{
		mapping.put(0, "3GPP");
		mapping.put(1, "DOCSIS");
		mapping.put(2, "xDSL");
		mapping.put(3, "WiMAX");
		mapping.put(4, "3GPP2");
	}
}
