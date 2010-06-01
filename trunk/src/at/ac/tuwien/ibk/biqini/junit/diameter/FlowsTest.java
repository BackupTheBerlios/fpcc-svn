/* 
* FlowsTest.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowNumber;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Flows;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaComponentNumber;

/**
 * @author mhappenhofer
 *
 */
public class FlowsTest extends AVPJunit{

	
	public static final int NUMBER_OF_FLOWNUMBERS = 13;
	
	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Flows#addFlowNumber(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowNumber)}.
	 */
	@Test
	public final void testAddFlowNumber() {
		
		Flows flows = null;
		
		for(int i = 0; i<NUMBER_OF_TESTS;i++)
		{
			flows = new Flows(new MediaComponentNumber(i));
			//insert Flow Numbers
			Vector<FlowNumber> flnu = new Vector<FlowNumber>();
			for(int j = 0; j<NUMBER_OF_FLOWNUMBERS;j++)
			{
				FlowNumber fl = new FlowNumber(getRandomFlowNumber());
				flnu.add(fl);
				flows.addFlowNumber(fl);
			}
			
			//now search for all
			for(Iterator<FlowNumber> ii = flnu.iterator();ii.hasNext();)
			{
				FlowNumber current = ii.next();
				for(Iterator<FlowNumber> it = flows.getFlowNumberIterator();it.hasNext();)
				{
					FlowNumber fn = it.next();
					if(fn.equals(current))
					{
						flows.deleteChildAVP(fn);
						current=null;
					}
				}
				assertEquals(null,current);
			}
			//check MediaComponentNumber
			MediaComponentNumber mc = flows.getMediaComponentNumber(); 
			long cnt = mc.getUnsigned32();
			assertEquals(i,cnt);
		}
	}
	
	
}
