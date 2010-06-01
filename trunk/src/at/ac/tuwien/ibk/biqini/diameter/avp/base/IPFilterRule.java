/* 
* IPFilterRule.java
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
package at.ac.tuwien.ibk.biqini.diameter.avp.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.ac.tuwien.ibk.biqini.diameter.avp.base.exceptions.IllegalAVPDataException;

/**
 * The IPFilterRule format is derived from the OctetString AVP Base
 *    Format.  It uses the ASCII charset.  Packets may be filtered based
 *    on the following information that is associated with it:
 *
 *       Direction                          (in or out)
 *       Source and destination IP address  (possibly masked)
 *       Protocol
 *       Source and destination port        (lists or ranges)
 *       TCP flags
 *       IP fragment flag
 *       IP options
 *       ICMP types
 *
 *    Rules for the appropriate direction are evaluated in order, with
 *    the first matched rule terminating the evaluation.  Each packet is
 *    evaluated once.  If no rule matches, the packet is dropped if the
 *    last rule evaluated was a permit, and passed if the last rule was
 *    a deny.
 *
 *    IPFilterRule filters MUST follow the format:
 *
 *       action dir proto from src to dst [options]
 *
 *       action       permit - Allow packets that match the rule.
 *                     deny   - Drop packets that match the rule.
 *
 *       dir          "in" is from the terminal, "out" is to the
 *                     terminal.
 *
 *       proto        An IP protocol specified by number.  The "ip"
 *                    keyword means any protocol will match.
 *
 *       src and dst  <address/mask> [ports]
 *
 *                    The <address/mask> may be specified as:
 *                    ipno       An IPv4 or IPv6 number in dotted-
 *                               quad or canonical IPv6 form.  Only
 *                               this exact IP number will match the
 *                               rule.
 *                    ipno/bits  An IP number as above with a mask
 *                               width of the form 1.2.3.4/24.  In
 *                               this case, all IP numbers from
 *                               1.2.3.0 to 1.2.3.255 will match.
 *                               The bit width MUST be valid for the
 *                               IP version and the IP number MUST
 *                               NOT have bits set beyond the mask.
 *                               For a match to occur, the same IP
 *                               version must be present in the
 *                               packet that was used in describing
 *                               the IP address.  To test for a
 *                               particular IP version, the bits part
 *                               can be set to zero.  The keyword
 *                               "any" is 0.0.0.0/0 or the IPv6
 *                               equivalent.  The keyword "assigned"
 *                               is the address or set of addresses
 *                               assigned to the terminal.  For IPv4,
 *                               a typical first rule is often "deny
 *                               in ip! assigned"
 *
 *                    The sense of the match can be inverted by
 *                    preceding an address with the not modifier (!),
 *                    causing all other addresses to be matched
 *                    instead.  This does not affect the selection of
 *                    port numbers.
 *
 *                    With the TCP, UDP and SCTP protocols, optional
 *                    ports may be specified as:
 *
 *                      {port/port-port}[,ports[,...]]
 *
 *                    The '-' notation specifies a range of ports
 *                    (including boundaries).
 *
 *                    Fragmented packets that have a non-zero offset
 *                    (i.e., not the first fragment) will never match
 *                    a rule that has one or more port
 *                    specifications.  See the frag option for
 *                    details on matching fragmented packets.
 *
 *       options:
 *          frag    Match if the packet is a fragment and this is not
 *                  the first fragment of the datagram.  frag may not
 *                  be used in conjunction with either tcpflags or
 *                  TCP/UDP port specifications.
 *
 *          ipoptions spec
 *                  Match if the IP header contains the comma
 *                  separated list of options specified in spec.  The
 *                  supported IP options are:
 *
 *                  ssrr (strict source route), lsrr (loose source
 *                  route), rr (record packet route) and ts
 *                  (timestamp).  The absence of a particular option
 *                  may be denoted with a '!'.
 *
 *          tcpoptions spec
 *                  Match if the TCP header contains the comma
 *                  separated list of options specified in spec.  The
 *                  supported TCP options are:
 *
 *                  mss (maximum segment size), window (tcp window
 *                  advertisement), sack (selective ack), ts (rfc1323
 *                  timestamp) and cc (rfc1644 t/tcp connection
 *                  count).  The absence of a particular option may
 *                  be denoted with a '!'.
 *
 *          established
 *                  TCP packets only.  Match packets that have the RST
 *                  or ACK bits set.
 *
 *          setup   TCP packets only.  Match packets that have the SYN
 *                  bit set but no ACK bit.
 *
 *          tcpflags spec
 *                  TCP packets only.  Match if the TCP header
 *                  contains the comma separated list of flags
 *                  specified in spec.  The supported TCP flags are:
 *
 *                  fin, syn, rst, psh, ack and urg.  The absence of a
 *                  particular flag may be denoted with a '!'.  A rule
 *                  that contains a tcpflags specification can never
 *                  match a fragmented packet that has a non-zero
 *                  offset.  See the frag option for details on
 *                  matching fragmented packets.
 *
 *          icmptypes types
 *                  ICMP packets only.  Match if the ICMP type is in
 *                  the list types.  The list may be specified as any
 *                  combination of ranges or individual types
 *                  separated by commas.  Both the numeric values and
 *                  the symbolic values listed below can be used.  The
 *                  supported ICMP types are:
 *
 *                  echo reply (0), destination unreachable (3),
 *                  source quench (4), redirect (5), echo request
 *                  (8), router advertisement (9), router
 *                  solicitation (10), time-to-live exceeded (11), IP
 *                  header bad (12), timestamp request (13),
 *                  timestamp reply (14), information request (15),
 *                  information reply (16), address mask request (17)
 *                  and address mask reply (18).
 *
 * There is one kind of packet that the access device MUST always
 * discard, that is an IP fragment with a fragment offset of one. This
 * is a valid packet, but it only has one use, to try to circumvent
 * firewalls.
 *
 *    An access device that is unable to interpret or apply a deny rule
 *    MUST terminate the session.  An access device that is unable to
 *    interpret or apply a permit rule MAY apply a more restrictive
 *    rule.  An access device MAY apply deny rules of its own before the
 *    supplied rules, for example to protect the access device owner's
 *    infrastructure.
 *
 * The rule syntax is a modified subset of ipfw(8) from FreeBSD, and the
 * ipfw.c code may provide a useful base for implementations.
 *
 * RFC 3588
 *
 * @author mhappenhofer
 *
 */
