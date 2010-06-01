/* 
* ChargingInformationTest.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingInformation;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.PrimaryChargingCollectionFunctionName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.PrimaryEventChargingFunctionName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.SecondaryChargingCollectionFunctionName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.SecondaryEventChargingFunctionName;

/**
 * @author mhappenhofer
 *
 */
public class ChargingInformationTest extends AVPJunit{

	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingInformation#setPrimaryChargingCollectionFunctionName(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.PrimaryChargingCollectionFunctionName)}.
	 */
	@Test
	public final void testSetPrimaryChargingCollectionFunctionName() {
		ChargingInformation c = new ChargingInformation();
		for(int i = 0;i<NUMBER_OF_TESTS;i++)
		{
			String testURI = getRandomDiameterURIString();
			c.setPrimaryChargingCollectionFunctionName(new PrimaryChargingCollectionFunctionName(testURI));
			String response = c.getPrimaryChargingCollectionFunctionName().getOctetString();
			assertEquals(testURI, response);
		}
		
	}

	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingInformation#setPrimaryEventChargingFunctionName(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.PrimaryEventChargingFunctionName)}.
	 */
	@Test
	public final void testSetPrimaryEventChargingFunctionName() {
		ChargingInformation c = new ChargingInformation();
		for(int i = 0;i<NUMBER_OF_TESTS;i++)
		{
			String testURI = getRandomDiameterURIString();
			c.setPrimaryEventChargingFunctionName(new PrimaryEventChargingFunctionName(testURI));
			String response = c.getPrimaryEventChargingFunctionName().getOctetString();
			assertEquals(testURI, response);
		}
	}

	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingInformation#setSecondaryChargingCollectionFunctionName(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.SecondaryChargingCollectionFunctionName)}.
	 */
	@Test
	public final void testSetSecondaryChargingCollectionFunctionName() {
		ChargingInformation c = new ChargingInformation();
		for(int i = 0;i<NUMBER_OF_TESTS;i++)
		{
			String testURI = getRandomDiameterURIString();
			c.setSecondaryChargingCollectionFunctionName(new SecondaryChargingCollectionFunctionName(testURI));
			String response = c.getSecondaryChargingCollectionFunctionName().getOctetString();
			assertEquals(testURI, response);
		}
	}

	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingInformation#setSecondaryEventChargingFunctionName(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.SecondaryEventChargingFunctionName)}.
	 */
	@Test
	public final void testSetSecondaryEventChargingFunctionName() {
		ChargingInformation c = new ChargingInformation();
		for(int i = 0;i<NUMBER_OF_TESTS;i++)
		{
			String testURI = getRandomDiameterURIString();
			c.setSecondaryEventChargingFunctionName(new SecondaryEventChargingFunctionName(testURI));
			String response = c.getSecondaryEventChargingFunctionName().getOctetString();
			assertEquals(testURI, response);
		}
	}

}
