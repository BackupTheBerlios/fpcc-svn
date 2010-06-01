/* 
* AFLogic.java
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

import java.util.Hashtable;
import java.util.Stack;

import de.fhg.fokus.diameter.DiameterPeer.DiameterPeer;
import de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage;
import de.fhg.fokus.diameter.DiameterPeer.transaction.TransactionListener;

/**
 * @author mhappenhofer
 *
 */
public class AFLogic implements Runnable, TransactionListener {

	
	private DiameterPeer diameterPeer;
	private Stack<Command> commands;
	boolean running = true;
	private Hashtable<String,SessionDescription> activeSessions;
	
	public AFLogic(DiameterPeer _diameterPeer) {
		this.diameterPeer = _diameterPeer;
		this.commands = new Stack<Command>();
		this.running = true;
		activeSessions = new Hashtable<String, SessionDescription>();
		
	}


	public void run() {
		while(running)
		{
			synchronized(this){
				while(!commands.empty())
				{
					Command c = commands.pop();
					c.execute(diameterPeer,this);
				}
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		diameterPeer.shutdown();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
	}
	public synchronized void exit()	{
		//TODO remove all Sessions
		running = false;
		this.notify();
	}
	
	public synchronized void upDateSession(SessionDescription _sd)	{
		//verify the Session Description
		if(!activeSessions.containsKey(_sd.getSessionID()))
		{
			if(_sd.terminated())	{
				System.err.println("Cannot terminate this Session, because it does not exist!");
				return;
			} else if (_sd.answered())	{
				commands.add(new Command(_sd,Command.FINAL));
			} else	{
				commands.add(new Command(_sd,Command.PREPRELIMINARY));
			}
			activeSessions.put(_sd.getSessionID().getUTF8String(), _sd);
		} else	{
			if(_sd.terminated())	{
				commands.add(new Command(_sd,Command.TERMINATE));
			} else if (_sd.answered())	{
				commands.add(new Command(_sd,Command.FINAL));
			} else	{
				System.err.println("Awaiting a answer!");
				return;
			}
		}
		this.notify();
	}


	public void receiveAnswer(String FQDN, DiameterMessage request,
			DiameterMessage answer) {
		System.out.println("RECEIVED ANSWER FOR:\n\n\n"+request.toString()+"\n\n\n"+answer.toString());
		
	}


	public void timeout(DiameterMessage request) {
		System.out.println("Did not receive any Response to :\n\n\n\n\n"+request.toString());
		
	}
}
