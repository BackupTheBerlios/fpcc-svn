/* 
* RatingGroup.java
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
 * The Rating-Group AVP is of type Unsigned32 (AVP Code 432) and
 * contains the identifier of a rating group.  All the services subject
 * to the same rating type are part of the same rating group.  The
 * specific rating group the request relates to is uniquely identified
 * by the combination of Service-Context-Id and Rating-Group AVPs.
 *
 * RFC4006
 *
 * @author mhappenhofer
 *
 */
public class RatingGroup extends Unsigned32 implements IIETFConstants{

	public final static int AVP_CODE = 432;
	
	public RatingGroup() {
		super(AVP_CODE,true,VENDOR_ID);
	}


	/**
	 * @param _ratingGroup
	 */
	public RatingGroup(long _ratingGroup) {
		super(AVP_CODE,true,VENDOR_ID);
		this.setUnsigned32(_ratingGroup);
	}

}
