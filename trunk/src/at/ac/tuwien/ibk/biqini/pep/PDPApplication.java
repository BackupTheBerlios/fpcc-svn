/* 
* PDPApplication.java
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
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.DestinationHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.DestinationRealm;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginRealm;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SessionID;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleDefinition;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleInstall;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleRemove;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowStatus;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.GuaranteedBitrateDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.GuaranteedBitrateUL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthUL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSClassIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSInformation;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxRAR;
import de.fhg.fokus.diameter.DiameterPeer.DiameterPeer;
import de.fhg.fokus.diameter.DiameterPeer.EventListener;
import de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage;
import de.fhg.fokus.diameter.DiameterPeer.transaction.TransactionListener;

/**
 * A test application to communicate with the PEP
 * @author Marco Happenhofer
 *
 */
public class PDPApplication implements EventListener, TransactionListener{

	private static final Logger LOGGER = Logger.getLogger(PDPApplication.class);

	static final int MAX_NUMBER_SESSIONS=3;
	static final int MAX_NUMBER_FLOWS=3;
	// IP address of the PEP
	static final String PEP_HOST="192.168.1.5";
	// host name of the PEP
	static final String PEP_HOST_FQDN="pep-user.open-ims.test";
	
	static final String HOSTS[] = {"192.168.1.7","192.168.1.8"};
	//static final String HOSTS[] = {"0.0.0.0","0.0.0.0"};
	static final String PERMIT_IN[] ={"permit in 17 from "+HOSTS[0]+" to "+HOSTS[1]+" 15001",
		"permit in 17 from "+HOSTS[0]+" to "+HOSTS[1]+" 15001",
		"permit in 17 from "+HOSTS[0]+" to "+HOSTS[1]+" 15001"};
	static final String PERMIT_OUT[] ={"permit out 17 from "+HOSTS[1]+" 15001 to "+HOSTS[0]+"",
		"permit out 17 from "+HOSTS[1]+" 15001 to "+HOSTS[0]+"",
		"permit out 17 from "+HOSTS[1]+" 15001 to "+HOSTS[0]+""};

	public PDPApplication(){
		
	}

