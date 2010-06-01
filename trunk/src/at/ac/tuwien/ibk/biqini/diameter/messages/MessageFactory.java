/* 
* MessageFactory.java
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
package at.ac.tuwien.ibk.biqini.diameter.messages;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.log4j.Logger;

import at.ac.tuwien.ibk.biqini.common.ClassListHelper;
import de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage;

@SuppressWarnings("unchecked")
public class MessageFactory {

	private static Hashtable<String, Class<DiameterMessage>> diameterMessages;
	/** The logger */
	private static final Logger LOGGER = Logger.getLogger(MessageFactory.class);

	static{
		diameterMessages = new Hashtable<String, Class<DiameterMessage>>();
		//get Classes
		ArrayList<String> classList = null;
		try {
			classList = ClassListHelper.getClassNamesInPackage("at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx");
			classList.addAll(ClassListHelper.getClassNamesInPackage("at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx"));
			//classList.addAll(ClassListHelper.getClassNamesInPackage("at.ac.tuwien.ibk.biqini.diameter.messages.tispan.gqs"));
			//classList.addAll(ClassListHelper.getClassNamesInPackage("at.ac.tuwien.ibk.biqini.diameter.messages.tispan.re"));
			//classList.addAll(ClassListHelper.getClassNamesInPackage("at.ac.tuwien.ibk.biqini.diameter.messages.tispan.rq"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Iterator<String> classIterator = classList.iterator();
		while(classIterator.hasNext())
		{
			String className = classIterator.next();
			//System.out.println(className);
			Class<DiameterMessage> diameterMsg = null;
			int applicationid=0;
			int commandCode=0;
			boolean request = false;
			Field applicationIdField = null;
			Field commandCodeField = null;
			Field requestField = null;
			try {
				diameterMsg = (Class<DiameterMessage>) Class.forName(className);

				applicationIdField = diameterMsg.getField("APPLICATION_ID");
				applicationid=(Integer)applicationIdField.get(null);

				commandCodeField = diameterMsg.getField("MESSAGE_CODE");
				commandCode = (Integer)commandCodeField.get(null);

				requestField = diameterMsg.getField("REQUEST_FLAG");
				request = (Boolean) requestField.get(null);
				String hash = getHashString(applicationid, request, commandCode);
				//LOGGER.info("Insert Class "+className+" with hash "+hash);
				diameterMessages.put(hash, diameterMsg);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				LOGGER.info(className+" was skipped, because it does not support all fileds");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

	public static String getHashString(int _applicationId, boolean _request, int _commandcode)	{
		return String.valueOf(_applicationId)+"-"+String.valueOf(_request)+"-"+String.valueOf(_commandcode);
	}


	public static DiameterMessage parse(int _applicationId, boolean _request, int _commandcode){
		String hash = getHashString(_applicationId, _request, _commandcode);
		Class<DiameterMessage> msg = diameterMessages.get(hash);
		// in the case we have no AVP for this code
		if(msg==null){
			if (_request)
				return new DiameterRequest(_commandcode, _applicationId);
			else return new DiameterAnswer(_commandcode, _applicationId);
		}
		try {
			return msg.newInstance();
		} catch (InstantiationException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		// in any exceptions
		return new DiameterMessage();
	}

	public static DiameterMessage parse(DiameterMessage _msg){
		DiameterMessage newMsg = parse(_msg.applicationID, _msg.flagRequest, _msg.commandCode);
		newMsg.avps = _msg.avps;
		return newMsg;
	}

	public static Enumeration<Class<DiameterMessage>> getAllMessages()	{
		return diameterMessages.elements();
	}
}
