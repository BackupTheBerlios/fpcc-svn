/* 
* PEPXMLConfig.java
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.AccessException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.regex.Pattern;

import javax.naming.ConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleDefinition;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowStatus;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.GuaranteedBitrateDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.GuaranteedBitrateUL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthUL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSClassIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSInformation;
import at.ac.tuwien.ibk.biqini.pep.Exception.ConfigurationMissingException;
/**
 * parses the PEP config XML file
 * @author Marco Happenhofer
 *
 */
public class PEPXMLConfig {
	/**singleton object*/
	private static PEPXMLConfig instance=null;
	/**DOM object*/
	private Document config;
	
	private static final String COMMON_CONFIG="common-config";
	//private static final String PREDEFINED_RULES="predefined-rules";
	private static final String GATE_BLOCKING = "gateblocking";
	private static final String QUEUE_SIZE = "queuesize";
	private static final String LOGGING_TAG = "logging";
	private static final String COMMAND_EXECUTION = "CommandExecution";
	private static final String INTERFACE = "interface";
	private static final String DSCP_MAPPING = "DSCP_mapping";
	private static final String TC_ID = "TC_IDs";
	private static final String UNCLASSIFIED_TRAFFIC = "unclassified-traffic";
	private static final String BANDWIDTH_USAGE_LOGGING = "bandwidthUsageLogging";
	private static final String EXPIRES = "expires";

	/**store how much config tags where found*/
	private Hashtable<String,Integer> foundCommons;
	/**gateblocking actived (IPTables)*/
	private boolean gateBlocking=false;
	/**assign certain queuesizes to the configured streams*/
	private boolean queuesize_enabled = false;
	/**size for the queues*/
	private int queuesize_size=0;
	/**specify the kind of queue (pfifo or bfifo)*/
	private String queuesize_measure="pfifo";
	/**logging info messages*/
	private boolean logging_info=false;
	/**logging diameter Messages*/
	private boolean logging_message=false;
	/**do not execute the command on the system*/
	private boolean command_execution_emulation=false;
	/**print system call on terminal*/
	private boolean command_execution_display=false;
	/**name of the in Interface*/
	private String interface_in_name=null;
	/**Bandwidth of th in Interface*/
	private long interface_in_bandwidth=0;
	/**name of the out Interface*/
	private String interface_out_name=null;
	/**Bandwidth of the out Interface*/
	private long interface_out_bandwidth=0;
	/**Queue ID for default queue*/
	private int TC_ID_defaultid=7000;
	/**first queueid to be used for dynamic rules*/
	private int TC_ID_startid=7100;
	/**last queueid to be used for dynamic rules*/
	private int TC_ID_stoptid=8000;
	/**unclassified Traffic min bitrate*/
	private int unclassifiedTraffic_min=0;
	/**unclassified Traffic max bitrate*/
	private int unclassifiedTraffic_max=0;
	/**intervall for reading bandwidthusage in msec*/
	private int intervall=500;
	/**mapping from QCI to DSCP*/
	private String[] dscpmap ={"0x00","0x00","0x00","0x00","0x00","0x00","0x00","0x00","0x00","0x00"};
	/**Collection with all predefined rules*/
	private Hashtable<String,QoSRule> staticRules;
	
	private PEPXMLConfig()	{
		foundCommons=new Hashtable<String, Integer>();
		instance = this;
	}
	/**
	 * initailly setup the Configobject by supplying the XML file
	 * @param xmlFile
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ConfigurationException 
	 */
	public static void parse(String xmlFile) throws ParserConfigurationException, SAXException, IOException, ConfigurationException	{
		// check existence of singelton
		if(instance!=null)
			throw new IllegalStateException("Already configured. It is not possible to reconfigure the PEP!");
		PEPXMLConfig local = new PEPXMLConfig();
		// check File permissions
		File f = new File(xmlFile);
		if(!f.exists())
			throw new FileNotFoundException("Config File "+xmlFile+" not found!");
		if(!f.canRead())
			throw new AccessException("Can not read "+xmlFile+"!");
		// parse XML Config
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// factory.setValidating(true);
		// factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		local.config = builder.parse(xmlFile);
		/** Configuration DOM */
		// Document config;
		// find all usefull tags
		NodeList common = local.config.getDocumentElement().getElementsByTagName(COMMON_CONFIG);
		NodeList rule = local.config.getDocumentElement().getElementsByTagName("rule");
		// parse both parts
		try {
			local.parseCommomConfig(common);
		} catch (NullPointerException e) {
			throw new ConfigurationException("could not find all needed tags and Attributes to configure correct!");
		}
		local.parseStaticRules(rule);
		
		// configure components
		TrafficControl t = TrafficControl.getInstance();
		t.configure_Command_Execution(local.command_execution_display,local.command_execution_emulation);
		t.configure_Bandwidth_Check_Intervall(local.intervall);
		t.configure_DSCP(local.dscpmap);
		t.configure_FIFO(local.queuesize_enabled, local.queuesize_measure, local.queuesize_size);
		t.configure_Interface_Access(local.interface_in_name, local.interface_in_bandwidth);
		t.configure_Interface_Core(local.interface_out_name, local.interface_out_bandwidth);
		t.configure_IPTables(local.gateBlocking);
		t.configure_TC_ID(local.TC_ID_defaultid,local.TC_ID_startid, local.TC_ID_stoptid);
		t.configure_Unclassified_Traffic(local.unclassifiedTraffic_min,local.unclassifiedTraffic_max);
		System.out.println("Configuration completed!");
		
	}
	/**
	 * provides all predefined rules
	 * @return	a collection of all predefined rules
	 */
	public Collection<QoSRule> getAllStaticRules()	{
		return staticRules.values();
	}

