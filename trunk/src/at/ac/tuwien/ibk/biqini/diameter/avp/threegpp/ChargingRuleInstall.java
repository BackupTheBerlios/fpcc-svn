/* 
* ChargingRuleInstall.java
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
 * The Charging-Rule-Install AVP (AVP code 1001) is of type Grouped, and it is 
 * used to activate, install or modify PCC rules as instructed from the PCRF to 
 * the PCEF.
 * For installing a new PCC rule or modifying a PCC rule already installed, 
 * Charging-Rule-Definition AVP shall be used.
 * For activating a specific PCC rule predefined at the PCEF, 
 * Charging-Rule-Name AVP shall be used as a reference for that PCC rule. 
 * The Charging-Rule-Base-Name AVP is a reference that may be used for 
 * activating a group of PCC rules predefined at the PCEF.
 * For GPRS scenarios where the bearer binding is performed by the PCRF, 
 * the Bearer Identifier AVP shall be included as part of Charging-Rule-Install 
 * AVP.
 * If present within Charging-Rule-Install AVP, the Bearer-Identifier AVP 
 * indicates that the PCC rules within this Charging-Rule-Install AVP shall
 *  be installed or activated within the IP CAN bearer identified by the 
 *  Bearer-Identifier AVP. 
 * If no Bearer-Identifier AVP is included within the Charging-Rule-Install 
 * AVP, the PCEF shall select an IP CAN bearer for each of the PCC rules 
 * within this Charging-Rule-Install AVP, were the PCC rule is installed or 
 * activated.
 * AVP Format:
 * Charging-Rule-Install ::= 	< AVP Header: 1001 >
 *							*[ Charging-Rule-Definition ]
 * 							*[ Charging-Rule-Name ]
 *							*[ Charging-Rule-Base-Name ]
 *							 [ Bearer-Identifier ]
 *							*[ AVP ]
 *
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class ChargingRuleInstall extends Grouped implements I3GPPConstants {

	public static final int AVP_CODE = 1001;

	public ChargingRuleInstall() {
		super(AVP_CODE,true,VENDOR_ID);
	}


	/**
	 * @return the bearerIdentifier
	 */
	public BearerIdentifier getBearerIdentifier() {
		return (BearerIdentifier)findChildAVP(BearerIdentifier.AVP_CODE);
	}
	/**
	 * @param _bearerIdentifier the bearerIdentifier to set
	 */
	public void setBearerIdentifier(BearerIdentifier _bearerIdentifier) {
		this.setSingleAVP(_bearerIdentifier);
	}
	
	/**
	 * adds a ChargingRuleBaseName AVP
	 * @param _chargingRuleBaseName	the AVP
	 */
	public void addChargingRuleBaseName(ChargingRuleBaseName _chargingRuleBaseName)	{
		this.addChildAVP(_chargingRuleBaseName);
	}
	
	/**
	 * Queries all ChargingRuleBaseName stored 
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
	 * Queries all ChargingRuleName stored 
	 * @return	Iterator of all ChargingRuleName
	 */
	public Iterator<ChargingRuleName> getChargingRuleNameIterator()	{
		return getIterator(new ChargingRuleName());
	}
	
	/**
	 * adds a ChargingRuleDefinition AVP
	 * @param _chargingRuleDefinition	the AVP
	 */
	public void addChargingRuleDefinition(ChargingRuleDefinition _chargingRuleDefinition)	{
		this.addChildAVP(_chargingRuleDefinition);
	}
	
	/**
	 * Queries all ChargingRuleDefinition stored 
	 * @return	Iterator of all ChargingRuleDefinition
	 */
	public Iterator<ChargingRuleDefinition> getChargingRuleDefinitionIterator()	{
		return getIterator(new ChargingRuleDefinition());
	}
}
