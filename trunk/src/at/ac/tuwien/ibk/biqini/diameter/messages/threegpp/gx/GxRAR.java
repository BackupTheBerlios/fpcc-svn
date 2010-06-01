/* 
* GxRAR.java
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
package at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx;

import java.util.Iterator;

import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.AuthApplicationId;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleInstall;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleRemove;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.EventReportIndication;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.EventTrigger;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSInformation;
import at.ac.tuwien.ibk.biqini.diameter.messages.common.RAR;

/**
 * The RAR command, indicated by the Command-Code field set to 258 and the 
 * 'R' bit set in the Command Flags field, is sent by the PCRF to the 
 * BBERF/PCEF in order to provision QoS/PCC rules using the PUSH procedure 
 * initiate the provision of unsolicited QoS/PCC rules. It is used to 
 * provision QoS/PCC rules, event triggers and event report indications for 
 * the session. If the PCRF performs the bearer binding, PCC rules will be 
 * provisioned at bearer level.
 * Message Format:
 * <RA-Request> ::= < Diameter Header: 258, REQ, PXY >
 *				 < Session-Id >
 *				 { Auth-Application-Id }
 *				 { Origin-Host }
 *				 { Origin-Realm }
 *				 { Destination-Realm }
 *				 { Destination-Host }
 *				 { Re-Auth-Request-Type }
 *				 [ Origin-State-Id ]
 *				*[ Event-Trigger ]
 *				 [ Event-Report-Indication ]
 *				*[ Charging-Rule-Remove ]
 *				*[ Charging-Rule-Install ]
 *				*[ QoS-Information ]
 *				*[ Proxy-Info ]
 *				*[ Route-Record ]
 *				*[ AVP]
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class GxRAR extends RAR implements I3GPPGxInterface{

	public GxRAR() {
		super(APPLICATION_ID);
		setAuthApplicationId(new AuthApplicationId(APPLICATION_ID));
	}

	/**
	 * adds a EventTrigger to this GxRAR Message
	 * @param _eventTrigger
	 */
	public void addEventTrigger(EventTrigger _eventTrigger)	{
		this.addAVP(_eventTrigger);
	}
	
	/**
	 * Queries all EventTrigger stored at this GxRAR Message
	 * @return	Iterator of all EventTrigger
	 */
	public Iterator<EventTrigger> getEventTriggerIterator()	{
		return getIterator(new EventTrigger());
	}
	/**
	 * adds a ChargingRuleRemove to this GxRAR Message
	 * @param _chargingRuleRemove
	 */
	public void addChargingRuleRemove(ChargingRuleRemove _chargingRuleRemove)	{
		this.addAVP(_chargingRuleRemove);
	}
	/**
	 * Queries all ChargingRuleRemove stored at this GxRAR Message
	 * @return	Iterator of all ChargingRuleRemove
	 */
	public Iterator<ChargingRuleRemove> getChargingRuleRemoveIterator()	{
		return getIterator(new ChargingRuleRemove());
	}
	/**
	 * adds a ChargingRuleInstall to this GxRAR Message
	 * @param _chargingRuleInstall
	 */
	public void addChargingRuleInstall(ChargingRuleInstall _chargingRuleInstall)	{
		this.addAVP(_chargingRuleInstall);
	}
	/**
	 * Queries all ChargingRuleInstall stored at this GxRAR Message
	 * @return	Iterator of all ChargingRuleInstall
	 */
	public Iterator<ChargingRuleInstall> getChargingRuleInstallIterator()	{
		return getIterator(new ChargingRuleInstall());
	}
	/**
	 * adds a QoSInformation to this GxRAR Message
	 * @param _qoSInformation
	 */
	public void addQoSInformation(QoSInformation _qoSInformation)	{
		this.addAVP(_qoSInformation);
	}
	/**
	 * Queries all QoSInformation stored at this GxRAR Message
	 * @return	Iterator of all QoSInformation
	 */
	public Iterator<QoSInformation> getQoSInformationIterator()	{
		return getIterator(new QoSInformation());
	}
	
	/**
	 * Searches for the EventReportIndication AVP inside a message
	 * @return the found EventReportIndication AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public EventReportIndication getEventReportIndication() {
		return (EventReportIndication)findAVP(EventReportIndication.AVP_CODE);
	}
	/**
	 * sets the EventReportIndication and overrides a existing one
	 * @param _eventReportIndication	the new EventReportIndication
	 * 
	 * @author mhappenhofer
	 */
	public void setEventReportIndication(EventReportIndication _eventReportIndication)	{
		this.setSingleAVP( _eventReportIndication);
	}
}
