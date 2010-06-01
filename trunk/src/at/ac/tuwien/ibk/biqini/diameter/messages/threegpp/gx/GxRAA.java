/* 
* GxRAA.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AccessNetworkChargingAddress;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AccessNetworkChargingIdentifierGx;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.BearerIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleReport;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.EventTrigger;
import at.ac.tuwien.ibk.biqini.diameter.messages.common.RAA;

/**
 * The RAA command, indicated by the Command-Code field set to 258 and the 'R' 
 * bit cleared in the Command Flags field, is sent by the PCEF to the PCRF in 
 * response to the RAR command.
 * Message Format:
 * <RA-Answer> ::=  < Diameter Header: 258, PXY >
 *				 < Session-Id >
 *				 { Origin-Host }
 *				 { Origin-Realm }
 *				 [ Result-Code ]
 *				 [ Experimental-Result ]
 *				 [ Origin-State-Id ]
 *				 [ Event-Trigger ]
 *				*[ Charging-Rule-Report]
 *				 [ Access-Network-Charging-Address ]
 *				*[ Access-Network-Charging-Identifier-Gx ]
 *				 [ Bearer-Identifier ]
 *				 [ Error-Message ]
 *				 [ Error-Reporting-Host ] 
 *				*[ Failed-AVP ]
 *				*[ Proxy-Info ]
 *				*[ AVP ]
 * 
 * TS 29.212
 *
 * @author mhappenhofer
 *
 */
public class GxRAA extends RAA implements I3GPPGxInterface {


	public GxRAA() {
		super(APPLICATION_ID);
	
	}

	/**
	 * Searches for the EventTrigger AVP inside a message
	 * @return the found EventTrigger AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public EventTrigger getEventTrigger() {
		return (EventTrigger)findAVP(EventTrigger.AVP_CODE);
	}
	/**
	 * sets the EventTrigger and overrides a existing one
	 * @param _eventTrigger	the new EventTrigger
	 * 
	 * @author mhappenhofer
	 */
	public void setEventTrigger(EventTrigger _eventTrigger)	{
		this.setSingleAVP( _eventTrigger);
	}
	/**
	 * adds a ChargingRuleReport to this GxRAR Message
	 * @param _chargingRuleReport
	 */
	public void addChargingRuleReport(ChargingRuleReport _chargingRuleReport)	{
		this.addAVP(_chargingRuleReport);
	}
	/**
	 * Queries all ChargingRuleReport stored at this GxRAR Message
	 * @return	Iterator of all ChargingRuleReport
	 */
	public Iterator<ChargingRuleReport> getChargingRuleReport()	{
		return getIterator(new ChargingRuleReport());
	}
	/**
	 * Searches for the AccessNetworkChargingAddress AVP inside a message
	 * @return the found AccessNetworkChargingAddress AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public AccessNetworkChargingAddress getAccessNetworkChargingAddress() {
		return (AccessNetworkChargingAddress)findAVP(AccessNetworkChargingAddress.AVP_CODE);
	}
	/**
	 * sets the AccessNetworkChargingAddress and overrides a existing one
	 * @param _accessNetworkChargingAddress	the new AccessNetworkChargingAddress
	 * 
	 * @author mhappenhofer
	 */
	public void setAccessNetworkChargingAddress(AccessNetworkChargingAddress _accessNetworkChargingAddress)	{
		this.setSingleAVP(_accessNetworkChargingAddress);
	}
	/**
	 * adds a AccessNetworkChargingIdentifierGx to this GxRAR Message
	 * @param _accessNetworkChargingIdentifierGx
	 */
	public void addAccessNetworkChargingIdentifierGx(AccessNetworkChargingIdentifierGx _accessNetworkChargingIdentifierGx)	{
		this.addAVP(_accessNetworkChargingIdentifierGx);
	}
	/**
	 * Queries all AccessNetworkChargingIdentifierGx stored at this GxRAR Message
	 * @return	Iterator of all AccessNetworkChargingIdentifierGx
	 */
	public Iterator<AccessNetworkChargingIdentifierGx> getAccessNetworkChargingIdentifierGx()	{
		return getIterator(new AccessNetworkChargingIdentifierGx());
	}
	/**
	 * Searches for the BearerIdentifier AVP inside a message
	 * @return the found BearerIdentifier AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public BearerIdentifier getBearerIdentifier() {
		return (BearerIdentifier)findAVP(BearerIdentifier.AVP_CODE);
	}
	/**
	 * sets the BearerIdentifier and overrides a existing one
	 * @param _bearerIdentifier	the new AccessNetworkChargingAddress
	 * 
	 * @author mhappenhofer
	 */
	public void setBearerIdentifier(BearerIdentifier _bearerIdentifier)	{
		this.setSingleAVP( _bearerIdentifier);
	}
}
