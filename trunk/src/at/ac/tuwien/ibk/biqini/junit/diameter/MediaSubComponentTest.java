/* 
* MediaSubComponentTest.java
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

import org.junit.Test;

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowNumber;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowStatus;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowUsage;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthUL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaSubComponent;

/**
 * @author mhappenhofer
 *
 */
public class MediaSubComponentTest extends AVPJunit{

	
	
	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaSubComponent#setFlowStatus(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowStatus)}.
	 */
	@Test
	public final void testSetFlowStatus() {
		FlowStatus flowStatus = null;
		MediaSubComponent mediaSubComponent = new MediaSubComponent();
		for(int i =0; i<=FlowStatus.REMOVED;i++)
		{
			flowStatus = new FlowStatus(i);
			mediaSubComponent.setFlowStatus(flowStatus);
			int value = mediaSubComponent.getFlowStatus().getInteger32();
			assertEquals(i, value);
		}
	}
	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaSubComponent#setFlowUsage(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowUsage)}.
	 */
	@Test
	public final void testSetFlowUsage() {
		FlowUsage flowUsage = null;
		MediaSubComponent mediaSubComponent = new MediaSubComponent();
		for(int i =1; i<=FlowUsage.AF_SIGNALLING;i++)
		{
			flowUsage = new FlowUsage(i);
			mediaSubComponent.setFlowUsage(flowUsage);
			int value = mediaSubComponent.getFlowUsage().getInteger32();
			assertEquals(i, value);
		}
	}

	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaSubComponent#setFlowNumber(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowNumber)}.
	 */
	@Test
	public final void testSetFlowNumber() {
		FlowNumber flowNumber;
		MediaSubComponent mediaSubComponent = new MediaSubComponent();
		long randomNumber=0;
		for(int i = 0;i<NUMBER_OF_TESTS;i++)
		{
			randomNumber=getRandomFlowNumber();
			flowNumber = new FlowNumber(randomNumber);
			mediaSubComponent.setFlowNumber(flowNumber);
			long value = mediaSubComponent.getFlowNumber().getUnsigned32();
			assertEquals(randomNumber, value);
		}

	}



	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaSubComponent#setMaxRequestedBandwidthDL(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthDL)}.
	 */
	@Test
	public final void testSetMaxRequestedBandwidthDL() {
		MaxRequestedBandwidthDL maxRequestedBandwidthDL;
		MediaSubComponent mediaSubComponent = new MediaSubComponent();
		long randomNumber=0;
		for(int i = 0;i<NUMBER_OF_TESTS;i++)
		{
			randomNumber=getBandwith();
			maxRequestedBandwidthDL = new MaxRequestedBandwidthDL(randomNumber);
			mediaSubComponent.setMaxRequestedBandwidthDL(maxRequestedBandwidthDL);
			long value = mediaSubComponent.getMaxRequestedBandwidthDL().getUnsigned32();
			assertEquals(randomNumber, value);
		}
	}

	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaSubComponent#setMaxRequestedBandwidthUL(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthUL)}.
	 */
	@Test
	public final void testSetMaxRequestedBandwidthUL() {
		MaxRequestedBandwidthUL maxRequestedBandwidthUL;
		MediaSubComponent mediaSubComponent = new MediaSubComponent();
		long randomNumber=0;
		for(int i = 0;i<NUMBER_OF_TESTS;i++)
		{
			randomNumber=getBandwith();
			maxRequestedBandwidthUL = new MaxRequestedBandwidthUL(randomNumber);
			mediaSubComponent.setMaxRequestedBandwidthUL(maxRequestedBandwidthUL);
			long value = mediaSubComponent.getMaxRequestedBandwidthUL().getUnsigned32();
			assertEquals(randomNumber, value);
		}
	}

	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaSubComponent#setFlowDescriptionIn(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowDescription)}.
	 */
	@Test
	public final void testSetFlowDescription() {
		FlowDescription flowDescription;
		MediaSubComponent mediaSubComponent = new MediaSubComponent();
		String value;
		String testString;
		for(int i = 0;i<NUMBER_OF_TESTS*2;i++)
		{
			testString = IPFilterRuleTest.getRandomIPRuleString();
			flowDescription = new FlowDescription(testString);
			if(flowDescription.isDirectionIn())
			{
				mediaSubComponent.setFlowDescriptionIn(flowDescription);
				value = mediaSubComponent.getFlowDescriptionIn().getOctetString();
			}
			else
			{
				mediaSubComponent.setFlowDescriptionOut(flowDescription);
				value = mediaSubComponent.getFlowDescriptionOut().getOctetString();
			}
			assertEquals(testString, value);
		}
	}
	
	@Test
	public final void testAll()	{
		String flowIn;
		FlowDescription flowDescriptionIn=null;
		String flowOut;
		FlowDescription flowDescriptionOut=null;
		long maxReqUL;
		MaxRequestedBandwidthUL maxRequestedBandwidthUL=null;
		long maxReqDL;
		MaxRequestedBandwidthDL maxRequestedBandwidthDL=null;
		int flowus;
		FlowUsage flowUsage = null;
		int flowst;
		FlowStatus flowStatus = null;
		long flownu;
		FlowNumber flowNumber = null;
		
		MediaSubComponent mediaSubComponent = new MediaSubComponent();
		for(int i = 0;i<NUMBER_OF_TESTS;i++)
		{
			//Set the values
			flowIn = IPFilterRuleTest.getRandomIPRuleString(true);
			System.out.println(flowIn);
			
			flowOut = IPFilterRuleTest.getRandomIPRuleString(false);
			maxReqDL = getBandwith();
			maxReqUL = getBandwith();
			flowus = getRandomFlowUsageValue();
			flowst = getRandomFlowUsageValue();
			flownu = getRandomFlowNumber();
			// create Objects
			flowDescriptionIn = new FlowDescription(flowIn);
			flowDescriptionOut = new FlowDescription(flowOut);
			maxRequestedBandwidthDL= new MaxRequestedBandwidthDL(maxReqDL);
			maxRequestedBandwidthUL= new MaxRequestedBandwidthUL(maxReqUL);
			flowUsage = new FlowUsage(flowus);
			flowStatus = new FlowStatus(flowst);
			flowNumber = new FlowNumber(flownu);
			//set the Objects
			mediaSubComponent.setFlowDescriptionIn(flowDescriptionIn);
			mediaSubComponent.setFlowDescriptionOut(flowDescriptionOut);
			mediaSubComponent.setMaxRequestedBandwidthDL(maxRequestedBandwidthDL);
			mediaSubComponent.setMaxRequestedBandwidthUL(maxRequestedBandwidthUL);
			mediaSubComponent.setFlowUsage(flowUsage);
			mediaSubComponent.setFlowStatus(flowStatus);
			mediaSubComponent.setFlowNumber(flowNumber);
			//read values
			System.out.println(mediaSubComponent.getFlowDescriptionIn().getOctetString());
			assertEquals(flowIn,mediaSubComponent.getFlowDescriptionIn().getOctetString());
			assertEquals(flowOut,mediaSubComponent.getFlowDescriptionOut().getOctetString());
			assertEquals(maxReqDL, maxRequestedBandwidthDL.getUnsigned32());
			assertEquals(maxReqUL, maxRequestedBandwidthUL.getUnsigned32());
			assertEquals(flowus, mediaSubComponent.getFlowUsage().getEnumerated());
			assertEquals(flowst, mediaSubComponent.getFlowStatus().getEnumerated());
			assertEquals(flownu, mediaSubComponent.getFlowNumber().getUnsigned32());
		}
	}

	
	
	
	
	
}
