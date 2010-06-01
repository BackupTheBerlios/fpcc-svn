/* 
* ChargingRuleRemoveTest.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleBaseName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleRemove;


/**
 * @author mhappenhofer
 *
 */
public class ChargingRuleRemoveTest extends AVPJunit{
	
public static final int NUMBER_OF_CHARGING_RULES = 3;
	
	public final void testChargingRuleRemove()	{
		ChargingRuleRemove c = new ChargingRuleRemove();
		Vector<ChargingRuleBaseName> crbn = null;
		Vector<ChargingRuleName> crn = null;
		for(int i =0;i<NUMBER_OF_TESTS;i++)
		{
			crbn = new Vector<ChargingRuleBaseName>();
			crn = new Vector<ChargingRuleName>();
			for(int j=0;j<NUMBER_OF_CHARGING_RULES;j++)
			{
				ChargingRuleBaseName crbnO = getRandomChargingRuleBaseName();
				crbn.add(crbnO);
				c.addChargingRuleBaseName(crbnO);
				
				ChargingRuleName crnO = getRandomChargingRuleName();
				crn.add(crnO);
				c.addChargingRuleName(crnO);
			}
			Iterator<ChargingRuleBaseName>itCRBN = crbn.iterator();
			while(itCRBN.hasNext())
			{
				ChargingRuleBaseName current = itCRBN.next();
				for(Iterator<ChargingRuleBaseName> it = c.getChargingRuleBaseNameIterator();it.hasNext();)
				{
					ChargingRuleBaseName local = it.next();
					if(local.equals(current))
					{
						c.deleteChildAVP(local);
						current = null;
						break;
					}
				}
				assertEquals(null, current);
			}
			assertEquals(false, c.getChargingRuleBaseNameIterator().hasNext());
				
			Iterator<ChargingRuleName>itCRN = crn.iterator();
			while(itCRN.hasNext())
			{
				ChargingRuleName current = itCRN.next();
				for(Iterator<ChargingRuleName> it = c.getChargingRuleNameIterator();it.hasNext();)
				{
					ChargingRuleName local = it.next();
					if(local.equals(current))
					{
						c.deleteChildAVP(local);
						current = null;
						break;
					}
				}
				assertEquals(null, current);
			}
			assertEquals(false, c.getChargingRuleNameIterator().hasNext());
		}
	}

}
