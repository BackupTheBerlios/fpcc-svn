/* 
* QoSNegotiation.java
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

import at.ac.tuwien.ibk.biqini.diameter.I3GPPConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Enumerated;

/**
 * The QoS-Negotiation AVP (AVP code yyyy) is of type Enumerated.  The value 
 * of the AVP indicates for a single PCC rule request if the PCRF is allowed 
 * to negotiate the QoS by supplying in the answer to this request an 
 * authorized QoS different from the requested QoS. 
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class QoSNegotiation extends Enumerated implements I3GPPConstants {

	//FIXME AVP CODE assignment is not done
	public static final int AVP_CODE = 9998;
	/**
	 * This value indicates that a QoS negotiation is not allowed for the 
	 * corresponding PCC rule request.
	 */
	public static final int NO_QoS_NEGOTIATION = 0;
	/**
	 * This value indicates that a QoS negotiation is allowed for the 
	 * corresponding PCC rule request.  This is the default value applicable 
	 * if this AVP is not supplied
	 */
	public static final int QoS_NEGOTIATION_SUPPORTED = 1;
	

	public QoSNegotiation() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}


	/**
	 * @param enumerated
	 */
	public QoSNegotiation(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}
	
	private void init()	{
		mapping.put(0,"NO_QoS_NEGOTIATION");
		mapping.put(1,"QoS_NEGOTIATION_SUPPORTED");
		
		
	}

}
