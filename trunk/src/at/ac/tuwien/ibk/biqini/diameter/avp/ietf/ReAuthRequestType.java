/* 
* ReAuthRequestType.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Enumerated;

/**
 * The Re-Auth-Request-Type AVP (AVP Code 285) is of type Enumerated and
 * is included in application-specific auth answers to inform the client
 * of the action expected upon expiration of the Authorization-Lifetime.
 * If the answer message contains an Authorization-Lifetime AVP with a
 * positive value, the Re-Auth-Request-Type AVP MUST be present in an
 * answer message.  The following values are defined:
 *
 *
 * RFC3588
 * 
 * @author mhappenhofer
 *
 */
public class ReAuthRequestType extends Enumerated implements IIETFConstants{

	public static final int AVP_CODE = 285;
	/**
	 * An authorization only re-auth is expected upon expiration of the
	 * Authorization-Lifetime.  This is the default value if the AVP is
	 * not present in answer messages that include the Authorization-
	 * Lifetime.
	 */
	public static final int AUTHORIZE_ONLY = 0;
	/**
	 * An authentication and authorization re-auth is expected upon
     * expiration of the Authorization-Lifetime.
	 */
  	public static final int AUTHORIZE_AUTHENTICATE = 1;

	public ReAuthRequestType() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	/**
	 * @param enumerated
	 */
	public ReAuthRequestType(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}
	
	private void init()	{
		mapping.put(0,"AUTHORIZE_ONLY");
		mapping.put(1,"AUTHORIZE_AUTHENTICATE");
	}

}
