/* 
* PEPSession.java
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

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ErrorMessage;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SessionID;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleDefinition;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleReport;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.PCCRuleStatus;
import at.ac.tuwien.ibk.biqini.diameter.messages.IIETFResponseCodes;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxRAA;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxRAR;
import at.ac.tuwien.ibk.biqini.pep.Exception.ChargingRuleInstallException;
import at.ac.tuwien.ibk.biqini.pep.Exception.ChargingRuleRemoveException;
import at.ac.tuwien.ibk.biqini.pep.Exception.TrafficControlException;
import de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage;
/**
 * this class includes all relevant information for a PEP session including all single rules
 * 
 * @author Marco Happenhofer
 *
 */
public class PEPSession {
	/**timestamp for Session setup*/
	private Calendar setup = null;
	/**timestamp for last session modification*/
	private Calendar lastUpdate = null;

	public static PEPLogic pepLogic;
	/**the sessionID*/
	private SessionID sessionID = null;
	/**a collection of all QoSRules within this Session*/
	private Hashtable<String, QoSRule> ruleSet;
	/**a collection for all exchanged Diameter Messages*/
	private Hashtable<Calendar,DiameterMessage> exchangedMessages;
	/**a collection for all predefined static rules*/
	private static Hashtable<String, QoSRule> staticRules = new Hashtable<String, QoSRule>();
	
	/**
	 * @param sessionID
	 */
	public PEPSession(SessionID sessionID) {
		super();
		this.sessionID = sessionID;
		ruleSet = new Hashtable<String, QoSRule>();
		exchangedMessages = new Hashtable<Calendar, DiameterMessage>();
		setup = Calendar.getInstance();
		lastUpdate = null;
	}
	
	/**
	 * checks if this session is still running
	 * @return	true if this session has active QoS Rules that allocate ressources
	 */
	public boolean isActive()	{
		boolean activeSession = false;
		Enumeration<QoSRule> qoSRules = ruleSet.elements();
		while(qoSRules.hasMoreElements())	{
			QoSRule rule = qoSRules.nextElement();
			activeSession = activeSession || rule.isActive();
		}
		return activeSession;
	}

	/**
	 * delivers the unique sessionID String (not the AVP content)
	 * 
	 * @return
	 */
	public String getSessionIDString() {
		return sessionID.getUTF8String();
	}

	/**
	 * calculates the time since setup of this session
	 * 
	 * @return
	 */
	public long timeSinceSetup() {
		return Calendar.getInstance().getTimeInMillis()
				- setup.getTimeInMillis();
	}
	
	/**
	 * adds a static QoSRule
	 * @param rule	a static QoSRule
	 */
	public static void addStaticQoSRule(QoSRule rule){
		staticRules.put(rule.getName(), rule);
	}
	
	/**
	 * checks if this rule is a static rule 
	 * @param name
	 * @return
	 */
	public static boolean isStaticRule(String name)	{
		return staticRules.containsKey(name);
	}
	
