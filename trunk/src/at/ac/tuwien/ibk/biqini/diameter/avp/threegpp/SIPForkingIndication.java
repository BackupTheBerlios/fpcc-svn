/* 
* SIPForkingIndication.java
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
 * The SIP-Forking-Indication AVP (AVP code 523) is of type 
 * Enumerated, and describes if several SIP dialogues are related 
 * to one Diameter session
 * 
 * TS 29.214
 * 
 * @author mhappenhofer
 *
 */
public class SIPForkingIndication extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE = 523;
	
	/**
	 * This value is used to indicate that the Diameter session 
	 * relates to a single SIP dialogue.
	 * 
	 * This is the default value applicable if the AVP is omitted.
	 */
	public static final int SINGLE_DIALOGUE = 0;
	/**
	 * This value is used to indicate that the Diameter session 
	 * relates to several SIP dialogues.
	 */
	public static final int SEVERAL_DIALOGUES = 1;
	
	
	public SIPForkingIndication() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	public SIPForkingIndication(int _sipForkingIndication) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(_sipForkingIndication);
		init();
	}

	private void init()	{
		mapping.put(0, "SINGLE_DIALOGUE");
		mapping.put(1, "SEVERAL_DIALOGUES");
	}
}
