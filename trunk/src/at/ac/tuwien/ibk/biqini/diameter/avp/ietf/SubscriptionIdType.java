/* 
* SubscriptionIdType.java
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
 * The Subscription-Id-Type AVP (AVP Code 450) is of type Enumerated,
 * and it is used to determine which type of identifier is carried by
 * the Subscription-Id AVP.
 *
 * This specification defines the following subscription identifiers.
 * However, new Subscription-Id-Type values can be assigned by an IANA
 * designated expert, as defined in section 12.  A server MUST implement
 * all the Subscription-Id-Types required to perform credit
 * authorization for the services it supports, including possible future
 * values.  Unknown or unsupported Subscription-Id-Types MUST be treated
 * according to the 'M' flag rule, as defined in [DIAMBASE].
 *
 * RFC4006
 *
 * @author mhappenhofer
 *
 */
public class SubscriptionIdType extends Enumerated implements IIETFConstants{

	public static final int AVP_CODE = 450;
	
	/**
	 * The identifier is in international E.164 format (e.g., MSISDN),
	 * according to the ITU-T E.164 numbering plan defined in [E164] and
	 * [CE164].
	 */
	public static final int END_USER_E164 = 0;
	/**
	 * The identifier is in international IMSI format, according to the
	 * ITU-T E.212 numbering plan as defined in [E212] and [CE212].
	 */
  	public static final int END_USER_IMSI = 1;
  	/**
  	 * The identifier is in the form of a SIP URI, as defined in [SIP].
  	 */
  	public static final int END_USER_SIP_URI = 2;
	/**
	 * The identifier is in the form of a Network Access Identifier, as
	 * defined in [NAI].
	 */      
	public static final int END_USER_NAI = 3;
	/**
	 * The Identifier is a credit-control server private identifier.
	 */
  	public static final int END_USER_PRIVATE = 4;
	      
	public SubscriptionIdType() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}
	

	/**
	 * @param _subscriptionIdType
	 */
	public SubscriptionIdType(int _subscriptionIdType) {
		super(AVP_CODE,true,VENDOR_ID);
		this.setInteger32(_subscriptionIdType);
		init();
	}

	
	private void init()	{
		mapping.put(0, "END_USER_E164");
		mapping.put(1, "END_USER_IMSI");
		mapping.put(2, "END_USER_SIP_URI");
		mapping.put(3, "END_USER_NAI");
		mapping.put(4, "END_USER_PRIVATE");
		
	}
}
