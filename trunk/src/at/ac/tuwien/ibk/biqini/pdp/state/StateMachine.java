/* 
* StateMachine.java
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
package at.ac.tuwien.ibk.biqini.pdp.state;

import org.apache.log4j.Logger;

import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterAnswer;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterRequest;
import at.ac.tuwien.ibk.biqini.diameter.messages.IfMessageReceiver;
import at.ac.tuwien.ibk.biqini.pdp.session.PDPSession;

/**
 * @author Christoph Egger
 *
 */
public class StateMachine implements IfMessageReceiver {
	
	private PDPSession pdpSession;
	private State state;
	private static Logger LOGGER = Logger.getLogger(StateMachine.class);
	
	public StateMachine(PDPSession _pdpSession){
		pdpSession = _pdpSession;
		state = new Receiving(this);
	}
		
	public void recvdAnswer(String _fqdn, DiameterRequest _req, DiameterAnswer _answ) {
		try {
			LOGGER.debug("State: "+state.getName());
			LOGGER.debug(_answ.getClass().toString());
			state = state.incomingAnswer(_fqdn, _answ, _req);
		} catch (InvalidNextStateException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public void recvdRequest(String _fqdn, DiameterRequest _req) {
		try {
			LOGGER.debug("State: "+state.getName());
			LOGGER.debug(_req.getClass().toString());
			state = state.incomingRequest(_fqdn, _req);
		} catch (InvalidNextStateException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	/**
	 * @return the pdpSession
	 */
	public PDPSession getPdpSession() {
		return pdpSession;
	}
	
	public String getStateName(){
		return state.getName();
	}
	/*
	public void setCurrentBandwidthUP(int _currentBandwidthUP){
		pdpSession.setCurrentBandwidthUP(_currentBandwidthUP);
	}
	public void setCurrentBandwidthDOWN(int _currentBandwidthDOWN){
		pdpSession.setCurrentBandwidthDOWN(_currentBandwidthDOWN);
	}*/
	
}
