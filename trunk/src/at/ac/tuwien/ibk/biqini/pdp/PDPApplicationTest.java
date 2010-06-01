/* 
* PDPApplicationTest.java
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
package at.ac.tuwien.ibk.biqini.pdp;

import java.io.File;

/**
 * @author Christoph Egger
 *
 */
public class PDPApplicationTest {

	private static PDPLogic logic;

	public PDPApplicationTest(String _diameterConfigFile, String _systemCfgFile){
		System.out.println("Starting PDPApplication...");
		logic = new PDPLogic(_diameterConfigFile, _systemCfgFile);
		Thread logicThread = new Thread(logic, "PDPLogic");
		logicThread.start();
		System.out.println("PDPApplication started...\n");
		doUIWork();


	}

	private void doUIWork(){
		while(true){
			println("Possible options: ");
			println("(1) Show active Sessions");
			println("(2) Kill Session");
			println("(3) Display configuration");
			println("(9) Re-Print this Screen");
			println("(0) Exit");
			int whichWork = read();
			switch (whichWork){
			case 0:
				println("Really exit? (0)");
				int really = read();
				if (really == 0)
					System.exit(0);
			case 1:
				println("Active Sessions: ");
				println(logic.getSessionContainer().toString());
				println(logic.getFreeBandwith());
				break;
			case 2:
				println("Which Session? ");
				println(logic.getSessionContainer().toString());
				int which = read();
				logic.killSession(which-1);
				break;
			case 3:
				logic.printConfig();
			case 9:
				println("Re-Printing...");
				break;
			default:
				break;
			}

		}
	}
	
	private void println(String _toPrint){
		System.out.println(_toPrint);
	}

	private int read()
	{
		try {
			java.io.BufferedReader stdin = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			String line = stdin.readLine();
			return Integer.parseInt(line);
		}

		catch (java.io.IOException e) { }
		catch (NumberFormatException e) { }

		return 9;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage:");
			System.out.println("  PDPApplicationTest DiameterConfigFile SystemConfigFile");

			System.exit(0);
		} 
		String diameterConfigFile = args[0];
		String systemConfigFile = args[1];
		File xml = new File(diameterConfigFile);
		File sys = new File(systemConfigFile);
		if(!xml.exists())
		{
			System.out.println("Can not find File "+xml.getAbsoluteFile());
			System.exit(0);
		}
		if(!sys.exists())
		{
			System.out.println("Can not find File "+sys.getAbsoluteFile());
			System.exit(0);
		}
		
		System.out.println("Opening file "+xml.getAbsoluteFile());
		System.out.println("Opening file "+sys.getAbsoluteFile());
		PDPApplicationTest pT = new PDPApplicationTest(diameterConfigFile, systemConfigFile);
	}

}
