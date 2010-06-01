/* 
* MeteringMethod.java
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
 * The Metering-Method AVP (AVP code 1007) is of type Enumerated, and 
 * it defines what parameters shall be metered for offline charging. 
 * The PCEF may use the AVP for online charging in case of 
 * decentralized unit determination, refer to 3GPP TS 32.299 [19].
 * 
 * If the Metering-Method AVP is omitted but has been supplied 
 * previously, the previous information remains valid. If the 
 * Metering-Method AVP is omitted and has not been supplied 
 * previously, the metering method pre-configured at the PCEF is 
 * applicable as default metering method.
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class MeteringMethod extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE = 1007; 
	/**
	 * This value shall be used to indicate that the duration of the 
	 * service flow shall be metered.
	 */
	public static final int DURATION = 0;
	/**
	 * This value shall be used to indicate that volume of the service 
	 * flow traffic shall be metered. 
	 */
	public static final int VOLUME = 1;
	/**
	 * This value shall be used to indicate that the duration and the 
	 * volume of the service flow traffic shall be metered.
	 
	 */
	public static final int DURATION_VOLUME = 2;
	
	public MeteringMethod() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	/**
	 * @param enumerated
	 */
	public MeteringMethod(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}
	
	private void init()	{
		mapping.put(0,"DURATION");
		mapping.put(1, "VOLUME");
		mapping.put(2, "DURATION_VOLUME");
	}

}
