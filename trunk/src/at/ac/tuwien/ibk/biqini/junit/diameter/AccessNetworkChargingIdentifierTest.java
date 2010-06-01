/* 
* AccessNetworkChargingIdentifierTest.java
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

import org.junit.Test;

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AccessNetworkChargingIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Flows;

/**
 * @author mhappenhofer
 *
 */
public class AccessNetworkChargingIdentifierTest extends junit.framework.TestCase {

	
	public static final int NUMBER_OF_TESTS = 1024;
	public static final int NUMBER_OF_FLOWS = 53;
	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AccessNetworkChargingIdentifier#addFlows(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Flows)}.
	 */
	@Test
	public final void testAddFlows() {
		AccessNetworkChargingIdentifier a = null;
		for(int i = 0; i<NUMBER_OF_TESTS;i++)
		{
			a = new AccessNetworkChargingIdentifier(String.valueOf(i));
			//create some Flows
			Vector<Flows> flows = new Vector<Flows>(NUMBER_OF_FLOWS);
			for(int j = 0;j< NUMBER_OF_FLOWS;j++)
			{
				Flows fl =FlowsTest.getRandomFlows(); 
				flows.add(fl);
				a.addFlows(fl);
			}
			//Check if all flows are stored and deleting works fine 
			Iterator<Flows>fit = flows.iterator();
			while(fit.hasNext())
			{
				Flows curr = fit.next();
				for(Iterator<Flows>it = a.getFlowsIterator();it.hasNext();)
				{
					Flows local = it.next();
					if(local.equals(curr))
					{
						a.deleteChildAVP(local);
						curr = null;
						break;
					}
				}
				assertEquals(null,curr);
			}
			assertEquals(false,	a.getFlowsIterator().hasNext());
			//Check AccesNetworkChargingIdentifer
			assertEquals(String.valueOf(i),a.getAccessNetworkChargingIdentifierValue().getOctetString());
			
		}
	}

}
