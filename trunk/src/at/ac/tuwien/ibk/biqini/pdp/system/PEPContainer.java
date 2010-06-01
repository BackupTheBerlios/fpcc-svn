/* 
* PEPContainer.java
* Christoph Egger
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
package at.ac.tuwien.ibk.biqini.pdp.system;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.fhg.fokus.diameter.DiameterPeer.DiameterPeer;

import at.ac.tuwien.ibk.biqini.pdp.session.PDPSession;

/**
 * 
 * @author Christoph Egger
 * 
 */
public class PEPContainer {
	// private Hashtable<InetAddress, PEP> PEPList;
	private Hashtable<String, Vector<PEP>> User2PEPs;
	Vector<PEP> pepVect;
	/** Configuration DOM */
	Document config;
	/** The logger */
	private static final Logger LOGGER = Logger.getLogger(DiameterPeer.class);

	public PEPContainer(String cfgFile) {
		if (!readConfigFile(cfgFile)) {
			LOGGER.error("PEPContainer: Error parsing config File");
			System.exit(1);
		}
		configure();

	}

	public Vector<PEP> getPEPs(String _framedIPAddress) {
		return User2PEPs.get(_framedIPAddress);
	}

	public void registerSession(PDPSession _pdpSession) {
		Vector<PEP> peps = User2PEPs.get(_pdpSession.getFramedIPAddress());
		for (int i = 0; i < peps.size(); i++) {
			peps.elementAt(i).registerSession(_pdpSession);
		}
	}

	public String getFreeBandwidth() {
		String returnString = "";
		for (int i = 0; i < pepVect.size(); i++) {
			returnString += pepVect.elementAt(i).getFreeBandwidth() + "\n";
		}
		return returnString;

	}

	private void configure() {
		NodeList PEPList;
		Node pepNode;
		User2PEPs = new Hashtable<String, Vector<PEP>>();
		pepVect = new Vector<PEP>();
		PEPList = config.getDocumentElement().getElementsByTagName("PEP");
		int NumOfPEPS = PEPList.getLength();
		pepVect = new Vector<PEP>(NumOfPEPS);
		for (int i = 0; i < NumOfPEPS; i++) {
			pepNode = PEPList.item(i);
			String ip = pepNode.getAttributes().getNamedItem("ip")
					.getNodeValue();
			String domain = pepNode.getAttributes().getNamedItem("domain")
					.getNodeValue();
			long upbw = Long.parseLong(pepNode.getAttributes().getNamedItem("upbw").getNodeValue());
			long dobw = Long.parseLong(pepNode.getAttributes().getNamedItem("dobw").getNodeValue());
			PEP pep = new PEP(ip, domain, upbw, dobw);
			pepVect.add(pep);
			NodeList userList = pepNode.getChildNodes();
			for (int j = 0; j < userList.getLength(); j++) {
				Node userNode = userList.item(j);
				if (userNode.getNodeName() == "User") {
					String userIP = userNode.getAttributes().getNamedItem("ip")
							.getNodeValue();
					Vector<PEP> up = User2PEPs.get(userIP);
					if (up == null) {
						up = new Vector<PEP>(1);
					}
					up.add(pep);
					User2PEPs.put(userIP, up);
				}
			}
		}
	}

	private boolean readConfigFile(String cfgFile) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// factory.setValidating(true);
		// factory.setNamespaceAware(true);
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			config = builder.parse(cfgFile);
			/** Configuration DOM */
			// Document config;
		} catch (SAXException sxe) {
			// Error generated during parsing)
			Exception x = sxe;
			if (sxe.getException() != null)
				x = sxe.getException();
			x.printStackTrace();
			return false;
		} catch (ParserConfigurationException pce) {
			// Parser with specified options can't be built
			pce.printStackTrace();
			return false;
		} catch (IOException ioe) {
			// I/O error
			ioe.printStackTrace();
			return false;
		}
		return true;
	}
	public void printConfig(){
		Enumeration<String> user = User2PEPs.keys();
		while (user.hasMoreElements()){
			String u = user.nextElement();
			System.out.println("PEPs for user at IP: "+u+":");
			Vector<PEP> peps = User2PEPs.get(u);
			for (int i = 0;i<peps.size();i++){
				System.out.println("   IP: "+peps.get(i).getAddress());
				System.out.println("     MaxUP: "+peps.get(i).getMaxBandwidthUP());
				System.out.println("     MaxDW: "+peps.get(i).getMaxBandwidthDOWN());

			}
			System.out.println();
		}
	}

}
