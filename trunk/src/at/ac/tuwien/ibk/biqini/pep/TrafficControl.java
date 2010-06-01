/* 
* TrafficControl.java
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowDescription;
import at.ac.tuwien.ibk.biqini.pep.Exception.TrafficControlException;
/**
 * Executes all SystemCalls
 * @author Marco Happenhofer
 *
 */
public class TrafficControl implements Runnable {
	/** singleton */
	private static TrafficControl singleton = null;
	/** default TC ID */
	private int default_TCID = 7000;
	/** first TCID used for rules */ 
	private int start_TCID = 7100;
	/** last TCID used for rules */
	private int stop_TCID = 8000;

	private static int SUCCESS_RETURN_CODE = 0;
	/** Mapping of QCI to DSCP values */
	private String QCItoDSCP[] = { "0x00", "0x01", "0x02", "0x03",
			"0x04", "0x05", "0x06", "0x07", "0x08", "0x09" };
	/** debug = true implies that all Systemcalls will be displayed */
	public boolean debug = false;
	/**emulates = true implies that all Systemcalls will not be executed*/
	public boolean emulate = false;
	/** should gateblocking be applied ? */
	public boolean IPTABLES = false;
	/** active FIFO systems */
	public boolean PFIFO = false;
	/** interfacename of the Interface to the access */
	private String interfaceAccess = "eth0";
	/** interfacename of the Interface to the core */
	private String interfaceCore = "eth0";
	/** size of the fifo queue */
	private int pfifoSize = 2000;
	/** type of the fifo queue (bfifo|pfifo)*/
	private String fifoType="pfifo";
	/**bandwidth for unclassified traffic min*/
	private long unclassified_min=1500;
	/**bandwidth for unclassified traffic max*/
	private long unclassified_max=3000;
	/** Bandwidth from Access to Core */
	private long totalBandwidthUp = 1000 * 1000 * 50;
	/** Bandwidth from Core to Access */
	private long totalBandwidthDown = 1000 * 1000 * 50;

	private Hashtable<Long, QoSRule> idTorule;

	private boolean exitThread = false;

	private Thread bandwidthCheck = null;

	private int BANDWIDTH_CHECK_INTERVALL = 500;

	private Calendar lastBandwidthCheck = null;

	private static final int MAX_STAT_SIZE = 10;

	private LinkedList<BandwidthStat> stat;

	static {
		singleton = new TrafficControl();
	}

	public static TrafficControl getInstance() {
		return singleton;
	}

	private TrafficControl() {
		idTorule = new Hashtable<Long, QoSRule>();
		stat = new LinkedList<BandwidthStat>();
	}
	
	public void configure_Interface_Access(String _eth_Access, long _totalDown)	{
		interfaceAccess = _eth_Access;
		totalBandwidthDown = _totalDown;
	}

	public void configure_Interface_Core(String _eth_Core, long _totalUp)	{
		interfaceCore = _eth_Core;
		totalBandwidthUp = _totalUp;
	}

	public void configure_Unclassified_Traffic(long min, long max)	{
		unclassified_min = min;
		unclassified_max = max;
	}
	
	public void configure_DSCP(String[] dscp){
		QCItoDSCP=dscp;
	}
	
	public void configure_TC_ID(int defaultid, int startid, int stopid){
		default_TCID=defaultid;
		start_TCID=startid;
		stop_TCID=stopid;
	}
	
	public void configure_Command_Execution(boolean display, boolean emulate){
		this.emulate= emulate;
		this.debug = display;
	}
	
	public void configure_IPTables(boolean iptables){
		this.IPTABLES=iptables;
	}
	
	public void configure_FIFO(boolean enable, String type, int size){
		this.PFIFO=enable;
		this.fifoType=type;
		this.pfifoSize=size;
	}
	
