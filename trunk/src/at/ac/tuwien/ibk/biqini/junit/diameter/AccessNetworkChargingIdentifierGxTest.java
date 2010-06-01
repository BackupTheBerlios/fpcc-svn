/* 
* AccessNetworkChargingIdentifierGxTest.java
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
import java.util.Iterator;
import java.util.Random;

import org.junit.Test;

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AccessNetworkChargingIdentifierGx;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AccessNetworkChargingIdentifierValue;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleBaseName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleName;

/**
 * @author mhappenhofer
 *
 */
public class AccessNetworkChargingIdentifierGxTest extends junit.framework.TestCase {

	private static Random RANDOMIZER;
	static{
		RANDOMIZER = new Random(Calendar.getInstance().getTimeInMillis());
	}
	public static final int NUMBER_OF_TESTS = 1024;
	public static final int NUMBER_OF_RULES = 5;
	
	@Test
	public final void testAccessNetworkChargingIdentifierGxComplete() {
		AccessNetworkChargingIdentifierGx a = new AccessNetworkChargingIdentifierGx();
		for(int i =0;i<NUMBER_OF_TESTS;i++)
		{
			String test = String.valueOf(RANDOMIZER.nextLong());
			String rulesName[] = new String[NUMBER_OF_RULES];
			String rulesBaseName[] = new String[NUMBER_OF_RULES];
			// fill up the object with random values
			for(int j = 0; j< NUMBER_OF_RULES;j++)
			{
				rulesName[j]= String.valueOf(RANDOMIZER.nextLong());
				a.addChargingRuleName(new ChargingRuleName(rulesName[j]));
				rulesBaseName[j]= String.valueOf(RANDOMIZER.nextLong());
				a.addChargingRuleBaseName(new ChargingRuleBaseName(rulesBaseName[j]));
			}
			a.setAccessNetworkChargingIdentifierValue(new AccessNetworkChargingIdentifierValue(test));
			// read out values and compare
			String response = a.getAccessNetworkChargingIdentifierValue().getOctetString();
			assertEquals(test, response);
			for(int j = 0; j< NUMBER_OF_RULES;j++)
			{
				for(Iterator<ChargingRuleBaseName> it=a.getChargingRuleBaseNameIterator();it.hasNext();)
				{
					ChargingRuleBaseName c = it.next();
					if(c.getUTF8String().equals(rulesBaseName[j]))
					{
						rulesBaseName[j]=null;
						a.deleteChildAVP(c);
						break;
					}
				}
				if(rulesBaseName[j]!=null)
					fail("could not find a certain inserted ChargingRuleBaseName!");
				for(Iterator<ChargingRuleName> it=a.getChargingRuleNameIterator();it.hasNext();)
				{
					ChargingRuleName c = it.next();
					if(c.getOctetString().equals(rulesName[j]))
					{
						rulesName[j]=null;
						a.deleteChildAVP(c);
						break;
					}
				}
				if(rulesName[j]!=null)
					fail("could not find a certain inserted ChargingRuleName!");
			}
			//all child successful deleted ?
			assertEquals(1,a.getChildCount());
		}
	}
}
