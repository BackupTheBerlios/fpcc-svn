/* 
* DiameterMessageJunit.java
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
package at.ac.tuwien.ibk.biqini.junit.diameter;

import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxCCA;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxCCR;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxRAA;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxRAR;


/**
 * @author mhappenhofer
 *
 */
public class DiameterMessageJunit extends AVPJunit{
	
	public static final int MAX = 5;
	
	public static GxCCR getRandomGxCCR()	{
		GxCCR ccr = new GxCCR();
		ccr.setAccessNetworkChargingAddress(getRandomAccessNetworkChargingAddress());
		ccr.setAuthApplicationId(getRandomAuthApplicationId());
		ccr.setBearerControlMode(getRandomBearerControlMode());
		ccr.setBearerIdentifier(getRandomBearerIdentifier());
		ccr.setBearerOperation(getRandomBearerOperation());
		ccr.setBearerUsage(getRandomBearerUsage());
		ccr.setCalledStationId(getRandomCalledStationId());
		ccr.setCCRequestNumber(getRandomCCRequestNumber());
		ccr.setCCRequestType(getRandomCCRequestType());
		ccr.setDestinationHost(getRandomDestinationHost());
		ccr.setDestinationRealm(getRandomDestinationRealm());
		ccr.setFramedIPAddress(getRandomFramedIPAddress());
		ccr.setFramedIPv6Prefix(getRandomFramedIPv6Prefix());
		ccr.setIPCANType(getRandomIPCANType());
		ccr.setNetworkRequestSupport(getRandomNetworkRequestSupport());
		ccr.setOffline(getRandomOffline());
		ccr.setOnline(getRandomOnline());
		ccr.setOriginHost(getRandomOriginHost());
		ccr.setOriginRealm(getRandomOriginRealm());
		ccr.setQoSInformation(getRandomQoSInformation());
		ccr.setQoSNegotiation(getRandomQoSNegotiation());
		ccr.setQoSUpgrade(getRandomQoSUpgrade());
		ccr.setRAI(getRandomRAI());
		ccr.setRATType(getRandomRATType());
		ccr.setSessionId(getRandomSessionID());
		ccr.setSubscriptionId(getRandomSubscriptionId());
		ccr.setTerminationCause(getRandomTerminationCause());
		ccr.setThreeGPPSGSNAddress(getRandomThreeGPPSGSNAddress());
		ccr.setThreeGPPSGSNIPv6Prefix(getRandomThreeGPPSGSNIPv6Prefix());
		ccr.setThreeGPPSGSNMCCMNC(getRandomThreeGPPSGSNMCCMNC());
		ccr.setThreeGPPUserLocationInfo(getRandomThreeGPPUserLocationInfo());
		ccr.setUserEquipmentInfo(getRandomUserEquipmentInfo());
		int size = RANDOMIZER.nextInt(MAX);
		for(int i =0;i<size;i++)
		{
			ccr.addChargingRuleReport(getRandomChargingRuleReport());
			ccr.addEventTrigger(getRandomEventTrigger());
			ccr.addTFTPacketFilterInformation(getRandomTFTFilterInformation());
		}
		return ccr;
	}
	
	public static GxCCA getRandomGxCCA()	{
		GxCCA cca = new GxCCA();
		cca.setAuthApplicationId(getRandomAuthApplicationId());
		cca.setBearerControlMode(getRandomBearerControlMode());
		cca.setCCRequestNumber(getRandomCCRequestNumber());
		cca.setCCRequestType(getRandomCCRequestType());
		cca.setChargingInformation(getRandomChargingInformation());
		cca.setErrorMessage(getRandomErrorMessage());
		cca.setErrorReportingHost(getRandomErrorReportingHost());
		cca.setFailedAVP(getRandomFailedAVP());
		cca.setOffline(getRandomOffline());
		cca.setOnline(getRandomOnline());
		cca.setOriginHost(getRandomOriginHost());
		cca.setOriginRealm(getRandomOriginRealm());
		cca.setResultCode(getRandomResultCode());
		cca.setSessionId(getRandomSessionID());
		int size = RANDOMIZER.nextInt(MAX);
		for(int i =0;i<size;i++)
		{
			cca.addChargingRuleInstall(getRandomChargingRuleInstall());
			cca.addChargingRuleRemove(getRandomChargingRuleRemove());
			cca.addEventTrigger(getRandomEventTrigger());
		}
		return cca;
	}
	
	public static GxRAR getRandomGxRAR()	{
		GxRAR rar = new GxRAR();
		rar.setAuthApplicationId(getRandomAuthApplicationId());
		rar.setDestinationHost(getRandomDestinationHost());
		rar.setDestinationRealm(getRandomDestinationRealm());
		rar.setEventReportIndication(getRandomEventReportIndication());
		rar.setOriginHost(getRandomOriginHost());
		rar.setOriginRealm(getRandomOriginRealm());
		rar.setReAuthRequestType(getRandomReAuthRequestType());
		rar.setSessionId(getRandomSessionID());
		int size = RANDOMIZER.nextInt(MAX);
		for(int i =0;i<size;i++)
		{
			rar.addChargingRuleInstall(getRandomChargingRuleInstall());
			rar.addChargingRuleRemove(getRandomChargingRuleRemove());
			rar.addEventTrigger(getRandomEventTrigger());
			rar.addQoSInformation(getRandomQoSInformation());
		}
		return rar;
	}
	
	public static GxRAA getRandomGxRAA()	{
		GxRAA raa = new GxRAA();
		raa.setAccessNetworkChargingAddress(getRandomAccessNetworkChargingAddress());
		raa.setBearerIdentifier(getRandomBearerIdentifier());
		raa.setErrorMessage(getRandomErrorMessage());
		raa.setErrorReportingHost(getRandomErrorReportingHost());
		raa.setEventTrigger(getRandomEventTrigger());
		raa.setFailedAVP(getRandomFailedAVP());
		raa.setOriginHost(getRandomOriginHost());
		raa.setOriginRealm(getRandomOriginRealm());
		raa.setResultCode(getRandomResultCode());
		raa.setSessionId(getRandomSessionID());
		int size = RANDOMIZER.nextInt(MAX);
		for(int i =0;i<size;i++)
		{
			raa.addAccessNetworkChargingIdentifierGx(getRandomAccessNetworkChargingIdentifierGx());
			raa.addChargingRuleReport(getRandomChargingRuleReport());
		}
		return raa;
	}

}
