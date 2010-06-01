/* 
* BearerControlMode.java
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
 * The Bearer-Control-Mode AVP (AVP code 1023) is of type of 
 * Enumerated. When sent from PCEF to PCRF, it indicates the UE 
 * preferred bearer control mode. When sent from PCRF to PCEF, it 
 * indicates the PCRF selected bearer control mode.
 * If the Bearer-Control-Mode AVP has not been previously provided by 
 * the PCEF, its absence shall indicate the value UE_ONLY. If the 
 * Bearer-Control AVP has been provided, its value shall remain valid 
 * until it is provided the next time.
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class BearerControlMode extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE= 1023;

	/**
	 * This value is used to indicate that the UE shall request any 
	 * additional bearer establishment.
	 */
	public static final int UE_ONLY = 0;
	/**
	 * This value is used to indicate that the PCEF shall request any 
	 * additional bearer establishment.
	 */
	public static final int NW_ONLY = 1;
	/**
	 * This value is used to indicate that both the UE and PCEF may 
	 * request any additional bearer establishment and add own traffic 
	 * mapping information to an IP-CAN bearer. 
	 */
	public static final int UE_NW = 2;
	
	public BearerControlMode() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}


	/**
	 * @param enumerated
	 */
	public BearerControlMode(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}
	
	private void init()	{
		mapping.put(0, "UE_ONLY");
		mapping.put(1, "NW_ONLY");
		mapping.put(2, "UE_NW");
	}

}
