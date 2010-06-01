/* 
* EventReportIndication.java
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
 * The Event-Report-Indication AVP (AVP code xxxx) is of type Grouped, and 
 * it is used to report an event coming from the Access Network (BBERF) and 
 * relevant info to the PCEF.
 * AVP Format:
 * Event-Report-Indication ::= < AVP Header: xxxx >
 * 						*[Event-Trigger]
 * 						 [RAT-Type]
 * 						*[AVP]
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class EventReportIndication extends Grouped implements I3GPPConstants {

	//FIXME AVP CODE assignment is not done
	public static final int AVP_CODE = 9999;

	public EventReportIndication() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @return the RATType
	 */
	public RATType getRATType() {
		return (RATType)findChildAVP(RATType.AVP_CODE);
	}
	/**
	 * @param _rATType the RATType to set
	 */
	public void setRATType(RATType _rATType) {
		this.setSingleAVP(_rATType);
	}
	
	/**
	 * adds a EventTrigger AVP
	 * @param _eventTrigger	the AVP
	 */
	public void addEventTrigger(EventTrigger _eventTrigger)	{
		this.addChildAVP(_eventTrigger);
	}
	
	/**
	 * Queries all EventTrigger stored 
	 * @return	Iterator of all EventTrigger
	 */
	public Iterator<EventTrigger> getEventTriggerIterator()	{
		return getIterator(new EventTrigger());
	}
}
