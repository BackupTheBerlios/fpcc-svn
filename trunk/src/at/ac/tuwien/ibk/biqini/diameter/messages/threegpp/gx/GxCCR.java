/* 
* GxCCR.java
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
package at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx;

import java.util.Iterator;

import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.FramedIPAddress;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.FramedIPv6Prefix;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AccessNetworkChargingAddress;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AccessNetworkChargingIdentifierGx;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.BearerControlMode;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.BearerIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.BearerOperation;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.BearerUsage;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleReport;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.EventTrigger;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.IPCANType;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.NetworkRequestSupport;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Offline;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Online;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSInformation;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSNegotiation;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSUpgrade;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.RAI;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.RATType;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.TFTPacketFilterInformation;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ThreeGPPSGSNAddress;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ThreeGPPSGSNIPv6Prefix;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ThreeGPPSGSNMCCMNC;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ThreeGPPUserLocationInfo;
import at.ac.tuwien.ibk.biqini.diameter.messages.common.CCR;

/**
 * The CCR command, indicated by the Command-Code field set to 272 and the 
 * 'R' bit set in the Command Flags field, is sent by the PCEF to the PCRF 
 * in order to request PCC rules for a bearer. The CCR command is also sent 
 * by the PCEF to the PCRF in order to indicate bearer or PCC rule related 
 * events or the termination of the IP CAN bearer and/or session.
 * Message Format:
 * <CC-Request> ::= < Diameter Header: 272, REQ, PXY >
 *				 < Session-Id >
 *				 { Auth-Application-Id }
 *				 { Origin-Host }
 *				 { Origin-Realm }
 *				 { Destination-Realm }
 *				 { CC-Request-Type }
 *				 { CC-Request-Number }
 *				 [ Destination-Host ]
 *				 [ Origin-State-Id ]
 *				*[ Subscription-Id ]
 *				 [ Bearer-Control-Mode ]
 *				 [ Network-Request-Support ]
 *				 [ Bearer-Identifier ]
 *				 [ Bearer-Operation ]
 *				 [ Framed-IP-Address ]
 *				 [ Framed-IPv6-Prefix ]
 *				 [ IP-CAN-Type ]
 *				 [RAT-Type ]
 *				 [ Termination-Cause ]
 *				 [ User-Equipment-Info ]
 *				 [ QoS-Information ] 
 *               [ QoS-Negotiation ]
 *               [ QoS-Upgrade ]								 
 *				 [ 3GPP-SGSN-MCC-MNC ]
 *				 [ 3GPP-SGSN-Address ]
 *				 [ 3GPP-SGSN-IPv6-Address ]
 *				 [ RAI ]
 *				 [ 3GPP-User-Location-Info]
 *				 [ Called-Station-ID ]
 *				 [ Bearer-Usage ]
 *				 [ Online ]
 *				 [ Offline ]
 *				*[ TFT-Packet-Filter-Information ]
 *				*[ Charging-Rule-Report]
 *				*[ Event-Trigger]
 *				 [ Access-Network-Charging-Address ]
 *				*[ Access-Network-Charging-Identifier-Gx ]
 *				*[ Proxy-Info ]
 *				*[ Route-Record ]
 *				*[ AVP ]
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class GxCCR extends CCR implements I3GPPGxInterface {

	public GxCCR() {
		super(APPLICATION_ID);
	}
	
	
	/**
	 * Searches for the BearerControlMode AVP inside a message
	 * @return the found BearerControlMode AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public BearerControlMode getBearerControlMode() {
		return (BearerControlMode)findAVP(BearerControlMode.AVP_CODE);
	}
	/**
	 * sets the BearerControlMode and overrides a existing one
	 * @param _BearerControlMode	the new BearerControlMode
	 * 
	 * @author mhappenhofer
	 */
	public void setBearerControlMode(BearerControlMode _BearerControlMode)	{
		this.setSingleAVP( _BearerControlMode);
	}
	/**
	 * Searches for the NetworkRequestSupport AVP inside a message
	 * @return the found NetworkRequestSupport AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public NetworkRequestSupport getNetworkRequestSupport() {
		return (NetworkRequestSupport)findAVP(NetworkRequestSupport.AVP_CODE);
	}
	/**
	 * sets the NetworkRequestSupport and overrides a existing one
	 * @param _NetworkRequestSupport	the new NetworkRequestSupport
	 * 
	 * @author mhappenhofer
	 */
	public void setNetworkRequestSupport(NetworkRequestSupport _NetworkRequestSupport)	{
		this.setSingleAVP( _NetworkRequestSupport);
	}
	/**
	 * Searches for the BearerIdentifier AVP inside a message
	 * @return the found BearerIdentifier AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public BearerIdentifier getBearerIdentifier() {
		return (BearerIdentifier)findAVP(BearerIdentifier.AVP_CODE);
	}
	/**
	 * sets the BearerIdentifier and overrides a existing one
	 * @param _BearerIdentifier	the new BearerIdentifier
	 * 
	 * @author mhappenhofer
	 */
	public void setBearerIdentifier(BearerIdentifier _BearerIdentifier)	{
		this.setSingleAVP( _BearerIdentifier);
	}
	/**
	 * Searches for the BearerOperation AVP inside a message
	 * @return the found BearerOperation AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public BearerOperation getBearerOperation() {
		return (BearerOperation)findAVP(BearerOperation.AVP_CODE);
	}
	/**
	 * sets the BearerOperation and overrides a existing one
	 * @param _BearerOperation	the new BearerOperation
	 * 
	 * @author mhappenhofer
	 */
	public void setBearerOperation(BearerOperation _BearerOperation)	{
		this.setSingleAVP( _BearerOperation);
	}
	/**
	 * Searches for the FramedIPAddress AVP inside a message
	 * @return the found FramedIPAddress AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public FramedIPAddress getFramedIPAddress() {
		return (FramedIPAddress)findAVP(FramedIPAddress.AVP_CODE);
	}
	/**
	 * sets the FramedIPAddress and overrides a existing one
	 * @param _FramedIPAddress	the new FramedIPAddress
	 * 
	 * @author mhappenhofer
	 */
	public void setFramedIPAddress(FramedIPAddress _FramedIPAddress)	{
		this.setSingleAVP( _FramedIPAddress);
	}
	/**
	 * Searches for the FramedIPv6Prefix AVP inside a message
	 * @return the found FramedIPv6Prefix AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public FramedIPv6Prefix getFramedIPv6Prefix() {
		return (FramedIPv6Prefix)findAVP(FramedIPv6Prefix.AVP_CODE);
	}
	/**
	 * sets the FramedIPv6Prefix and overrides a existing one
	 * @param _FramedIPv6Prefix	the new FramedIPv6Prefix
	 * 
	 * @author mhappenhofer
	 */
	public void setFramedIPv6Prefix(FramedIPv6Prefix _FramedIPv6Prefix)	{
		this.setSingleAVP( _FramedIPv6Prefix);
	}
	/**
	 * Searches for the IPCANType AVP inside a message
	 * @return the found IPCANType AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public IPCANType getIPCANType() {
		return (IPCANType)findAVP(IPCANType.AVP_CODE);
	}
	/**
	 * sets the IPCANType and overrides a existing one
	 * @param _IPCANType	the new IPCANType
	 * 
	 * @author mhappenhofer
	 */
	public void setIPCANType(IPCANType _IPCANType)	{
		this.setSingleAVP( _IPCANType);
	}
	/**
	 * Searches for the RATType AVP inside a message
	 * @return the found RATType AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public RATType getRATType() {
		return (RATType)findAVP(RATType.AVP_CODE);
	}
	/**
	 * sets the RATType and overrides a existing one
	 * @param _RATType	the new RATType
	 * 
	 * @author mhappenhofer
	 */
	public void setRATType(RATType _RATType)	{
		this.setSingleAVP( _RATType);
	}
	/**
	 * Searches for the QoSInformation AVP inside a message
	 * @return the found QoSInformation AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public QoSInformation getQoSInformation() {
		return (QoSInformation)findAVP(QoSInformation.AVP_CODE);
	}
	/**
	 * sets the QoSInformation and overrides a existing one
	 * @param _QoSInformation	the new QoSInformation
	 * 
	 * @author mhappenhofer
	 */
	public void setQoSInformation(QoSInformation _QoSInformation)	{
		this.setSingleAVP( _QoSInformation);
	}
	/**
	 * Searches for the QoSNegotiation AVP inside a message
	 * @return the found QoSNegotiation AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public QoSNegotiation getQoSNegotiation() {
		return (QoSNegotiation)findAVP(QoSNegotiation.AVP_CODE);
	}
	/**
	 * sets the QoSNegotiation and overrides a existing one
	 * @param _QoSNegotiation	the new QoSNegotiation
	 * 
	 * @author mhappenhofer
	 */
	public void setQoSNegotiation(QoSNegotiation _QoSNegotiation)	{
		this.setSingleAVP( _QoSNegotiation);
	}
	/**
	 * Searches for the QoSUpgrade AVP inside a message
	 * @return the found QoSUpgrade AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public QoSUpgrade getQoSUpgrade() {
		return (QoSUpgrade)findAVP(QoSUpgrade.AVP_CODE);
	}
	/**
	 * sets the QoSUpgrade and overrides a existing one
	 * @param _QoSUpgrade	the new QoSUpgrade
	 * 
	 * @author mhappenhofer
	 */
	public void setQoSUpgrade(QoSUpgrade _QoSUpgrade)	{
		this.setSingleAVP( _QoSUpgrade);
	}
	/**
	 * Searches for the ThreeGPPSGSNMCCMNC AVP inside a message
	 * @return the found ThreeGPPSGSNMCCMNC AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public ThreeGPPSGSNMCCMNC getThreeGPPSGSNMCCMNC() {
		return (ThreeGPPSGSNMCCMNC)findAVP(ThreeGPPSGSNMCCMNC.AVP_CODE);
	}
	/**
	 * sets the ThreeGPPSGSNMCCMNC and overrides a existing one
	 * @param _ThreeGPPSGSNMCCMNC	the new ThreeGPPSGSNMCCMNC
	 * 
	 * @author mhappenhofer
	 */
	public void setThreeGPPSGSNMCCMNC(ThreeGPPSGSNMCCMNC _ThreeGPPSGSNMCCMNC)	{
		this.setSingleAVP( _ThreeGPPSGSNMCCMNC);
	}
	/**
	 * Searches for the ThreeGPPSGSNAddress AVP inside a message
	 * @return the found ThreeGPPSGSNAddress AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public ThreeGPPSGSNAddress getThreeGPPSGSNAddress() {
		return (ThreeGPPSGSNAddress)findAVP(ThreeGPPSGSNAddress.AVP_CODE);
	}
	/**
	 * sets the ThreeGPPSGSNAddress and overrides a existing one
	 * @param _ThreeGPPSGSNAddress	the new ThreeGPPSGSNAddress
	 * 
	 * @author mhappenhofer
	 */
	public void setThreeGPPSGSNAddress(ThreeGPPSGSNAddress _ThreeGPPSGSNAddress)	{
		this.setSingleAVP( _ThreeGPPSGSNAddress);
	}
	/**
	 * Searches for the ThreeGPPSGSNIPv6Prefix AVP inside a message
	 * @return the found ThreeGPPSGSNIPv6Prefix AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public ThreeGPPSGSNIPv6Prefix getThreeGPPSGSNIPv6Prefix() {
		return (ThreeGPPSGSNIPv6Prefix)findAVP(ThreeGPPSGSNIPv6Prefix.AVP_CODE);
	}
	/**
	 * sets the ThreeGPPSGSNIPv6Prefix and overrides a existing one
	 * @param _ThreeGPPSGSNIPv6Prefix	the new ThreeGPPSGSNIPv6Prefix
	 * 
	 * @author mhappenhofer
	 */
	public void setThreeGPPSGSNIPv6Prefix(ThreeGPPSGSNIPv6Prefix _ThreeGPPSGSNIPv6Prefix)	{
		this.setSingleAVP( _ThreeGPPSGSNIPv6Prefix);
	}
	/**
	 * Searches for the RAI AVP inside a message
	 * @return the found RAI AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public RAI getRAI() {
		return (RAI)findAVP(RAI.AVP_CODE);
	}
	/**
	 * sets the RAI and overrides a existing one
	 * @param _RAI	the new RAI
	 * 
	 * @author mhappenhofer
	 */
	public void setRAI(RAI _RAI)	{
		this.setSingleAVP( _RAI);
	}
	/**
	 * Searches for the ThreeGPPUserLocationInfo AVP inside a message
	 * @return the found ThreeGPPUserLocationInfo AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public ThreeGPPUserLocationInfo getThreeGPPUserLocationInfo() {
		return (ThreeGPPUserLocationInfo)findAVP(ThreeGPPUserLocationInfo.AVP_CODE);
	}
	/**
	 * sets the ThreeGPPUserLocationInfo and overrides a existing one
	 * @param _ThreeGPPUserLocationInfo	the new ThreeGPPUserLocationInfo
	 * 
	 * @author mhappenhofer
	 */
	public void setThreeGPPUserLocationInfo(ThreeGPPUserLocationInfo _ThreeGPPUserLocationInfo)	{
		this.setSingleAVP( _ThreeGPPUserLocationInfo);
	}
	/**
	 * Searches for the BearerUsage AVP inside a message
	 * @return the found BearerUsage AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public BearerUsage getBearerUsage() {
		return (BearerUsage)findAVP(BearerUsage.AVP_CODE);
	}
	/**
	 * sets the BearerUsage and overrides a existing one
	 * @param _BearerUsage	the new BearerUsage
	 * 
	 * @author mhappenhofer
	 */
	public void setBearerUsage(BearerUsage _BearerUsage)	{
		this.setSingleAVP( _BearerUsage);
	}
	



	/**
	 * Searches for the Online AVP inside a message
	 * @return the found Online AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public Online getOnline() {
		return (Online)findAVP(Online.AVP_CODE);
	}
	/**
	 * sets the Online and overrides a existing one
	 * @param _destinationRealm	the new Online
	 * 
	 * @author mhappenhofer
	 */
	public void setOnline(Online _Online)	{
		this.setSingleAVP( _Online);
	}
	/**
	 * Searches for the Offline AVP inside a message
	 * @return the found Offline AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public Offline getOffline() {
		return (Offline)findAVP(Offline.AVP_CODE);
	}
	/**
	 * sets the Offline and overrides a existing one
	 * @param _destinationRealm	the new Offline
	 * 
	 * @author mhappenhofer
	 */
	public void setOffline(Offline _Offline)	{
		this.setSingleAVP( _Offline);
	}
	/**
	 * adds a TFTPacketFilterInformation to this GxRAA Message
	 * @param _TFTPacketFilterInformation
	 */
	public void addTFTPacketFilterInformation(TFTPacketFilterInformation _TFTPacketFilterInformation)	{
		this.addAVP(_TFTPacketFilterInformation);
	}
	
	/**
	 * Queries all TFTPacketFilterInformation stored at this GxRAR Message
	 * @return	Iterator of all TFTPacketFilterInformation
	 */
	public Iterator<TFTPacketFilterInformation> getTFTPacketFilterInformationIterator()	{
		return getIterator(new TFTPacketFilterInformation());
	}
	/**
	 * adds a ChargingRuleReport to this GxRAA Message
	 * @param _ChargingRuleReport
	 */
	public void addChargingRuleReport(ChargingRuleReport _ChargingRuleReport)	{
		this.addAVP(_ChargingRuleReport);
	}
	
	/**
	 * Queries all ChargingRuleReport stored at this GxRAR Message
	 * @return	Iterator of all ChargingRuleReport
	 */
	public Iterator<ChargingRuleReport> getChargingRuleReportIterator()	{
		return getIterator(new ChargingRuleReport());
	}
	/**
	 * adds a EventTrigger to this GxRAA Message
	 * @param _eventTrigger
	 */
	public void addEventTrigger(EventTrigger _eventTrigger)	{
		this.addAVP(_eventTrigger);
	}
	
	/**
	 * Queries all EventTrigger stored at this GxRAR Message
	 * @return	Iterator of all EventTrigger
	 */
	public Iterator<EventTrigger> getEventTriggerIterator()	{
		return getIterator(new EventTrigger());
	}
	/**
	 * Searches for the AccessNetworkChargingAddress AVP inside a message
	 * @return the found AccessNetworkChargingAddress AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public AccessNetworkChargingAddress getAccessNetworkChargingAddress() {
		return (AccessNetworkChargingAddress)findAVP(AccessNetworkChargingAddress.AVP_CODE);
	}
	/**
	 * sets the AccessNetworkChargingAddress and overrides a existing one
	 * @param _destinationRealm	the new AccessNetworkChargingAddress
	 * 
	 * @author mhappenhofer
	 */
	public void setAccessNetworkChargingAddress(AccessNetworkChargingAddress _AccessNetworkChargingAddress)	{
		this.setSingleAVP( _AccessNetworkChargingAddress);
	}
	
	/**
	 * adds a AccessNetworkChargingIdentifierGx to this GxRAA Message
	 * @param _AccessNetworkChargingIdentifierGx
	 */
	public void addAccessNetworkChargingIdentifierGx(AccessNetworkChargingIdentifierGx _AccessNetworkChargingIdentifierGx)	{
		this.addAVP(_AccessNetworkChargingIdentifierGx);
	}
	
	/**
	 * Queries all AccessNetworkChargingIdentifierGx stored at this GxRAR Message
	 * @return	Iterator of all AccessNetworkChargingIdentifierGx
	 */
	public Iterator<AccessNetworkChargingIdentifierGx> getAccessNetworkChargingIdentifierGxIterator()	{
		return getIterator(new AccessNetworkChargingIdentifierGx());
	}


}
