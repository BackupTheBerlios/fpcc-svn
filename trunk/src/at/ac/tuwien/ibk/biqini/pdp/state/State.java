/* 
* State.java
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
import at.ac.tuwien.ibk.biqini.diameter.messages.IIETFResponseCodes;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxAAR;
import at.ac.tuwien.ibk.biqini.pdp.PDPGx;
import at.ac.tuwien.ibk.biqini.pdp.PDPRx;
import at.ac.tuwien.ibk.biqini.pdp.policy.CodecNotAllowedException;
import at.ac.tuwien.ibk.biqini.pdp.policy.DomainPolicy;
import at.ac.tuwien.ibk.biqini.pdp.policy.IfPolicy;
import at.ac.tuwien.ibk.biqini.pdp.policy.MediaTypeNotAllowedException;
import at.ac.tuwien.ibk.biqini.pdp.policy.UserPolicy;
import at.ac.tuwien.ibk.biqini.pdp.system.NoCorrespondingPEPFoundException;
import at.ac.tuwien.ibk.biqini.pdp.system.NotEnoughBandwidthFreeException;
import at.ac.tuwien.ibk.biqini.pdp.system.SystemStructure;

/**
 * @author Christoph Egger
 * This class is the base class of the states used in the PDP
 */
public abstract class State implements IIETFResponseCodes {
	//	protected static DiameterHelper diameterHelper;
	//	protected static IDecisionEngine iDecEngine;
	private static IfPolicy domainPolicy;
	private static IfPolicy userPolicy;
	protected static SystemStructure systemStructure; 
	protected static PDPRx rx;
	protected static PDPGx gx;
	protected StateMachine stateMachine;
	protected abstract State incomingRequest(String _fqdn, DiameterRequest _req) throws InvalidNextStateException;
	protected abstract State incomingAnswer(String _fqdn, DiameterAnswer _ans, DiameterRequest _req) throws InvalidNextStateException;
	public abstract String getName();
	
	public State(StateMachine _stateMachine) {
		super();
		stateMachine = _stateMachine;
	}

	static{
		domainPolicy = DomainPolicy.getInstance();
		userPolicy = UserPolicy.getInstance();
		systemStructure = SystemStructure.getInstance();
		rx = PDPRx.getInstance();
		gx = PDPGx.getInstance();
	}

	protected void checkPolicies(RxAAR _req) throws CodecNotAllowedException, MediaTypeNotAllowedException {
		 	domainPolicy.checkPolicy(_req);
			userPolicy.checkPolicy(_req);
	}
	
	protected void checkAndReserveBandwidth(RxAAR _req) throws NotEnoughBandwidthFreeException, NoCorrespondingPEPFoundException{
			systemStructure.checkAndReserveBandwidth(_req);
	}
}