public class IPFilterRule extends OctetString {

	public static Pattern HOST;
	//public static Pattern PORTS;
	public static Pattern PORTRANGE;
	public static Pattern SOCKET;
	public static Pattern PROTOCOL;
	public static Pattern IPFILTERRULE;
	
	static{
		String host = "(((\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3}))(\\/(\\d{1,2}))?)";
		String port = "\\d{1,5}";
		String portRange = port+"[,"+port+"]*";
		String protocol = "(\\d{1,3}|ip)";
		String socket = host+"( ("+portRange+"))?";
		PROTOCOL = Pattern.compile(protocol);
		HOST = Pattern.compile(host);
		//PORTS = Pattern.compile(ports);
		PORTRANGE = Pattern.compile(portRange);
		SOCKET = Pattern.compile(socket);
		
		IPFILTERRULE = Pattern.compile("(permit|deny) (in|out) "+protocol+" from "+socket+" to "+socket+"( .+)*");
		//FIXME removed options
		//IPFILTERRULE = Pattern.compile("(permit|deny) (in|out) "+protocol+" from "+socket+" to "+socket);
	}
	
	/**
	 * for defining the action
	 */
	public static final String PERMIT_TOKEN="permit";
	public static final String DENY_TOKEN="deny";
	
	/**
	 * for defining the direction
	 */
	public static final String IN_TOKEN="in";
	public static final String OUT_TOKEN="out";
	
	public static final String TO_TOKEN="to";
	/**
	 * indicates the String which is the actual to the variables
	 */
	private String lastParsedString = "";
	
	/**
	 * is true if this action is a permit action
	 */
	private boolean actionIsPermit = false;
	/**
	 * is true if this rule defines an in direction
	 */
	private boolean directionIsIn = false;
	/**
	 * the protocol which is applied on this rule
	 */
	private String proto=null;

	/**
	 * the source of the flow
	 */
	private String srcHost = null;
	private String srcPort = null;
	/**
	 * the destination of the flow
	 */
	private String dstHost = null;
	private String dstPort = null;
	
	/**
	 * further options
	 */
	private String options = null;
	
	public IPFilterRule() {
		super();
		lastParsedString = "";
	}
	
	/**
	 * @param Code
	 * @param Mandatory
	 * @param Vendor_id
	 */
	public IPFilterRule(int Code, boolean Mandatory, int Vendor_id) {
		super(Code, Mandatory, Vendor_id);
	}

