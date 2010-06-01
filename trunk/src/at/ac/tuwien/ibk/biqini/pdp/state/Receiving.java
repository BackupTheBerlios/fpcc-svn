/* 
* Receiving.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ServiceInfoStatus;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterAnswer;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterRequest;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxAAR;
import at.ac.tuwien.ibk.biqini.pdp.system.NoCorrespondingPEPFoundException;
import at.ac.tuwien.ibk.biqini.pdp.system.NotEnoughBandwidthFreeException;

/**
 * @author Christoph Egger
 *
 */
public class Receiving extends State {
	
	
	public Receiving(StateMachine machine) {
		super(machine);
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see at.ac.tuwien.ibk.biqini.common.state.State#incomingAnswer(at.ac.tuwien.ibk.biqini.diameter.messages.DiameterAnswer)
	 */
	@Override
	protected State incomingAnswer(String _fqdn, DiameterAnswer _ans, DiameterRequest _req) throws InvalidNextStateException {
		throw new InvalidNextStateException("State 'Receiving' and got Diameter answer.");
	}
	/* (non-Javadoc)
	 * @see at.ac.tuwien.ibk.biqini.common.state.State#incomingRequest(at.ac.tuwien.ibk.biqini.diameter.messages.DiameterRequest)
	 */
	@Override
	protected State incomingRequest(String _fqdn, DiameterRequest _req) {
		if (_req instanceof RxAAR){
			try {
				//checks if the policies are met
				//FIXME acceptable service info implementieren
				checkPolicies((RxAAR)_req);
			} catch (Exception e) {
				rx.sendNegativeAnswer(_fqdn, _req, DIAMETER_UNABLE_TO_COMPLY, null);
				Rejected rej = new Rejected(stateMachine, e.getLocalizedMessage());
				return rej.incomingRequest(_fqdn, _req);
			}
			try {
				checkAndReserveBandwidth((RxAAR) _req);
			} catch (NotEnoughBandwidthFreeException e) {
				rx.sendNegativeAnswer(_fqdn, _req, DIAMETER_UNABLE_TO_COMPLY, e.getAcceptableServiceInfo());
				Rejected rej = new Rejected(stateMachine, e.getLocalizedMessage());
				return rej.incomingRequest(_fqdn, _req);
			} catch (NoCorrespondingPEPFoundException e) {
				rx.sendNegativeAnswer(_fqdn, _req, DIAMETER_UNABLE_TO_COMPLY, null);
				Rejected rej = new Rejected(stateMachine, e.getLocalizedMessage());
				return rej.incomingRequest(_fqdn, _req);
			}

			
			//rx.sendPositiveAnswer(_fqdn, _req);
			switch (((RxAAR)_req).getServiceInfoStatus().getEnumerated()){
			case ServiceInfoStatus.PRELIMINARY_SERVICE_INFORMATION:
				rx.sendPositiveAnswer(_fqdn, _req);
				return new PreAccepted(stateMachine);
			case ServiceInfoStatus.FINAL_SERVICE_INFORMATION:
				FinalAccepted finalAccepted = new FinalAccepted(stateMachine);
				return finalAccepted.incomingRequest(_fqdn, _req);
			default:
				return this;
			}
		}
		else
			return this;
	}
	
	public String getName(){
		return "Receiving";
	}
}
