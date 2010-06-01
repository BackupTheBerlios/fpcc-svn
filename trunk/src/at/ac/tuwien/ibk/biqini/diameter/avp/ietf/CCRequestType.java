/* 
* CCRequestType.java
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
package at.ac.tuwien.ibk.biqini.diameter.avp.ietf;

import at.ac.tuwien.ibk.biqini.diameter.IIETFConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Enumerated;

/**
 * The CC-Request-Type AVP (AVP Code 416) is of type Enumerated and
 * contains the reason for sending the credit-control request message.
 * It MUST be present in all Credit-Control-Request messages.
 *
 * RFC4006
 *
 * @author mhappenhofer
 *
 */
public class CCRequestType extends Enumerated implements IIETFConstants{

	public static final int AVP_CODE = 416;
	
	/**
	 * An Initial request is used to initiate a credit-control session,
     * and contains credit control information that is relevant to the
     * initiation.
	 */
	public static final int INITIAL_REQUEST = 1;
	/**
	 * An Update request contains credit-control information for an
	 * existing credit-control session.  Update credit-control requests
	 * SHOULD be sent every time a credit-control re-authorization is
	 * needed at the expiry of the allocated quota or validity time.
	 * Further, additional service-specific events MAY trigger a
	 * spontaneous Update request.
	 */
	public static final int UPDATE_REQUEST = 2;

	/**
	 * A Termination request is sent to terminate a credit-control
     * session and contains credit-control information relevant to the
     * existing session.
	 */
	public static final int TERMINATION_REQUEST = 3;

	/**
	 * An Event request is used when there is no need to maintain any
     * credit-control session state in the credit-control server.  This
     * request contains all information relevant to the service, and is
     * the only request of the service.  The reason for the Event request
     * is further detailed in the Requested-Action AVP.  The Requested-
     * Action AVP MUST be included in the Credit-Control-Request message
     * when CC-Request-Type is set to EVENT_REQUEST.
	 */
    public static final int EVENT_REQUEST = 4;

	public CCRequestType() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	/**
	 * @param _ccRequestType the correct CC-Request Type
	 */
	public CCRequestType(int _ccRequestType) {
		super(AVP_CODE,true,VENDOR_ID);
		this.setInteger32(_ccRequestType);
		init();
	}
	
	private void init()	{
		mapping.put(1, "INITIAL_REQUEST");
		mapping.put(2, "UPDATE_REQUEST");
		mapping.put(3, "TERMINATION_REQUEST");
		mapping.put(4, "EVENT_REQUEST");
	}
}
