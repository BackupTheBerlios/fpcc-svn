/* 
* NetworkRequestSupport.java
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
 * The Network-Request-Support AVP (AVP code 1024) is of type of 
 * Enumerated and indicates the UE and network support of the network 
 * requested bearer control mode.
 * If the Network Request Support AVP has not been previously provided, 
 * its absence shall indicate the value NETWORK_REQUEST NOT SUPPORTED. 
 * If the Network Request Support AVP has been provided, its value 
 * shall remain valid until it is provided the next time.
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class NetworkRequestSupport extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE = 024;
	/**
	 * This value is used to indicate that the UE and the access 
	 * network do not support the bearer establishment request 
	 * procedure.
	 */
	public static final int NETWORK_REQUEST_NOT_SUPPORTED = 0;
	/**
	 * This value is used to indicate that the UE and the access 
	 * network support the bearer establishment request procedure.
	 */
	public static final int NETWORK_REQUEST_SUPPORTED = 1;
	
	
	public NetworkRequestSupport() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	/**
	 * @param enumerated
	 */
	public NetworkRequestSupport(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}
	
	private void init()	{
		mapping.put(0,"NETWORK_REQUEST_NOT_SUPPORTED");
		mapping.put(1,"NETWORK_REQUEST_SUPPORTED");
		
	}

}
