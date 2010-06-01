/* 
* RATType.java
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
 * The RAT-Type AVP (AVP code yyyy) is of type Enumerated and is used to 
 * identify the radio access technology that is serving the UE. 
 * NOTE1: 	Values 0-999 are used for generic radio access technologies 
 * 			that can apply to different IP-CAN types and are not IP-CAN 
 * 			specific.
 * NOTE2: 	Values 1000-1999 are used for 3GPP specific radio access 
 * 			technology types.
 * NOTE3: 	Values 2000-2999 are used for 3GPP2 specific radio access 
 * 			technology types.
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class RATType extends Enumerated implements I3GPPConstants {

	//FIXME AVP CODE assignment is not done
	public static final int AVP_CODE =  9996;
	
	/**
	 * This value shall be used to indicate that the RAT is WLAN.
	 */
	public static final int WLAN = 0;
	/**
	 * This value shall be used to indicate that the RAT is UTRAN. For further 
	 * details refer to 3GPP TS 29.060 [18].
	 */
	public static final int UTRAN = 1000;
	/**
	 * This value shall be used to indicate that the RAT is GERAN. For further 
	 * details refer to 3GPP TS 29.060 [18].
	 */
	public static final int GERAN = 1001;
	/**
	 * This value shall be used to indicate that the RAT is GAN. For further 
	 * details refer to 3GPP TS 29.060 [18].
	 */
	public static final int GAN = 1002;
	/**
	 * This value shall be used to indicate that the RAT is HSPA Evolution. 
	 * For further details refer to 3GPP TS 29.060 [18].
	 */
	public static final int HSPA_EVOLUTION = 1003;
	/**
	 * This value shall be used to indicate that the RAT is CDMA2000 1X. For 
	 * further details refer to 3GPP2 X.S0011-D [20].
	 */
	public static final int CDMA2000_1X = 2000;
	/**
	 * This value shall be used to indicate that the RAT is HRPD. For further 
	 * details refer to 3GPP2 X.S0011-D [20].
	 */
	public static final int HRPD = 2001;
	/**
	 * This value shall be used to indicate that the RAT is UMB. For further 
	 * details refer to 3GPP2 X.S0011-D [20].
	 */
	public static final int UMB = 2002;
	
	public RATType() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	/**
	 * @param enumerated
	 */
	public RATType(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}
	
	private void init()	{
		mapping.put(0, "WLAN");
		mapping.put(1, "UTRAN");
		mapping.put(2, "GERAN");
		mapping.put(3, "GAN");
		mapping.put(4, "HSPA_EVOLUTION");
		mapping.put(5, "CDMA2000_1X");
		mapping.put(6, "HRPD");
		mapping.put(7, "UMB");
	}

}