	/**
	 * update a Session with a RAR request
	 * 
	 * @param rar
	 *            the received RAR request
	 * @return returns the corresponding Raa answer
	 */
	public GxRAA updateSession(GxRAR rar) {
		int code = IIETFResponseCodes.DIAMETER_SUCCESS;
		lastUpdate = Calendar.getInstance();
		// OK we have received a RAR message
		Vector<ChargingRuleReport> chargRR = new Vector<ChargingRuleReport>();
		ChargingRuleCommands crc = new ChargingRuleCommands();
		// collect all Charging Rule commands
		crc.addChargingRuleInstall(rar.getChargingRuleInstallIterator());
		crc.addChargingRuleRemove(rar.getChargingRuleRemoveIterator());
		// if some problem happened, safe them in the Charging Rule Report
		chargRR.addAll(crc.getAllChargingRuleReports());
		Enumeration<String> en = crc.getAllNames();
		while (en.hasMoreElements()) {
			String name = en.nextElement();
			ChargingRuleCommand crCommand = crc.getCommandByName(name);
			// pick up all command from the Command collection
			switch (crCommand.getCommand()) {
			case (ChargingRuleCommand.INSTALL):
			case (ChargingRuleCommand.INSTALL_PREDEFINED):
				// OK this Command has to install a new Flow
				try {
					// static Rule ??
					if(crCommand.getCommand()==ChargingRuleCommand.INSTALL_PREDEFINED)
						installStaticRule(name);
					else
						installRule(crCommand.cri);
					chargRR.add(createReport(PCCRuleStatus.ACTIVE, name));
				} catch (NullPointerException e) {
					chargRR
							.add(createReport(
									"Could not find all required QoSInformation!",
									name));
					code = 5142;
				} catch (ChargingRuleInstallException e) {
					if(!e.getMessage().contains("is active"))
						chargRR.add(createReport(e.getMessage(), name));
					else	{
						ChargingRuleReport r = createReport(PCCRuleStatus.ACTIVE, name);
						r.addChildAVP(new ErrorMessage(e.getMessage()));
						chargRR.add(r);
					}
				}
				break;
			case (ChargingRuleCommand.REMOVE):
				// OK this Command has to remove a existing Flow
				try {
					// static Rule ??
					if(isStaticRule(name))
						removeStaticRule(name);
					else
						removeRule(name);
					chargRR.add(createReport(PCCRuleStatus.INACTIVE, name));
				} catch (ChargingRuleRemoveException e) {
					chargRR.add(createReport(e.getMessage(), name));
					code = 5142;
				}
				break;
			case (ChargingRuleCommand.MODIFY):
				// OK this Command has to modify a existing Flow
				try {
					modifyRule(crCommand.cri, name);
					chargRR.add(createReport(PCCRuleStatus.ACTIVE, name));
				} catch (ChargingRuleRemoveException e) {
					chargRR.add(createReport(e.getMessage(), name));
					code = 5142;
				} catch (ChargingRuleInstallException e) {
					chargRR.add(createReport(e.getMessage(), name));
					code = 5142;
				}
				break;
			case (ChargingRuleCommand.INVALID):
				// what was going on
				PEPLogic.LOGGER.fatal("We have a Charging Rule Name with "
						+ name + ", but no assigned operation");
				code = 5142;
				break;
			}
		}
		// now create a new Diameter Answer with the response code
		GxRAA raa = pepLogic.newGxRAA(rar, code);
		// add all Charging Rule Reports
		Iterator<QoSRule> q = ruleSet.values().iterator();
		while (q.hasNext()){
			QoSRule rule = q.next();
			String name = rule.getRuleName();
			boolean insert = true;
			Iterator<ChargingRuleReport> crrIt = chargRR.iterator();
			while(crrIt.hasNext()){
				ChargingRuleReport currentCRR = crrIt.next();
				Iterator<ChargingRuleName> crnIt = currentCRR.getChargingRuleNameIterator();
				while(crnIt.hasNext()){
					String currentName = crnIt.next().getOctetString();
					if(currentName.compareTo(name)==0)
						insert = false;
				}
			}
			if(insert)
				chargRR.add(createReport(PCCRuleStatus.ACTIVE, name));
		}
		Iterator<ChargingRuleReport> crit = chargRR.iterator();
		while (crit.hasNext()) {
			raa.addChargingRuleReport(crit.next());
		}
		return raa;
	}

	/**
	 * evaluate the number of active rules
	 * 
	 * @return
	 */
	public int getNumberofActiveRules() {
		return ruleSet.size();
	}
	
	/**
	 * Modifies a rule
	 * 
	 * For performance issues these could change TC parameters instead of deleting and creating
	 * TODO
	 * @param cri	new defintion of the rule
	 * @param name	name of the rule that should be changed
	 * @throws ChargingRuleRemoveException		if installation does not work fine
	 * @throws ChargingRuleInstallException		if removing does not work fine
	 */
	private synchronized void modifyRule(ChargingRuleDefinition cri, String name)
			throws ChargingRuleRemoveException, ChargingRuleInstallException {
		// TODO modify in a atomar operation
		removeRule(name);
		installRule(cri);
	}

