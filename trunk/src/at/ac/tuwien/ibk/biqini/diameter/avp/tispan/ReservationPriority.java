/* 
* ReservationPriority.java
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
package at.ac.tuwien.ibk.biqini.diameter.avp.tispan;

import at.ac.tuwien.ibk.biqini.diameter.ITispanConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Enumerated;

/**
 * The Reservation-Priority AVP (AVP code 458) is of type Enumerated. The 
 * following values are specified:
 * DEFAULT (0): This is the lowest level of priority. If no 
 * Reservation-Priority AVP is specified in the AA-Request, this
 * is the priority associated with the reservation.
 * 
 * TS 183 017
 * 
 * @author mhappenhofer
 *
 */
public class ReservationPriority extends Enumerated implements ITispanConstants{

	public static final int AVP_CODE = 458;
	
	public static final int DEFAULT 		= 0;
	public static final int PRIORITY_ONE 	= 1;
	public static final int PRIORITY_TWO 	= 2;
	public static final int PRIORITY_THREE 	= 3;
	public static final int PRIORITY_FOUR 	= 4;
	public static final int PRIORITY_FIVE 	= 5;
	public static final int PRIORITY_SIX 	= 6;
	public static final int PRIORITY_SEVEN 	= 7;
	
	public ReservationPriority() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}
	/**
	 * @param enumerated
	 */
	public ReservationPriority(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}
	
	private void init()	{
		mapping.put(0, "DEFAULT");
		mapping.put(1, "PRIORITY_ONE");
		mapping.put(2, "PRIORITY_TWO");
		mapping.put(3, "PRIORITY_THREE");
		mapping.put(4, "PRIORITY_FOUR");
		mapping.put(5, "PRIORITY_FIVE");
		mapping.put(6, "PRIORITY_SIX");
		mapping.put(7, "PRIORITY_SEVEN");
	}

}
