/* 
* AccessNetworkChargingIdentifierGx.java
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

import java.util.Iterator;

import at.ac.tuwien.ibk.biqini.diameter.I3GPPConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Grouped;

/**
 * The Access-Network-Charging-Identifier-Gx AVP (AVP code 1022) is of type 
 * Grouped. It contains a charging identifier (e.g. GCID) within the 
 * Access-Network-Charging-Identifier-Value AVP and the related PCC rule 
 * name(s) within the Charging-Rule-Name AVP(s). If the IP CAN session 
 * contains only a single IP CAN bearer, no Charging-Rule-Name AVPs or 
 * Charging-Rule-Base-Name AVPs need to be provided. Otherwise, all the 
 * Charging-Rule-Name AVPs or Charging-Rule-Base-Name AVPs corresponding 
 * to PCC rules activated or installed within the IP CAN bearer corresponding 
 * to the provided Access-Network-Charging-Identifier-Value shall be included.
 * The Access-Network-Charging-Identifier-Gx AVP can be sent from the PCEF to 
 * the PCRF. The PCRF may use this information for charging correlation 
 * towards the AF.
 * 
 * AVP Format:
 * Access-Network-Charging-Identifier-Gx ::= 	< AVP Header: 1022 >
 *								{ Access-Network-Charging-Identifier-Value}
 *								*[ Charging-Rule-Base-Name ]
 *								*[ Charging-Rule-Name ] 
 *
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class AccessNetworkChargingIdentifierGx extends Grouped implements I3GPPConstants {

	public static final int AVP_CODE = 1022;

	public AccessNetworkChargingIdentifierGx() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * access the AccessNetworkChargingIdentifierValue
	 * @return
	 */
	public AccessNetworkChargingIdentifierValue getAccessNetworkChargingIdentifierValue()	{
		return (AccessNetworkChargingIdentifierValue)findChildAVP(AccessNetworkChargingIdentifierValue.AVP_CODE);
	}
	/**
	 * to set the AccessNetworkChargingIdentifierValue AVP
	 * 
	 * a potential existing AVP will be deleted
	 * @param _accessNetworkChargingIdentifier	the AccessNetworkChargingIdentifier AVP to set
	 */
	public void setAccessNetworkChargingIdentifierValue(AccessNetworkChargingIdentifierValue _accessNetworkChargingIdentifier)	{
		this.setSingleAVP(_accessNetworkChargingIdentifier);
	}
	
	/**
	 * adds a ChargingRuleBaseName AVP
	 * @param _chargingRuleBaseName	the AVP
	 */
	public void addChargingRuleBaseName(ChargingRuleBaseName _chargingRuleBaseName)	{
		this.addChildAVP(_chargingRuleBaseName);
	}
	
	/**
	 * Queries all ChargingRuleBaseName stored at this GxRAR Message
	 * @return	Iterator of all ChargingRuleBaseName
	 */
	public Iterator<ChargingRuleBaseName> getChargingRuleBaseNameIterator()	{
		return getIterator(new ChargingRuleBaseName());
	}
	
	/**
	 * adds a ChargingRuleName AVP
	 * @param _chargingRuleName	the AVP
	 */
	public void addChargingRuleName(ChargingRuleName _chargingRuleName)	{
		this.addChildAVP(_chargingRuleName);
	}
	
	/**
	 * Queries all ChargingRuleName stored at this GxRAR Message
	 * @return	Iterator of all ChargingRuleName
	 */
	public Iterator<ChargingRuleName> getChargingRuleNameIterator()	{
		return getIterator(new ChargingRuleName());
	}
	
}