	public void startWork(){
		

	}
	static String read()
	{
		try {
			java.io.BufferedReader stdin = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			String line = stdin.readLine();
			return line;
		}

		catch (java.io.IOException e) { System.out.println(e); }
		catch (NumberFormatException e) { System.out.println(e); }

		return "";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Starting PDPApplication...");
		if (args.length != 1) {
			LOGGER.error("Provide one XML config file as input");
			System.exit(0);
		} 
		String xmlFilename = args[0];
		File xml = new File(xmlFilename);
		if(!xml.exists())
		{
			LOGGER.error("Can not find File "+xml.getAbsoluteFile());
			System.exit(0);
		}
		LOGGER.debug("Opening file "+xml.getAbsoluteFile());
		DiameterPeer diameterPeer = new DiameterPeer();
		diameterPeer.configure(xmlFilename, true);
		PDPApplication pdpa = new PDPApplication();
		diameterPeer.addEventListener(pdpa);
		System.out.println("PDPApplication started...");
		
		SessionID sessions[] = new SessionID[MAX_NUMBER_SESSIONS];
		for(int i =0;i<MAX_NUMBER_SESSIONS;i++)	{
			sessions[i]= new SessionID("SessionID"+i);
		}
		ChargingRuleInstall cri[] = new ChargingRuleInstall[MAX_NUMBER_FLOWS+2];
		ChargingRuleRemove crr[] = new ChargingRuleRemove[MAX_NUMBER_FLOWS+2];
		for(int i = 0;i<MAX_NUMBER_FLOWS;i++){
			ChargingRuleInstall ci = new ChargingRuleInstall();
			ChargingRuleRemove cr = new ChargingRuleRemove();
			
			ChargingRuleDefinition cd = new ChargingRuleDefinition();
			ChargingRuleName name = new ChargingRuleName("ChargingRuleName"+i);
			cd.setChargingRuleName(name);
			cr.addChargingRuleName(name);
			QoSInformation q = new QoSInformation();
			q.setQoSClassIdentifier(new QoSClassIdentifier(i+1));
			q.setMaxRequestedBandwidthDL(new MaxRequestedBandwidthDL((i+1)*1050000));
			if(i!=2)
				q.setMaxRequestedBandwidthUL(new MaxRequestedBandwidthUL((i+1)*1050000));
			q.setGuaranteedBitrateDL(new GuaranteedBitrateDL((i+1)*1000000));
			if(i!=2)
				q.setGuaranteedBitrateUL(new GuaranteedBitrateUL((i+1)*1000000));
			cd.setQoSInformation(q);
			if(i!=2)
				cd.setFlowStatus(new FlowStatus(FlowStatus.ENABLED));
			else
			cd.setFlowStatus(new FlowStatus(FlowStatus.ENABLED_DOWNLINK));
			if(i!=2)
				cd.addFlowDescription(new FlowDescription(PERMIT_IN[i]));
			cd.addFlowDescription(new FlowDescription(PERMIT_OUT[i]));
			ci.addChargingRuleDefinition(cd);
			cri[i]=ci;
			crr[i]=cr;
		}
		ChargingRuleInstall ci = new ChargingRuleInstall();
		ChargingRuleRemove cr = new ChargingRuleRemove();
		ci.addChargingRuleName(new ChargingRuleName("sip"));
		cr.addChargingRuleName(new ChargingRuleName("sip"));
		cri[3]=ci;
		crr[3]=cr;
		ci = new ChargingRuleInstall();
		cr = new ChargingRuleRemove();
		ci.addChargingRuleName(new ChargingRuleName("sip_restricted"));
		cr.addChargingRuleName(new ChargingRuleName("sip_restricted"));
		cri[4]=ci;
		crr[4]=cr;
		while(true){
			try {
				GxRAR rar = new GxRAR();
				rar.setDestinationHost(new DestinationHost(PEP_HOST_FQDN));
				rar.setDestinationRealm(new DestinationRealm("open-ims.test"));
				rar.setOriginHost(new OriginHost(diameterPeer.FQDN));
				rar.setOriginRealm(new OriginRealm(diameterPeer.Realm));
				
				System.out.println("To send a RAR specify the session id");
				for(int i =0;i<MAX_NUMBER_SESSIONS;i++)
					System.out.println(i+" "+sessions[i].getUTF8String());
				System.out.println("e=exit");
				
				String what = read();
				if(what.compareToIgnoreCase("e")==0)
					System.exit(0);
				rar.setSessionId(sessions[Integer.parseInt(what)]);
				System.out.println("Specify the FlowDescriptions in the Install");
				for(int i =0;i<MAX_NUMBER_FLOWS+2;i++)
					System.out.println(i+" "+cri[i].toString());
				what = read();
				StringTokenizer st = new StringTokenizer(what);
				while(st.hasMoreElements())	{
					rar.addChargingRuleInstall(cri[Integer.parseInt(st.nextToken())]);
				}
				
				System.out.println("Specify the FlowDescriptions in the Remove");
				for(int i =0;i<MAX_NUMBER_FLOWS+2;i++)
					System.out.println(i+" "+crr[i].toString());
				what = read();
				st = new StringTokenizer(what);
				while(st.hasMoreElements())	{
					rar.addChargingRuleRemove(crr[Integer.parseInt(st.nextToken())]);
				}
				System.out.println(rar);
				
				diameterPeer.enableTransactions(10, 1);
				diameterPeer.sendRequestTransactional(PEP_HOST,rar,pdpa);
				
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	

	public void receiveAnswer(String FQDN, DiameterMessage request,
			DiameterMessage answer) {
		System.out.println(answer);
		
	}

	public void timeout(DiameterMessage request) {
		System.err.println("Time out!");
		
	}

	public void recvMessage(String FQDN, DiameterMessage msg) {
		// TODO Auto-generated method stub
		
	}

}
