/* 
* ChargingRuleDefinition.java
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

import java.util.Iterator;

import at.ac.tuwien.ibk.biqini.diameter.I3GPPConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Grouped;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.RatingGroup;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ServiceIdentifier;

/**
 * The Charging-Rule-Definition AVP (AVP code 1003) is of type Grouped, and it 
 * defines the PCC rule for a service flow sent by the PCRF to the PCEF. The 
 * Charging-Rule-Name AVP uniquely identifies the PCC rule and it is used to 
 * reference to a PCC rule in communication between the PCEF and the PCRF 
 * within one IP CAN session. The Flow-Description AVP(s) determines the 
 * traffic that belongs to the service flow.
 * If optional AVP(s) within a Charging-Rule-Definition AVP are omitted, but 
 * corresponding information has been provided in previous Gx messages, the 
 * previous information remains valid. If Flow-Description AVP(s) are supplied, 
 * they replace all previous Flow-Description AVP(s). If Flows AVP(s) are 
 * supplied, they replace all previous Flows AVP(s).
 * Flows AVP may appear if and only if AF-Charging-Identifier AVP is also 
 * present.
 * AVP Format:
 * Charging-Rule-Definition ::= < AVP Header: 1003 >
 *							 { Charging-Rule-Name }
 *							 [ Service-Identifier ]
 *							 [ Rating-Group ]
 *							*[ Flow-Description ]
 *							 [ Flow-Status ]
 *							 [ QoS-Information ]
 *							 [ Reporting-Level ]
 *							 [ Online ]
 *							 [ Offline ]
 *							 [ Metering-Method ]
 *							 [ Precedence ]
 *							 [ AF-Charging-Identifier ]
 *							*[ Flows ]
 *							*[ AVP ]
 *
 * TS 29.212
 *
 * @author mhappenhofer
 *
 */
public class ChargingRuleDefinition extends Grouped implements I3GPPConstants {

	public static final int AVP_CODE = 1003;
	
	public ChargingRuleDefinition() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @return the chargingRuleName
	 */
	public ChargingRuleName getChargingRuleName() {
		return (ChargingRuleName)findChildAVP(ChargingRuleName.AVP_CODE);
	}
	/**
	 * @param _chargingRuleName the chargingRuleName to set
	 */
	public void setChargingRuleName(ChargingRuleName _chargingRuleName) {
		this.setSingleAVP(_chargingRuleName);
	}
	
	/**
	 * @return the serviceIdentifier
	 */
	public ServiceIdentifier getServiceIdentifier() {
		return(ServiceIdentifier)findChildAVP(ServiceIdentifier.AVP_CODE);
	}
	/**
	 * @param _serviceIdentifier the serviceIdentifier to set
	 */
	public void setServiceIdentifier(ServiceIdentifier _serviceIdentifier) {
		this.setSingleAVP(_serviceIdentifier);
	}
	
	/**
	 * @return the ratingGroup
	 */
	public RatingGroup getRatingGroup() {
		return (RatingGroup)findChildAVP(RatingGroup.AVP_CODE);
	}
	/**
	 * @param _ratingGroup the ratingGroup to set
	 */
	public void setRatingGroup(RatingGroup _ratingGroup) {
		this.setSingleAVP(_ratingGroup);
	}
	
	/**
	 * @return the flowStatus
	 */
	public FlowStatus getFlowStatus() {
		return (FlowStatus)findChildAVP(FlowStatus.AVP_CODE);
	}
	
	/**
	 * @param _flowStatus the flowStatus to set
	 */
	public void setFlowStatus(FlowStatus _flowStatus) {
		this.setSingleAVP(_flowStatus);
	}
	
	/**
	 * @return the qoSInformation
	 */
	public QoSInformation getQoSInformation() {
		return (QoSInformation)findChildAVP(QoSInformation.AVP_CODE);
	}
	/**
	 * @param _qoSInformation the qoSInformation to set
	 */
	public void setQoSInformation(QoSInformation _qoSInformation) {
		this.setSingleAVP(_qoSInformation);
	}
	
	/**
	 * @return the reportingLevel
	 */
	public ReportingLevel getReportingLevel() {
		return (ReportingLevel)findChildAVP(ReportingLevel.AVP_CODE);
	}
	/**
	 * @param _reportingLevel the reportingLevel to set
	 */
	public void setReportingLevel(ReportingLevel _reportingLevel) {
		this.setSingleAVP(_reportingLevel);
	}
	
	/**
	 * @return the online
	 */
	public Online getOnline() {
		return (Online)findChildAVP(Online.AVP_CODE);
	}
	/**
	 * @param _online the online to set
	 */
	public void setOnline(Online _online) {
		this.setSingleAVP(_online);
	}
	
	/**
	 * @return the offline
	 */
	public Offline getOffline() {
		return (Offline)findChildAVP(Offline.AVP_CODE);	
	}
	/**
	 * @param _offline the offline to set
	 */
	public void setOffline(Offline _offline) {
		this.setSingleAVP(_offline);
	}
	
	/**
	 * @return the meteringMethod
	 */
	public MeteringMethod getMeteringMethod() {
		return (MeteringMethod)findChildAVP(MeteringMethod.AVP_CODE);
	}
	/**
	 * @param _meteringMethod the meteringMethod to set
	 */
	public void setMeteringMethod(MeteringMethod _meteringMethod) {
		this.setSingleAVP(_meteringMethod);
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
	 * @return the aFChargingIdentifier
	 */
	public AFChargingIdentifier getAFChargingIdentifier() {
		return (AFChargingIdentifier)findChildAVP(AFChargingIdentifier.AVP_CODE);
	}
	/**
	 * @param _chargingIdentifier the aFChargingIdentifier to set
	 */
	public void setAFChargingIdentifier(AFChargingIdentifier _chargingIdentifier) {
		this.setSingleAVP(_chargingIdentifier);
	}
	
	/**
	 * adds a Flows AVP
	 * @param _flows	the AVP
	 */
	public void addFlows(Flows _flows)	{
		this.addChildAVP(_flows);
	}
	
	/**
	 * Queries all Flows stored 
	 * @return	Iterator of all Flows
	 */
	public Iterator<Flows> getFlowsIterator()	{
		return getIterator(new Flows());
	}
	
	/**
	 * adds a FlowDescription AVP
	 * @param _flowDescription	the AVP
	 */
	public void addFlowDescription(FlowDescription _flowDescription)	{
		this.addChildAVP(_flowDescription);
	}
	
	/**
	 * Queries all FlowDescription stored 
	 * @return	Iterator of all FlowDescription
	 */
	public Iterator<FlowDescription> getFlowDescriptionIterator()	{
		return getIterator(new FlowDescription());
	}
	

}
