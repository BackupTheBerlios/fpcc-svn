/* 
* AcceptableServiceInfo.java
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
 * The Acceptable-Service-Info AVP (AVP code 526) is of type Grouped, and contains 
 * the maximum bandwidth for an AF session and/or for specific media components 
 * that will be authorized by the PCRF. The Max-Requested-Bandwidth-DL AVP and 
 * Max-Requested-Bandwidth-UL AVP directly within the Acceptable-Service-Info AVP 
 * indicate the acceptable bandwidth for the entire AF session. The 
 * Max-Requested-Bandwidth-DL AVP and Max-Requested-Bandwidth-UL AVP within a 
 * Media-Component-Description AVP included in the Acceptable-Service-Info AVP 
 * indicate the acceptable bandwidth for the corresponding media component.
 * If the acceptable bandwidth applies to one or more media components, only the 
 * Media-Component-Description AVP will be provided. If the acceptable bandwidth 
 * applies to the whole AF session, only the Max-Requested-Bandwidth-DL AVP and 
 * Max-Requested-Bandwidth-UL AVP will be included.
 * Acceptable-Service-Info::= < AVP Header: x >
 *						*[ Media-Component-Description]
 *						 [ Max-Requested-Bandwidth-DL ]
 *						 [ Max-Requested-Bandwidth-UL ]
 *						*[ AVP ]
 * 
 * TS 29.214
 * 
 * @author mhappenhofer
 *
 */
public class AcceptableServiceInfo extends Grouped implements I3GPPConstants{

	public static final int AVP_CODE = 526;
	
	public AcceptableServiceInfo() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * sets the maximal Requested Bandwidth in the download direction
 	 * if this bandwidth was already configured, it will be deleted before
	 * @param _maxRequestedBandwidthUL
	 */
	public void setMaxRequestedBandwidthDL(MaxRequestedBandwidthDL _maxRequestedBandwidthDL)	{
		this.setSingleAVP(_maxRequestedBandwidthDL);
	}
	/**
	 * queries the maximal Requested Bandwidth in the download direction
	 * @return
	 */
	public MaxRequestedBandwidthDL getMaxRequestedBandwidthDL()	{
		return (MaxRequestedBandwidthDL)findChildAVP(MaxRequestedBandwidthDL.AVP_CODE);
	}
	
	/**
	 * sets the maximal Requested Bandwidth in the upload direction
	 * if this bandwidth was already configured, it will be deleted before
	 * @param _maxRequestedBandwidthUL
	 */
	public void setMaxRequestedBandwidthUL(MaxRequestedBandwidthUL _maxRequestedBandwidthUL)	{
		this.setSingleAVP(_maxRequestedBandwidthUL);
	}
	/**
	 * queries the maximal Requested Bandwidth in the upload direction
	 * @return
	 */
	public MaxRequestedBandwidthUL getMaxRequestedBandwidthUL()	{
		return (MaxRequestedBandwidthUL)findChildAVP(MaxRequestedBandwidthUL.AVP_CODE);
	}
	
	/**
	 * adds a MediaComponentDescription
	 * automatically updates the maxRequested Bandwidths
	 * 
	 * @param _mediaSubComponent
	 */
	public void addMediaComponentDescription(MediaComponentDescription _mediaSubComponent)	{
		this.addChildAVP(_mediaSubComponent);
		
		// update some parameters 
		int maxReqUL = 0;
		int maxReqDL = 0;
		// FlowStatus
		for(Iterator<MediaComponentDescription>it = getMediaComponentDescriptionIterator();it.hasNext();)
		{
			// update Requested bandwidths
			MediaComponentDescription curr = it.next();
			MaxRequestedBandwidthUL maxRequestedBandwidthUL = curr.getMaxRequestedBandwidthUL();
			MaxRequestedBandwidthDL maxRequestedBandwidthDL = curr.getMaxRequestedBandwidthDL();
			if(maxRequestedBandwidthUL!=null)
				maxReqDL = maxReqDL + (int)maxRequestedBandwidthDL.getUnsigned32();
			if(maxRequestedBandwidthDL!=null)
				maxReqUL = maxReqUL + (int)maxRequestedBandwidthUL.getUnsigned32();
		}
		MaxRequestedBandwidthUL _mRUL = new MaxRequestedBandwidthUL(maxReqUL);
		MaxRequestedBandwidthDL _mRDL = new MaxRequestedBandwidthDL(maxReqDL);
		this.setMaxRequestedBandwidthDL(_mRDL);
		this.setMaxRequestedBandwidthUL(_mRUL);
	}
	
	/**
	 * Queries all MediaComponentDescription stored 
	 * @return	Iterator of all MediaComponentDescription
	 */
	public Iterator<MediaComponentDescription> getMediaComponentDescriptionIterator()	{
		return getIterator(new MediaComponentDescription());
	}
}
