/* 
* ChargingRuleCommand.java
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
package at.ac.tuwien.ibk.biqini.pep;

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleDefinition;
/**
 * Stores the task that had to be done by the PEP by a certain Charging Rule Install or Remove
 * @author Marco Happenhofer
 *
 */
public class ChargingRuleCommand {
	
	public static final int INVALID = 0;
	public static final int INSTALL = 1;
	public static final int REMOVE = 2;
	public static final int MODIFY = 3;
	public static final int INSTALL_PREDEFINED = 4;
	public static final int REMOVE_PREDEFINED = 5;
	public static final int INSTALL_PREDEFINED_BASE = 6;
	public static final int REMOVE_PREDEFINED_BASE = 7;
	
	/**
	 * the rule definition in the case of Install or modify
	 */
	ChargingRuleDefinition cri=null;
	/**
	 * the command
	 */
	int cmd=0;
	/**
	 * set the Command as install and provide the Rule Definition
	 * @param _cri	the Rule Definition
	 */
	public void setChargingRuleInstall(ChargingRuleDefinition _cri)	{
		this.cri=_cri;
		if(cmd==REMOVE)
			cmd=MODIFY;
		else
			cmd=INSTALL;
	}
	/**
	 * set as remove (might be a modify, if install was set before
	 *
	 */
	public void setChargingRuleRemove()	{
		if(cmd==INSTALL)
			cmd=MODIFY;
		else
			cmd=REMOVE;
	}
	/**
	 * set this command as install for predefined rules
	 *
	 */
	public void setChargingRuleInstall_Predefined(){
		this.cri=null;
		cmd=INSTALL_PREDEFINED;
	}
	/**
	 * set this command as install for predefined rules Base
	 *
	 */
	public void setChargingRuleInstall_Predefined_Base(){
		this.cri=null;
		cmd=INSTALL_PREDEFINED_BASE;
	}
	/**
	 * set this command as rmenove for predefined rules
	 *
	 */
	public void setChargingRuleRemove_Predefined(){
		this.cri=null;
		cmd=REMOVE_PREDEFINED;
	}
	/**
	 * set this command as rmenove for predefined rules Base
	 *
	 */
	public void setChargingRuleRemove_Predefined_Base(){
		this.cri=null;
		cmd=REMOVE_PREDEFINED_BASE;
	}
	/**
	 * queries the associated Charging rule
	 * @return	null if the Command is not Install or Modify
	 */
	public ChargingRuleDefinition getChargingRuleInstall()	{
		return cri;
	}
	/**
	 * provides the Command type
	 * @return type of command
	 */
	public int getCommand(){
		return cmd;
	}
}