	/**
	 * kick a Rule from the Session
	 * 
	 * @param name
	 *            name of the rule
	 * @throws ChargingRuleRemoveException
	 *             throws if the rule does not exits or if the QoS Information
	 *             is missing
	 */
	public synchronized void kickRule(String name)
			throws ChargingRuleRemoveException {
		// TODO send Diameter messages
		this.removeRule(name);
	}

	/**
	 * remove a rule
	 * 
	 * @param name
	 *            name of the rule to remove
	 * @throws ChargingRuleRemoveException
	 *             throws if the rule does not exist or if the QoS Information
	 *             is not there
	 */
	private synchronized void removeRule(String name)
			throws ChargingRuleRemoveException {
		synchronized (ruleSet) {
			if (!ruleSet.containsKey(name))
				throw new ChargingRuleRemoveException(
						"Could not remove Rule, because Rule does not exist!");
			QoSRule qoSRule = ruleSet.get(name);
			try {
				pepLogic.freeResources(qoSRule.getRule());
			} catch (NullPointerException e) {
				throw new ChargingRuleRemoveException(
						"Could not find Qos Information for removing!");
			}
			if ((pepLogic.getcurrentLogLevel() & PEPLogic.PRINT_DEBUG) != 0)
				PEPLogic.LOGGER.info("Session " + getSessionIDString()
						+ ": Remove Rule " + name + "!");
			ruleSet.remove(name);
			try {
				TrafficControl.getInstance().removeQoSRule(qoSRule);
			} catch (TrafficControlException e) {
				throw new ChargingRuleRemoveException(e.getMessage());
			}
		}
	}
	
	/**
	 * remove a predefined rule
	 * 
	 * @param name
	 *            name of the rule to remove
	 * @throws ChargingRuleRemoveException
	 *             throws if the rule does not exist or if the QoS Information
	 *             is not there
	 */
	public static synchronized void removeStaticRule(String name) throws ChargingRuleRemoveException {
		synchronized (staticRules) {
			if(staticRules.containsKey(name))	{
				QoSRule qosRule = staticRules.get(name);
				if(!qosRule.isActive())
					throw new ChargingRuleRemoveException("Rule "+name+" is not active!");
				pepLogic.freeResources(qosRule.getRule());
				try {
					TrafficControl.getInstance().removeQoSRule(qosRule);
				} catch (TrafficControlException e) {
					throw new ChargingRuleRemoveException(e.getMessage());
				}
				qosRule.setActive(false);
			}else throw new ChargingRuleRemoveException("Rule is not a static rule, use removeRule() !");
		}
	}
	
	/**
	 * install a predefined Rule
	 * 
	 * @param crd
	 *            Charging rule definition of the rule to install
	 * @param name
	 * @throws ChargingRuleInstallException
	 */
	public static synchronized void installStaticRule(String name) throws ChargingRuleInstallException {
		synchronized (staticRules) {
			if(staticRules.containsKey(name))	{
				QoSRule qosRule = staticRules.get(name);
				if(qosRule.isActive())
					throw new ChargingRuleInstallException("Rule "+name+" is active!");
				pepLogic.allocateResources(qosRule.getRule());
				try {
					TrafficControl.getInstance().installQoSRule(qosRule);
				} catch (TrafficControlException e) {
					pepLogic.freeResources(qosRule.getRule());
					throw new ChargingRuleInstallException(e.getMessage());
				}
				qosRule.setActive(true);
			}else throw new ChargingRuleInstallException("Rule is not a static rule, use installRule() !");
		}
	}

	/**
	 * install a new Rule
	 * 
	 * @param crd
	 *            Charging rule definition of the rule to install
	 * @param name
	 * @throws ChargingRuleInstallException
	 */
	private synchronized void installRule(ChargingRuleDefinition crd)
			throws ChargingRuleInstallException {
		synchronized (ruleSet) {
			String name = crd.getChargingRuleName().getOctetString();
			if (ruleSet.containsKey(name))
				throw new ChargingRuleInstallException(
						"Could not set up Rule, because Rule alreday exist (try modify)!");
			pepLogic.allocateResources(crd);
			try {
				QoSRule qosRule = new QoSRule(sessionID);
				qosRule.setRule(crd);
				if ((pepLogic.getcurrentLogLevel() & PEPLogic.PRINT_DEBUG) != 0)
					PEPLogic.LOGGER.info("Session " + getSessionIDString()
							+ ": Install Rule " + name + "!");
				TrafficControl.getInstance().installQoSRule(qosRule);
				ruleSet.put(name, qosRule);
			} catch (NullPointerException e) {
				pepLogic.freeResources(crd);
				throw new ChargingRuleInstallException(
						"Could not find all required QoSInformation!");
			} catch (TrafficControlException e) {
				pepLogic.freeResources(crd);
				throw new ChargingRuleInstallException(e.getMessage());
			}
		}
	}
	

