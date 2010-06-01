/* 
* BearerIdentifier.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.base.OctetString;

/**
 * The Bearer-Identifier AVP (AVP code 1020) is of type OctetString, 
 * and it indicates the bearer to which specific information refers. 
 * When present within a CC-Request Diameter command, subsequent AVPs 
 * within the CC-Request refer to the specific bearer identified by 
 * this AVP.
 * The bearer identifier of an IP CAN bearer shall be unique within 
 * the corresponding IP CAN session. The bearer identifier shall be 
 * selected by the PCEF.
 * 
 * TS 29.212
 *
 * @author mhappenhofer
 *
 */
public class BearerIdentifier extends OctetString implements I3GPPConstants {

	public static final int AVP_CODE = 1020;

	public BearerIdentifier() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @param octetString
	 */
	public BearerIdentifier(String octetString) {
		super(AVP_CODE,true,VENDOR_ID);
		setOctetString(octetString);
	}

}
