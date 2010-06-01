/*
 * $Id: DiameterMessage.java 167 2007-03-05 09:25:42Z shenny $
 * Copyright (C) 2010 FTW (Telecommunications Research Center Vienna)
 * Changed by Christoph Egger & Marco Happenhofer
 *
 * Copyright (C) 2004-2006 FhG Fokus
 *
 * This file is part of Open IMS Core - an open source IMS CSCFs & HSS
 * implementation
 *
 * Open IMS Core is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * For a license to use the Open IMS Core software under conditions
 * other than those described here, or to purchase support for this
 * software, please contact Fraunhofer FOKUS by e-mail at the following
 * addresses:
 *     info@open-ims.org
 *
 * Open IMS Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package de.fhg.fokus.diameter.DiameterPeer.data;

import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginRealm;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SessionID;


/**
 * This class defines the basic Diameter Message structure
 * 
 * @author Dragos Vingarzan vingarzan -at- fokus dot fraunhofer dot de
 * @param <T>
 *
 */
public class DiameterMessage {
	
	/** The logger */
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(DiameterMessage.class);

	/** The version of the message */
	int version=1;
	
	/** Command Code */
	public int commandCode;
	
	/** If the message is a Request*/
	public boolean flagRequest=true;
	
	/** If the message is proxiable */
	public boolean flagProxiable=true;
	
	/** If the message contains a protocol error */
	public boolean flagError=false;
	
	/** Potentially retransmission */
	public boolean flagRetransmission=false;
	
	/** Application ID */
	public int applicationID=0;
	
	/** Hop-by-Hop identifier */
	public long hopByHopID=0;

	/** End-to-End identifier */
	public long endToEndID=0;
	
	/** Contained AVPs */
	public Vector<AVP> avps;
	
	
	/** statistical time - close to media */
	public long networkTime=0;

	
	/**
	 * Dumb constructor.
	 */
	public DiameterMessage()
	{
		avps = new Vector<AVP>();
	}
	
	/**
	 * Constructor that initializes everything.
	 * @param Command_Code
	 * @param Request
	 * @param Proxiable
	 * @param Application_id
	 * @param HopByHop_id
	 * @param EndToEnd_id
	 */
	public DiameterMessage(int Command_Code,boolean Request,boolean Proxiable,
			int Application_id,long HopByHop_id, long EndToEnd_id)
	{
		this.commandCode = Command_Code;
		this.flagRequest = Request;
		this.flagProxiable = Proxiable;
		this.applicationID = Application_id;
		this.hopByHopID = HopByHop_id;
		this.endToEndID = EndToEnd_id;
		avps = new Vector<AVP>();
	}
	
	/**
	 * Constructor that initializes just the needed attributes.
	 * @param Command_Code
	 * @param Request
	 * @param Application_id
	 */
	public DiameterMessage(int Command_Code,boolean Request,int Application_id)
	{
		this.commandCode = Command_Code;
		this.flagRequest = Request;
		this.flagProxiable = false;
		this.applicationID = Application_id;
		avps = new Vector<AVP>();
	}
	
	/**
	 * Adds one AVP to the message.
	 * @param child
	 */
	public void addAVP(AVP child)
	{
		avps.add(child);
	}
	
	/**
	 * Returns the count of the AVPs.
	 * 
	 * @return The number of all available AVPs.
	 */
	public int getAVPCount()
	{
		return avps.size();
	}
	
	/**
	 * Returns the AVP. 
	 * @param index
	 * @return the found AVP or null if out of bounds
	 */
	public AVP getAVP(int index)
	{
		if (index<avps.size())
			return avps.get(index);
		else return null;
	}
	
	/**
	 * Deletes the given AVP from the list of AVPs.
	 * @param avp
	 */
	public void deleteAVP(AVP avp)
	{
		avps.remove(avp);
	}
	
	/**
	 * Searches for an AVP inside the Vector of AVPs.
	 * @param Code
	 * @param Mandatory
	 * @param Vendor_id
	 * @return the found AVP, null if not found
	 */
	public AVP findAVP(int Code,boolean Mandatory,int Vendor_id)
	{
		AVP avp;
		for(int i=0;i<avps.size();i++){
			avp =  avps.get(i);
			if (avp.code == Code &&
				avp.flag_mandatory == Mandatory &&
				avp.vendor_id == Vendor_id) 
					return avp;
		}
		return null;
	}
	
