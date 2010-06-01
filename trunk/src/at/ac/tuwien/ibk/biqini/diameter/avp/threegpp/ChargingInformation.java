/* 
* ChargingInformation.java
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
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Grouped;

/**
 * The Charging-Information is of type Grouped, and contains the addresses of the 
 * charging functions.
 * AVP format
 * Charging-Information :: = < AVP Header : 618 10415 >
 * 						[ Primary-Event-Charging-Function-Name ]
 * 						[ Secondary-Event-Charging-Function-Name ]
 * 						[ Primary-Charging-Collection-Function-Name ]
 * 						[ Secondary-Charging-Collection-Function-Name ]
 *						*[ AVP]
 * TS 29.229 
 *
 * @author mhappenhofer
 *
 */
public class ChargingInformation extends Grouped implements I3GPPConstants {

	public static final int AVP_CODE = 618;
	
	public ChargingInformation() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @return the primaryChargingCollectionFunctionName
	 */
	public PrimaryChargingCollectionFunctionName getPrimaryChargingCollectionFunctionName() {
		return (PrimaryChargingCollectionFunctionName)findChildAVP(PrimaryChargingCollectionFunctionName.AVP_CODE);
	}
	/**
	 * @param _primaryChargingCollectionFunctionName the primaryChargingCollectionFunctionName to set
	 */
	public void setPrimaryChargingCollectionFunctionName(
			PrimaryChargingCollectionFunctionName _primaryChargingCollectionFunctionName) {
		this.setSingleAVP(_primaryChargingCollectionFunctionName);
	}


	/**
	 * @return the primaryEventChargingFunctionName
	 */
	public PrimaryEventChargingFunctionName getPrimaryEventChargingFunctionName() {
		return (PrimaryEventChargingFunctionName)findChildAVP(PrimaryEventChargingFunctionName.AVP_CODE);
	}
	/**
	 * @param _primaryEventChargingFunctionName the primaryEventChargingFunctionName to set
	 */
	public void setPrimaryEventChargingFunctionName(
			PrimaryEventChargingFunctionName _primaryEventChargingFunctionName) {
		this.setSingleAVP(_primaryEventChargingFunctionName);
	}


	/**
	 * @return the secondaryChargingCollectionFunctionName
	 */
	public SecondaryChargingCollectionFunctionName getSecondaryChargingCollectionFunctionName() {
		return(SecondaryChargingCollectionFunctionName)findChildAVP(SecondaryChargingCollectionFunctionName.AVP_CODE);
	}
	/**
	 * @param _secondaryChargingCollectionFunctionName the secondaryChargingCollectionFunctionName to set
	 */
	public void setSecondaryChargingCollectionFunctionName(
			SecondaryChargingCollectionFunctionName _secondaryChargingCollectionFunctionName) {
		this.setSingleAVP(_secondaryChargingCollectionFunctionName);
	}


	/**
	 * @return the secondaryEventChargingFunctionName
	 */
	public SecondaryEventChargingFunctionName getSecondaryEventChargingFunctionName() {
		return (SecondaryEventChargingFunctionName)findChildAVP(SecondaryEventChargingFunctionName.AVP_CODE);
	}
	/**
	 * @param secondaryEventChargingFunctionName the secondaryEventChargingFunctionName to set
	 */
	public void setSecondaryEventChargingFunctionName(
			SecondaryEventChargingFunctionName _secondaryEventChargingFunctionName) {
		this.setSingleAVP(_secondaryEventChargingFunctionName);
	}
}
