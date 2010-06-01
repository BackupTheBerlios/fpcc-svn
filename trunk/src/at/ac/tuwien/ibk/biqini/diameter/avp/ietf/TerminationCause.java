/* 
* TerminationCause.java
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
 * The Termination-Cause AVP (AVP Code 295) is of type Enumerated, and
 * is used to indicate the reason why a session was terminated on the
 * access device.
 *
 * RFC3588
 * 
 * @author mhappenhofer
 *
 */
public class TerminationCause extends Enumerated implements IIETFConstants{


	public static final int AVP_CODE = 295; 
	/**
	 * The user initiated a disconnect
	 */
	public static final int DIAMETER_LOGOUT = 1;
	/**
	 * This value is used when the user disconnected prior to the receipt
	 * of the authorization answer message.
	 */
    public static final int DIAMETER_SERVICE_NOT_PROVIDED = 2;
	/**
	 * This value indicates that the authorization answer received by the
	 * access device was not processed successfully.
	 */      
	public static final int DIAMETER_BAD_ANSWER = 3;
	/**      
	 * The user was not granted access, or was disconnected, due to 
	 * administrative reasons, such as the receipt of a 
	 * Abort-Session-Request message.
	 */
	public static final int DIAMETER_ADMINISTRATIVE = 4;
	/**
	 * The communication to the user was abruptly disconnected.
	 */
	public static final int DIAMETER_LINK_BROKEN = 5;
	/**
	 * The user's access was terminated since its authorized session time 
	 * has expired.
	 */      
	public static final int DIAMETER_AUTH_EXPIRED = 6;
	/**
	 * The user is receiving services from another access device.
	 */      
	public static final int DIAMETER_USER_MOVED = 7;
	/**
	 * The user's session has timed out, and service has been terminated.
	 */
	public static final int DIAMETER_SESSION_TIMEOUT = 8;
	      

	public TerminationCause() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}


	/**
	 * @param enumerated
	 */
	public TerminationCause(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}
	
	private void init()	{
		mapping.put(1,"DIAMETER_LOGOUT");
		mapping.put(2,"DIAMETER_SERVICE_NOT_PROVIDED");
		mapping.put(3,"DIAMETER_BAD_ANSWER");
		mapping.put(4,"DIAMETER_ADMINISTRATIVE");
		mapping.put(5,"DIAMETER_LINK_BROKEN");
		mapping.put(6,"DIAMETER_AUTH_EXPIRED");
		mapping.put(7,"DIAMETER_USER_MOVED");
		mapping.put(8,"DIAMETER_SESSION_TIMEOUT");
	}
}
