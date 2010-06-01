/* 
* ChargingRuleCommands.java
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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ErrorMessage;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleBaseName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleDefinition;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleInstall;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleRemove;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleReport;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.PCCRuleStatus;

/**
 * Parses all tasks that have to be done by the PEP and stores as Colection
 * @author Marco Happenhofer
 *
 */
public class ChargingRuleCommands {
	/**
	 * a map that stores all rule names and their command
	 */
	private Hashtable<String, ChargingRuleCommand> commands;
	/**
	 * a colection of ChargingRuleReports, that inform about occured failures, e.g. not supported functions
	 */
	private Vector<ChargingRuleReport>chrgRR;
	
	/**
	 * 
	 */
	public ChargingRuleCommands() {
		commands = new Hashtable<String, ChargingRuleCommand>();
		chrgRR = new Vector<ChargingRuleReport>();
	}
	/**
	 * parse the Charging Rule Install and find all tasks that should be done and identify tasks that are not supported yet 
	 * @param _it	The ChargingRuleInstall AVP from the Diameter message
	 */
	public void addChargingRuleInstall(Iterator<ChargingRuleInstall> _it)	{
		while(_it.hasNext())
		{
			// user defined rules
			ChargingRuleInstall cri = _it.next();
			Iterator<ChargingRuleDefinition>crds = cri.getChargingRuleDefinitionIterator();
			while(crds.hasNext())
			{
				ChargingRuleDefinition crd = crds.next();
				String name = crd.getChargingRuleName().getOctetString();
				if(!commands.containsKey(name))
					commands.put(name, new ChargingRuleCommand());
				commands.get(name).setChargingRuleInstall(crd);
			}
			// predefined rules
			Iterator<ChargingRuleName> crnIt = cri.getChargingRuleNameIterator();
			while(crnIt.hasNext())
			{
				ChargingRuleName crn = crnIt.next();
				String name = crn.getOctetString();
				if(!commands.containsKey(name))
					commands.put(name, new ChargingRuleCommand());
				commands.get(name).setChargingRuleInstall_Predefined();
			}
			// predefined grouped rules
			Iterator<ChargingRuleBaseName>crbnIt = cri.getChargingRuleBaseNameIterator();
			while(crbnIt.hasNext())
			{
				ChargingRuleBaseName crBaseName =crbnIt.next(); 
				ChargingRuleReport crr = new ChargingRuleReport();
				crr.addChargingRuleBaseName(crBaseName);
				crr.setPCCRuleStatus(new PCCRuleStatus(PCCRuleStatus.INACTIVE));
				crr.addChildAVP(new ErrorMessage("This PCEF does not support predefined Rules(base)!"));
				chrgRR.add(crr);
			}
		}
	}
	/**
	 * parse the ChargingRuleRemove and find all tasks that should be done and identify tasks that are not supported yet 
	 * @param _it		The ChargingRuleRemove AVP from the Diameter message
	 */
	public void addChargingRuleRemove(Iterator<ChargingRuleRemove> _it)	{
		while(_it.hasNext())
		{
			// user or predefined rules
			ChargingRuleRemove crr = _it.next();
			Iterator<ChargingRuleName>crn = crr.getChargingRuleNameIterator();
			while(crn.hasNext())
			{
				ChargingRuleName crd = crn.next();
				String name = crd.getOctetString();
				if(!commands.containsKey(name))
					commands.put(name, new ChargingRuleCommand());
				commands.get(name).setChargingRuleRemove();
			}
			// predefiend grouped rules
			Iterator<ChargingRuleBaseName>crbnIt = crr.getChargingRuleBaseNameIterator();
			while(crbnIt.hasNext())
			{
				ChargingRuleBaseName crBaseName =crbnIt.next(); 
				ChargingRuleReport crr2 = new ChargingRuleReport();
				crr2.addChargingRuleBaseName(crBaseName);
				crr2.setPCCRuleStatus(new PCCRuleStatus(PCCRuleStatus.INACTIVE));
				crr2.addChildAVP(new ErrorMessage("This PCEF does not support predefined Rules(base)!"));
				chrgRR.add(crr2);
			}
		}
	}
	
	public Vector<ChargingRuleReport> getAllChargingRuleReports()	{
		return chrgRR;
	}
	
	public Enumeration<String> getAllNames()	{
		return commands.keys();
	}
	
	public ChargingRuleCommand getCommandByName(String name)	{
		return commands.get(name);
	}
}
