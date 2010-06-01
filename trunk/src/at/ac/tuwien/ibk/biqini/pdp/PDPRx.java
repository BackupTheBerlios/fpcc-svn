/* 
* PDPRx.java
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

import java.util.concurrent.Semaphore;

import at.ac.tuwien.ibk.biqini.diameter.DiameterHelper;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.DestinationHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ResultCode;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SessionID;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AcceptableServiceInfo;
import at.ac.tuwien.ibk.biqini.diameter.exceptions.DiameterHelperNotConfiguredException;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterAnswer;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterRequest;
import at.ac.tuwien.ibk.biqini.diameter.messages.IIETFResponseCodes;
import at.ac.tuwien.ibk.biqini.diameter.messages.IfMessageReceiver;
import at.ac.tuwien.ibk.biqini.diameter.messages.common.ICommonDiameterConstants;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.I3GPPRXInterface;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxAAA;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxAAR;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxASR;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxSTA;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxSTR;


/**
 * @author Christoph Egger
 *
 */
public class PDPRx implements IfPDPtoAF, IfMessageReceiver, I3GPPRXInterface, ICommonDiameterConstants, IIETFResponseCodes {

	private DiameterHelper diameterHelper;
	private IfMessageReceiver ifMessageReceiver;
	private Semaphore requestSemaphore;
	private DiameterRequest currentRequest;
	private String currentfromFQDN;
	
	private static PDPRx INSTANCE = null;
	
	public static PDPRx getInstance(IfMessageReceiver _pdpLogic){
		if (INSTANCE == null){
			INSTANCE = new PDPRx(_pdpLogic);
		}
		return INSTANCE;
	}
	public static PDPRx getInstance(){
		return INSTANCE;
	}
	/***
	 * The one and only Constructor to be used
	 * @param _decisionEngine the engine that decides what to do in case a request is coming in.
	 */
	private PDPRx(IfMessageReceiver _pdpLogic){
		requestSemaphore = new Semaphore(1);
		ifMessageReceiver = _pdpLogic;
		try {
			diameterHelper = DiameterHelper.getInstance();
		} catch (DiameterHelperNotConfiguredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		diameterHelper.registerApp(APPLICATION_ID, this);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		synchronized(this){
			while(true){		
				try{
					this.wait();
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				ifMessageReceiver.recvdRequest(currentfromFQDN, currentRequest);
				requestSemaphore.release();		
			}
		}

	}

	/**
	 * from IfMessageReceiver
	 */
	public void recvdAnswer(String _fqdn, DiameterRequest _req,
			DiameterAnswer _answ) {
		// TODO not implemented yet, because in the first version we are only server for Rx
	}
	/**
	 * from IfMessageReceiver
	 */
	public void recvdRequest(String _fqdn, DiameterRequest _req) {
		synchronized(this){
			try {
				requestSemaphore.acquire();
				currentRequest = _req;
				currentfromFQDN = _fqdn;
				System.out.println(_req.toString());
				this.notify();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * from ifPDPtoAF
	 */
	public void sendAnswer(String _fqdn, DiameterAnswer _answ) {
		diameterHelper.sendAnswer(_fqdn, _answ);
	}
	public void sendASR(String _fqdn, SessionID _sessionId) {
		RxASR rxASR = new RxASR();
		rxASR.setDestinationHost(new DestinationHost(_fqdn));
		rxASR.setSessionId(_sessionId);
		diameterHelper.sendRequestTransactional(rxASR);
	}
	
	public void sendPositiveAnswer(String _fqdn, DiameterRequest _req){
		DiameterAnswer answ = null;
		if (_req instanceof RxAAR) {
			answ = new RxAAA();
			if (_req.getAuthApplicationId() != null)
				answ.setAuthApplicationId(_req.getAuthApplicationId());
		}
		if (_req instanceof RxSTR) {
			answ = new RxSTA();
		}
		answ = diameterHelper.createAnswer(_req, answ);
		answ.setResultCode(new ResultCode(DIAMETER_SUCCESS));
		sendAnswer(_fqdn, answ);
	}
	public void sendNegativeAnswer(String _fqdn, DiameterRequest _req, long _reasonCode, AcceptableServiceInfo _info){
		DiameterAnswer answ = null;
		if (_req instanceof RxAAR) {
			answ = new RxAAA();
			if (_req.getAuthApplicationId() != null)
				answ.setAuthApplicationId(_req.getAuthApplicationId());
			if (_info != null)
				((RxAAA)answ).setAcceptableServiceInfo(_info);
		}
		else if (_req instanceof RxSTR) {
			answ = new RxSTA();
		}
		else{
			answ = new RxSTA();
			answ.commandCode = _req.commandCode;
		}
		answ = diameterHelper.createAnswer(_req, answ);
		answ.setResultCode(new ResultCode(_reasonCode));
		//TODO the follwing thing
		//answ.setAcceptableServiceInfo(_info);
		sendAnswer(_fqdn, answ);
	}


}
