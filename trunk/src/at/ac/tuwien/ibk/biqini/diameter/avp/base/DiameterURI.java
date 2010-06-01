/* 
* DiameterURI.java
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
 * The DiameterURI MUST follow the Uniform Resource Identifiers (URI)
 *    syntax [URI] rules specified below:
 *
 *    "aaa://" FQDN [ port ] [ transport ] [ protocol ]
 *
 *                    ; No transport security
 *
 *    "aaas://" FQDN [ port ] [ transport ] [ protocol ]
 *
 *                    ; Transport security used
 *
 *    FQDN               = Fully Qualified Host Name
 *
 *    port               = ":" 1*DIGIT
 *
 *                    ; One of the ports used to listen for
 *                    ; incoming connections.
 *                    ; If absent,
 *                    ; the default Diameter port (3868) is
 *                    ; assumed.
 *
 *    transport          = ";transport=" transport-protocol
 *
 *                    ; One of the transports used to listen
 *                    ; for incoming connections.  If absent,
 *                    ; the default SCTP [SCTP] protocol is
 *                    ; assumed.  UDP MUST NOT be used when
 *                    ; the aaa-protocol field is set to
 *                    ; diameter.
 *
 *    transport-protocol = ( "tcp" / "sctp" / "udp" )
 *
 *    protocol           = ";protocol=" aaa-protocol
 *                    ; If absent, the default AAA protocol
 *                    ; is diameter.
 *
 *    aaa-protocol       = ( "diameter" / "radius" / "tacacs+" )
 *
 *    The following are examples of valid Diameter host identities:
 *
 *    aaa://host.example.com;transport=tcp
 *    aaa://host.example.com:6666;transport=tcp
 *    aaa://host.example.com;protocol=diameter
 *    aaa://host.example.com:6666;protocol=diameter
 *    aaa://host.example.com:6666;transport=tcp;protocol=diameter
 *    aaa://host.example.com:1813;transport=udp;protocol=radius
 * 
 * RFC3588
 * 
 * @author mhappenhofer
 *
 */
public class DiameterURI extends OctetString {

	public static Pattern DIAMETERURI;
	/**
	 * Parser for the Full Qualified Name
	 */
	public static Pattern FQN;
	/**
	 * Parser for the transport protocol
	 */
	public static Pattern TRANSPORT;
	/**
	 * Parser for the AAA Protocol
	 */
	public static Pattern PROTOCOL;
	
	public static final String UNSECURE_TRANSPORT = "aaa";
	public static final String SECURE_TRANSPORT = "aaas";
	public static final String SEPERATOR ="://";
	
	public static final String TRANSPORT_PROTOCOLS[]={"udp","tcp","sctp"};
	public static final String AAA_PROTOCOLS[]={"diameter","radius","tacacs"};
	
	static{
		//String text = "\\w";
		String text = "[^\\.\\s\\:\\;]";
		String fqn = text+"+(\\."+text+"+)*";
		String port = "\\d{1,5}";
		StringBuffer sbf = new StringBuffer();
		sbf.append("(");
		for(int i = 0;i<TRANSPORT_PROTOCOLS.length;i++)
		{
			sbf.append(TRANSPORT_PROTOCOLS[i]);
			if(i!=TRANSPORT_PROTOCOLS.length-1)
				sbf.append("|");
		}
		sbf.append(")");
		String transportProtocol = sbf.toString();//"(tcp|sctp|udp)";
		String transport = ";transport="+transportProtocol;
		sbf = new StringBuffer();
		sbf.append("(");
		for(int i = 0;i<AAA_PROTOCOLS.length;i++)
		{
			sbf.append(AAA_PROTOCOLS[i]);
			if(i!=AAA_PROTOCOLS.length-1)
				sbf.append("|");
		}
		sbf.append(")");
		String protocolName = sbf.toString();//"(diameter|radius|tacacs)";
		String protocol = ";protocol="+protocolName;
		FQN = Pattern.compile(fqn);
		TRANSPORT = Pattern.compile(transportProtocol);
		PROTOCOL = Pattern.compile(protocolName);
		DIAMETERURI = Pattern.compile("("+UNSECURE_TRANSPORT+"|"+SECURE_TRANSPORT+")"+SEPERATOR+"("+fqn+")(\\:"+port+")?("+transport+")?"+"("+protocol+")?");
	}
	
	
	/**
	 * the full Qualified Name of the Diameter Peer
	 */
	private String fqn=null;
	/**
	 * the used Transport protocol
	 */
	private String transport=null; 
	/**
	 * the used AAA Protocol
	 */
	private String protocol=null;
	/**
	 * secure Transport ?
	 */
	private boolean secure = false;
	/**
	 * communiction port
	 */
	private int port = 0;


	private String lastPasrtString="";
	
	public DiameterURI() {
		super();
	}


	public DiameterURI(int Code, boolean Mandatory, int Vendor_id) {
		super(Code, Mandatory, Vendor_id);
	}


	public DiameterURI(String octetString) {
		super();
		this.setOctetString(octetString);
		try {
			reparseString();
		} catch (IllegalAVPDataException e) {
			e.printStackTrace();
		}
	}

