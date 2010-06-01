/* 
* AFApplication.java
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
package at.ac.tuwien.ibk.biqini.af;

import java.io.File;

import org.apache.log4j.Logger;

import at.ac.tuwien.ibk.biqini.af.testCases.AFTestCase;
import at.ac.tuwien.ibk.biqini.af.testCases.TestCase1;
import at.ac.tuwien.ibk.biqini.af.testCases.TestCase10;
import de.fhg.fokus.diameter.DiameterPeer.DiameterPeer;

public class AFApplication {

	/** The logger */
	private static final Logger LOGGER = Logger.getLogger(AFApplication.class);

	private static final String APPLICATIONNAME="Policy Enforcement Point BIQINI (IBK) Marco Happenhofer, Christoph Egger";

	private static final int KILO = 1000;
	private static final int MEGA = 1000*KILO;
	
	private static DiameterPeer diameterPeer;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		newAFDummy(args);
	}
	
	public static void newAFDummy(String[] args){
		if (args.length != 1) {
			LOGGER.error("Provide one XML config files as input");
			System.exit(0);
		} else {
			try {
				diameterPeer = configure(args[0]);
				AFTestCase t[] = {new TestCase1(),new TestCase10()};
				/*AFTestCase t[] ={new TestCase1(),
						new TestCase1(),
						new TestCase2(2,3),
						new TestCase3(),
						new TestCase4(3),
						new TestCase5(),
						new TestCase6(),
						new TestCase7(),
						new TestCase8(),
						new TestCase9(),
						new TestCase10()};*/
				
				//AFTestCase t = new TestCase10();
				//AFTestCase t = new TestCase9();
				//AFTestCase t = new TestCase8();
				//AFTestCase t = new TestCase7();
				//AFTestCase t = new TestCase6();
				//AFTestCase t = new TestCase5();
				//AFTestCase t = new TestCase4(2);
				//AFTestCase t = new TestCase3();
				//AFTestCase t = new TestCase2(1,3);
				//AFTestCase t = new TestCase1();
				for(int i = 0;i< t.length;i++){
					testTestCase(t[i], args[0]);
					Thread.sleep(250);
				}
				terminateDiameterPeer();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.exit(0);
	}
	
	private static boolean testTestCase(AFTestCase testCase, String xml) throws Exception{
		testCase.init(diameterPeer, "192.168.1.50", "open-ims.test");
		if(!testCase.startTest())	{
			System.err.println("Failed!");
			return false;
		}
		testCase.exit();
		return true;
	}
	
	private static void terminateDiameterPeer()	{
		diameterPeer.shutdown();
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static DiameterPeer configure(String filename) throws Exception{
		DiameterPeer diameterPeer = null;
		String xmlFilename = filename;
		File xml = new File(xmlFilename);
		if(!xml.exists())
		{
			LOGGER.error("Can not find File "+xml.getAbsoluteFile());
			throw new Exception("Can not find File "+xml.getAbsoluteFile());
		}
		diameterPeer = new DiameterPeer();
		diameterPeer.configure(xmlFilename, true);
		diameterPeer.enableTransactions(10,1);
		Thread.sleep(1500);
		return diameterPeer;
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

	public static SSDP readSDP()	{
		System.out.println("Local IP:");
		SSDP b = new SSDP(readString());
		System.out.println("AUDIO[Y|N]");
		if(readString().toUpperCase().compareTo("Y")==0)
			b.audio = readMedia(Media.AUDIO);
		System.out.println("VIDEO[Y|N]");
		if(readString().toUpperCase().compareTo("Y")==0)
			b.video = readMedia(Media.VIDEO);
		System.out.println(b.toString());
		return b;
	}
	public static Media readMedia(int type)	  {
		int[] a = {0,1,2,3,5,6,7,8,9,10,11,14,15};
		int[] v = {25,26,31,32};
		System.out.println("Local Port:");
		int port = -1;
		do{
			try {
				port = Integer.parseInt(readString().trim());
			} catch (NumberFormatException e) {

			}
		}while(port <0);
		System.out.println("Bandwidth:");
		int bandwidth = -1;
		do{
			try {
				bandwidth = Integer.parseInt(readString().trim());
			} catch (NumberFormatException e) {

			}
		}while(bandwidth <0);

		System.out.println("Status[a|i]:");
		String status="";
		do{
			status = readString();
			status = status.toUpperCase();
		}while(status.compareTo("A")!=0 && status.compareTo("I")!=0);
		int stat = Media.INACTIVE;
		if(status.compareTo("A")==0)
			stat = Media.ACTIVE;
		int[] codec;
		if(type == Media.AUDIO)
			codec = a;
		else
			codec = v;
		Media ret =  new Media(type,port,codec,bandwidth,stat);
		return ret;
	}

}