	public void configure_Bandwidth_Check_Intervall(int intervall)	{
		this.BANDWIDTH_CHECK_INTERVALL=intervall;
	}
	/**
	 * configures and starts the Traffic Control Engine
	 * 
	 * @param config
	 *            Configuration file for the Traffic Control Engine
	 */
	public void init() throws TrafficControlException {
		try {
			// clear all
			terminate();
			// configure HTB
			executeCommand("tc qdisc add dev " + interfaceAccess
					+ " root handle 1: htb default " + default_TCID);
			executeCommand("tc qdisc add dev " + interfaceCore
					+ " root handle 1: htb default " + default_TCID);
			// configure Bandwidths
			executeCommand("tc class add dev " + interfaceAccess
					+ " parent 1: classid 1:1 htb rate " + totalBandwidthDown
					/ 1000 + "kbit");
			executeCommand("tc class add dev " + interfaceCore
					+ " parent 1: classid 1:1 htb rate " + totalBandwidthUp
					/ 1000 + "kbit");
			// configure a queuing system for unclassified traffic
			executeCommand("tc class add dev " + interfaceAccess
					+ " parent 1:1 classid 1:" + default_TCID + " htb rate "
					+ unclassified_min / 1000 + "kbit ceil " + unclassified_max
					/ 1000 + "kbit");
			executeCommand("tc class add dev " + interfaceCore
					+ " parent 1:1 classid 1:" + default_TCID + " htb rate "
					+ unclassified_min / 1000 + "kbit ceil " + unclassified_max
					/ 1000 + "kbit");
			// configure Diffserv queue
			// executeCommand("tc qdisc add dev "+interfaceAccess+" parent
			// 1:"+DEFUALTID+" handle "+DEFUALTID+": dsmark indices 2
			// default_index 1");
			// executeCommand("tc qdisc add dev "+interfaceCore+" parent
			// 1:"+DEFUALTID+" handle "+DEFUALTID+": dsmark indices 2
			// default_index 1");
			// configure Diffserv marking
			// executeCommand("tc class change dev "+interfaceAccess+" classid
			// "+DEFUALTID+":1 dsmark mask 0x00 value "+diffServMarkOtherDown);
			// executeCommand("tc class change dev "+interfaceCore+" classid
			// "+DEFUALTID+":1 dsmark mask 0x00 value "+diffServMarkOtherUp);
			if(PFIFO)	{
				long handle = default_TCID + 1000;
				executeCommand("tc qdisc add dev " + interfaceAccess + " parent 1:"
						+ default_TCID + " handle " + handle + ": "+fifoType+" limit "+pfifoSize);
				executeCommand("tc qdisc add dev " + interfaceCore + " parent 1:"
						+ default_TCID + " handle " + handle + ": "+fifoType+" limit "+pfifoSize);
			}
			if (IPTABLES) {
				executeCommand("iptables -P FORWARD DROP");
				// accept ICMP (for PING)
				executeCommand("iptables -A FORWARD -p icmp -j ACCEPT");
				// accept DNS
				executeCommand("iptables -A FORWARD -p udp --dport 53 -j ACCEPT");
				executeCommand("iptables -A FORWARD -p udp --sport 53 -j ACCEPT");
			}
			// Start bandwidthcheck
			bandwidthCheck = new Thread(this);
			bandwidthCheck.setName("BandwidthCheck");
			bandwidthCheck.start();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TrafficControlException(
					"Could not setup Traffic Control Engine, check scripting and configuration, ("
							+ e.getClass().getCanonicalName() + ") :"
							+ e.getMessage());
		}
	}

	/**
	 * Terminates the Traffic Control Engine and restores any TC configurations
	 */
	public void terminate() {
		if (bandwidthCheck != null) {
			exitThread = true;
			bandwidthCheck.interrupt();
		}
		try {
			executeCommand("tc qdisc del dev " + interfaceAccess + " root");
		} catch (Exception e) {
		}
		try {
			executeCommand("tc qdisc del dev " + interfaceCore + " root");
		} catch (Exception e) {
		}
		try {
			if (IPTABLES) {
				executeCommand("iptables -F");
				executeCommand("iptables -X");
				executeCommand("iptables -P FORWARD ACCEPT");
			}
		} catch (Exception e) {

		}
		try {
			if (bandwidthCheck != null)
				Thread.sleep(BANDWIDTH_CHECK_INTERVALL);
		} catch (InterruptedException e) {

		}
	}