	public void setDiameterURI(String _diameterURI){
		this.setOctetString(_diameterURI);
		try {
			reparseString();
		} catch (IllegalAVPDataException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * the ByteArray will be rewritten
	 */
	private void updateString()	{
		StringBuffer sbf = new StringBuffer();
		if(secure)
			sbf.append(SECURE_TRANSPORT);
		else
			sbf.append(UNSECURE_TRANSPORT);
		sbf.append(SEPERATOR);
		sbf.append(fqn);
		if(port>0)
			sbf.append(":"+port);
		if(transport!=null)
			sbf.append(";transport="+transport);
		if(protocol!=null)
			sbf.append(";protocol="+protocol);
		this.setOctetString(sbf.toString());
	}
	/**
	 * reparse the dataArray and stores to temporary member variables
	 * @throws IllegalAVPDataException if the String stored in the ByteArray could not be parst
	 */
	private void reparseString() throws IllegalAVPDataException	{
		if(lastPasrtString.compareTo(this.getOctetString())==0)
			return;
		Matcher m = DIAMETERURI.matcher(this.getOctetString());
		if(m.matches())
		{
			for(int i = 0;i<=m.groupCount();i++)
			{
				//System.out.println(i+"\t"+m.group(i));
				switch(i)
				{
				case 0:
					//complete String
					break;
				case 1:
					if(m.group(i).compareTo(SECURE_TRANSPORT)==0)
						secure=true;
					else
						secure = false;
					break;
				case 2:
					fqn = m.group(i);
					break;
				case 3:
					//FIXME The parser might have some problems with the fqn
					break;
				case 4:
					if(m.group(i)==null)
						port = 0;
					else
					{
						try {
								port=Integer.valueOf(m.group(i).substring(1));
							} catch (NumberFormatException e) {
								throw new IllegalAVPDataException("could not parse the Port of the DiameterURI(this Exception should never happen!)");
							}
					}
					break;
				case 5:
					//the complete transport token
					break;
				case 6:
					transport=m.group(i);
					break;
				case 7:
					//the complete protocol
					break;
				case 8:
					protocol = m.group(i);
					break;
				}
			}
		}
		else
			throw new IllegalAVPDataException("could not parse the String to DiameterURI ("+this.getOctetString()+")");
	}
	
	/**
	 * Queries the FQN of this Diameter URI
	 * @return	Full Qualified Name
	 * @throws IllegalAVPDataException if the String stored in the ByteArray could not be parst
	 */
	public String getFqn() throws IllegalAVPDataException {
		reparseString();
		return fqn;
	}
	/**
	 * sets the FQN
	 * @param fqn	Full Qualified Name
	 * @throws IllegalAVPDataException if the fqn does not match the parser
	 */
	public void setFqn(String _fqn) throws IllegalAVPDataException {
		if(!FQN.matcher(_fqn).matches())
			throw new IllegalAVPDataException("Parser does not accpet the FQN "+_fqn);
		this.fqn = _fqn;
		updateString();
	}


	/**
	 * Queries the transport Protocol (udp, tcp or sctp)
	 * @return	transport protocol used for this URI 
	 * @throws IllegalAVPDataException if the String stored in the ByteArray could not be parst
	 */
	public String getTransport() throws IllegalAVPDataException {
		reparseString();
		return transport;
	}
	/**
	 * configures the used transport protocol (udp, tcp or sctp)
	 * @param transport	transport protocol
	 * @throws IllegalAVPDataException if the transport protocol is not acceptable
	 */
	public void setTransport(String _transport) throws IllegalAVPDataException {
		if(!TRANSPORT.matcher(_transport).matches())
			throw new IllegalAVPDataException("Parser does not accpet the transport protocol "+_transport);
		this.transport = _transport;
		updateString();
	}

	/**
	 * Queries the used protocol for communication as diameter, radius or tacacs
	 * @return	protocol name (not transport protocol!)
	 * @throws IllegalAVPDataException if the String stored in the ByteArray could not be parst
	 */
	public String getProtocol() throws IllegalAVPDataException {
		reparseString();
		return protocol;
	}
	/**
	 * Configures the used protocol (not transport!) (diameter, radius or tacacs)
	 * @param protocol	used protocol (not transport!)
	 * @throws IllegalAVPDataException if the protocol is not one of the accepted
	 */
	public void setProtocol(String _protocol) throws IllegalAVPDataException {
		if(!PROTOCOL.matcher(_protocol).matches())
			throw new IllegalAVPDataException("Parser does not accpet the protocol "+_protocol);
		this.protocol = _protocol;
		updateString();
	}

	/**
	 * indicates if this communication is secured
	 * @return	true if this communication is secured
	 * @throws IllegalAVPDataException if the String stored in the ByteArray could not be parst
	 */
	public boolean isSecure() throws IllegalAVPDataException {
		reparseString();
		return secure;
	}
	/**
	 * configures the secure flag of this communication
	 * @param secure	true to indicate that this communication is secured
	 */
	public void setSecure(boolean _secure) {
		this.secure = _secure;
		updateString();
	}

	/**
	 * queries the used port for this communication
	 * @return	the associated port for the service
	 * @throws IllegalAVPDataException if the String stored in the ByteArray could not be parst
	 */
	public int getPort() throws IllegalAVPDataException {
		reparseString();
		return port;
	}
	/**
	 * configures the port on which the remote peer is listening
	 * @param port	the port to which the peer should connect
	 * @throws IllegalAVPDataException if the port is not in the accepted range
	 */
	public void setPort(int _port) throws IllegalAVPDataException {
		if(_port<0 || _port > 65536)
			throw new IllegalAVPDataException("The pport must be in the range of 0 - 65536 (got "+_port+")");
		this.port = _port;
		updateString();
	}
	
	/* (non-Javadoc)
	 * @see de.fhg.fokus.diameter.DiameterPeer.data.AVP#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append(commonInfo());
		sbf.append(" DiameterURI="+this.getOctetString());
		return sbf.toString();
	}
}
