/* 
* Online.java
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
 * The Online AVP (AVP code 1009) is of type Enumerated.
 * If the Online AVP is embedded within a Charging_Rule-Definition 
 * AVP, it defines whether the online charging interface from the PCEF 
 * for the associated PCC rule shall be enabled. The absence of this 
 * AVP within the first provisioning of the Charging-Rule-Definition 
 * AVP of a new PCC rule indicates that the default charging method for 
 * online shall be used.
 * If the Online AVP is embedded within the initial CCR on command 
 * level, it indicates the default charging method for online 
 * pre-configured at the PCEF is applicable as default charging method 
 * for online. The absence of this AVP within the initial CCR indicates 
 * that the charging method for online pre-configured at the PCEF is 
 * not available. 
 * If the Online AVP is embedded within the initial CCA on command 
 * level, it indicates the default charging method for online. The 
 * absence of this AVP within the initial CCA indicates that the 
 * charging method for online pre-configured at the PCEF is applicable 
 * as default charging method for online.
 * The default charging method provided by the PCRF shall take 
 * precedence over any pre-configured default charging method at the 
 * PCEF.
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class Online extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE = 1009;
	
	/**
	 * This value shall be used to indicate that the online charging 
	 * interface for the associated PCC rule shall be disabled.
	 */
	public static final int DISABLE_ONLINE = 0;
	/**
	 * This value shall be used to indicate that the online charging 
	 * interface for the associated PCC rule shall be enabled.
	 */
	public static final int ENABLE_ONLINE = 1;
	

	public Online() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	/**
	 * @param enumerated
	 */
	public Online(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}
	
	private void init()	{
		mapping.put(0,"DISABLE_ONLINE");
		mapping.put(1,"ENABLE_ONLINE");
		
	}

}
