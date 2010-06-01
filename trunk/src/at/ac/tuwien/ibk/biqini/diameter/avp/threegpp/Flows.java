/* 
* Flows.java
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
 * The Flows AVP (AVP code 510) is of type Grouped, and it indicates IP flows 
 * via their flow identifiers.
 * If no Flow-Number AVP(s) are supplied, the Flows AVP refers to all Flows 
 * matching the media component number.
 * AVP Format:
 *	Flows::= < AVP Header: x >
 *	      { Media-Component-Number}
 *	      *[ Flow-Number]
 *
 * TS 29.214
 *
 * @author mhappenhofer
 *
 */
public class Flows extends Grouped implements I3GPPConstants {

	public static final int AVP_CODE = 510;
	
	public Flows() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	public Flows(long _MediaComponentNumber) {
		super(AVP_CODE,true,VENDOR_ID);
		this.setMediaComponentNumber(new MediaComponentNumber(_MediaComponentNumber));
	}
	
	public Flows(MediaComponentNumber _MediaComponentNumber) {
		super(AVP_CODE,true,VENDOR_ID);
		this.setMediaComponentNumber(_MediaComponentNumber);
	}
	/**
	 * access the MediaComponentNumber
	 * @return
	 */
	public MediaComponentNumber getMediaComponentNumber()	{
		return (MediaComponentNumber)findChildAVP(MediaComponentNumber.AVP_CODE);
	}
	/**
	 * to set the MediaComponenetNumber AVP
	 * 
	 * a potential existing AVP will be deleted
	 * @param _mediaComponentNumber	the MediaComponentNumber AVP to set
	 */
	public void setMediaComponentNumber(MediaComponentNumber _mediaComponentNumber)	{
		this.setSingleAVP(_mediaComponentNumber);
	}
	/**
	 * adds a FlowNumber AVP to the Flow
	 * @param _flowNumber	the AVP
	 */
	public void addFlowNumber(FlowNumber _flowNumber)	{
		this.addChildAVP(_flowNumber);
	}

	/**
	 * Queries all FlowNumber stored 
	 * @return	Iterator of all FlowNumber
	 */
	public Iterator<FlowNumber> getFlowNumberIterator()	{
		return getIterator(new FlowNumber());
	}
}