	/**
	 * @param octetString
	 * @throws IllegalAVPDataException 
	 */
	/**
	public IPFilterRule(String octetString){
		super();
		this.setOctetString(octetString);
		lastParsedString = "";
		try {
			this.updateMembers();
		} catch (IllegalAVPDataException e) {
			e.printStackTrace();
		}
	}
	**/
	/**
	 * sets the IPFilterRule with a complete configuration String
	 * @param _rule
	 */
	public void setIPFilterRule(String _rule){
		this.setOctetString(_rule);
		try {
			this.updateMembers();
		} catch (IllegalAVPDataException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * is this rule to permit a flow
	 * @return	true if this rule allows a flow
	 */
	public boolean isActionPermit()	{
		checkUpdating();
		return actionIsPermit;
	}
	/**
	 * is this rule to deny a flow
	 * @return	true if this rule rejects a flow 
	 */
	public boolean isActionDeny()	{
		checkUpdating();
		return !actionIsPermit;
	}
	/**
	 * set the action to permit
	 */
	public void setActionPermit()	{
		if(actionIsPermit!= true)
		{
			actionIsPermit = true;
			updateBytes();
		}
	}
	/**
	 * set the action to deny
	 */
	public void setActionDeny()	{
		if(actionIsPermit!=false)
		{
			actionIsPermit = false;
			updateBytes();
		}
	}
	
	/**
	 * is this rule direction in
	 * @return	true if this rule is in
	 */
	public boolean isDirectionIn()	{
		checkUpdating();
		return directionIsIn;
	}
	/**
	 * is this rule direction out
	 * @return	true if this rule is out
	 */
	public boolean isDirectionOut()	{
		checkUpdating();
		return !directionIsIn;
	}
	/**
	 * set the direction to in
	 */
	public void setDirectionIn()	{
		if(directionIsIn!=true)
		{
			directionIsIn = true;
			updateBytes();
		}
	}
	/**
	 * set the direction to out
	 */
	public void setDirectionOut()	{
		if(directionIsIn!=false)
		{
			directionIsIn = false;
			updateBytes();
		}
	}
	/**
	 * requests the protocols which should be handled by this filter
	 * @return	the protocol number (e.g. UDP 17) or ip if all IP traffic is considered 
	 */
	public String getProto()	{
		checkUpdating();
		return proto;
	}
	/**
	 * sets the protocol which should be applied on this rule
	 * @param _proto	pthe protocol number (e.g. UDP 17) or ip if all IP traffic is considered
	 * @throws IllegalAVPDataException  
	 */
	public void setProto(String _proto) throws IllegalAVPDataException	{
		if(!PROTOCOL.matcher(_proto).matches())
			throw new IllegalAVPDataException("The value "+_proto+" for the protocol is not allowed!");
		if(this.proto == null || _proto.compareTo(this.proto)!=0)
		{
			this.proto=_proto;
			this.updateBytes();
		}
	}
	
	
	public String getSrcHost() {
		checkUpdating();
		return srcHost;
	}
	/**
	 * sets the src Host IP address
	 * the parser accepts x.x.x.x[/xx]
	 * @param srcHost
	 * @throws IllegalAVPDataException	the given String does not match with the parser
	 */
	public void setSrcHost(String srcHost) throws IllegalAVPDataException {
		if(!HOST.matcher(srcHost).matches())
			throw new IllegalAVPDataException("Could not accept the sourceHost "+srcHost+" !");
		if(this.srcHost == null || this.srcHost.compareTo(srcHost)!=0)
		{
			this.srcHost = srcHost;
			updateBytes();
		}
	}
	public String getSrcPort() {
		checkUpdating();
		return srcPort;
	}
	/**
	 * sets the src Port
	 * ATTENTION:
	 * This parser only accepts the 3GPP recommendations
	 * 
	 * the parser accepts port[,port]*
	 * @param srcPort
	 * @throws IllegalAVPDataException	the given String does not match with the parser
	 */
	public void setSrcPort(String srcPort) throws IllegalAVPDataException {
		if(!PORTRANGE.matcher(srcPort).matches())
			throw new IllegalAVPDataException("Could not accept the sourceHost "+srcPort+" !");
		if(this.srcPort == null || this.srcPort.compareTo(srcPort)!=0)
		{
			this.srcPort = srcPort;
			updateBytes();
		}
	}
	public String getDstHost() {
		checkUpdating();
		return dstHost;
	}
	/**
	 * sets the dst Host IP address
	 * the parser accepts x.x.x.x[/xx]
	 * @param dstHost
	 * @throws IllegalAVPDataException	the given String does not match with the parser
	 */
	public void setDstHost(String dstHost) throws IllegalAVPDataException {
		if(!HOST.matcher(dstHost).matches())
			throw new IllegalAVPDataException("Could not accept the sourceHost "+dstHost+" !");
		if(this.dstHost == null || this.dstHost.compareTo(dstHost)!=0)
		{
			this.dstHost = dstHost;
			updateBytes();
		}
	}
	public String getDstPort() {
		checkUpdating();
		return dstPort;
	}
	/**
	 * sets the dst Port
	 * ATTENTION:
	 * This parser only accepts the 3GPP recommendations
	 * 
	 * the parser accepts port[,port]*
	 * @param dstPort
	 * @throws IllegalAVPDataException	the given String does not match with the parser
	 */
	public void setDstPort(String dstPort) throws IllegalAVPDataException {
		if(!PORTRANGE.matcher(dstPort).matches())
			throw new IllegalAVPDataException("Could not accept the sourceHost "+dstPort+" !");
		if(this.dstPort == null || this.dstPort.compareTo(dstPort)!=0)
		{
			this.dstPort = dstPort;
			updateBytes();
		}
	}
	
	public String getOptions() {
		checkUpdating();
		return options;
	}
	/**
	 * sets the options
	 * all tokens are allowed!
	 * @param options
	 */
	public void setOptions(String options) {
		if(this.options == null || this.options.compareTo(options)!=0)
		{
			this.options = options;
			updateBytes();
		}
	}
	
	
	/**
	 * should be called if the rule is manipulated but the byte array is not up to date
	 */
	private void updateBytes()	{
		//recreate the string
		StringBuffer sbf = new StringBuffer();
		//append action
		if (isActionPermit())
			sbf.append(PERMIT_TOKEN);
		if (isActionDeny())
			sbf.append(DENY_TOKEN);
		sbf.append(" ");
		//append direction
		if(isDirectionIn())
			sbf.append(IN_TOKEN);
		if(isDirectionOut())
			sbf.append(OUT_TOKEN);
		sbf.append(" ");
		//append protocol
		sbf.append(proto+" from ");
		//append source
		sbf.append(srcHost+" ");
		if(srcPort!=null)
			sbf.append(srcPort+" ");
		// append to
		sbf.append(TO_TOKEN+" ");
		//append destination
		sbf.append(dstHost+" ");
		if(dstPort!=null)
			sbf.append(dstPort+" ");
		//append options
		if(options!=null)
			sbf.append(options);
		this.setOctetString(sbf.toString());
		lastParsedString=sbf.toString();
	}
	/**
	 * should be called if the byte array is changed, but the member variables not
	 * @throws IllegalAVPDataException 
	 */
	public void updateMembers() throws IllegalAVPDataException	{
		//parse
		String str = this.getOctetString();
		//System.out.println("Parsing!!!");
		//System.out.println(str);
		//System.out.println(IPFILTERRULE.pattern());
		Matcher m = IPFILTERRULE.matcher(str);
		if(m.matches())
		{
			int grpCount = m.groupCount();
			try {
				int value;
				for (int i = 0; i <= grpCount; i++) {
					//System.out.println(i + ": " + m.group(i));
					switch (i) {
					case 1:
						if (m.group(i).compareTo(PERMIT_TOKEN) == 0)
							actionIsPermit=true;
						if (m.group(i).compareTo(DENY_TOKEN) == 0)
							actionIsPermit=false;
						break;
					case 2:
						if (m.group(i).compareTo(IN_TOKEN) == 0)
							directionIsIn=true;
						if (m.group(i).compareTo(OUT_TOKEN) == 0)
							directionIsIn=false;
						break;
					case 3:
						proto = m.group(i);
						break;
					case 4:
						//src Host
						srcHost=m.group(i);
						break;
					case 6:
					case 7:
					case 8:
					case 9:
						// single parts of the IP Address
						value = Integer.parseInt(m.group(i));
						if(value<0 && value>256)
							throw new IllegalArgumentException("The Src IP Address is not a valid Address !");
						break;
					case 10:
					case 11:
					case 12:
						break;
					case 13:
						
						srcPort=m.group(i);
						break;
					case 14:
						//dst Host
						dstHost=m.group(i);
						break;
					case 16:
					case 17:
					case 18:
					case 19:
						// single parts of the IP Address
						value = Integer.parseInt(m.group(i));
						if(value<0 && value>256)
							throw new IllegalArgumentException("The Src IP Address is not a valid Address !");
						break;
					case 20:
					case 21:
					case 22:
						break;
					case 23:
						dstPort=m.group(i);
						break;
					case 24:
						if(m.group(i)!=null)
							options=m.group(i).trim();
						else
							options = null;
						break;
					}
				}
			}  catch(IllegalArgumentException e1)	{
				throw new IllegalAVPDataException(e1.getMessage());
			}
			
			lastParsedString = str;
		}
		else
		{
			throw new IllegalArgumentException("String does not match to the given Pattern "+str);
		}
		
	}
	/**
	 * checks if the member variables are up to date
	 * lastParsedString includes the String corresponding to the current member variables
	 */
	private void checkUpdating()	{
		if(lastParsedString.compareTo(this.getOctetString())!=0)	{
			try {
				this.updateMembers();
			} catch (IllegalAVPDataException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see de.fhg.fokus.diameter.DiameterPeer.data.AVP#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append(commonInfo());
		sbf.append(" IPFilterRule="+this.getOctetString());
		return sbf.toString();
	}
		
}
