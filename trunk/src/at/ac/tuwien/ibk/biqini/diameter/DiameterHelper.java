/* 
* DiameterHelper.java
* Christoph Egger 
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
package at.ac.tuwien.ibk.biqini.diameter;

import java.util.Hashtable;

import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginRealm;
import at.ac.tuwien.ibk.biqini.diameter.exceptions.DiameterHelperNotConfiguredException;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterAnswer;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterRequest;
import at.ac.tuwien.ibk.biqini.diameter.messages.IfMessageReceiver;
import at.ac.tuwien.ibk.biqini.diameter.messages.MessageFactory;
import de.fhg.fokus.diameter.DiameterPeer.DiameterPeer;
import de.fhg.fokus.diameter.DiameterPeer.EventListener;
import de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage;
import de.fhg.fokus.diameter.DiameterPeer.transaction.TransactionListener;

/**
 * @author Christoph Egger
 *
 */
public class DiameterHelper implements EventListener, TransactionListener {

	private DiameterPeer diameterPeer;
	private Hashtable<Integer, IfMessageReceiver> apps;
	private static DiameterHelper INSTANCE;
	private static String diameterPeerConfigFile;
	private static boolean configured = false;

	private DiameterHelper(){
		diameterPeer = new DiameterPeer();
		diameterPeer.configure(diameterPeerConfigFile, true);
		//wait for peer to configure itself
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		diameterPeer.enableTransactions(300, 30);
		diameterPeer.addEventListener(this);
		apps = new Hashtable<Integer, IfMessageReceiver>(2);
	}
	public static void setDiameterConfigFile(String _diameterPeerConfigFile){
		diameterPeerConfigFile = _diameterPeerConfigFile;
		configured = true;
	}
	public static DiameterHelper getInstance() throws DiameterHelperNotConfiguredException{
		if (configured){
			if(INSTANCE == null){
				INSTANCE = new DiameterHelper();
			}
			return INSTANCE;
		}
		else{
			throw new DiameterHelperNotConfiguredException("no Diameter Peer configuration set!");
		}
	}
	
	public String getPeerFQDN(){
		return diameterPeer.FQDN;
	}
	public String getPeerRealm(){
		return diameterPeer.Realm;
	}
	public void shutDownPeer(){
		diameterPeer.shutdown();
	}

	/***
	 * register an application with its id so that it can receive incoming requests or answers
	 * applicable when multiple applications run on the same host within different threads
	 * to find the corresponding thread which handles the application
	 * @param _appID
	 * @param _receiver
	 */
	public void registerApp(int _appID, IfMessageReceiver _receiver){
		/***
		 * TODO check if _appid is supported by diameterPeer
		 */
		apps.put(_appID, _receiver);
	}

	public void sendRequestTransactional(DiameterRequest _req){
		diameterPeer.sendRequestTransactional(_req, this);
	}

	public void sendAnswer(String _fqdn, DiameterAnswer _answ){
		if (_answ.getOriginHost() == null)
			_answ.setOriginHost(new OriginHost(getPeerFQDN()));
		if (_answ.getOriginRealm() == null)
			_answ.setOriginRealm(new OriginRealm(getPeerRealm()));
		diameterPeer.sendMessage(_fqdn, _answ);
	}
	
	/* (non-Javadoc)
	 * @see de.fhg.fokus.diameter.DiameterPeer.EventListener#recvMessage(java.lang.String, de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage)
	 */
	public void recvMessage(String _fqdn, DiameterMessage _msg) {
		IfMessageReceiver recv = apps.get(_msg.applicationID);
	/*	if (recv != null){
			if (_msg.flagRequest){
				DiameterRequest req = (DiameterRequest) MessageFactory.parse(_msg);
				recv.recvdRequest(_fqdn, req);
			}
		}
		else{
			//throw new NoSuchApplicationException("No application "+_msg.applicationID+" registered!");
		}*/
		if (_msg instanceof DiameterRequest) {
			DiameterRequest req = (DiameterRequest) _msg;
			recv.recvdRequest(_fqdn, req);
		}
		

	}
	/* (non-Javadoc)
	 * @see de.fhg.fokus.diameter.DiameterPeer.TransactionListener#receiveAnswer(java.lang.String, de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage)
	 */
	public void receiveAnswer(String _fqdn, DiameterMessage _request, DiameterMessage _answer) {
		IfMessageReceiver recv = apps.get(_answer.applicationID);
		if (recv != null){
			DiameterRequest req = (DiameterRequest) MessageFactory.parse(_request);
			DiameterAnswer answ = (DiameterAnswer) MessageFactory.parse(_answer);
			recv.recvdAnswer(_fqdn, req, answ);
		}
		else{
			//throw new NoSuchApplicationException("No application "+_msg.applicationID+" registered!");
		}		
	}
	/* (non-Javadoc)
	 * @see de.fhg.fokus.diameter.DiameterPeer.TransactionListener#timeout(java.lang.String, de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage)
	 */
	public void timeout(DiameterMessage request) {
		// TODO Auto-generated method stub

	}
	
	public DiameterAnswer createAnswer(DiameterRequest _req, DiameterAnswer _answ){
		return diameterPeer.newAnswer(_req, _answ);
	}
	
	/** Generates Hop-by-Hop id. */
	public synchronized int getNextHopByHopId()
	{
		return diameterPeer.getNextHopByHopId();
	}
	/** Generates End-to-End id. */
	public synchronized int getNextEndToEndId()
	{
		return diameterPeer.getNextEndToEndId();
	}

}
