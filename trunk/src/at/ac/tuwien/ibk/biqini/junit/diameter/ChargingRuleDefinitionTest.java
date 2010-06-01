/* 
* ChargingRuleDefinitionTest.java
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

import java.util.Iterator;
import java.util.Vector;

import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.RatingGroup;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ServiceIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AFChargingIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleDefinition;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowStatus;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Flows;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MeteringMethod;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Offline;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Online;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Precedence;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSInformation;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ReportingLevel;


/**
 * @author mhappenhofer
 *
 */
public class ChargingRuleDefinitionTest extends AVPJunit {
	
	public static final int NUMBER_OF_FLOWS = 5;
	public static final int NUMBER_OF_FLOWDESCRIPTION = 5;
	
	public final void testChargingRuleDefinition ()	{
		ChargingRuleDefinition crd = new ChargingRuleDefinition();
		AFChargingIdentifier af = null;
		ChargingRuleName crn = null;
		FlowStatus fs = null;
		MeteringMethod mm = null;
		Offline of = null;
		Online on = null;
		Precedence pre = null;
		QoSInformation qos = null;
		RatingGroup rg = null;
		ReportingLevel rl = null;
		ServiceIdentifier si = null;
		Vector<Flows> flows = new Vector<Flows>();
		Vector<FlowDescription> flowDescription = new Vector<FlowDescription>();
		for(int i = 0;i<NUMBER_OF_TESTS;i++)
		{
			flows = new Vector<Flows>();
			flowDescription = new Vector<FlowDescription>();
			
			af = getRandomAFChargingIdentifier();
			crd.setAFChargingIdentifier(af);
			crn = getRandomChargingRuleName();
			crd.setChargingRuleName(crn);
			fs = getRandomFlowStatus();
			crd.setFlowStatus(fs);
			mm = getRandomMeteringMethod();
			crd.setMeteringMethod(mm);
			of = getRandomOffline();
			crd.setOffline(of);
			on = getRandomOnline();
			crd.setOnline(on);
			pre = getRandomPrecedence();
			crd.setPrecedence(pre);
			qos = getRandomQoSInformation();
			crd.setQoSInformation(qos);
			rg = getRandomRatingGroup();
			crd.setRatingGroup(rg);
			rl = getRandomReportingLevel();
			crd.setReportingLevel(rl);
			si = getRandomServiceIdentifier();
			crd.setServiceIdentifier(si);
			for(int j = 0;j<NUMBER_OF_FLOWS;j++)
			{
				Flows f = FlowsTest.getRandomFlows();
				flows.add(f);
				crd.addFlows(f);
			}
			for(int j = 0;j<NUMBER_OF_FLOWDESCRIPTION;j++)
			{
				FlowDescription f = new FlowDescription(IPFilterRuleTest.getRandomIPRuleString());
				flowDescription.add(f);
				crd.addFlowDescription(f);
			}
			
			//compare inserted AVPS
			assertEquals(af, crd.getAFChargingIdentifier());
			assertEquals(crn, crd.getChargingRuleName());
			assertEquals(fs, crd.getFlowStatus());
			assertEquals(mm, crd.getMeteringMethod());
			assertEquals(of, crd.getOffline());
			assertEquals(on, crd.getOnline());
			assertEquals(pre, crd.getPrecedence());
			assertEquals(qos, crd.getQoSInformation());
			assertEquals(rg, crd.getRatingGroup());
			assertEquals(rl, crd.getReportingLevel());
			assertEquals(si, crd.getServiceIdentifier());
			//check if the same number of Flows are in the Vector and the AVP
			//assertEquals(flows.size(), crd.getFlowsCount());
			Iterator<Flows> itFlows = flows.iterator();
			while(itFlows.hasNext())
			{
				Flows current = itFlows.next();
				for(Iterator<Flows> it = crd.getFlowsIterator();it.hasNext();)
				{
					Flows c = it.next();
					if(c.equals(current))
					{
						current = null;
						crd.deleteChildAVP(c);
						break;
					}
				}
				if(current!=null)
					fail("could not find inserted Flows!");
			}
			if(crd.getFlowsIterator().hasNext()!=false)
				fail("there are still some Flows in the AVP!");
			Iterator<FlowDescription> itFlowDescription = flowDescription.iterator();
			while(itFlowDescription.hasNext())
			{
				FlowDescription current = itFlowDescription.next();
				for(Iterator<FlowDescription>it = crd.getFlowDescriptionIterator();it.hasNext();)
				{
					FlowDescription c = it.next();
					if(c.equals(current))
					{
						current = null;
						crd.deleteChildAVP(c);
						break;
					}
				}
				if(current!=null)
					fail("could not find inserted FlowDescription!");
			}
			if(crd.getFlowDescriptionIterator().hasNext()!=false)
				fail("there are still some FlowDescription in the AVP!");
		}
	}
}