	/**
	 * requests the singleton
	 * @return
	 * @throws ConfigurationMissingException
	 */
	public static PEPXMLConfig getInstance() throws ConfigurationMissingException {
		if(instance==null)
			throw new ConfigurationMissingException("No XML file was provided to read the config!");
		else
			return instance;
	}
	/**
	 * parses the generic part of the XML file
	 * @param nodelist	The nodeList with all common-configs (should exitst only one)
	 * @throws ConfigurationException	is thrown if any parsing problems occure
	 */
	private void parseCommomConfig(NodeList nodelist) throws ConfigurationException	{
		// check the numbers 
		if(nodelist.getLength()>1)
			throw new ConfigurationException("Several "+COMMON_CONFIG+" tags found, but only a single entry is allowed!");
		if(nodelist.getLength()<1)
			throw new ConfigurationException("Exactly one "+COMMON_CONFIG+" tags is requiered!");
		Node common = nodelist.item(0);
		NodeList current;
		//gateblocking ??
		current = common.getChildNodes();
		for(int i = 0;i<current.getLength();i++){
			Node node = current.item(i);
			String name = node.getNodeName(); 
			if(name.compareTo(GATE_BLOCKING)==0){
				checkSingleConfiguration(GATE_BLOCKING);
				try {
					gateBlocking =  (node.getAttributes().getNamedItem("blockallGates").getNodeValue().compareTo("true")==0);
				} catch (Exception e) {
					throw new ConfigurationException("Could not parse "+GATE_BLOCKING+" Attributes ("+e.getMessage()+")");				
				}
			} else if(name.compareTo(QUEUE_SIZE)==0){
				checkSingleConfiguration(QUEUE_SIZE);
				queuesize_enabled =  (node.getAttributes().getNamedItem("enable").getNodeValue().compareTo("true")==0);
				if(queuesize_enabled){
					queuesize_size = Integer.parseInt(node.getAttributes().getNamedItem("size").getNodeValue());
					String queuetype= node.getAttributes().getNamedItem("count").getNodeValue();
					if(queuetype.compareTo("packets")==0||queuetype.compareTo("packet")==0)
						queuesize_measure="pfifo";
					else if(queuetype.compareTo("bytes")==0||queuetype.compareTo("byte")==0)
						queuesize_measure="bfifo";
					else
						throw new ConfigurationException("can not identify measurementtype (count attribute) "+queuetype);
				}
			} else if(name.compareTo(LOGGING_TAG)==0){
				checkSingleConfiguration(LOGGING_TAG);
				logging_info=(node.getAttributes().getNamedItem("info").getNodeValue().compareTo("true")==0);
				logging_message=(node.getAttributes().getNamedItem("diameterMessages").getNodeValue().compareTo("true")==0);
				
			} else if(name.compareTo(COMMAND_EXECUTION)==0){
				checkSingleConfiguration(COMMAND_EXECUTION);
				command_execution_emulation=(node.getAttributes().getNamedItem("emulate").getNodeValue().compareTo("true")==0);
				command_execution_display=(node.getAttributes().getNamedItem("debug").getNodeValue().compareTo("true")==0);
				
			} else if(name.compareTo(INTERFACE)==0){
				int v=-1;
				if( foundCommons.containsKey(INTERFACE))	{
					v = foundCommons.get(INTERFACE);
					if(v==2)
						throw new ConfigurationException("Too much "+INTERFACE+" Entries (only 2 in and out)");
					else	{
						foundCommons.remove(INTERFACE);
						foundCommons.put(INTERFACE, 2);
					}
				}	else	{
					foundCommons.put(INTERFACE, 1);
					v=1;
				}
				String direction= node.getAttributes().getNamedItem("direction").getNodeValue();
				if(v==2 && direction.compareTo("in")==0 && interface_in_name!=null){
					throw new ConfigurationException("Interface in was defined twice!");
				} else if(v==2 && direction.compareTo("out")==0 && interface_out_name!=null){
					throw new ConfigurationException("Interface out was defined twice!");
				} else if(direction.compareTo("out")==0)	{
					interface_out_name=node.getAttributes().getNamedItem("name").getNodeValue();
					interface_out_bandwidth = parseToLong(node.getAttributes().getNamedItem("bandwidth").getNodeValue());
				} else if(direction.compareTo("in")==0)	{
					interface_in_name=node.getAttributes().getNamedItem("name").getNodeValue();
					interface_in_bandwidth = parseToLong(node.getAttributes().getNamedItem("bandwidth").getNodeValue());
				}
			} else if(name.compareTo(DSCP_MAPPING)==0){
				checkSingleConfiguration(DSCP_MAPPING);
				Pattern p = Pattern.compile("0x(\\d|[a-f]|[A-F]){2}");

				NodeList cNodeList = node.getChildNodes();
				for(int j=0;j<cNodeList.getLength();j++){
					Node dscp = cNodeList.item(j);
					if(dscp.getNodeName().compareTo("entry")==0){
						String v = dscp.getAttributes().getNamedItem("QCI").getNodeValue();
						int pos = Integer.parseInt(v);
						String dscp_value = dscp.getAttributes().getNamedItem("DSCP").getNodeValue();
						if(!p.matcher(dscp_value).matches())
							throw new ConfigurationException("Can not parse "+dscp_value+" to a hax Value");
						dscpmap[pos]=dscp_value;
					}
				}
			} else if(name.compareTo(TC_ID)==0){
				checkSingleConfiguration(TC_ID);
				TC_ID_defaultid = Integer.parseInt(node.getAttributes().getNamedItem("defaultid").getNodeValue());
				TC_ID_startid = Integer.parseInt(node.getAttributes().getNamedItem("startid").getNodeValue());
				TC_ID_stoptid = Integer.parseInt(node.getAttributes().getNamedItem("stopid").getNodeValue());
			} else if(name.compareTo(UNCLASSIFIED_TRAFFIC)==0){
				checkSingleConfiguration(UNCLASSIFIED_TRAFFIC);
				unclassifiedTraffic_min = (int)(parseToLong(node.getAttributes().getNamedItem("min").getNodeValue()));
				unclassifiedTraffic_max = (int)(parseToLong(node.getAttributes().getNamedItem("max").getNodeValue()));
			} else if(name.compareTo(BANDWIDTH_USAGE_LOGGING)==0){
				checkSingleConfiguration(BANDWIDTH_USAGE_LOGGING);
				String intervallString = node.getAttributes().getNamedItem("intervall").getNodeValue();
				if(intervallString.contains("msec"))
					intervall=Integer.parseInt(intervallString.substring(0,intervallString.length()-4));
				else
					intervall=Integer.parseInt(intervallString.substring(0,intervallString.length()-3))*1000;
			} else if(name.compareTo(EXPIRES)==0){
				checkSingleConfiguration(EXPIRES);
				// TODO
			} else {
				//System.err.println("Ignore unsupported tag "+name+" in configuration file!");
			}
		}
	}
	/**
	 * verifies that a Configuration exists only once
	 * @param tagname	name of the Configuration
	 * @throws ConfigurationException	thrown if the configuration was already found
	 */
	private void checkSingleConfiguration(String tagname) throws ConfigurationException	{
		if( foundCommons.containsKey(tagname))
			throw new ConfigurationException("Double entry of "+tagname);
		foundCommons.put(tagname, 1);
	}
	/**
	 * parses a text with postfic notation of M or k for Mega and Kilo to correct values
	 * @param size
	 * @return
	 * @throws ConfigurationException 
	 */
	private long parseToLong(String size) throws ConfigurationException	{
		char last= size.charAt(size.length()-1);
		if(last>=0 && last<=9)
			return Long.parseLong(size);
		else if(last=='K'||last=='k')
			return (long)(1000*Double.parseDouble(size.substring(0, size.length()-1)));
		else if(last=='M'||last=='m')
			return (long)(1000000*Double.parseDouble(size.substring(0, size.length()-1)));
		else if(last=='G'||last=='g')
			return (long)(1000000000*Double.parseDouble(size.substring(0, size.length()-1)));
		else
			throw new ConfigurationException("Could not identify Multiplikator for "+size);

	}
	