	/**
	 * installs a new QoSRule an the Traffic Engine
	 * 
	 * @param rule
	 *            rule to be installed
	 * @throws TrafficControlException
	 */
	public void installQoSRule(QoSRule rule) throws TrafficControlException {
		long id = rule.getTrafficControlID();
		if (id == -1)
			throw new TrafficControlException(
					"This Rule points to a queuing system that already exits!");
		id = getUnusedId();
		if (id == -1)
			throw new TrafficControlException(
					"Could not find an Identifier for the queuing system!");
		rule.setTrafficControlID(id);
		idTorule.put(id, rule);

		// execute Script
		FlowDescription in = null;
		FlowDescription out = null;

		Iterator<FlowDescription> defs;
		try {
			defs = rule.getRule().getFlowDescriptionIterator();
		} catch (NullPointerException e1) {
			throw new TrafficControlException(
					"Not enough ipfilters to process Rule!");
		}
		while (defs.hasNext()) {
			FlowDescription current = defs.next();
			if (current.isDirectionIn())
				in = current;
			if (current.isDirectionOut())
				out = current;
		}
		if (in == null && out == null)
			throw new TrafficControlException(
					"There is no FlowDescription for out or in!");

		long max = 0;
		long guaranty = 0;
		int qci = 0;
		// uplink Access -> Core
		if (in != null) {
			try {
				max = rule.getRule().getQoSInformation()
						.getMaxRequestedBandwidthUL().getUnsigned32();
			} catch (NullPointerException e) {
				throw new TrafficControlException(
						"Could not detect the max requested bandwidth for uplink!");
			}
			try {
				guaranty = rule.getRule().getQoSInformation()
						.getGuaranteedBitrateUL().getUnsigned32();
			} catch (NullPointerException e) {
				guaranty = max;
			}
			try {
				qci = rule.getRule().getQoSInformation()
						.getQoSClassIdentifier().getEnumerated();
			} catch (NullPointerException e) {
				qci = 0;
			}
			setUpFlowDescription(in, guaranty, max, id, interfaceAccess, qci);
		}
		// downlink Core -> Access
		if (out != null) {
			try {
				max = rule.getRule().getQoSInformation()
						.getMaxRequestedBandwidthDL().getUnsigned32();
			} catch (NullPointerException e) {
				throw new TrafficControlException(
						"Could not detect the max requested bandwidth for downlink!");
			}
			try {
				guaranty = rule.getRule().getQoSInformation()
						.getGuaranteedBitrateUL().getUnsigned32();
			} catch (NullPointerException e) {
				guaranty = max;
			}
			try {
				qci = rule.getRule().getQoSInformation()
						.getQoSClassIdentifier().getEnumerated();
			} catch (NullPointerException e) {
				qci = 0;
			}
			try {
				setUpFlowDescription(out, guaranty, max, id, interfaceCore, qci);
			} catch (TrafficControlException e) {
				if (in != null) {
					System.out.println("Remove rule!");
					removeFlowDescription(in, id, interfaceAccess);
				}
				idTorule.remove(id);
				throw e;
			}
		}
	}

