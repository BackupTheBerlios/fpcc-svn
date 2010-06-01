/* 
* ChargingRuleRemove.java
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
 * The Charging-Rule-Remove AVP (AVP code 1002) is of type Grouped, and it is 
 * used to deactivate or remove PCC rules from an IP CAN session.
 * Charging-Rule-Name AVP is a reference for a specific PCC rule at the PCEF 
 * to be removed or for a specific PCC rule predefined at the PCEF to be 
 * deactivated. The Charging-Rule-Base-Name AVP is a reference for a group of 
 * PCC rules predefined at the PCEF to be deactivated.
 * AVP Format:
 * Charging-Rule-Remove ::= < AVP Header: 1002 >
 *							*[ Charging-Rule-Name ]
 *							*[ Charging-Rule-Base-Name ]
 *							*[ AVP ]
 *
 * TS 29.212
 *
 * @author mhappenhofer
 *
 */
public class ChargingRuleRemove extends Grouped implements I3GPPConstants {

	public static final int AVP_CODE = 1002;

	public ChargingRuleRemove() {
		super(AVP_CODE,true,VENDOR_ID);
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
}