	/**
	 * Searches for all AVPs with the same code inside the Vector of AVPs.
	 * @param Code
	 * @return the found AVP, null if not found
	 */
	public AVP[] findAVPs(int Code)
	{
		AVP[] avpset;
		int j = 0, count = 0;
		AVP avp;
		
		for(int i=0;i<avps.size();i++){
			avp =  avps.get(i);
			if (avp.code == Code)
				count++;
		}
		
		if (count == 0) return null;
		avpset = new AVP[count];
		for(int i=0;i<avps.size();i++){
			avp = avps.get(i);
			if (avp.code == Code) {
				avpset[j++] = avp;
				if (j == count) break;
			}
		}
		
		return avpset;
	}
	
	/**
	 * Searches for an AVP inside the Vector of AVPs.
	 * @param Code
	 * @return the found AVP, null if not found
	 */
	public AVP findAVP(int Code) {
		AVP avp;
		for(int i=0;i<avps.size();i++){
			avp = avps.get(i);
			if (avp.code == Code) {
				
				return avp;
			}
		}
		return null;	
		
	}
	
	/**
	 * Searches for the Session-Id AVP inside a message
	 * @return the found Session-Id AVP, null if not found
	 */
	public SessionID getSessionId() {
		// modified by mhappenhofer
		return (SessionID)findAVP(SessionID.AVP_CODE);
	}
	
	/**
	 * sets a AVP which should occur only once
	 * @param _avp	the new AVP
	 * 
	 * @author mhappenhofer
	 */
	protected void setSingleAVP(AVP _avp)	{
		AVP current = this.findAVP(_avp.code);
		if(current != null)
			this.deleteAVP(current);
		this.addAVP(_avp);
	}
	/**
	 * sets the SessionID and overrides a existing one
	 * @param _sessionID	the new SessionID
	 * 
	 * @author mhappenhofer
	 */
	public void setSessionId(SessionID _sessionID)	{
		this.setSingleAVP( _sessionID);
	}
	
	/**
	 * Searches for the Origin-Host AVP inside a message
	 * @return the found Origin-Host AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public OriginHost getOriginHost() {
		return (OriginHost)findAVP(OriginHost.AVP_CODE);
	}
	
	/**
	 * sets the Origin-Host and overrides a existing one
	 * @param _originHost	the new OriginHost
	 * 
	 * @author mhappenhofer
	 */
	public void setOriginHost(OriginHost _originHost)	{
		this.setSingleAVP( _originHost);
	}
	
	/**
	 * Searches for the Origin-Realm AVP inside a message
	 * @return the found Origin-Realm AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public OriginRealm getOriginRealm() {
		return (OriginRealm)findAVP(OriginRealm.AVP_CODE);
	}
	
	/**
	 * sets the Origin-Realm and overrides a existing one
	 * @param _originRealm	the new OriginRealm
	 * 
	 * @author mhappenhofer
	 */
	public void setOriginRealm(OriginRealm _originRealm)	{
		this.setSingleAVP( _originRealm);
	}
	
	/**
	 * searches for the number of a certain type of AVPs
	 * @param _code	the Code of the AVPs
	 * @return	the number of AVPs of this kind in this Diameter Message
	 * 
	 * @author mhappenhofer
	 */
	public int getAVPCount(int _code){
		int count = 0;
		AVP avp;
		
		for(int i=0;i<avps.size();i++){
			avp =  avps.get(i);
			if (avp.code == _code)
				count++;
		}
		return count;
	}
	/**
	 * access a certain AVP defined by a AVP CODE and the index, whereby the 
	 * index must be smaller than the Count value 
	 * @param _code		AVP code of this message
	 * @param _index	index of this AVP code 
	 * @return			the avp, or null if no such exist
	 * 
	 * @author mhappenhofer
	 */
	public AVP getAVPByCodeAndIndex(int _code, int _index)	{
		int count = 0;
		AVP avp;
		
		for(int i=0;i<avps.size();i++){
			avp =  avps.get(i);
			if (avp.code == _code)
			{	
				count++;
				if(count==_index)
					return avp;
			}
		}
		return null;
	}
	
