/* 
* ServiceURN.java
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
 * The Service-URN AVP (AVP code 525) is of type OctetString, and it indicates 
 * that an AF session is used for emergency traffic.
 * It contains values of the service URN including subservices, as defined 
 * in [21] or registered at IANA. The string "urn:service:" in the beginning of 
 * the URN shall be omitted in the AVP and all subsequent text shall be included. 
 * Examples of valid values of the AVP are "sos", "sos.fire", "sos.police" and 
 * "sos.ambulance".
 * 
 * TS 29.214
 *
 * 
 * @author mhappenhofer
 *
 */
public class ServiceURN extends OctetString implements I3GPPConstants {

	public static final int AVP_CODE = 525;

	public ServiceURN() {
		super(AVP_CODE,true,VENDOR_ID);
	}


	public ServiceURN(String _serviceURN) {
		super(AVP_CODE,true,VENDOR_ID);
		setOctetString(_serviceURN);
	}

}
