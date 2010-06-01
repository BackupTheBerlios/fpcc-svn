/* 
* TFTPacketFilterInformation.java
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
 * The TFT-Packet-Filter-Information AVP (AVP code 1013) is of type Grouped, 
 * and it contains the information from a single TFT packet filter including 
 * the evaluation precedence, the filter and the Type-of-Service/Traffic Class 
 * sent from the PCEF to the PCRF. The PCEF shall include one 
 * TFT-Packet-Filter-Information AVP for each TFT packet filters applicable at 
 * a PDP context in separate TFT-Packet-Filter-Information AVPs within each 
 * PCC rule request. corresponding to that PDP context. 
 * TFT-Packet-Filter-Information AVPs are derived from the Traffic Flow 
 * Template (TFT) defined in 3GPP TS 24.008 [13].
 * AVP Format:
 * TFT-Packet-Filter-Information ::= < AVP Header: 1013 >
 * 							 [ Precedence ]
 *							 [ TFT-Filter ]
 *							 [ ToS-Traffic-Class ]
 *
 * TS 29.212
 *
 * @author mhappenhofer
 *
 */
public class TFTPacketFilterInformation extends Grouped implements I3GPPConstants {

	public static final int AVP_CODE = 1013;

	
	public TFTPacketFilterInformation() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @return the precedence
	 */
	public Precedence getPrecedence() {
		return (Precedence)findChildAVP(Precedence.AVP_CODE);
	}
	/**
	 * @param _precedence the precedence to set
	 */
	public void setPrecedence(Precedence _precedence) {
		this.setSingleAVP(_precedence);
	}


	/**
	 * @return the TFTFilter
	 */
	public TFTFilter getTFTFilter() {
		return (TFTFilter)findChildAVP(TFTFilter.AVP_CODE);
	}
	/**
	 * @param _filter the tFilter to set
	 */
	public void setTFTFilter(TFTFilter _filter) {
		this.setSingleAVP(_filter);
	}

	/**
	 * @return the toSTrafficClass
	 */
	public ToSTrafficClass getToSTrafficClass() {
		return (ToSTrafficClass)findChildAVP(ToSTrafficClass.AVP_CODE);
	}

	/**
	 * @param _toSTrafficClass the toSTrafficClass to set
	 */
	public void setToSTrafficClass(ToSTrafficClass _toSTrafficClass) {
		this.setSingleAVP(_toSTrafficClass);
	}

	
	
}
