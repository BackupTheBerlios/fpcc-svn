/* 
* FailedAVP.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Grouped;

/**
 * The Failed-AVP AVP (AVP Code 279) is of type Grouped and provides
 * debugging information in cases where a request is rejected or not
 * fully processed due to erroneous information in a specific AVP.  The
 * value of the Result-Code AVP will provide information on the reason
 * for the Failed-AVP AVP.
 *
 * The possible reasons for this AVP are the presence of an improperly
 * constructed AVP, an unsupported or unrecognized AVP, an invalid AVP
 * value, the omission of a required AVP, the presence of an explicitly
 * excluded AVP (see tables in Section 10), or the presence of two or
 * more occurrences of an AVP which is restricted to 0, 1, or 0-1
 * occurrences.
 *
 * A Diameter message MAY contain one Failed-AVP AVP, containing the
 * entire AVP that could not be processed successfully.  If the failure
 * reason is omission of a required AVP, an AVP with the missing AVP
 * code, the missing vendor id, and a zero filled payload of the minimum
 * required length for the omitted AVP will be added.
 *
 * AVP Format
 *
 *    <Failed-AVP> ::= < AVP Header: 279 >
 *                  1* {AVP}
 *
 * RFC3588
 *
 * @author mhappenhofer
 *
 */
public class FailedAVP extends Grouped implements IIETFConstants{

	public static final int AVP_CODE = 279;

	public FailedAVP() {
		super(AVP_CODE,true,VENDOR_ID);
	}

}
