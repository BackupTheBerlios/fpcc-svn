/* 
* QoSInformation.java
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
 * The QoS-Information AVP (AVP code 1016) is of type Grouped, and it defines 
 * the QoS information for an IP-CAN bearer, PCC rule or QCI. When this AVP is 
 * sent from the PCEF to the PCRF, it indicates the requested QoS information 
 * for an IP CAN bearer. When this AVP is sent from the PCRF to the PCEF, it 
 * indicates the authorized QoS for an IP CAN bearer (when appearing at CCA 
 * or RAR command level or a service flow (when included within the PCC rule) 
 * or a QCI (when appearing at CCA or RAR command level with the 
 * QoS-Class-Identifier AVP and the Maximum-Requested-Bandwidth-UL AVP and/or 
 * the Maximum-Requested-Bandwidth-DL AVP).
 * The QoS class identifier identifies a set of IP-CAN specific QoS parameters 
 * that define QoS, excluding the applicable bitrates. It is applicable both 
 * for uplink and downlink direction.
 * The Max-Requested-Bandwidth-UL defines the maximum bit rate allowed for the 
 * uplink direction.
 * The Max-Requested-Bandwidth-DL defines the maximum bit rate allowed for the 
 * downlink direction.
 * The Guaranteed-Bitrate-UL defines the guaranteed bit rate allowed for the 
 * uplink direction.
 * The Guaranteed-Bitrate-DL defines the guaranteed bit rate allowed for the 
 * downlink direction.
 * The Bearer Identifier AVP shall be included as part of the QoS-Information 
 * AVP if the QoS information refers to an IP CAN bearer initiated by the UE 
 * and the PCRF performs the bearer binding. The Bearer Identifier AVP 
 * identifies this bearer. Several QoS-Information AVPs for different Bearer 
 * Identifiers may be provided per command.
 * If the QoS-Information AVP has been supplied previously but is omitted 
 * in a Diameter message or AVP, the previous information remains valid. 
 * If the QoS-Information AVP has not been supplied from the PCRF to the 
 * PCEF previously and is omitted in a Diameter message or AVP, no 
 * enforcement of the authorized QoS shall be performed.
 * AVP Format:
 * QoS-Information ::= 	< AVP Header: 1016 >
 * 					[ QoS-Class-Identifier ]
 *					[ Max-Requested-Bandwidth-UL ]
 *					[ Max-Requested-Bandwidth-DL ]
 *					[ Guaranteed-Bitrate-UL ]
 *					[ Guaranteed-Bitrate-DL ]
 *					[ Bearer-Identifier ]
 *
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class QoSInformation extends Grouped implements I3GPPConstants {

	public static final int AVP_CODE = 1016;
	
	
	public QoSInformation() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	
	/**
	 * @return the qoSClassIdentifier
	 */
	public QoSClassIdentifier getQoSClassIdentifier() {
		return (QoSClassIdentifier)findChildAVP(QoSClassIdentifier.AVP_CODE);
	}
	/**
	 * @param _qoSClassIdentifier the qoSClassIdentifier to set
	 */
	public void setQoSClassIdentifier(QoSClassIdentifier _qoSClassIdentifier) {
		setSingleAVP(_qoSClassIdentifier);
	}

	/**
	 * @return the maxRequestedBandwidthUL
	 */
	public MaxRequestedBandwidthUL getMaxRequestedBandwidthUL() {
		return (MaxRequestedBandwidthUL)findChildAVP(MaxRequestedBandwidthUL.AVP_CODE);
	}
	/**
	 * @param _maxRequestedBandwidthUL the maxRequestedBandwidthUL to set
	 */
	public void setMaxRequestedBandwidthUL(
			MaxRequestedBandwidthUL _maxRequestedBandwidthUL) {
		setSingleAVP(_maxRequestedBandwidthUL);
	}

	/**
	 * @return the maxRequestedBandwidthDL
	 */
	public MaxRequestedBandwidthDL getMaxRequestedBandwidthDL() {
		return (MaxRequestedBandwidthDL)findChildAVP(MaxRequestedBandwidthDL.AVP_CODE);
	}
	/**
	 * @param _maxRequestedBandwidthDL the maxRequestedBandwidthDL to set
	 */
	public void setMaxRequestedBandwidthDL(
			MaxRequestedBandwidthDL _maxRequestedBandwidthDL) {
		setSingleAVP(_maxRequestedBandwidthDL);
	}

	/**
	 * @return the guaranteedBitrateUL
	 */
	public GuaranteedBitrateUL getGuaranteedBitrateUL() {
		return (GuaranteedBitrateUL)findChildAVP(GuaranteedBitrateUL.AVP_CODE);
	}
	/**
	 * @param _guaranteedBitrateUL the guaranteedBitrateUL to set
	 */
	public void setGuaranteedBitrateUL(GuaranteedBitrateUL _guaranteedBitrateUL) {
		setSingleAVP(_guaranteedBitrateUL);
	}

	/**
	 * @return the guaranteedBitrateDL
	 */
	public GuaranteedBitrateDL getGuaranteedBitrateDL() {
		return (GuaranteedBitrateDL)findChildAVP(GuaranteedBitrateDL.AVP_CODE);
	}
	/**
	 * @param _guaranteedBitrateDL the guaranteedBitrateDL to set
	 */
	public void setGuaranteedBitrateDL(GuaranteedBitrateDL _guaranteedBitrateDL) {
		setSingleAVP(_guaranteedBitrateDL);
	}

	/**
	 * @return the bearerIdentifier
	 */
	public BearerIdentifier getBearerIdentifier() {
		return (BearerIdentifier)findChildAVP(BearerIdentifier.AVP_CODE);
	}
	/**
	 * @param _bearerIdentifier the bearerIdentifier to set
	 */
	public void setBearerIdentifier(BearerIdentifier _bearerIdentifier) {
		setSingleAVP(_bearerIdentifier);
	}

	
}