	/**
	 * setup a single Flowdescription
	 * 
	 * @param flowDescription
	 *            the description to setup
	 * @param guaranty
	 *            the guarantied bitrate
	 * @param max
	 *            the maximum required bitrate
	 * @param id
	 *            the selected identifier for the queues
	 * @param interfaceName
	 *            the interface where to set up
	 * @throws TrafficControlException
	 */
	private void setUpFlowDescription(FlowDescription flowDescription,
			long guaranty, long max, long id, String interfaceName,
			int qosClassIdentifier) throws TrafficControlException {
		if (flowDescription.isActionDeny())
			throw new TrafficControlException(
					"Deny traffic is per default, only permit access is allowed ("
							+ flowDescription.getOctetString() + ")");
		String srcHost = null;
		String dstHost = null;
		String srcPort = null;
		String dstPort = null;
		srcHost = flowDescription.getSrcHost();
		dstHost = flowDescription.getDstHost();
		srcPort = flowDescription.getSrcPort();
		dstPort = flowDescription.getDstPort();
		if (srcHost == null && dstHost == null && srcPort == null
				&& dstPort == null)
			throw new TrafficControlException(
					"All relevant IP parameters for installing the rule are zero!");

		String command;
		// configure bandwidth
		command = "tc class add dev " + interfaceName
				+ " parent 1:1 classid 1:" + id + " htb rate " + guaranty
				/ 1000 + "kbit ceil " + max / 1000 + "kbit";
		try {
			executeCommand(command);
		} catch (Exception e) {
			throw new TrafficControlException("Could not add rule, "
					+ e.getMessage());
		}
		try {
			// configure Diffserv queue
			executeCommand("tc qdisc add dev " + interfaceName + " parent 1:"
					+ id + " handle " + id
					+ ": dsmark indices 2 default_index 1");
			// configure Diffserv marking
			executeCommand("tc class change dev " + interfaceName + " classid "
					+ id + ":1 dsmark mask 0x00 value "
					+ QCItoDSCP[qosClassIdentifier]);
		} catch (Exception e) {
			try {
				executeCommand("tc class del dev " + interfaceName
						+ " classid 1:" + id);
			} catch (Exception e1) {
				// removing does not work, ok ignore
			}
			throw new TrafficControlException("Could not configure filter, "
					+ e.getMessage());
		}
		// add a filter
		StringBuffer sbf = new StringBuffer();
		sbf.append("tc filter add dev " + interfaceName
				+ " parent 1:0 protocol ip prio 1 u32 ");
		if (srcHost != null && srcHost.compareTo("0.0.0.0") != 0)
			sbf.append("match ip src " + srcHost + " ");
		if (dstHost != null && dstHost.compareTo("0.0.0.0") != 0)
			sbf.append("match ip dst " + dstHost + " ");
		if (srcPort != null)
			sbf.append("match ip sport " + srcPort + " 0xffff ");
		if (dstPort != null)
			sbf.append("match ip dport " + dstPort + " 0xffff ");
		sbf.append("flowid 1:" + id);
		try {
			executeCommand(sbf.toString());
		} catch (Exception e) {
			try {
				executeCommand("tc class del dev " + interfaceName
						+ " classid 1:" + id);
			} catch (Exception e1) {
				// removing does not work, ok ignore
			}
			throw new TrafficControlException("Could not configure filter, "
					+ e.getMessage());
		}
		// add a pfifo for limiting packets
		if(PFIFO)	{
			long handle = id + 1000;
			String commandQdisc = "tc qdisc add dev " + interfaceName + " parent "
				+ id + ":1 handle " + handle + ": "+fifoType+" limit "+pfifoSize;
			try {
				executeCommand(commandQdisc);
			} catch (Exception e) {
				try {
					removeFlowDescription(flowDescription, id, interfaceName);
				} catch (Exception e1) {
					// removing does not work, ok ignore
				}
				throw new TrafficControlException("Could not configure filter, "
					+ e.getMessage());
			}
		}
		if (IPTABLES) {
			try {
				executeCommands(getIPTablesCommand(flowDescription, false));
			} catch (Exception e) {
				// could not configure IPTables
				// TODO act if IPTables install was not successfully
				e.printStackTrace();
			}
		}
		// Ok, all works fine
	}
	/**
	 * executes a serier of Systemcalls
	 * @param commands	Array of systemcalls
	 * @throws InterruptedException	
	 * @throws IOException
	 * @throws TrafficControlException
	 */
	private void executeCommands(String[] commands)
			throws InterruptedException, IOException, TrafficControlException {
		for (int i = 0; i < commands.length; i++)
			executeCommand(commands[i]);
	}

