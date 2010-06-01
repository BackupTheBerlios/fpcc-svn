/* 
* PDPLogic.java
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
* fpcc@ftw.at ÊÊ
*
* BIQINI is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. ÊSee the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License 
* along with this program; if not, write to the Free Software 
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA Ê02111-1307 ÊUSA
*/
package at.ac.tuwien.ibk.biqini.pdp;

import org.apache.log4j.Logger;

import at.ac.tuwien.ibk.biqini.diameter.DiameterHelper;
import at.ac.tuwien.ibk.biqini.diameter.exceptions.DiameterHelperNotConfiguredException;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterAnswer;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterRequest;
import at.ac.tuwien.ibk.biqini.diameter.messages.IIETFResponseCodes;
import at.ac.tuwien.ibk.biqini.diameter.messages.IfMessageReceiver;
import at.ac.tuwien.ibk.biqini.diameter.messages.common.ICommonDiameterConstants;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxAAR;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxSTR;
import at.ac.tuwien.ibk.biqini.pdp.session.PDPSession;
import at.ac.tuwien.ibk.biqini.pdp.session.SessionContainer;
import at.ac.tuwien.ibk.biqini.pdp.system.NoCorrespondingPEPFoundException;
import at.ac.tuwien.ibk.biqini.pdp.system.SystemStructure;

public class PDPLogic implements Runnable, ICommonDiameterConstants, IfMessageReceiver {

	private static final Logger LOGGER = Logger.getLogger(PDPApplicationTest.class);
	private String configFileName;
	public static IfPDPtoPEP pdpToPeP;
	public static IfPDPtoAF pdpToAf;
	@SuppressWarnings("unused")
	private DiameterHelper diameterHelper;
	private SessionContainer sessionContainer;
	//private Semaphore messageSemaphore;
	private DiameterRequest currentRequest;
	private DiameterAnswer currentAnswer;
	private String currentfromFQDN;
	private boolean request;
	private SystemStructure systemStructure;


	public PDPLogic(String _diameterConfigFile, String _systemCfgFile){
		//messageSemaphore = new Semaphore(1);
		configFileName = _diameterConfigFile;
		DiameterHelper.setDiameterConfigFile(configFileName);
		diameterHelper = null;
		try {
			diameterHelper = DiameterHelper.getInstance();
		} catch (DiameterHelperNotConfiguredException e) {
			LOGGER.error("Error occurred while trying to create DiameterHelper!");
			LOGGER.error("Message: "+e.getLocalizedMessage());
			System.exit(-1);
		}
		sessionContainer = new SessionContainer();
		SystemStructure.createInstance(_systemCfgFile);
		systemStructure = SystemStructure.getInstance();
		/***
		 * Create instance of interface to PeP. In this case, only Gx is supported
		 * TODO read "real" config do corresponding things
		 * e.g., 3gpp or tispan enforcer, etc.
		 */
		pdpToPeP = PDPGx.getInstance(this);
		Thread thrPdpToPep = new Thread(pdpToPeP, "pdpGx");
		/***
		 * Create instance of interface to Af. In this case, only Rx is supported
		 * TODO read "real" config do corresponding things
		 * e.g., 3gpp or tispan enforcer, etc.
		 */
		pdpToAf = PDPRx.getInstance(this);
		Thread thrpdpToAf = new Thread(pdpToAf, "pdpRx");
		/***
		 * start worker threads
		 */
		thrPdpToPep.start();
		thrpdpToAf.start();
	}

	/*read int from terminal
	static int read()
	{
		try {
			java.io.BufferedReader stdin = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			String line = stdin.readLine();
			return Integer.parseInt(line);
		}

		catch (java.io.IOException e) { System.out.println(e); }
		catch (NumberFormatException e) { System.out.println(e); }

		return 0;
	}*/

	private void gotRxRequest(){
		String sessionID = currentRequest.getSessionId().getUTF8String();
		PDPSession sess = sessionContainer.getSession(sessionID);
		if (sess == null){
			if (currentRequest instanceof RxAAR){
				sess = new PDPSession(sessionID, ((RxAAR)currentRequest).getFramedIPAddress().getOctetString(), currentfromFQDN);
				try {
					systemStructure.addSession(sess);
					sessionContainer.addSession(sess);
				} catch (NoCorrespondingPEPFoundException e) {
					// TODO what to do here?
				}

			}
			else if (currentRequest instanceof RxSTR){
				((PDPRx)pdpToAf).sendNegativeAnswer(currentfromFQDN, currentRequest, IIETFResponseCodes.DIAMETER_UNKNOWN_SESSION_ID, null);
				return;
			}
			else{
				((PDPRx)pdpToAf).sendNegativeAnswer(currentfromFQDN, currentRequest, IIETFResponseCodes.DIAMETER_APPLICATION_UNSUPPORTED, null);
				return;
			}
		}
		sess.recvdRequest(currentfromFQDN, currentRequest);
	}

	private void gotGxAnswer() {
		String sessionID = currentRequest.getSessionId().getUTF8String();
		PDPSession sess = sessionContainer.getSession(sessionID);
		if (sess == null){
			//was mach ich hier???
		}
		sess.recvdAnswer(currentfromFQDN, currentRequest, currentAnswer);

	}

	public void run() {
		while(true){
			synchronized(this){
				try {
					this.wait();
					if (request){
						gotRxRequest();						
					}
					else{
						gotGxAnswer();
					}
					//messageSemaphore.release();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void recvdAnswer(String _fqdn, DiameterRequest _req,
			DiameterAnswer _answ) {
		synchronized(this){
			try {
				//messageSemaphore.acquire();
				currentRequest = _req;
				currentfromFQDN = _fqdn;
				currentAnswer = _answ;
				request = false;
				this.notify();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void recvdRequest(String _fqdn, DiameterRequest _req) {
		synchronized(this){
			try {
				//messageSemaphore.acquire();
				currentRequest = _req;
				currentfromFQDN = _fqdn;
				request = true;
				this.notify();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the sessionContainer
	 */
	public SessionContainer getSessionContainer() {
		return sessionContainer;
	}

	public void killSession(int _which){
		if (sessionContainer.getSize()>0){
			PDPSession sess = sessionContainer.killSession(_which);
			systemStructure.killSession(sess);
		}
	}
	
	public String getFreeBandwith(){
		return systemStructure.getFreeBandwidth();
	}
	
	public void printConfig(){
		systemStructure.printConfig();
	}
	
	
}
