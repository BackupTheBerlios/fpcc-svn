/* 
* EventTrigger.java
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
 * The Event-Trigger AVP (AVP code 1006) is of type Enumerated. When 
 * sent from the PCRF to the PCEF the Event-Trigger AVP indicates an 
 * event that shall cause a re-request of PCC rules. When sent from 
 * the PCEF to the PCRF the Event-Trigger AVP indicates that the 
 * corresponding event has occurred at the gateway.
 * NOTE:	An exception to the above is the Event Trigger AVP set to 
 * NOT_EVENT_TRIGGERS, that indicates that PCEF shall not notify PCRF 
 * of any event.
 * Whenever the PCRF subscribes to one or more event triggers by using 
 * the RAR command, the PCEF shall send the corresponding currently 
 * applicable values (e.g. 3GPP-SGSN-Address AVP or 
 * 3GPP-SGSN-IPv6-Address AVP, RAT-Type, 3GPP-User-Location-Info, etc.) 
 * to the PCRF in the RAA if available, and in this case, the 
 * Event-Trigger AVPs shall not be included.
 * Whenever one of these events occurs, the PCEF shall send the 
 * related AVP that has changed together with the event trigger 
 * indication.
 * Unless stated for a specific value, the Event-Trigger AVP applies 
 * to all access types.
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class EventTrigger extends Enumerated implements I3GPPConstants {

	/**
	 * This value shall be used in CCA and RAR commands by the PCRF to 
	 * indicate that upon the change of the serving SGSN PCC rules 
	 * shall be requested. When used in a CCR command, this value 
	 * indicates that the PCEF generated the request because the 
	 * serving SGSN changed. The new value of the serving SGSN shall 
	 * be indicated in either 3GPP-SGSN-Address AVP or 
	 * 3GPP-SGSN-IPv6-Address AVP. Applicable only for GPRS.
	 */
	public static final int SGSN_CHANGE = 0;
	/**
	 * This value shall be used in CCA and RAR commands by the PCRF to 
	 * indicate that upon any QoS change (even within the limits of 
	 * the current authorization) at bearer level PCC rules shall be 
	 * requested. When used in a CCR command, this value indicates 
	 * that the PCEF generated the request because there has been a 
	 * change in the requested QoS for a specific bearer (e.g. the 
	 * previously maximum authorized QoS has been exceeded). The 
	 * Bearer-Identifier AVP shall be provided to indicate the 
	 * affected bearer. QoS-Information AVP is required to be 
	 * provided in the same request with the new value.
	 */
	public static final int QOS_CHANGE = 1;
	/**
	 * This value shall be used in CCA and RAR commands by the PCRF to 
	 * indicate that upon a RAT change PCC rules shall be requested. 
	 * When used in a CCR command, this value indicates that the PCEF 
	 * generated the request because of a RAT change. The new RAT type 
	 * shall be provided in the RAT-Type AVP. 
	 */
	public static final int RAT_CHANGE = 2;
	/**
	 * This value shall be used in CCA and RAR commands by the PCRF to 
	 * indicate that upon a TFT change at bearer level PCC rules shall 
	 * be requested. When used in a CCR command, this value indicates 
	 * that the PCEF generated the request because of a change in the 
	 * TFT. The Bearer-Identifier AVP shall be provided to indicate 
	 * the affected bearer. The new TFT values shall be provided in 
	 * TFT-Packet-Filter-Information AVP. Applicable only for GPRS. 
	 */ 
	public static final int TFT_CHANGE = 3;
	/**
	 * This value shall be used in CCA and RAR commands by the PCRF to 
	 * indicate that upon a PLMN change PCC rules shall be requested. 
	 * When used in a CCR command, this value indicates that the PCEF 
	 * generated the request because there was a change of PLMN. 
	 * 3GPP-SGSN-MCC-MNC AVP shall be provided in the same request 
	 * with the new value. Applicable only for GPRS. 
	 */
	public static final int PLMN_CHANGE = 4;
	/**
	 * This value shall be used in CCA and RAR commands by the PCRF to 
	 * indicate that upon loss of bearer, GW should inform PCRF. When 
	 * used in a CCR command, this value indicates that the PCEF 
	 * generated the request because the bearer associated with the 
	 * PCC rules indicated by the corresponding Charging Rule Report 
	 * AVP was lost. The PCC-Rule-Status AVP within the Charging Rule 
	 * Report AVP shall indicate that these PCC rules are temporary 
	 * inactive. Applicable for those access-types that handle 
	 * multiple bearers within one single IP-CAN session (e.g. GPRS).
	 * The mechanism of indicating loss of bearer to the GW is IP-CAN 
	 * access type specific. For GPRS, this is indicated by a PDP 
	 * context modification request with Maximum Bit Rate (MBR) in QoS 
	 * profile changed to 0 kbps.
	 * When the PCRF performs the bearer binding, the PCEF shall 
	 * provide the Bearer-Identifier AVP to indicate the bearer that 
	 * has been lost. 
	 */
	public static final int LOSS_OF_BEARER = 5;
	/**
	 * This value shall be in CCA and RAR commands by the PCRF used to 
	 * indicate that upon recovery of bearer, GW should inform PCRF. 
	 * When used in a CCR command, this value indicates that the PCEF 
	 * generated the request because the bearer associated with the 
	 * PCC rules indicated by the corresponding Charging Rule Report 
	 * AVP was recovered. The PCC-Rule-Status AVP within the Charging 
	 * Rule Report AVP shall indicate that these rules are active again. 
	 * Applicable for those access-types that handle multiple bearers 
	 * within one single IP-CAN session (e.g. GPRS).
	 * The mechanism for indicating recovery of bearer to the GW is 
	 * IP-CAN access type specific. For GPRS, this is indicated by a 
	 * PDP context modification request with Maximum Bit Rate (MBR) in 
	 * QoS profile changed from 0 kbps to a valid value.
	 * When the PCRF performs the bearer binding, the PCEF shall provide 
	 * the Bearer-Identifier AVP to indicate the bearer that has been 
	 * recovered.
	 */
	public static final int RECOVERY_OF_BEARER = 6;
	/**
	 * This value shall be used in CCA and RAR commands by the PCRF to 
	 * indicate that upon a change in the IP-CAN type PCC rules shall 
	 * be requested. When used in a CCR command, this value indicates 
	 * that the PCEF generated the request because there was a change 
	 * of IP-CAN type. IP-CAN-Type AVP shall be provided in the same 
	 * request with the new value., The RAT-Type AVP shall also be 
	 * provided when applicable for the specific IP-CAN Type (e.g. 
	 * 3GPP IP-CAN Type). 
	 */
	public static final int IP_CAN_CHANGE = 7;
	/**
	 * This value shall be used in CCA and RAR commands by the PCRF to 
	 * indicate that upon a failure in the enforcement of PCC rules 
	 * due to GW/PCEF malfunction, the PCEF shall inform the PCRF.
	 * When used in a CCR or RAA command, this value indicates that the 
	 * PCEF generated the request or answer due to a malfunction in the 
	 * PCEF the PCC rules cannot be enforced. The affected PCC rules will 
	 * be provided in the Charging-Rule-Report AVP. 
	 * When PCRF performs the bearer binding, the PCEF must provide the 
	 * Bearer-Identifier AVP for the affected bearer and should provide 
	 * the Charging-Rule-Report AVP to indicate what PCC rules are 
	 * affected within that bearer. In this case, absence of the 
	 * Charging-Rule-Report AVP means that all provided PCC rules for 
	 * that specific bearer are affected.
	 * When the PCEF performs the bearer binding, the PCEF should provide 
	 * the Charging-Rule-Report AVP to indicate the PCC rules that are 
	 * affected. In this case, absence of Charging-Rule-Report AVP means 
	 * that all the PCC rules for the corresponding IP-CAN session are 
	 * affected. 
	 */
	public static final int GW_PCEF_MALFUNCTION = 8;
	/**
	 * This value shall be used in CCA and RAR commands by the PCRF to 
	 * indicate that upon a failureto provide the required resource 
	 * for the service flows described by the PCC rules, the PCEF 
	 * shall inform the PCRF.
	 * When used in a CCR or RAA command, this value indicates that 
	 * the PCEF generated the request or answer because of resource 
	 * limitation. The affected PCC rules will be provided in the 
	 * Charging-Rule-Report AVP. When the PCRF performs the bearer 
	 * binding, the PCEF shall provide the Bearer-Identifier for the 
	 * affected bearer. In this case, absence of the 
	 * Charging-Rule-Report AVP means that all provided PCC rules 
	 * for that specific bearer are affected. Otherwise, only the 
	 * PCC rules included in Charging-Rule-Report AVP are affected.
	 */
	public static final int RESOURCES_LIMITATION = 9;
	/**
	 * This value shall be used in CCA and RAR commands by the PCRF to 
	 * subscribe to this event. If the PCRF subscribes to this event, 
	 * the PCEF shall inform the PCRF whenever a failure in the 
	 * enforcement of PCC rules occurs due to the maximum number of 
	 * bearer have been reached for the IP-CAN session, PCEF shall 
	 * inform PCRF.
	 * When used in a CCR or RAA command, this value indicates that 
	 * the PCEF generated the request or answer because the PCC rules 
	 * cannot be enforced since the IP-CAN session already contains 
	 * the maximum number of bearers allowed. The affected PCC rules 
	 * will be provided in the Charging-Rule-Report AVP. 
	 */
	public static final int MAX_NR_BEARERS_REACHED = 10;
	/**
	 * This value shall be used in CCA and RAR commands by the PCRF to 
	 * indicate that only upon a requested QoS change beyond the 
	 * current authorized value(s) at bearer level PCC rules shall 
	 * be requested. When used in a CCR or RAA command, this value 
	 * indicates that the PCEF generated the request or answer 
	 * because there has been a change in the requested QoS beyond 
	 * the authorized value(s) for a specific bearer. The 
	 * Bearer-Identifier AVP shall be provided to indicate the 
	 * affected bearer. QoS-Information AVP is required to be 
	 * provided in the same request with the new value. 
	 */
	public static final int QOS_CHANGE_EXCEEDING_AUTHORIZATION = 11;
	/**
	 * This value shall be used in CCA and RAR commands by the PCRF to
	 * indicate that upon a change in the RAI, PCEF shall inform the 
	 * PCRF. When used in a CCR command, this value indicates that the 
	 * PCEF generated the request because there has been a change in 
	 * the RAI. The new RAI value shall be provided in the RAI AVP.  
	 * If the user location has been changed but the PCEF can not get 
	 * the detail location information for some reasons (eg. handover 
	 * from 3G to 2G network), the PCEF shall send the RAI AVP to the 
	 * PCRF by setting the LAC of the RAI to value 0x0000. Applicable 
	 * only for GPRS. 
	 */
	public static final int RAI_CHANGE = 12;
	/**
	 * This value shall be used in CCA and RAR commands by the PCRF to 
	 * indicate that upon a change in the user location, PCEF shall 
	 * inform the PCRF. When used in a CCR command, this value 
	 * indicates that the PCEF generated the request because there 
	 * has been a change in the user location. The new location value 
	 * shall be provided in the 3GPP-User-Location-Info AVP. If the 
	 * user location has been changed but the PCEF can not get the 
	 * detail location information for some reasons (eg. handover 
	 * from 3G to 2G network), the PCEF shall send the 
	 * 3GPP-User-Location-Info AVP to the PCRF by setting the LAC of 
	 * the CGI/SAI to value 0x0000. Applicable only for GPRS.
	 */
	public static final int USER_LOCATION_CHANGE = 13;
	/**
	 * This value shall be used in CCA and RAR commands by the PCRF to 
	 * indicate that PCRF does not require any Event Trigger 
	 * notification. 
	 */
	public static final int NO_EVENT_TRIGGER = 14;
	
	public static final int AVP_CODE = 1006;

	public EventTrigger() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	/**
	 * @param enumerated
	 */
	public EventTrigger(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}
	
	private void init()	{
		mapping.put(0,"SGSN_CHANGE");
		mapping.put(1,"QOS_CHANGE");
		mapping.put(2,"RAT_CHANGE");
		mapping.put(3,"TFT_CHANGE");
		mapping.put(4,"PLMN_CHANGE");
		mapping.put(5,"LOSS_OF_BEARER");
		mapping.put(6,"RECOVERY_OF_BEARER");
		mapping.put(7,"IP_CAN_CHANGE");
		mapping.put(8,"GW_PCEF_MALFUNCTION");
		mapping.put(9,"RESOURCES_LIMITATION");
		mapping.put(10,"MAX_NR_BEARERS_REACHED");
		mapping.put(11,"QOS_CHANGE_EXCEEDING_AUTHORIZATION");
		mapping.put(12,"RAI_CHANGE");
		mapping.put(13,"USER_LOCATION_CHANGE");
		mapping.put(14,"NO_EVENT_TRIGGER");
	}

}