	/**
	 * creates a correct IPtables command
	 * 
	 * @param flowDescription
	 *            the flowdescription which should be axpressed in iptables
	 * @param drop
	 *            if the command should remove or add
	 * @return the Iptables command
	 */
	private String[] getIPTablesCommand(FlowDescription flowDescription,
			boolean drop) {
		String srcHost = flowDescription.getSrcHost();
		String dstHost = flowDescription.getDstHost();
		String srcPort = flowDescription.getSrcPort();
		String dstPort = flowDescription.getDstPort();
		String protocol = flowDescription.getProto();
		ArrayList<String> retList = new ArrayList<String>();
		StringBuffer sbf = new StringBuffer();
		if (!drop)
			sbf.append("iptables -A FORWARD ");
		else
			sbf.append("iptables -D FORWARD ");
		if (protocol.compareTo("ip") != 0) {
			if (protocol.compareTo("17") == 0)
				sbf.append("-p udp ");
			if (protocol.compareTo("6") == 0)
				sbf.append("-p tcp ");
		} else
			sbf.append("-p tcp ");
		if (srcHost != null && srcHost.compareTo("0.0.0.0") != 0)
			sbf.append("-s " + srcHost + " ");
		if (dstHost != null && dstHost.compareTo("0.0.0.0") != 0)
			sbf.append("-d " + dstHost + " ");
		if (srcPort != null)
			sbf.append("--sport " + srcPort + " ");
		if (dstPort != null)
			sbf.append("--dport " + dstPort + " ");
		sbf.append("-j ACCEPT");
		retList.add(sbf.toString());
		if (protocol.compareTo("ip") == 0)
			retList.add(sbf.toString().replace("tcp", "udp"));
		String retArray[] = new String[retList.size()];
		retList.toArray(retArray);
		return retArray;
	}

	/**
	 * removes a specific flow Description
	 * 
	 * @param flowDescription
	 * @param id
	 * @param interfaceName
	 * @throws TrafficControlException
	 */
	private void removeFlowDescription(FlowDescription flowDescription,
			long id, String interfaceName) throws TrafficControlException {
		// remove the filter
		try {
			String handles[] = getHandler(id, interfaceName);
			for (int i = 0; i < handles.length; i++) {
				executeCommand("tc filter del dev " + interfaceName
						+ " pref 1 protocol ip handle " + handles[i] + " u32");
			}
		} catch (Exception e) {
			throw new TrafficControlException(
					"Can not remove the Flow from system, " + e.getMessage());
		}
		// remove the queue
		try {
			executeCommand("tc class del dev " + interfaceName + " classid 1:"
					+ id);
		} catch (Exception e) {
			throw new TrafficControlException(
					"Can not remove the Flow from system, " + e.getMessage());
		}
		if (IPTABLES) {
			try {
				executeCommands(getIPTablesCommand(flowDescription, true));
			} catch (Exception e) {
				// could not configure IPTables
				// TODO act if IPTables install was not successfully
				e.printStackTrace();
			}
		}
	}
	/**
	 * find handler for a certain TC ID
	 * @param id	a TC ID for that we need a handler
	 * @param interfaceName	the interface where to search
	 * @return	the handler for deleting rules
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws TrafficControlException
	 */
	private String[] getHandler(long id, String interfaceName)
			throws IOException, InterruptedException, TrafficControlException {
		if (emulate) {
			String[] ret = { "55" };
			return ret;
		} else {
			String command = "tc filter show dev " + interfaceName;
			//System.out.println(command);
			Process p = Runtime.getRuntime().exec("sudo " + command);
			InputStream i = p.getInputStream();
			BufferedReader buff = new BufferedReader(new InputStreamReader(i));
			String str;
			ArrayList<String> list = new ArrayList<String>();
			while ((str = buff.readLine()) != null) {
				if (str.indexOf("1:" + id) > 0) {
					StringTokenizer st = new StringTokenizer(str);
					int cnt = 0;
					while (st.hasMoreTokens()) {
						String temp = st.nextToken();
						if (++cnt == 10)
							list.add(temp);
					}

				}
			}
			int ret = p.waitFor();
			if (ret != SUCCESS_RETURN_CODE)
				throw new TrafficControlException("Executing command failed: "
						+ list.get(0));
			String[] array = new String[list.size()];
			list.toArray(array);
			return array;
		}
	}

