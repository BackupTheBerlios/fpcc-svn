/* 
* AVPFactory.java
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
package at.ac.tuwien.ibk.biqini.diameter.avp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import at.ac.tuwien.ibk.biqini.common.ClassListHelper;
import de.fhg.fokus.diameter.DiameterPeer.data.AVP;

/**
 * @author Christoph Egger
 *
 */
@SuppressWarnings("unchecked")
public class AVPFactory {
	
	
	private static Hashtable<String, Class<AVP>> avpTypes;
	/** The logger */
	private static final Logger LOGGER = Logger.getLogger(AVPFactory.class);
	
	static{
		//get Classes
		ArrayList<String> classList = null;
		try {
			classList = ClassListHelper.getClassNamesInPackage("at.ac.tuwien.ibk.biqini.diameter.avp.ietf");
			classList.addAll(ClassListHelper.getClassNamesInPackage("at.ac.tuwien.ibk.biqini.diameter.avp.threegpp"));
			classList.addAll(ClassListHelper.getClassNamesInPackage("at.ac.tuwien.ibk.biqini.diameter.avp.tispan"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int numClasses = classList.size();
		avpTypes = new Hashtable<String, Class<AVP>>(numClasses);
		for (int i = 0; i<numClasses;i++){
			Class<AVP> avp = null;
			try {
				avp = (Class<AVP>) Class.forName(classList.get(i));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Field avpCodeField = null;
			Integer code = 0;
			Field vendorIDField = null;
			Integer vendorID = 0;
			try {
				avpCodeField = avp.getDeclaredField("AVP_CODE");
				vendorIDField = avp.getField("VENDOR_ID");
				code = (Integer)avpCodeField.get(null);
				vendorID = (Integer)vendorIDField.get(null);
				String hash = getHash(code, vendorID);
				//LOGGER.info(avpCode+"\t"+avp.getSimpleName()+"\t"+avp.getCanonicalName());
				if(avpTypes.containsKey(hash))
					LOGGER.error("The AVP Code with the AVP CODE "+code+" and VendorID "+vendorID+" already exists with the AVP "+avpTypes.get(hash).getCanonicalName()+" So we will skipp adding the AVP "+avp.getCanonicalName());
				avpTypes.put(hash, avp);
			} catch (Exception e) {
				System.err.println(classList.get(i));
				e.printStackTrace();
			} 
			
			
			
			
		}
		
	}
	
	public static String getHash(int _code,int _vendor_id)	{
		return _code+"-"+_vendor_id;
	}
	
	public static AVP parse(int _code, int _vendor_id){
		Class<?> avpClass = avpTypes.get(getHash(_code, _vendor_id));
		AVP returnAVP = null;
		// in the case we have no AVP for this code
		if(avpClass==null)
			return null;
		try {
			returnAVP = (AVP) avpClass.newInstance();
		} catch (InstantiationException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnAVP;
	}
	/**
	 * queries the name for a certain AVP code
	 * @param _code	
	 * @return
	 */
	public static String queryAVPName(int _code, int _vendor_id)	{
		Class<?> avpClass = avpTypes.get(getHash(_code, _vendor_id));
		if(avpClass==null)
			return "unknownAVP";
		else
			return avpClass.getSimpleName();
	}
}
