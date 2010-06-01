/* 
* PEPApplication.java
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

import at.ac.tuwien.ibk.biqini.pep.Exception.ConfigurationMissingException;
import at.ac.tuwien.ibk.biqini.pep.Exception.TrafficControlException;
import at.ac.tuwien.ibk.biqini.pep.gui.PEPGUI;
import de.fhg.fokus.diameter.DiameterPeer.DiameterPeer;
/**
 * The PEP Application
 * 
 * Executeable needs two configuartion files
 * *) Diameter Configuration
 * *) PEP Configuration
 * 
 * @author Marco Happenhofer
 *
 */
public class PEPApplication {

	/** The logger */
	private static final Logger LOGGER = Logger.getLogger(PEPApplication.class);
	/** Application name */
	private static final String APPLICATIONNAME="Policy Enforcement Point BIQINI (IBK) Marco Happenhofer, Christoph Egger";
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length !=2) {
			LOGGER.error("Provide one XML config files as input");
			LOGGER.error("PEPApplication <diameter_xmlFile> <pep_xmlFile>");
			System.exit(0);
		} else {
			LOGGER.info(APPLICATIONNAME+" Starting...");
			// parse configuration for PEP
			try {
				PEPXMLConfig.parse(args[1]);
			} catch (Exception e2) {
				LOGGER.error("Could not parse pep configuration file. See following details ..");
				LOGGER.error(e2.getMessage());
				e2.printStackTrace();
				System.exit(-1);
			}
			PEPXMLConfig config=null;
			try {
				config = PEPXMLConfig.getInstance();
			} catch (ConfigurationMissingException e2) {
				LOGGER.error("Could not access Configurations, because not xml was provided until now!");
				System.exit(-1);
			}
			
			// parse and setup of Diameter Stack
			String xmlFilename = args[0];
			File xml = new File(xmlFilename);
			if(!xml.exists())
			{
				LOGGER.error("Can not find File "+xml.getAbsoluteFile());
				System.exit(-1);
			}
			DiameterPeer diameterPeer = new DiameterPeer();
			diameterPeer.configure(xmlFilename, true);

			// load application logic
			PEPLogic pepLogic=null;
			try {
				pepLogic = new PEPLogic(diameterPeer, config.getInterface_in_bandwidth(),config.getInterface_out_bandwidth());
				pepLogic.configureLogging(config.isLogging_info(),config.isLogging_message());
			} catch (Exception e2) {
				LOGGER.error("Error occured while setting up enviroment ("+e2.getMessage()+")");
				try {
					pepLogic.clearTrafficControl();
				} catch (TrafficControlException e1) {
					e1.printStackTrace();
				}
				diameterPeer.shutdown();
				try {
					Thread.sleep(750);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
			//pepLogic.diameterPeer= pepLogic.setDiameterPeer(diameterPeer);
			diameterPeer.addEventListener(pepLogic);
			
			//start Traffic Control
			boolean exitflag = false;
			try {
				pepLogic.setupTrafficControl();
			} catch (TrafficControlException e1) {
				LOGGER.error(e1.getMessage());
				exitflag = true;
			}
			
			// Start Terminal I/O
			String read = null;
			while(!exitflag)	{
				System.out.println("Free down/up :"+pepLogic.getFreeDown()/1000+"/"+pepLogic.getFreeUp()/1000+" kbit/sec");
				System.out.println(getCommandList());
				read = readString();
				read = read.toUpperCase();
				if(read.compareTo("E")==0)	{
					// Exit applications
					exitflag = true;
					//TODO abort all Sessions
					try {
						pepLogic.clearTrafficControl();
					} catch (TrafficControlException e1) {
						e1.printStackTrace();
					}
					diameterPeer.shutdown();
					try {
						Thread.sleep(750);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else if(read.compareTo("D")==0){
					// toggle debug messaging
					int level = pepLogic.getcurrentLogLevel();
					if((level & PEPLogic.PRINT_DEBUG)==0)	{
						System.out.println("Enable Debug Messages!");
						level = level + PEPLogic.PRINT_DEBUG;
					}else	{
						System.out.println("Disable Debug Messages!");
						level = level - PEPLogic.PRINT_DEBUG;
					}
					pepLogic.configureLogging(level);
				} else if(read.compareTo("I")==0){
					// toggle info messaging
					int level = pepLogic.getcurrentLogLevel();
					if((level & PEPLogic.PRINT_INFO)==0){
						System.out.println("Enable Info Messages!");
						level = level + PEPLogic.PRINT_INFO;
					}else{
						System.out.println("Disable Info Messages!");
						level = level - PEPLogic.PRINT_INFO;
					}
					pepLogic.configureLogging(level);
				} else if(read.compareTo("G")==0){
					PEPGUI.startGUI(QoSRule.allRules);
				} else if(read.compareTo("A")==0){
					// remove a certain session
					PEPSession[] all = pepLogic.getAllSessions();
					if(all.length>0)
					{
						System.out.println("Select a Session:\n");
						for(int i = 0;i<all.length;i++)
						{
							System.out.println(i+"\t"+all[i]+"\n");
						}
						System.out.println("Your Choice <id> <d|r> for details or remove (any other input aborts session remove):\n");
						read = readString();
						try {
							StringTokenizer st = new StringTokenizer(read);
							int pos = Integer.parseInt(st.nextToken());
							PEPSession session = all[pos];
							String option = st.nextToken(); 
							if (option.compareToIgnoreCase("r")==0)	{
								//delete session
								session.kickSession();
							}
							else if(option.compareToIgnoreCase("d")==0){
								// details to a session
								QoSRule[] rules = session.getAllRules();
								System.out.println("Select a rule to delete:\n");
								for(int i = 0;i<rules.length;i++)
								{
									System.out.println(i+"\t"+rules[i]+"\n");
								}
								System.out.println("Your Choice <id> to remove (any other input aborts rule remove):\n");
								read = readString();
								pos = Integer.parseInt(read);
								session.kickRule(rules[pos].getRuleName());
							}
							
						} catch (Exception e) {
							System.out.println("Could not perform action:\n" +e.getMessage());
						}
					}
					else
						System.out.println("No active Sessions");
				}
			}
			LOGGER.debug(APPLICATIONNAME+" Stopping...");
			System.exit(0);
		}
	}
	public static String getCommandList()	{
		StringBuffer sbf = new StringBuffer();
		sbf.append("A\tActual Sessions\n");
		sbf.append("D\tToggle Debug Messaging\n");
		sbf.append("I\tToggle Info Messaging\n");
		sbf.append("G\tStart GUI to observe Bitrates\n");
		sbf.append("E\tExit Policy Enforcement Point\n");
		sbf.append("=======================================\n");
		return sbf.toString();
	}
	
	static String readString()
	{
		try {
			java.io.BufferedReader stdin = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			String line = stdin.readLine();
			return line.trim();
		}

		catch (java.io.IOException e) { System.out.println(e); }
		catch (NumberFormatException e) { System.out.println(e); }

		return "";
	}

}
