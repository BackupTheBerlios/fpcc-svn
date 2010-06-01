/* 
* GxCCA.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.BearerControlMode;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingInformation;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleInstall;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleRemove;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.EventTrigger;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Offline;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Online;
import at.ac.tuwien.ibk.biqini.diameter.messages.common.CCA;

/**
 * The CCA command, indicated by the Command-Code field set to 272 and the 
 * 'R' bit cleared in the Command Flags field, is sent by the PCRF to the 
 * PCEF in response to the CCR command. It is used to provision PCC rules 
 * and event triggers for the bearer/session and to provide the selected 
 * bearer control mode for the IP-CAN session. If the PCRF performs the 
 * bearer binding, PCC rules will be provisioned at bearer level. The 
 * primary and secondary CCF and/or primary and secondary OCS addresses 
 * may be included in the initial provisioning.
 * Message Format:
 * <CC-Answer> ::=  < Diameter Header: 272, PXY >
 *				 < Session-Id >
 *				 { Auth-Application-Id }
 *				 { Origin-Host }
 *				 { Origin-Realm }
 *				 [ Result-Code ]
 *				 [ Experimental-Result ]
 *				 { CC-Request-Type }
 *				 { CC-Request-Number }
 *				 [ Bearer-Control-Mode ]
 *				*[ Event-Trigger ]
 *				 [ Origin-State-Id ]
 *				*[ Charging-Rule-Remove ]
 *				*[ Charging-Rule-Install ]
 *				 [ Charging-Information ]
 *				 [ Online ]
 *				 [ Offline ]
 *				*[ QoS-Information ]
 *				 [ Error-Message ]
 *				 [ Error-Reporting-Host ]
 *				*[ Failed-AVP ]
 *				*[ Proxy-Info ]
 *				*[ Route-Record ]
 *				*[ AVP ]
 *
 * TS 29.212
 *
 * 
 * @author mhappenhofer
 *
 */
public class GxCCA extends CCA implements I3GPPGxInterface {

	public GxCCA() {
		super(APPLICATION_ID);
	}
	
	/**
	 * Searches for the BearerControlMode AVP inside a message
	 * @return the found BearerControlMode AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public BearerControlMode getBearerControlMode() {
		return (BearerControlMode)findAVP(BearerControlMode.AVP_CODE);
	}
	/**
	 * sets the BearerControlMode and overrides a existing one
	 * @param _destinationRealm	the new BearerControlMode
	 * 
	 * @author mhappenhofer
	 */
	public void setBearerControlMode(BearerControlMode _BearerControlMode)	{
		this.setSingleAVP( _BearerControlMode);
	}
	/**
	 * adds a EventTrigger 
	 * @param _eventTrigger
	 */
	public void addEventTrigger(EventTrigger _eventTrigger)	{
		this.addAVP(_eventTrigger);
	}
	
	/**
	 * Queries all EventTrigger stored 
	 * @return	Iterator of all EventTrigger
	 */
	public Iterator<EventTrigger> getEvenTriggerIterator()	{
		return getIterator(new EventTrigger());
	}
	/**
	 * adds a ChargingRuleRemove 
	 * @param _chargingRuleRemove
	 */
	public void addChargingRuleRemove(ChargingRuleRemove _chargingRuleRemove)	{
		this.addAVP(_chargingRuleRemove);
	}
	/**
	 * Queries all ChargingRuleRemove stored at 
	 * @return	Iterator of all ChargingRuleRemove
	 */
	public Iterator<ChargingRuleRemove> getChargingRuleRemoveIterator()	{
		return getIterator(new ChargingRuleRemove());
	}
	/**
	 * adds a ChargingRuleInstall
	 * @param _chargingRuleInstall
	 */
	public void addChargingRuleInstall(ChargingRuleInstall _chargingRuleInstall)	{
		this.addAVP(_chargingRuleInstall);
	}
	/**
	 * Queries all ChargingRuleInstall stored 
	 * @return	Iterator of all ChargingRuleInstall
	 */
	public Iterator<ChargingRuleInstall> getChargingRuleInstallIterator()	{
		return getIterator(new ChargingRuleInstall());
	}
	/**
	 * Searches for the ChargingInformation AVP inside a message
	 * @return the found ChargingInformation AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public ChargingInformation getChargingInformation() {
		return (ChargingInformation)findAVP(ChargingInformation.AVP_CODE);
	}
	/**
	 * sets the ChargingInformation and overrides a existing one
	 * @param _destinationRealm	the new ChargingInformation
	 * 
	 * @author mhappenhofer
	 */
	public void setChargingInformation(ChargingInformation _ChargingInformation)	{
		this.setSingleAVP( _ChargingInformation);
	}
	/**
	 * Searches for the Online AVP inside a message
	 * @return the found Online AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public Online getOnline() {
		return (Online)findAVP(Online.AVP_CODE);
	}
	/**
	 * sets the Online and overrides a existing one
	 * @param _destinationRealm	the new Online
	 * 
	 * @author mhappenhofer
	 */
	public void setOnline(Online _Online)	{
		this.setSingleAVP( _Online);
	}
	/**
	 * Searches for the Offline AVP inside a message
	 * @return the found Offline AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public Offline getOffline() {
		return (Offline)findAVP(Offline.AVP_CODE);
	}
	/**
	 * sets the Offline and overrides a existing one
	 * @param _destinationRealm	the new Offline
	 * 
	 * @author mhappenhofer
	 */
	public void setOffline(Offline _Offline)	{
		this.setSingleAVP( _Offline);
	}

}