	/**
	 * Automatically generate a Charging Rule Report with a certain error
	 * message
	 * 
	 * @param msg
	 *            Message to transport
	 * @param name
	 *            relates to that Rulename
	 * @return
	 */
	public ChargingRuleReport createReport(String msg, String name) {
		ChargingRuleReport crr = new ChargingRuleReport();
		crr.addChargingRuleName(new ChargingRuleName(name));
		crr.setPCCRuleStatus(new PCCRuleStatus(PCCRuleStatus.INACTIVE));
		crr.addChildAVP(new ErrorMessage(msg));
		if ((pepLogic.getcurrentLogLevel() & PEPLogic.PRINT_INFO) != 0)
			PEPLogic.LOGGER.info("Rule " + name + " caused a problem :" + msg);
		return crr;
	}
	/**
	 * creates a Charging RUle Report with only a Status
	 * @param state	state of this Rule
	 * @param name	name of this rule
	 * @return		Charging Rule Report with name and state
	 */
	public ChargingRuleReport createReport(int state, String name){
		ChargingRuleReport crr = new ChargingRuleReport();
		crr.addChargingRuleName(new ChargingRuleName(name));
		crr.setPCCRuleStatus(new PCCRuleStatus(state));
		return crr;
	}

	/**
	 * removes the complete Session and so all including rules
	 * 
	 * @throws ChargingRuleRemoveException
	 */
	public synchronized void kickSession() throws ChargingRuleRemoveException {
		Iterator<QoSRule> it = ruleSet.values().iterator();
		Vector<String> toDelete = new Vector<String>();
		while (it.hasNext()) {
			QoSRule rule = it.next();
			toDelete.add(rule.getRuleName());
		}
		Iterator<String> names = toDelete.iterator();
		while (names.hasNext()) {
			String rule = names.next();
			kickRule(rule);
		}
	}

	/**
	 * delivers the age of this Session
	 * 
	 * @return age of this session (last Updated or setup)
	 */
	public long getAge() {
		if (lastUpdate == null)
			timeSinceSetup();
		return Calendar.getInstance().getTimeInMillis()
				- lastUpdate.getTimeInMillis();
	}
	
	/**
	 * small information of this session
	 */
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append(sessionID.getUTF8String() + " " + timeSinceSetup() / 1000
				+ " secs\n");
		Iterator<QoSRule> it = ruleSet.values().iterator();
		while (it.hasNext()) {
			QoSRule rule = it.next();
			sbf.append("\t\t" + rule.toString() + "\n");
		}
		return sbf.toString();
	}

	/**
	 * delivers all rules within this session
	 * 
	 * @return a array with all rules
	 */
	public QoSRule[] getAllRules() {
		QoSRule[] ret = new QoSRule[ruleSet.size()];
		int i = 0;
		Iterator<QoSRule> it = ruleSet.values().iterator();
		while (it.hasNext()) {
			ret[i++] = it.next();
		}
		return ret;
	}
	/**
	 * stores the Diameter Messages that belongs to that session
	 * @param message	a Diameter message that belongs to that session
	 */
	public void addDiameterMessage(DiameterMessage message)	{
		Calendar now = Calendar.getInstance();
		exchangedMessages.put(now, message);
	}
	/**
	 * queries the messageFlow between the PEP & the PDP
	 * @return	a Collection of Diameter Messages and TimeStamps
	 */
	public Hashtable<Calendar, DiameterMessage> getMessageFlow()	 {
		return exchangedMessages;
	}
}
