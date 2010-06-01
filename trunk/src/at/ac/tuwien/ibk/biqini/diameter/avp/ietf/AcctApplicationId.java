/* 
* AcctApplicationId.java
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
package at.ac.tuwien.ibk.biqini.diameter.avp.ietf;

import at.ac.tuwien.ibk.biqini.diameter.IIETFConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Unsigned32;

/**
 * The Acct-Application-Id AVP (AVP Code 259) is of type Unsigned32 and
 * is used in order to advertise support of the Accounting portion of an
 * application (see Section 2.4).  The Acct-Application-Id MUST also be
 * present in all Accounting messages.  Exactly one of the Auth-
 * Application-Id and Acct-Application-Id AVPs MAY be present.
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class AcctApplicationId extends Unsigned32 implements IIETFConstants{

	public static final int AVP_CODE = 259;

	public AcctApplicationId() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @param unsigned32
	 */
	public AcctApplicationId(long unsigned32) {
		super(AVP_CODE,true,VENDOR_ID);
		setUnsigned32(unsigned32);
	}

}
