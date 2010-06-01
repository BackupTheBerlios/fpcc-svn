/* 
* ServiceInfoStatus.java
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
 * The Service-Info-Status AVP (AVP code 527) is of type Enumerated, 
 * and indicates the status of the service information that the AF is 
 * providing to the PCRF. If the Service-Info-Status AVP is not provided 
 * in the AA request, the value FINAL SERVICE INFORMATION shall be assumed.
 * 
 * TS 29.214
 * 
 * @author mhappenhofer
 *
 */
public class ServiceInfoStatus extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE = 527;
	/**
	 * This value is used to indicate that the service has been fully 
	 * negotiated between the two ends and service information provided 
	 * is the result of that negotiation.
	 */
	public static final int FINAL_SERVICE_INFORMATION = 0;
	/**
	 * This value is used to indicate that the service information that 
	 * the AF has provided to the PCRF is preliminary and needs to be further 
	 * negotiated between the two ends (e.g. for IMS when the service information 
	 * is sent based on the SDP offer).
	 */
	public static final int PRELIMINARY_SERVICE_INFORMATION = 1;
	
	public ServiceInfoStatus() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	public ServiceInfoStatus(int _serviceInfoStatus) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(_serviceInfoStatus);
		init();
	}
	
	private void init()	{
		mapping.put(0, "FINAL_SERVICE_INFORMATION");
		mapping.put(1, "PRELIMINARY_SERVICE_INFORMATION");
	}

}
