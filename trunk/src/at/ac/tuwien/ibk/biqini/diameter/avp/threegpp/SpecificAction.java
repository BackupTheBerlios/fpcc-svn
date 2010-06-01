/* 
* SpecificAction.java
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
 * The Specific-Action AVP (AVP code 513) is of type Enumerated.
 * Within a PCRF initiated Re-Authorization Request, the Specific-Action AVP 
 * determines the type of the action.
 * Within an initial AA request the AF may use the Specific-Action AVP to request 
 * specific actions from the server at the bearer events and to limit the contact 
 * to such bearer events where specific action is required. If the Specific-Action 
 * AVP is omitted within the initial AA request, no notification of any of the 
 * events defined below is requested.
 * 
 * TS 29.214
 * 
 * @author mhappenhofer
 *
 */
public class SpecificAction extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE = 513;
	/**
	 * Within a RAR, this value shall be used when the server requests the 
	 * service information from the AF for the bearer event. In the AAR, this 
	 * value indicates that the AF requests the server to demand service 
	 * information at each bearer authorization.
	 */
	public static final int SERVICE_INFORMATION_REQUEST = 0;
	/**
	 * Within a RAR, this value shall be used when the server reports the access 
	 * network charging identifier to the AF. The 
	 * Access-Network-Charging-Identifier AVP shall be included within the 
	 * request. In the AAR, this value indicates that the AF requests the server 
	 * to provide an access network charging identifier to the AF at each bearer 
	 * establishment/modification, when a new access network charging identifier 
	 * becomes available.
	 */
	public static final int CHARGING_CORRELATION_EXCHANGE = 1;
	/**
	 * Within a RAR, this value shall be used when the server reports a loss of a 
	 * bearer (e.g. in the case of GPRS PDP context bandwidth modification to 
	 * 0 kbit) to the AF. The SDFs that are deactivated as a consequence of this 
	 * loss of bearer shall be provided within the Flows AVP. In the AAR, this 
	 * value indicates that the AF requests the server to provide a notification 
	 * at the loss of a bearer.
	 */
	public static final int INDICATION_OF_LOSS_OF_BEARER = 2;
	/**
	 * Within a RAR, this value shall be used when the server reports a recovery 
	 * of a bearer (e.g. in the case of GPRS, PDP context bandwidth modification 
	 * from 0 kbit to another value) to the AF. The SDFs that are re-activated as 
	 * a consequence of the recovery of bearer shall be provided within the Flows 
	 * AVP. In the AAR, this value indicates that the AF requests the server to 
	 * provide a notification at the recovery of a bearer.
	 */
	public static final int INDICATION_OF_RECOVERY_OF_BEARER = 3;
	/**
	 * Within a RAR, this value shall be used when the server reports the release 
	 * of a bearer (e.g. PDP context removal for GPRS) to the AF. The SDFs that 
	 * are deactivated as a consequence of this release of bearer shall be 
	 * provided within the Flows AVP. In the AAR, this value indicates that the 
	 * AF requests the server to provide a notification at the removal of a 
	 * bearer.
	 */
	public static final int INDICATION_OF_RELEASE_OF_BEARER = 4;
	/**
	 * Within a RAR, this value shall be used when the server reports the 
	 * establishment of a bearer (e.g. PDP context activation for GPRS) to the 
	 * AF. In the AAR, this value indicates that the AF requests the server to 
	 * provide a notification at the establishment of a bearer.
	 */
	public static final int INDICATION_OF_ESTABLISHMENT_OF_BEARER = 5;
	/**
	 * This value shall be used in RAR command by the PCRF to indicate a change 
	 * in the IP-CAN type. When used in an AAR command, this value indicates that 
	 * the AF is requesting subscription for IP-CAN change notification. When 
	 * used in RAR it indicates that the PCRF generated the request because of 
	 * an IP-CAN change. IP-CAN-Type AVP shall be provided in the same request 
	 * with the new value. The RAT-Type AVP shall also be provided when 
	 * applicable for the specific IP-CAN Type (e.g. 3GPP IP-CAN Type).
	 */
	public static final int IP_CAN_CHANGE = 6;

	public SpecificAction() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	public SpecificAction(int _specificAction) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(_specificAction);
		init();
	}
	
	private void init()	{
		mapping.put(0,"SERVICE_INFORMATION_REQUEST");
		mapping.put(1,"CHARGING_CORRELATION_EXCHANGE");
		mapping.put(2,"INDICATION_OF_LOSS_OF_BEARER");
		mapping.put(3,"INDICATION_OF_RECOVERY_OF_BEARER");
		mapping.put(4,"INDICATION_OF_RELEASE_OF_BEARER");
		mapping.put(5,"INDICATION_OF_ESTABLISHMENT_OF_BEARER");
		mapping.put(6,"IP_CAN_CHANGE");
		
	}

	
}
