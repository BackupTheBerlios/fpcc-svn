/* 
* ErrorReportingHost.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.base.DiameterIdentity;

/**
 * The Error-Reporting-Host AVP (AVP Code 294) is of type
 * DiameterIdentity.  This AVP contains the identity of the Diameter
 * host that sent the Result-Code AVP to a value other than 2001
 * (Success), only if the host setting the Result-Code is different from
 * the one encoded in the Origin-Host AVP.  This AVP is intended to be
 * used for troubleshooting purposes, and MUST be set when the Result-
 * Code AVP indicates a failure.
 * 
 * RFC3588
 * 
 * @author mhappenhofer
 *
 */
public class ErrorReportingHost extends DiameterIdentity implements IIETFConstants{

	public static final int AVP_CODE = 294;

	public ErrorReportingHost() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @param octetString
	 */
	public ErrorReportingHost(String octetString) {
		super(AVP_CODE,true,VENDOR_ID);
		setOctetString(octetString);
	}

}
