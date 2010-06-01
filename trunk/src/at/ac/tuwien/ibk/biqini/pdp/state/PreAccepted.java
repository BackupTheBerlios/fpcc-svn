/* 
* PreAccepted.java
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

import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterAnswer;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterRequest;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxAAR;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxSTR;

/**
 * @author Christoph Egger
 *
 */
public class PreAccepted extends State  {

	public PreAccepted(StateMachine machine) {
		super(machine);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see at.ac.tuwien.ibk.biqini.common.state.State#incomingAnswer(at.ac.tuwien.ibk.biqini.diameter.messages.DiameterAnswer)
	 */
	@Override
	protected State incomingAnswer(String _fqdn, DiameterAnswer _ans, DiameterRequest _req) {
		// TODO Auto-generated method stub
		return this;
	}

	/* (non-Javadoc)
	 * @see at.ac.tuwien.ibk.biqini.common.state.State#incomingRequest(at.ac.tuwien.ibk.biqini.diameter.messages.DiameterRequest)
	 */
	@Override
	protected State incomingRequest(String _fqdn, DiameterRequest _req) {
		if (_req instanceof RxAAR){
			Committed committed = new Committed(stateMachine);
			return committed.incomingRequest(_fqdn, _req);
		}
		if (_req instanceof RxSTR){
			rx.sendPositiveAnswer(_fqdn, _req);
			Terminated terminated = new Terminated(stateMachine);
			return terminated.incomingRequest(_fqdn, _req);
		}
		else return this;
	}
	
	public String getName(){
		return "PreAccepted";
	}
}
