/* 
* FlowUsage.java
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
 * The Flow-Usage AVP (AVP code 512) is of type Enumerated, and provides information 
 * about the usage of IP Flows.
 * 
 * TS 29.214
 *  
 * @author mhappenhofer
 *
 */
public class FlowUsage extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE = 512;

	/**
	 * This value is used to indicate that no information about the usage of 
	 * the IP flow is being provided.
	 */
	public static final int NO_INFORMATION = 0;
	/**
	 * This value is used to indicate that an IP flow is used to transport RTCP.
	 */
	public static final int RTCP = 1;
	/**
	 * This value is used to indicate that the IP flow is used to transport AF 
	 * Signalling Protocols (e.g. SIP/SDP).
	 */
	public static final int AF_SIGNALLING = 2;
	

	public FlowUsage() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}
	
	public FlowUsage(int _flowUsage) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(_flowUsage);
		init();
	}
	
	private void init()	{
		mapping.put(0,"NO_INFORMATION");
		mapping.put(1,"RTCP");
		mapping.put(2,"AF_SIGNALING");
	}

}
