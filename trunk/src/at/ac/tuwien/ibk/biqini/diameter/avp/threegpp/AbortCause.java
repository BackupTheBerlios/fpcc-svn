/* 
* AbortCause.java
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
 * The Abort-Cause AVP (AVP code 500) is of type Enumerated, and determines 
 * the cause of an abort session request (ASR) or of a RAR indicating a bearer 
 * release. 
 * 
 * TS 29.214
 * 
 * @author mhappenhofer
 *
 */
public class AbortCause extends Enumerated implements I3GPPConstants{

	public static final int AVP_CODE = 500;
	
	/**
	 * This value is used when the bearer has been deactivated as a result 
	 * from normal signalling handling. For GPRS the bearer refers to the PDP 
	 * Context.
	 */
	public static final int BEARER_RELEASED = 0;
	/**
	 * This value is used to indicate that the server is overloaded and needs 
	 * to abort the session.
	 */
	public static final int INSUFFICIENT_SERVER_RESOURCES = 1;
	/**
	 * This value is used when the bearer has been deactivated due to 
	 * insufficient bearer resources at a transport gateway (e.g. GGSN for GPRS).
	 */
	public static final int INSUFFICIENT_BEARER_RESOURCES = 2;
	


	public AbortCause() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}
	/**
	 * 
	 * @param _abortCause
	 */
	public AbortCause(int _abortCause) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(_abortCause);
		init();
	}
	
	private void init()	{
		mapping.put(0, "BEARER_RELEASED");
		mapping.put(1, "INSUFFICIENT_SERVER_RESOURCES");
		mapping.put(2, "INSUFFICIENT_BEARER_RESOURCES");
	}

}
