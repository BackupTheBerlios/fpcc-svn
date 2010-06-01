/* 
* BearerUsage.java
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
 * The Bearer-Usage AVP (AVP code 1000) is of type Enumerated, and it 
 * shall indicate how the bearer is being used. If the Bearer-Usage AVP 
 * has not been previously provided, its absence shall indicate that 
 * no specific information is available. If the Bearer-Usage AVP has 
 * been provided, its value shall remain valid until it is provided 
 * the next time. The following values are defined:
 *
 * TS 29.212
 *
 * @author mhappenhofer
 *
 */
public class BearerUsage extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE = 1000;
	/**
	 * This value shall indicate no specific bearer usage information 
	 * is available.
	 */
	public static final int GENERAL = 0;
	/**
	 * This value shall indicate that the bearer is used for IMS 
	 * signalling only.
	 */
	public static final int IMS_SIGNALLING = 1;
	

	public BearerUsage() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	/**
	 * @param enumerated
	 */
	public BearerUsage(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}
	
	private void init()	{
		mapping.put(0, "GENERAL");
		mapping.put(1, "IMS_SIGNALING"); 
	}

}
