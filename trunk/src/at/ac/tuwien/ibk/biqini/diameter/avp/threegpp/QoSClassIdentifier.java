/* 
* QoSClassIdentifier.java
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
 * QoS-Class-Identifier AVP (AVP code 1028) is of type Enumerated, and 
 * it identifies a set of IP-CAN specific QoS parameters that define 
 * the authorized QoS, excluding the applicable bitrates for the IP-CAN 
 * bearer or service flow. The following values are defined:
 * The mapping of QCI to UMTS QoS parameters for GPRS is shown in the 
 * following table (coming from TS 23.203 [7] Annex A table A.3):
 * 
 * TS 29.212
 *
 * @author mhappenhofer
 *
 */
public class QoSClassIdentifier extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE = 1028;
	
	public static final int MIN = 1;
	public static final int MAX = 9;
	
	public static final int CONVERSATIONAL_SPEECH = 1;
	public static final int CONVERSATIONAL_UNKNOWN = 2;
	public static final int STREAMING_SPEECH = 3;
	public static final int STREAMING_UNKNOWN = 4;
	public static final int INTERACTIVE_5 = 5;
	public static final int INTERACTIVE_6 = 6;
	public static final int INTERACTIVE_7 = 7;
	public static final int INTERACTIVE_8 = 8;
	public static final int BACKGROUND = 9;

	public QoSClassIdentifier() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	/**
	 * @param enumerated
	 */
	public QoSClassIdentifier(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}
	
	private void init()	{
		mapping.put(CONVERSATIONAL_SPEECH,"Conversational (speech)");
		mapping.put(CONVERSATIONAL_UNKNOWN,"Conversational (unknown)");
		mapping.put(STREAMING_SPEECH,"Streaming (speech)");
		mapping.put(STREAMING_UNKNOWN,"Streaming (unknown)");
		mapping.put(INTERACTIVE_5,"Interactive");
		mapping.put(INTERACTIVE_6,"Interactive");
		mapping.put(INTERACTIVE_7,"Interactive");
		mapping.put(INTERACTIVE_8,"Interactive");
		mapping.put(BACKGROUND,"Background");
	}

}
