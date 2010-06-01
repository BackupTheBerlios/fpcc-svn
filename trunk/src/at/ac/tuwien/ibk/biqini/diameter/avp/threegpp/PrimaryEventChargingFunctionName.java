/* 
* PrimaryEventChargingFunctionName.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.base.DiameterURI;

/**
 * The Primary-Event-Charging-Function-Name AVP is of type DiameterURI. This AVP 
 * contains the address of the Primary Online Charging Function. The receiving network 
 * element shall extract the FQDN of the DiameterURI in this AVP and may use it as 
 * content of the Destination-Host AVP for the Diameter accounting requests. The parent 
 * domain of the FQDN in the DiameterURI shall be used as Destination-Realm. The number 
 * of labels used for the Destination-Realm shall be determined before the Charging 
 * Information is provisioned and may be a configuration option.
 * NOTE:	A FQDN is an absolute domain name including a subdomain and its parent 
 * domain. The subdomain and the parent domain contain one or more labels separated 
 * by dots.
 * 
 *  TS 29.229
 *
 * @author mhappenhofer
 *
 */
public class PrimaryEventChargingFunctionName extends DiameterURI implements I3GPPConstants {

	public static final int AVP_CODE = 619;

	public PrimaryEventChargingFunctionName() {
		super(AVP_CODE,true,VENDOR_ID);
	
	}

	/**
	 * @param octetString
	 */
	public PrimaryEventChargingFunctionName(String octetString) {
		super(AVP_CODE,true,VENDOR_ID);
		setDiameterURI(octetString);
	}
}
