/* 
* CCRequestNumber.java
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
 * The CC-Request-Number AVP (AVP Code 415) is of type Unsigned32 and
 * identifies this request within one session.  As Session-Id AVPs are
 * globally unique, the combination of Session-Id and CC-Request-Number
 * AVPs is also globally unique and can be used in matching credit-
 * control messages with confirmations.  An easy way to produce unique
 * numbers is to set the value to 0 for a credit-control request of type
 * INITIAL_REQUEST and EVENT_REQUEST and to set the value to 1 for the
 * first UPDATE_REQUEST, to 2 for the second, and so on until the value
 * for TERMINATION_REQUEST is one more than for the last UPDATE_REQUEST.
 *
 * RFC4006
 *
 * @author mhappenhofer
 *
 */
public class CCRequestNumber extends Unsigned32 implements IIETFConstants{

	public static final int AVP_CODE = 415;

	public CCRequestNumber() {
		super(AVP_CODE,true,VENDOR_ID);
	}
	
	public CCRequestNumber(long _ccRequestNumber) {
		super(AVP_CODE,true,VENDOR_ID);
		this.setUnsigned32(_ccRequestNumber);
	}

}
