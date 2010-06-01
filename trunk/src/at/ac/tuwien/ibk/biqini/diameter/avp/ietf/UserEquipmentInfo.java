/* 
* UserEquipmentInfo.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Grouped;

/**
 * The User-Equipment-Info AVP (AVP Code 458) is of type Grouped and
 * allows the credit-control client to indicate the identity and
 * capability of the terminal the subscriber is using for the connection
 * to network.
 *
 * It is defined as follows (per the grouped-avp-def of RFC 3588
 * [DIAMBASE]):
 *
 *    User-Equipment-Info ::= < AVP Header: 458 >
 *                            { User-Equipment-Info-Type }
 *                            { User-Equipment-Info-Value }
 *
 * RFC4006
 *
 * @author mhappenhofer
 *
 */
public class UserEquipmentInfo extends Grouped implements IIETFConstants{

	public static final int AVP_CODE = 458;

	public UserEquipmentInfo() {
		super(AVP_CODE,true,VENDOR_ID);
	}
	
	public UserEquipmentInfo(UserEquipmentInfoType _UserEquipmentInfoType, UserEquipmentInfoValue _UserEquipmentInfoValue) {
		super(AVP_CODE,true,VENDOR_ID);
		this.setUserEquipmentInfoType(_UserEquipmentInfoType);
		this.setUserEquipmentInfoValue(_UserEquipmentInfoValue);
	}

	public void setUserEquipmentInfoType(UserEquipmentInfoType _UserEquipmentInfoType)	{
		this.setSingleAVP(_UserEquipmentInfoType);
	}
	public UserEquipmentInfoType getUserEquipmentInfoType()	{
		return (UserEquipmentInfoType)findChildAVP(UserEquipmentInfoType.AVP_CODE);
	}
	
	public void setUserEquipmentInfoValue(UserEquipmentInfoValue _UserEquipmentInfoValue)	{
		this.setSingleAVP(_UserEquipmentInfoValue);
	}
	public UserEquipmentInfoValue getUserEquipmentInfoValue()	{
		return (UserEquipmentInfoValue)findChildAVP(UserEquipmentInfoValue.AVP_CODE);
	}

}