	/**
	 * Requests a iterator with all AVPs of a certain type t
	 * @param <T>	the requested AVP type
	 * @param t		a dummy Object of the Type T to enable compares
	 * @return 		a Iteratior with all AVPs of the Type T
	 * @author mhappenhofer
	 */
	@SuppressWarnings("unchecked")
	public <T> Iterator<T> getIterator(T t){
		
		Class u = t.getClass();
		Vector<T> temp = new Vector<T>();
		for(Iterator<AVP> it = avps.iterator();it.hasNext();)
		{
			AVP avp = it.next();
			if (u.isInstance(avp)) {
				T found = (T) avp;
				temp.add(found);
			}
		}
		return temp.iterator();
	}
	
		
	/**
	 * Human readable version of the AVP for logging
	 */
	public String toString()
	{
		StringBuffer x = new StringBuffer();
		x.append("Diameter: Code=");x.append(commandCode);
		if (flagRequest) x.append(" R");
		if (flagProxiable) x.append(" P");
		if (flagError) x.append(" E");
		if (flagRetransmission) x.append(" T");
		
		x.append(" AppID=");x.append(applicationID);
		x.append(" HbHID=");x.append(hopByHopID);
		x.append(" E2EID=");x.append(endToEndID);
		
		x.append("\n");
		for(int i=0;i<avps.size();i++){
			x.append("\t");
			x.append(avps.get(i).toString());
			if(x.lastIndexOf("\n")!=x.length()-1)
				x.append("\n");
		}		
		return x.toString();
	}
	
	/** Command code of Capabilities-Exchange Request/Answer */
	public static final int Code_CE=257;
	
	/** Command code of Device-Watchdog Request/Answer */
	public static final int Code_DW=280;
	
	/** Command code of Disconnect-Peer-Request/Answer */
	public static final int Code_DP=282;
	
	/** 
	 * ResultCode returned is 2001, when the request was successfully completed.
	 */
	public static final int DIAMETER_SUCCESS = 2001;
	
	/** 
	 * ResultCode returned is 5010, when CER message is received, and there are no common
	 * applications supported between the peers.
	 */
	public static final int DIAMETER_NO_COMMON_APPLICATION = 5010;
	
	/**
	 * ResultCode returned is 5012, when a request was received, whose version
	 * number is unsupported.
	 */
	public static final int DIAMETER_UNABLE_TO_COMPLY = 5012;


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @author mhappenhofer
	 */
	@Override
	public boolean equals(Object obj) {
		//the same class
		if(this.getClass().getCanonicalName().compareTo(obj.getClass().getCanonicalName())!=0)
			return false;
		DiameterMessage other =(DiameterMessage) obj;
		
		if(this.applicationID!=other.applicationID)
			return false;
		if(this.commandCode!=other.commandCode)
			return false;
		if(this.endToEndID!=other.endToEndID)
			return false;
		if(this.flagError!=other.flagError)
			return false;
		if(this.flagProxiable!=other.flagProxiable)
			return false;
		if(this.flagRequest!=other.flagRequest)
			return false;
		if(this.flagRetransmission!=other.flagRetransmission)
			return false;
		if(this.hopByHopID!=other.hopByHopID)
			return false;
		
		// ok now compare the AVPs
		if(this.avps.size()!=other.avps.size())
			return false;
		for(Iterator<AVP> tit = this.avps.iterator();tit.hasNext();)
		{
			AVP thisAVP = tit.next();
			for(Iterator<AVP> oit = this.avps.iterator();oit.hasNext();)
			{
				AVP otherAVP = oit.next();
				if(thisAVP.equals(otherAVP))
				{
					thisAVP = null;
					break;
				}
			}
			//if thisAVp was also in the other Diameter message then this AVP must be equal to null
			if(thisAVP!=null)
				return false;
		}
		//we did not find any difference, so we conclude, that the messages are equal 
		return true;
	}
	
	
	
}
