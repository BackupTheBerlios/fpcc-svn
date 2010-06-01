/* 
* GuaranteedBitrateDL.java
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
 * The Guaranteed-Bitrate-DL AVP (AVP code 1025) is of type Unsigned32, 
 * and it indicates the guaranteed bitrate in bits per second for a 
 * downlink service data flow. The bandwidth contains all the overhead 
 * coming from the IP-layer and the layers above, e.g. IP, UDP, RTP and 
 * RTP payload.
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class GuaranteedBitrateDL extends Unsigned32 implements I3GPPConstants {

	public static final int AVP_CODE = 1025;
	
	public GuaranteedBitrateDL() {
		super(AVP_CODE,true,VENDOR_ID);
	}
	/**
	 * 
	 * @param unsigned32
	 */
	public GuaranteedBitrateDL(long unsigned32) {
		super(AVP_CODE,true,VENDOR_ID);
		setUnsigned32(unsigned32);
	}

}
