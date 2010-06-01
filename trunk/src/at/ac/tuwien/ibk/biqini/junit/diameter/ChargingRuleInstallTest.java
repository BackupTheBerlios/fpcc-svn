/* 
* ChargingRuleInstallTest.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.BearerIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleBaseName;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleDefinition;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleInstall;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleName;


/**
 * @author mhappenhofer
 *
 */
public class ChargingRuleInstallTest extends AVPJunit{
	
	public static final int NUMBER_OF_CHARGING_RULES = 3;
	
	public final void testChargingRuleInstall()	{
		ChargingRuleInstall c = new ChargingRuleInstall();
		BearerIdentifier bi = null;
		Vector<ChargingRuleBaseName> crbn = null;
		Vector<ChargingRuleName> crn = null;
		Vector<ChargingRuleDefinition> crd = null;
		for(int i =0;i<NUMBER_OF_TESTS;i++)
		{
			bi = getRandomBearerIdentifier();
			c.setBearerIdentifier(bi);
			crbn = new Vector<ChargingRuleBaseName>();
			crn = new Vector<ChargingRuleName>();
			crd = new Vector<ChargingRuleDefinition>();
			for(int j=0;j<NUMBER_OF_CHARGING_RULES;j++)
			{
				ChargingRuleBaseName crbnO = getRandomChargingRuleBaseName();
				crbn.add(crbnO);
				c.addChargingRuleBaseName(crbnO);
				
				ChargingRuleName crnO = getRandomChargingRuleName();
				crn.add(crnO);
				c.addChargingRuleName(crnO);
				
				ChargingRuleDefinition crdO = getRandomChargingRuleDefinition();
				crd.add(crdO);
				c.addChargingRuleDefinition(crdO);
				
			}
			Iterator<ChargingRuleBaseName>itCRBN = crbn.iterator();
			while(itCRBN.hasNext())
			{
				ChargingRuleBaseName current = itCRBN.next();
				for(Iterator<ChargingRuleBaseName>it = c.getChargingRuleBaseNameIterator();it.hasNext();)
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
			
			Iterator<ChargingRuleDefinition>itCRD =crd.iterator();
			while(itCRD.hasNext())
			{
				ChargingRuleDefinition current = itCRD.next();
				for(Iterator<ChargingRuleDefinition> it = c.getChargingRuleDefinitionIterator();it.hasNext();)
				{
					ChargingRuleDefinition local = it.next();
					if(local.equals(current))
					{
						c.deleteChildAVP(local);
						current = null;
						break;
					}
				}
				assertEquals(null, current);
			}
			assertEquals(false, c.getChargingRuleDefinitionIterator().hasNext());
			assertEquals(bi, c.getBearerIdentifier());
		}
	}

}
