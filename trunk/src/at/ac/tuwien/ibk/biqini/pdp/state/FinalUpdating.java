/* 
* FinalUpdating.java
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
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxRAA;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxAAR;

/**
 * @author Christoph Egger
 *
 */
public class FinalUpdating extends State {

	/**
	 * @param stateMachine
	 */
	public FinalUpdating(StateMachine stateMachine) {
		super(stateMachine);
	}

	public String getName() {
		return "FinalUpdating";
	}

	protected State incomingAnswer(String _fqdn, DiameterAnswer _ans,
			DiameterRequest _req) {
		GxRAA raa = null;
		if (_ans instanceof GxRAA){
			raa = (GxRAA)_ans;
		}
		else{
			Failed failed = new Failed(stateMachine);
			return failed.incomingAnswer(_fqdn, _ans, _req);
		}
		if (raa.getResultCode().isSuccess()){
			return new Committed(stateMachine);
		}
		else{
			FailedUpdating failedUpdating = new FailedUpdating(stateMachine);
			return failedUpdating.incomingAnswer(_fqdn, _ans, _req);
		}	
	}

	protected State incomingRequest(String _fqdn, DiameterRequest _req) {
		if (_req instanceof RxAAR){
			rx.sendPositiveAnswer(_fqdn, _req);
			gx.updateSession(stateMachine.getPdpSession());
			return this;

		}
		else return this;
	}

}
