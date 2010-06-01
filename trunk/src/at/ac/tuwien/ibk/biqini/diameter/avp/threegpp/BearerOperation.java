/* 
* BearerOperation.java
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
* fpcc@ftw.at ��
*
* BIQINI is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. �See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License 
* along with this program; if not, write to the Free Software 
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA �02111-1307 �USA
*/
package at.ac.tuwien.ibk.biqini.diameter.avp.threegpp;

import at.ac.tuwien.ibk.biqini.diameter.I3GPPConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Enumerated;

/**
 * The Bearer-Operation AVP (AVP code 1021) is of type of Enumerated, 
 * and it indicates the bearer event that causes a request for PCC 
 * rules. This AVP shall be supplied if the bearer event relates to an 
 * IP CAN bearer initiated by the UE.
 * 	
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class BearerOperation extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE = 1021;
	/**
	 * This value is used to indicate that a bearer is being terminated. 
	 */
	public static final int TERMINATION = 0;
	/**
	 * This value is used to indicate that a new bearer is being 
	 * established. 
	 */
	public static final int ESTABLISHMENT = 1;
	/**
	 * This value is used to indicate that an existing bearer is being 
	 * modified. 
	 */
	public static final int MODIFICATION = 2;
	

	public BearerOperation() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	/**
	 * @param enumerated
	 */
	public BearerOperation(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}
	
	private void init()	{
		mapping.put(0,"TERMINATION");
		mapping.put(1,"ESTABLISHMENT");
		mapping.put(2,"MODIFICATION"); 
	}
}