	/**
	 * removes a certain QoSRule from the Traffic Engine
	 * 
	 * @param rule
	 *            rule to be removed
	 * @throws TrafficControlException
	 */
	public void removeQoSRule(QoSRule rule) throws TrafficControlException {
		long id = rule.getTrafficControlID();
		if (id < start_TCID || id > stop_TCID)
			throw new TrafficControlException(
					"This Rule points to a queuing system that should not exists!");
		if (!idTorule.containsKey(id))
			throw new TrafficControlException(
					"Could not recognize, that this rule is installed on this TrafficControl!");
		// remove the id from the table
		idTorule.remove(id);
		// execute Script
		FlowDescription in = null;
		FlowDescription out = null;

		Iterator<FlowDescription> defs;
		try {
			defs = rule.getRule().getFlowDescriptionIterator();
		} catch (NullPointerException e1) {
			throw new TrafficControlException(
					"Not enough ipfilters to process Rule!");
		}
		while (defs.hasNext()) {
			FlowDescription current = defs.next();
			if (current.isDirectionIn())
				in = current;
			if (current.isDirectionOut())
				out = current;
		}
		// execute script
		TrafficControlException e = null;
		if(in!=null){
			try {
				removeFlowDescription(in, id, interfaceAccess);
			} catch (TrafficControlException e1) {
				e = e1;
			}
		}
		if(out!=null)	{
			try {
				removeFlowDescription(out, id, interfaceCore);
			} catch (TrafficControlException e1) {
				e = e1;
			}
		}
		if (e != null)
			throw e;

	}
	
	/**
	 * executes a single Systemcall
	 * @param command	the systemcall to be executed
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws TrafficControlException
	 */
	private synchronized void executeCommand(String command)
			throws InterruptedException, IOException, TrafficControlException {

		if (emulate)	{
			if (debug)
				System.out.println("Emulate : " + command);
		} else {
			
			if (debug)
				System.out.println("Execute : " + command);
			Process p = Runtime.getRuntime().exec("sudo " + command);
			InputStream i = p.getErrorStream();
			BufferedReader buff = new BufferedReader(new InputStreamReader(i));
			String str;
			StringBuffer sbf = new StringBuffer();
			while ((str = buff.readLine()) != null) {
				sbf.append(str);
			}
			int ret = p.waitFor();
			if (ret != SUCCESS_RETURN_CODE)
				throw new TrafficControlException("Executing command failed: "
						+ sbf.toString());
		}
	}

	/**
	 * finds an unused id for a new rule
	 * 
	 * @return
	 */
	private long getUnusedId() {
		for (long id = start_TCID; start_TCID < stop_TCID; id++) {
			if (!idTorule.containsKey(id))
				return id;
		}
		return -1;
	}
	/**
	 * Thread runs to collect bandwidth actual usages
	 */
	public void run() {
		lastBandwidthCheck = GregorianCalendar.getInstance();
		while (!exitThread) {
			try {
				long now = GregorianCalendar.getInstance().getTimeInMillis();
				long next = lastBandwidthCheck.getTimeInMillis()+BANDWIDTH_CHECK_INTERVALL;
				long sleep = BANDWIDTH_CHECK_INTERVALL;
				long shouldSleep = next-now;
				if(shouldSleep>10)
					sleep = shouldSleep;
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				// e.printStackTrace();
			}
			if (exitThread)
				break;
			lastBandwidthCheck = GregorianCalendar.getInstance();
			BandwidthStat current = new BandwidthStat();
			current.time = GregorianCalendar.getInstance();
			try {
				current.iAccess = readBandwidthInformation(interfaceAccess);
				current.iCore = readBandwidthInformation(interfaceCore);
				//System.out.println(current.iAccess);
				//System.out.println(current.iCore);
			} catch (IOException e) {

			}
			synchronized (stat) {
				stat.addFirst(current);
				while (stat.size() > MAX_STAT_SIZE)
					stat.removeLast();	
			}
		}
	}
	/**
	 * is Emulation actived ?
	 * @return	true if emulation is activated
	 */
	public boolean isEmulation(){
		return emulate;
	}

	/**
	 * Queries the used bandwidth e.g.
	 * 
	 * class htb 1:1 root rate 7000Kbit ceil 7000Kbit burst 5Kb cburst 5Kb Sent
	 * 34080573 bytes 23650 pkt (dropped 0, overlimits 0 requeues 0) rate
	 * 2053Kbit 176pps backlog 0b 0p requeues 0 lended: 692 borrowed: 0 giants:
	 * 0 tokens: 4020 ctokens: 4020
	 * 
	 * class htb 1:7102 parent 1:1 leaf 7102: prio 0 rate 2000Kbit ceil 2100Kbit
	 * burst 2603b cburst 2653b Sent 16081632 bytes 10636 pkt (dropped 0,
	 * overlimits 0 requeues 0) rate 2034Kbit 168pps backlog 0b 0p requeues 0
	 * lended: 10343 borrowed: 293 giants: 0 tokens: -7468 ctokens: 4248
	 * 
	 * @param interfaceName
	 *            the interface where to query
	 * @return the complete response
	 * @throws IOException
	 */
	private String readBandwidthInformation(String interfaceName)
			throws IOException {
		String command = "tc -s class show dev " + interfaceName;
		if (emulate)	{
			//System.out.println("Execute : " + command);
		}
		else {
			Process p = Runtime.getRuntime().exec("sudo " + command);
			InputStream i = p.getInputStream();
			BufferedReader buff = new BufferedReader(new InputStreamReader(i));
			String str;
			StringBuffer sbf = new StringBuffer();
			while ((str = buff.readLine()) != null) {
				sbf.append(str + "\n");
			}
			return sbf.toString();
		}
		return "no information (debug)";
	}
	