	/**
	 * parses the predefined rules
	 * @param nodelist	the list of all rule nodes
	 * @throws ConfigurationException	is thrown if a parser error occures
	 * @throws DOMException				is thrown if a parser error occures
	 */
	private void parseStaticRules(NodeList nodelist) throws ConfigurationException, DOMException	{
		staticRules= new Hashtable<String, QoSRule>();
		for(int i=0;i<nodelist.getLength();i++){
			Node rule = nodelist.item(i);
			if(rule.getNodeName().compareTo("rule")==0)	{
				String name = rule.getAttributes().getNamedItem("name").getNodeValue();
				int qci = Integer.parseInt(rule.getAttributes().getNamedItem("QCI").getNodeValue());
				boolean active = rule.getAttributes().getNamedItem("active").getNodeValue().compareTo("default")==0;
				boolean up = false;
				boolean down = false;
				ChargingRuleDefinition crd = new ChargingRuleDefinition();
				crd.setChargingRuleName(new ChargingRuleName(name));
				QoSInformation qos = new QoSInformation();
				qos.setQoSClassIdentifier(new QoSClassIdentifier(qci));
				crd.setQoSInformation(qos);
				NodeList streams = rule.getChildNodes();
				for(int j = 0;j<streams.getLength();j++){
					Node stream = streams.item(j);
					if(stream.getNodeName().compareTo("uplink")==0){
						up=true;
						long g = parseToLong(stream.getAttributes().getNamedItem("GuaranteedBitrate").getNodeValue());
						long max = parseToLong(stream.getAttributes().getNamedItem("MaxRequestedBandwidth").getNodeValue());
						qos.setGuaranteedBitrateUL(new GuaranteedBitrateUL(g));
						qos.setMaxRequestedBandwidthUL(new MaxRequestedBandwidthUL(max));
						Node text = stream.getFirstChild();
						if(text.getNodeName().compareTo("#text")==0)	{
							FlowDescription f = new FlowDescription(text.getNodeValue().trim());
							crd.addFlowDescription(f);
						}
							
					} else if (stream.getNodeName().compareTo("downlink")==0 )	{
						down=true;
						long g = parseToLong(stream.getAttributes().getNamedItem("GuaranteedBitrate").getNodeValue());
						long max = parseToLong(stream.getAttributes().getNamedItem("MaxRequestedBandwidth").getNodeValue());
						qos.setGuaranteedBitrateDL(new GuaranteedBitrateDL(g));
						qos.setMaxRequestedBandwidthDL(new MaxRequestedBandwidthDL(max));
						Node text = stream.getFirstChild();
						if(text.getNodeName().compareTo("#text")==0)	{
							FlowDescription f = new FlowDescription(text.getNodeValue().trim());
							crd.addFlowDescription(f);
						}
					}
				}
				if(up && down)
					crd.setFlowStatus(new FlowStatus(FlowStatus.ENABLED));
				else if(up && !down)
					crd.setFlowStatus(new FlowStatus(FlowStatus.ENABLED_UPLINK));
				else if(!up && down)
					crd.setFlowStatus(new FlowStatus(FlowStatus.ENABLED_DOWNLINK));
				else
					throw new ConfigurationException("No flow is defined in rule "+name);
				QoSRule q = new QoSRule(null);
				q.setRule(crd);
				q.setActive(active);
				if(staticRules.containsKey(name))
					throw new ConfigurationException("The static rulename "+name+" was alreay defined!");
				staticRules.put(name,q);
			}
		}
	}
	/**
	 * queries the configured bandwidth in uplink
	 * @return	configured bandwidth uplink
	 */
	public long getInterface_in_bandwidth() {
		return interface_in_bandwidth;
	}
	/**
	 * queries the configured bandwidth in downlink
	 * @return	configured bandwidth downlink
	 */
	public long getInterface_out_bandwidth() {
		return interface_out_bandwidth;
	}
	/**
	 * is logging info set
	 * @return	true if logging info is set
	 */
	public boolean isLogging_info() {
		return logging_info;
	}
	/**
	 * is logging messages is set
	 * @return	true if logging messages is set
	 */
	public boolean isLogging_message() {
		return logging_message;
	}
}
