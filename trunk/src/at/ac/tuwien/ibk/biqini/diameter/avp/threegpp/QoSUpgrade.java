/* 
* QoSUpgrade.java
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
 * The QoS-Upgrade AVP (AVP code zzzz) is of type Enumerated. The value of the AVP indicates whether the SGSN supports that the GGSN upgrades the QoS in a Create PDP context response or Update PDP context response. If the SGSN does not support a QoS upgrade, the PCRF shall not provision an authorized QoS which is higher than the requested QoS for this IP CAN bearer. The setting is applicable to the bearer indicated in the request within the Bearer-Identifier AVP. 
 * If no QoS-Upgrade AVP has been supplied for an IP CAN bearer, the default value QoS_UPGRADE_NOT_SUPPORTED is applicable. If the QoS-Upgrade AVP has previously been supplied for an IP CAN bearer but is not supplied in a new PCC rule request, the previously supplied value remains applicable.
 * 
 * TS 29.212
 *
 * @author mhappenhofer
 *
 */
public class QoSUpgrade extends Enumerated implements I3GPPConstants {

	//FIXME AVP CODE assignment is not done
	public static final int AVP_CODE = 9997;
	/**
	 * This value indicates that the IP-CAN bearer does not support the 
	 * upgrading of the requested QoS.  This is the default value applicable 
	 * if no QoS-Upgrade AVP has been supplied for an IP CAN bearer.
	 */
	public static final int QoS_UPGRADE_NOT_SUPPORTED = 0;
	/**
	 * This value indicates that the IP-CAN bearer supports the upgrading of 
	 * the requested QoS.
	 */
	public static final int QoS_UPGRADE_SUPPORTED = 1;
	 
	public QoSUpgrade() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	/**
	 * @param enumerated
	 */
	public QoSUpgrade(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}

	private void init()	{
		mapping.put(0, "QoS_UPGRADE_NOT_SUPPORTED");
		mapping.put(1, "QoS_UPGRADE_SUPPORTED");
		
	}
}
