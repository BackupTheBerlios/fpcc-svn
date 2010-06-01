/* 
* AccessNetworkChargingIdentifier.java
* Marco Happenhofer
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
package at.ac.tuwien.ibk.biqini.diameter.avp.threegpp;

import java.util.Iterator;

import at.ac.tuwien.ibk.biqini.diameter.I3GPPConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Grouped;

/**
 * The Access-Network-Charging-Identifier AVP (AVP code 502) is of type Grouped, 
 * and contains a charging identifier (e.g. GCID) within the 
 * Access-Network-Charging-Identifier-Value AVP along with information about the 
 * flows transported within the corresponding bearer within the Flows AVP. If no 
 * Flows AVP is provided, the Access Network Charging-Identifier-Value applies 
 * for all flows within the AF session.
 * The Access-Network-Charging-Identifier AVP can be sent from the PCRF to the 
 * AF. The AF may use this information for charging correlation with session 
 * layer.
 * AVP Format:
 * Access-Network-Charging-Identifier ::= < AVP Header: 502 >
 *					 { Access-Network-Charging-Identifier-Value}
 *					 *[ Flows ] 
 *
 * TS 29.214
 * 
 * @author mhappenhofer
 *
 */
public class AccessNetworkChargingIdentifier extends Grouped implements I3GPPConstants {

	public static final int AVP_CODE = 502;

	public AccessNetworkChargingIdentifier() {
		super(AVP_CODE,true,VENDOR_ID);
	}
	

	public AccessNetworkChargingIdentifier(String _accessNetworkChargingIdentifierValue) {
		super(AVP_CODE,true,VENDOR_ID);
		this.setAccessNetworkChargingIdentifierValue(new AccessNetworkChargingIdentifierValue(_accessNetworkChargingIdentifierValue));
	}
	
	public AccessNetworkChargingIdentifier(AccessNetworkChargingIdentifierValue _accessNetworkChargingIdentifierValue) {
		super(AVP_CODE,true,VENDOR_ID);
		this.setAccessNetworkChargingIdentifierValue(_accessNetworkChargingIdentifierValue);
	}
	
	/**
	 * access the AccessNetworkChargingIdentifierValue
	 * @return
	 */
	public AccessNetworkChargingIdentifierValue getAccessNetworkChargingIdentifierValue()	{
		return (AccessNetworkChargingIdentifierValue)findChildAVP(AccessNetworkChargingIdentifierValue.AVP_CODE);
	}

	/**
	 * to set the AccessNetworkChargingIdentifierValue AVP
	 * 
	 * a potential existing AVP will be deleted
	 * @param _accessNetworkChargingIdentifier	the AccessNetworkChargingIdentifier AVP to set
	 */
	public void setAccessNetworkChargingIdentifierValue(AccessNetworkChargingIdentifierValue _accessNetworkChargingIdentifier)	{
		setSingleAVP(_accessNetworkChargingIdentifier);
	}
	
	/**
	 * adds a Flows AVP to the Flow
	 * @param _flows	the AVP
	 */
	public void addFlows(Flows _flows)	{
		this.addChildAVP(_flows);
	}
	
	/**
	 * Queries all Flows stored at this GxRAR Message
	 * @return	Iterator of all Flows
	 */
	public Iterator<Flows> getFlowsIterator()	{
		return getIterator(new Flows());
	}

}
