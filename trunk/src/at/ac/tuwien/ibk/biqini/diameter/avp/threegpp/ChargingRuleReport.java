/* 
* ChargingRuleReport.java
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
 * The Charging-Rule-Report AVP (AVP code 1018) is of types Grouped, and it is 
 * used to report the status of PCC rules.
 * Charging-Rule-Name AVP is a reference for a specific PCC rule at the PCEF 
 * that has been successfully installed, modified or removed (for dynamic PCC 
 * rules), or activated or deactivated (for predefined PCC rules) because of 
 * trigger from the MS. Charging-Rule-Base-Name AVP is a reference for a group 
 * of PCC rules predefined at the PCEF that has been successfully activated or 
 * deactivated because of trigger from the MS.
 * The Charging-Rule-Report AVP can also be used to report the status of the 
 * PCC rules which cannot be installed/activated at the PCEF. In this condition, 
 * the Charging-Rule-Name AVP is used to indicate a specific PCC rule which 
 * cannot be installed/activated, and the Charging-Rule-Base-Name AVP is used 
 * to indicate a group of PCC rules which cannot be activated.
 * AVP Format:
 * Charging-Rule-Report ::= < AVP Header: 1018 >
 *						*[Charging-Rule-Name]
 *						*[Charging-Rule-Base-Name]
 *						 [PCC-Rule-Status]
 *						*[AVP]
 *
 * Multiple instances of Charging-Rule-Report AVPs shall be used in the case 
 * it is required to report different PCC-Rule-Status values for different 
 * groups of rules within the same Diameter command.
 * 
 * TS 29.212
 *
 * @author mhappenhofer
 *
 */
public class ChargingRuleReport extends Grouped implements I3GPPConstants {

	public static final int AVP_CODE = 1018;

	public ChargingRuleReport() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @return the PCCRuleStatus
	 */
	public PCCRuleStatus getPCCRuleStatus() {
		return (PCCRuleStatus)findChildAVP(PCCRuleStatus.AVP_CODE);
	}
	/**
	 * @param _pCCRuleStatus the PCCRuleStatus to set
	 */
	public void setPCCRuleStatus(PCCRuleStatus _pCCRuleStatus) {
		this.setSingleAVP(_pCCRuleStatus);
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
