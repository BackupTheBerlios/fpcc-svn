/* 
* AVPJunit.java
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

import java.util.Calendar;
import java.util.Random;

import at.ac.tuwien.ibk.biqini.diameter.avp.base.DiameterURI;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.IPFilterRule;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.AuthApplicationId;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.CCRequestNumber;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.CCRequestType;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.CalledStationId;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.DestinationHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.DestinationRealm;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ErrorMessage;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ErrorReportingHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.FailedAVP;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.FramedIPAddress;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.FramedIPv6Prefix;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.OriginRealm;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.RatingGroup;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ReAuthRequestType;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ResultCode;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ServiceIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SessionID;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SubscriptionId;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SubscriptionIdData;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SubscriptionIdType;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.TerminationCause;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.UserEquipmentInfo;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.UserEquipmentInfoType;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.UserEquipmentInfoValue;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AFChargingIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AccessNetworkChargingAddress;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AccessNetworkChargingIdentifierGx;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AccessNetworkChargingIdentifierValue;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.BearerControlMode;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.BearerIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.BearerOperation;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.BearerUsage;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingInformation;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleBaseName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleDefinition;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleInstall;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleRemove;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleReport;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.CodecData;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.EventReportIndication;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.EventTrigger;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowNumber;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowStatus;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowUsage;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Flows;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.GuaranteedBitrateDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.GuaranteedBitrateUL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.IPCANType;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthUL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaComponentDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaComponentNumber;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaSubComponent;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MeteringMethod;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.NetworkRequestSupport;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Offline;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Online;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.PCCRuleStatus;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Precedence;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.PrimaryChargingCollectionFunctionName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.PrimaryEventChargingFunctionName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSClassIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSInformation;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSNegotiation;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSUpgrade;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.RAI;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.RATType;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ReportingLevel;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.SecondaryChargingCollectionFunctionName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.SecondaryEventChargingFunctionName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.TFTFilter;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.TFTPacketFilterInformation;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ThreeGPPSGSNAddress;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ThreeGPPSGSNIPv6Prefix;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ThreeGPPSGSNMCCMNC;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ThreeGPPUserLocationInfo;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ToSTrafficClass;

public class AVPJunit extends junit.framework.TestCase{
public static Random RANDOMIZER;
	
	public static final int NUMBER_OF_TESTS = 1024;
	
	static	{
		RANDOMIZER = new Random(Calendar.getInstance().getTimeInMillis());
	}
	
	public static String getRandomDiameterURIString()	{
		StringBuffer sbf = new StringBuffer();
		if(RANDOMIZER.nextBoolean())
			sbf.append(DiameterURI.SECURE_TRANSPORT);
		else
			sbf.append(DiameterURI.UNSECURE_TRANSPORT);
		sbf.append(DiameterURI.SEPERATOR);
		sbf.append(getRandomFQN());
		if(RANDOMIZER.nextBoolean())
			sbf.append(":"+RANDOMIZER.nextInt(65536));
		if(RANDOMIZER.nextBoolean())
			sbf.append(";transport="+DiameterURI.TRANSPORT_PROTOCOLS[RANDOMIZER.nextInt(DiameterURI.TRANSPORT_PROTOCOLS.length)]);
		if(RANDOMIZER.nextBoolean())
			sbf.append(";protocol="+DiameterURI.AAA_PROTOCOLS[RANDOMIZER.nextInt(DiameterURI.AAA_PROTOCOLS.length)]);
		return sbf.toString();
	}
	
	public static final String getRandomName()	{
		return Long.toString(Math.abs(RANDOMIZER.nextLong()), 36);

	}
	
	public static Flows getRandomFlows()	{
		Flows f = new Flows();
		f.setMediaComponentNumber(new MediaComponentNumber(Math.abs(RANDOMIZER.nextLong())));
		int flowsNumber = RANDOMIZER.nextInt(7);
		for(int i =0;i<flowsNumber;i++)
		{
			f.addFlowNumber(new FlowNumber(Math.abs(RANDOMIZER.nextInt(255))));
		}
		return f;
	}
	
	public static final String ACCESS[] ={IPFilterRule.DENY_TOKEN,IPFilterRule.PERMIT_TOKEN};
	public static final String DIRECTION[] ={IPFilterRule.IN_TOKEN,IPFilterRule.OUT_TOKEN};
	
	public static String getRandomIPRuleString(boolean in)	{
		StringBuffer sbf = new StringBuffer();
		sbf.append(ACCESS[RANDOMIZER.nextInt(2)]);
		sbf.append(" ");
		if(in)
			sbf.append(IPFilterRule.IN_TOKEN);
		else
			sbf.append(IPFilterRule.OUT_TOKEN);
		sbf.append(" ");
		if(RANDOMIZER.nextBoolean())
			sbf.append(RANDOMIZER.nextInt(256));
		else
			sbf.append("ip");
		sbf.append(" from ");
		sbf.append(getRandomSocketString());
		sbf.append(" "+IPFilterRule.TO_TOKEN+" ");
		sbf.append(getRandomSocketString());
		return sbf.toString();
	}
	
	public static String getRandomIPRuleString()	{
		StringBuffer sbf = new StringBuffer();
		sbf.append(ACCESS[RANDOMIZER.nextInt(2)]);
		sbf.append(" ");
		sbf.append(DIRECTION[RANDOMIZER.nextInt(2)]);
		sbf.append(" ");
		if(RANDOMIZER.nextBoolean())
			sbf.append(RANDOMIZER.nextInt(256));
		else
			sbf.append("ip");
		sbf.append(" from ");
		sbf.append(getRandomSocketString());
		sbf.append(" "+IPFilterRule.TO_TOKEN+" ");
		sbf.append(getRandomSocketString());
		return sbf.toString();
	}
	public static String getRandomSocketString()	{
		StringBuffer sbf = new StringBuffer();
		sbf.append(RANDOMIZER.nextInt(256));
		sbf.append(".");
		sbf.append(RANDOMIZER.nextInt(256));
		sbf.append(".");
		sbf.append(RANDOMIZER.nextInt(256));
		sbf.append(".");
		sbf.append(RANDOMIZER.nextInt(256));
		if(RANDOMIZER.nextBoolean())
		{
			sbf.append("/");
			sbf.append(RANDOMIZER.nextInt(33));
		}
		if(RANDOMIZER.nextBoolean())
		{
			sbf.append(" ");
			sbf.append(RANDOMIZER.nextInt(65537));
			while(RANDOMIZER.nextBoolean())
			{
				sbf.append(",");
				sbf.append(RANDOMIZER.nextInt(65537));
			}
		}
		return sbf.toString();
	}
	
	public static CodecData getRandomCodecData()	{
		String randomString;
		byte arr[] = new byte[RANDOMIZER.nextInt(1025)];
		RANDOMIZER.nextBytes(arr);
		randomString = new String(arr);
		return new CodecData(randomString);
	}
	

	
	public static long getRandomUnsigned32()	{
		return Math.abs(RANDOMIZER.nextInt());
	}
	
	public static MediaComponentDescription getRandomMediaComponentDescription()	{
		MediaComponentDescription m = new MediaComponentDescription(getRandomUnsigned32());
		int medias = RANDOMIZER.nextInt(4)+1;
		for(int i = 0;i<medias;i++)
		{
			MediaSubComponent ms = MediaSubComponentTest.generateRandomMediaSubComponent();
			m.addMediaSubComponent(ms);
			CodecData c = getRandomCodecData();
			m.addCodecData(c);
		}
		return m;
	}
	
	public static int getRandomFlowUsageValue()	{
		return RANDOMIZER.nextInt(FlowUsage.AF_SIGNALLING+1);
	}
	
	public static int getRandomFlowStatusValue()	{
		return RANDOMIZER.nextInt(FlowStatus.ENABLED+1);
	}
	
	public static long getRandomFlowNumber() {
		return Math.abs(RANDOMIZER.nextInt(Integer.MAX_VALUE));
	}
	public static long getBandwith()	{
		return Math.abs(RANDOMIZER.nextInt(100000));
	}
	
	public static MediaSubComponent generateRandomMediaSubComponent()	{
		MediaSubComponent m = new MediaSubComponent();
		m.setFlowNumber(new FlowNumber(getRandomFlowNumber()));
		m.setFlowDescriptionIn(new FlowDescription(IPFilterRuleTest.getRandomIPRuleString(true)));
		m.setFlowDescriptionOut(new FlowDescription(IPFilterRuleTest.getRandomIPRuleString(false)));
		m.setMaxRequestedBandwidthDL(new MaxRequestedBandwidthDL(getBandwith()));
		m.setMaxRequestedBandwidthUL(new MaxRequestedBandwidthUL(getBandwith()));
		m.setFlowStatus(new FlowStatus(getRandomFlowStatusValue()));
		m.setFlowUsage(new FlowUsage(getRandomFlowUsageValue()));
		return m;
	}
	
	public static BearerIdentifier getRandomBearerIdentifier () 	{
		return new BearerIdentifier(String.valueOf(RANDOMIZER.nextLong()));
	}
	
	public static QoSClassIdentifier getRandomQoSClassIdentifier()	{
		return new QoSClassIdentifier(RANDOMIZER.nextInt(QoSClassIdentifier.MAX)+QoSClassIdentifier.MIN);
	}
	public static GuaranteedBitrateDL getRandomGuaranteedBitrateDL()	{
		return new GuaranteedBitrateDL(MediaSubComponentTest.getBandwith());
	}
	public static GuaranteedBitrateUL getRandomGuaranteedBitrateUL()	{
		return new GuaranteedBitrateUL(MediaSubComponentTest.getBandwith());
	}
	public static MaxRequestedBandwidthDL getRandomMaxRequestedBandwidthDL()	{
		return new MaxRequestedBandwidthDL(MediaSubComponentTest.getBandwith());
	}
	public static MaxRequestedBandwidthUL getRequestedBandwidthUL()	{
		return new MaxRequestedBandwidthUL(MediaSubComponentTest.getBandwith());
	}
	
	public static AFChargingIdentifier getRandomAFChargingIdentifier()	{
		return new AFChargingIdentifier(getRandomName());
	}
	public static ChargingRuleName getRandomChargingRuleName()	{
		return new ChargingRuleName(getRandomName());
	}
	public static ChargingRuleBaseName getRandomChargingRuleBaseName()	{
		return new ChargingRuleBaseName(getRandomName());
	}
	public static FlowStatus getRandomFlowStatus()	{
		return new FlowStatus(getRandomFlowStatusValue());
	}
	public static MeteringMethod getRandomMeteringMethod()	{
		return new MeteringMethod(RANDOMIZER.nextInt(MeteringMethod.DURATION_VOLUME+1));
	}
	public static Online getRandomOnline()	{
		return new Online(RANDOMIZER.nextInt(Online.ENABLE_ONLINE+1));
	}
	public static Offline getRandomOffline()	{
		return new Offline(RANDOMIZER.nextInt(Offline.ENABLE_OFFLINE+1));
	}
	public static Precedence getRandomPrecedence()	{
		return new Precedence(getRandomUnsigned32());
	}
	public static RatingGroup getRandomRatingGroup (){
		return new RatingGroup(getRandomUnsigned32());
	}
	public static ReportingLevel getRandomReportingLevel()	{
		return new ReportingLevel(RANDOMIZER.nextInt(ReportingLevel.RATING_GROUP_LEVEL+1));
	}
	public static ServiceIdentifier getRandomServiceIdentifier()	{
		return new ServiceIdentifier(getRandomUnsigned32());
	}
	public static QoSInformation getRandomQoSInformation ()	{
		QoSInformation q = new QoSInformation();
		q.setBearerIdentifier(getRandomBearerIdentifier());
		q.setGuaranteedBitrateDL(getRandomGuaranteedBitrateDL());
		q.setGuaranteedBitrateUL(getRandomGuaranteedBitrateUL());
		q.setMaxRequestedBandwidthDL(getRandomMaxRequestedBandwidthDL());
		q.setMaxRequestedBandwidthUL(getRequestedBandwidthUL());
		return q;
	}
	public static TFTFilter getRandomTFTFilter()	{
		return new TFTFilter(IPFilterRuleTest.getRandomIPRuleString());
	}
	
	public static ToSTrafficClass getRandomToSTrafficClass()	{
		return new ToSTrafficClass(getRandomName());
	}
	
	public static ChargingRuleDefinition getRandomChargingRuleDefinition()	{
		ChargingRuleDefinition c = new ChargingRuleDefinition();
		c.setAFChargingIdentifier(getRandomAFChargingIdentifier());
		c.setChargingRuleName(getRandomChargingRuleName());
		c.setFlowStatus(getRandomFlowStatus());
		c.setMeteringMethod(getRandomMeteringMethod());
		c.setOffline(getRandomOffline());
		c.setOnline(getRandomOnline());
		c.setPrecedence(getRandomPrecedence());
		c.setQoSInformation(getRandomQoSInformation());
		c.setRatingGroup(getRandomRatingGroup());
		c.setReportingLevel(getRandomReportingLevel());
		c.setServiceIdentifier(getRandomServiceIdentifier());
		for(int j = 0;j<4;j++)
		{
			Flows f = FlowsTest.getRandomFlows();
			c.addFlows(f);
		}
		for(int j = 0;j<4;j++)
		{
			FlowDescription f = new FlowDescription(IPFilterRuleTest.getRandomIPRuleString());
			c.addFlowDescription(f);
		}
		return c;
	}
	
	public static PCCRuleStatus getRandomPCCRuleStatus()	{
		return new PCCRuleStatus(RANDOMIZER.nextInt(PCCRuleStatus.TEMPORARY_INACTIVE+1));
	}
	
	public static RATType getRandomRATType	()	{
		//TODO also generates RATTYPEs that are not specified
		return new RATType(RANDOMIZER.nextInt(RATType.UMB+1));
	}
	
	public static EventTrigger getRandomEventTrigger()	{
		return new EventTrigger(RANDOMIZER.nextInt(EventTrigger.NO_EVENT_TRIGGER+1));
	}
	
	public static AccessNetworkChargingAddress getRandomAccessNetworkChargingAddress()	{
		return new AccessNetworkChargingAddress(getRandomName());
	}
	
	public static AuthApplicationId getRandomAuthApplicationId()	{
		return new AuthApplicationId(getRandomUnsigned32());
	}
	
	public static BearerControlMode getRandomBearerControlMode()	{
		return new BearerControlMode(RANDOMIZER.nextInt(BearerControlMode.UE_NW+1));
	}
	
	public static BearerOperation getRandomBearerOperation()	{
		return new BearerOperation(RANDOMIZER.nextInt(BearerOperation.MODIFICATION+1));
	}
	
	public static BearerUsage getRandomBearerUsage()	{
		return new BearerUsage(RANDOMIZER.nextInt(BearerUsage.IMS_SIGNALLING+1));
	}
	
	public static CalledStationId getRandomCalledStationId()	{
		return new CalledStationId(getRandomName());
	}
	
	public static CCRequestNumber getRandomCCRequestNumber()	{
		return new CCRequestNumber(getRandomUnsigned32());
	}

	public static CCRequestType getRandomCCRequestType()	{
		return new CCRequestType(RANDOMIZER.nextInt(CCRequestType.EVENT_REQUEST+1));
	}
	
	public static DestinationHost getRandomDestinationHost()	{
		return new DestinationHost(getRandomFQN());
	}
	
	public static DestinationRealm getRandomDestinationRealm()	{
		return new DestinationRealm(getRandomRealm());
	}
	
	public static OriginHost getRandomOriginHost()	{
		return new OriginHost(getRandomFQN());
	}
	
	public static OriginRealm getRandomOriginRealm()	{
		return new OriginRealm(getRandomRealm());
	}
	
	public static FramedIPAddress getRandomFramedIPAddress()	{
		return new FramedIPAddress(getRandomName());
	}
	
	public static FramedIPv6Prefix getRandomFramedIPv6Prefix()	{
		return new FramedIPv6Prefix(getRandomName());
	}
	
	public static IPCANType getRandomIPCANType()	{
		return new IPCANType(RANDOMIZER.nextInt(IPCANType.ThreeGPP2+1));
	}
	
	public static NetworkRequestSupport getRandomNetworkRequestSupport()	{
		return new NetworkRequestSupport(RANDOMIZER.nextInt(NetworkRequestSupport.NETWORK_REQUEST_SUPPORTED+1));
	}
	
	public static QoSNegotiation getRandomQoSNegotiation()	{
		return new QoSNegotiation(RANDOMIZER.nextInt(QoSNegotiation.QoS_NEGOTIATION_SUPPORTED+1));
	}
	
	public static QoSUpgrade getRandomQoSUpgrade()	{
		return new QoSUpgrade(RANDOMIZER.nextInt(QoSUpgrade.QoS_UPGRADE_SUPPORTED+1));
	}
	
	public static RAI getRandomRAI()	{
		return new RAI(getRandomName());
	}
	
	public static SessionID getRandomSessionID()	{
		return new SessionID(getRandomName());
	}
	
	public static ChargingRuleReport getRandomChargingRuleReport()	{
		ChargingRuleReport c = new ChargingRuleReport();
		int size = RANDOMIZER.nextInt(5);
		for(int i = 0;i<size;i++)
		{
			c.addChargingRuleBaseName(getRandomChargingRuleBaseName());
			c.addChargingRuleName(getRandomChargingRuleName());
		}
		c.setPCCRuleStatus(getRandomPCCRuleStatus());
		return c;
	}
	
	public static TFTPacketFilterInformation getRandomTFTFilterInformation()	{
		TFTPacketFilterInformation t = new TFTPacketFilterInformation();
		t.setPrecedence(getRandomPrecedence());
		t.setTFTFilter(getRandomTFTFilter());
		t.setToSTrafficClass(getRandomToSTrafficClass());
		return t;
	}
	
	public static SubscriptionId getRandomSubscriptionId()	{
		return new SubscriptionId(getRandoSubscriptionIdType(),getRandomSubscriptionIdData());
	}
	
	public static SubscriptionIdData getRandomSubscriptionIdData()	{
		return new SubscriptionIdData(getRandomName());
	}
	public static SubscriptionIdType getRandoSubscriptionIdType()	{
		return new SubscriptionIdType(RANDOMIZER.nextInt(SubscriptionIdType.END_USER_PRIVATE+1));
	}
	
	public static TerminationCause getRandomTerminationCause()	{
		return new TerminationCause(RANDOMIZER.nextInt(TerminationCause.DIAMETER_SESSION_TIMEOUT+1));
	}
	
	public static ThreeGPPSGSNAddress getRandomThreeGPPSGSNAddress()	{
		return new ThreeGPPSGSNAddress(getRandomName());
	}
	
	public static ThreeGPPSGSNIPv6Prefix getRandomThreeGPPSGSNIPv6Prefix()	{
		return new ThreeGPPSGSNIPv6Prefix(getRandomName());
	}
	
	public static ThreeGPPSGSNMCCMNC getRandomThreeGPPSGSNMCCMNC()	{
		return new ThreeGPPSGSNMCCMNC(getRandomName());
	}
	
	public static ThreeGPPUserLocationInfo getRandomThreeGPPUserLocationInfo()	{
		return new ThreeGPPUserLocationInfo(getRandomName());
	}
	
	public static UserEquipmentInfo getRandomUserEquipmentInfo()	{
		return new UserEquipmentInfo(getRandomUserEquipmentInfoType(),getRandomUserEquipmentInfoValue());
	}
	
	public static UserEquipmentInfoType getRandomUserEquipmentInfoType()	{
		return new UserEquipmentInfoType(RANDOMIZER.nextInt(UserEquipmentInfoType.MODIFIED_EUI64+1));
	}
	
	public static UserEquipmentInfoValue getRandomUserEquipmentInfoValue()	{
		return new UserEquipmentInfoValue(getRandomName());
	}
	
	public static ChargingInformation getRandomChargingInformation()	{
		ChargingInformation c = new ChargingInformation();
		c.setPrimaryChargingCollectionFunctionName(getRandomPrimaryChargingCollectionFunctionName());
		c.setPrimaryEventChargingFunctionName(getRandomPrimaryEventChargingFunctionName());
		c.setSecondaryChargingCollectionFunctionName(getRandomSecondaryChargingCollectionFunctionName());
		c.setSecondaryEventChargingFunctionName(getRandomSecondaryEventChargingFunctionName());
		return c;
	}
	
	public static String getRandomText(int min, int max)	{
		StringBuffer sbf = new StringBuffer();
		int size = RANDOMIZER.nextInt(max-min)+min;
		for(int i = 0;i<size;i++)
		{
			String s = Long.toString(RANDOMIZER.nextInt(26)+10,36);
			if(RANDOMIZER.nextBoolean())
				s.toUpperCase();
			sbf.append(s);
		}
		return sbf.toString();
	}
	public static String getRandomRealm()	{
		String tld[]={"at","de","com","info","eu","fr","uk","ac.at"};
		String domain[] ={"tuwien","ftw","mka","aon","telekom","vodaphone","apple","derstandard","microsoft","alcatel-lucent","kapsch"};
		StringBuffer sbf = new StringBuffer();
		sbf.append(domain[RANDOMIZER.nextInt(domain.length)]);
		sbf.append(".");
		sbf.append(tld[RANDOMIZER.nextInt(tld.length)]);
		return sbf.toString();
	}
	
	public static String getRandomFQN()	{
		StringBuffer sbf = new StringBuffer();
		String host[] ={"www","pcef","pcrf","ccf","p-cscf","racs","rcef","hss","diameter","smtp","pcscf","pcef1","pcef2","PCRF1","SPDF","PCEF","UE1","ue1","ue00","ue01"};
		sbf.append(host[RANDOMIZER.nextInt(host.length)]);
		sbf.append(".");
		sbf.append(getRandomRealm());
		return sbf.toString();
	}
	
	public static PrimaryChargingCollectionFunctionName getRandomPrimaryChargingCollectionFunctionName()	{
		return new PrimaryChargingCollectionFunctionName(getRandomDiameterURIString());
	}
	
	public static PrimaryEventChargingFunctionName getRandomPrimaryEventChargingFunctionName()	{
		return new PrimaryEventChargingFunctionName(getRandomDiameterURIString());
	}
	
	public static SecondaryChargingCollectionFunctionName getRandomSecondaryChargingCollectionFunctionName()	{
		return new SecondaryChargingCollectionFunctionName(getRandomDiameterURIString());
	}
	
	public static SecondaryEventChargingFunctionName getRandomSecondaryEventChargingFunctionName()	{
		return new SecondaryEventChargingFunctionName(getRandomDiameterURIString());
	}
	
	public static ErrorMessage getRandomErrorMessage()	{
		return new ErrorMessage(getRandomName());
	}
	
	public static ErrorReportingHost getRandomErrorReportingHost()	{
		return new ErrorReportingHost(getRandomName());
	}
	
	public static FailedAVP getRandomFailedAVP()	{
		FailedAVP avp = new FailedAVP();
		avp.addChildAVP(getRandomCalledStationId());
		return avp;
	}
	
	public static ResultCode getRandomResultCode()	{
		return new ResultCode(RANDOMIZER.nextInt(9999));
	}
	
	public static ChargingRuleInstall getRandomChargingRuleInstall()	{
		final int MAX =3;
		ChargingRuleInstall c = new ChargingRuleInstall();
		c.setBearerIdentifier(getRandomBearerIdentifier());
		int size = RANDOMIZER.nextInt(MAX);
		for(int i = 0;i<size;i++)
		{
			c.addChargingRuleBaseName(getRandomChargingRuleBaseName());
			c.addChargingRuleDefinition(getRandomChargingRuleDefinition());
			c.addChargingRuleName(getRandomChargingRuleName());
		}
		return c;
	}
	
	public static ChargingRuleRemove getRandomChargingRuleRemove()	{
		final int MAX =3;
		ChargingRuleRemove c = new ChargingRuleRemove();
		int size = RANDOMIZER.nextInt(MAX);
		for(int i = 0;i<size;i++)
		{
			c.addChargingRuleBaseName(getRandomChargingRuleBaseName());
			c.addChargingRuleName(getRandomChargingRuleName());
		}
		return c;
	}
	
	public static EventReportIndication getRandomEventReportIndication()	{
		final int MAX = 3;
		EventReportIndication e = new EventReportIndication();
		int size = RANDOMIZER.nextInt(MAX);
		for(int i = 0;i<size;i++)
		{
			e.addEventTrigger(getRandomEventTrigger());
		}
		e.setRATType(getRandomRATType());
		return e;
	}
	
	public static ReAuthRequestType getRandomReAuthRequestType()	{
		return new ReAuthRequestType(RANDOMIZER.nextInt(ReAuthRequestType.AUTHORIZE_AUTHENTICATE+1));
	}
	
	public static AccessNetworkChargingIdentifierGx getRandomAccessNetworkChargingIdentifierGx()	{
		final int MAX = 3;
		AccessNetworkChargingIdentifierGx a = new AccessNetworkChargingIdentifierGx();
		a.setAccessNetworkChargingIdentifierValue(getRandomAccessNetworkChargingIdentifierValue());
		int size = RANDOMIZER.nextInt(MAX);
		for(int i = 0;i<size;i++)
		{
			a.addChargingRuleBaseName(getRandomChargingRuleBaseName());
			a.addChargingRuleName(getRandomChargingRuleName());
		}
		return a;
	}
	
	public static AccessNetworkChargingIdentifierValue getRandomAccessNetworkChargingIdentifierValue()	{
		return new AccessNetworkChargingIdentifierValue(getRandomName());
	}
}