	/**
	 * evaluates the total amout of traffic sent over a certain interface
	 * @param uplink	true if we want to query infos about the uplink
	 * @param id		the identifier for this htb
	 * @return			the number of bytes sent since establishing this bearer
	 */
	public long getTotalBandwidth(boolean uplink, int id)	{
		BandwidthStat last = stat.getFirst();
		String details = getDetailsForID(last,uplink, id);
		return getTotalSentTraffic(details);
	}
	
	/**
	 * reads the total amount of traffic sent by this infopart
	 * @param details	a status description of a htb queue
	 * @return		the number of bytes sent over this bearer
	 */
	private long getTotalSentTraffic(String details)	{
		if(details.length()==0)
			return 0;
		StringTokenizer st = new StringTokenizer(details.split("\n")[1]);
		if(st.nextToken().equals("Sent"))	{
			return Long.parseLong(st.nextToken());
		}
		else	{
			System.err.println("could not find Sent data in "+details);
			return 0;
		}
			
	}
	
	/**
	 * evaluates the details for a certain id and uplink/downlink channel
	 * @param			The stat where all infos are stored
	 * @param uplink	true if we want to query infos about the uplink
	 * @param id		the identifier for this htb
	 * @return			a string that describes the current state of this queue
	 */
	private String getDetailsForID(BandwidthStat statInfo, boolean uplink, int id){
		String info=null;
		if(uplink)
			info = statInfo.iCore;
		else
			info = statInfo.iAccess;
		String magicCookie = "class htb 1:"+id;
		String[] details = info.split("\n");
		StringBuffer sbf = new StringBuffer();
		for(int i = 0;i<details.length;i++){
			String line = details[i].trim();
			if(line.startsWith(magicCookie))	{
				for(;i<details.length;i++){
					line = details[i].trim();
					if (line.length()==0)
						return sbf.toString();
					sbf.append(line+"\n");
				}
			}
		}
		return sbf.toString();
	}
	
	public long getBitrate(boolean uplink, int id)	{
		LinkedList<BandwidthStat> localList = new LinkedList<BandwidthStat>();
		synchronized (stat) {
			for(int i = 0;i<stat.size();i++)	{
				localList.add(i, stat.get(i));
			}
		}
		long first=0;
		Calendar firstTimeStamp = null;
		long last = 0;
		Calendar lastTimeSTamp = null;
		for(int i = localList.size()-1;i>=0;i--)	{
			BandwidthStat local = localList.get(i);
			String info = getDetailsForID(local, uplink, id);
			if(info.length()!=0)	{
				lastTimeSTamp = local.time;
				last = getTotalSentTraffic(info);
				if(firstTimeStamp==null){
					firstTimeStamp= local.time;
					first = last;
				}
			}
			
		}
		if(first==last)
			return 0;
		double diffBytes = last-first;
		double diffSecs = ((double)(lastTimeSTamp.getTimeInMillis()-firstTimeStamp.getTimeInMillis()))/1000;
		//System.out.println("first: "+first+" last:"+last+" "+diffBytes);
		//System.out.println(firstTimeStamp.getTimeInMillis()+" "+lastTimeSTamp.getTimeInMillis()+" "+diffSecs);
		return (long) (Math.max((diffBytes)*8/diffSecs,0));
	}

	public class BandwidthStat {
		public Calendar time;

		public String iAccess = null;

		public String iCore = null;
		
	}
}
