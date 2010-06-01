/* 
* EventReportIndicationTest.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.EventReportIndication;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.EventTrigger;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.RATType;


/**
 * @author mhappenhofer
 *
 */
public class EventReportIndicationTest extends AVPJunit{
	
	public static final int NUMBER_OF_EVENT_TRIGGERS = 3;
	
	public final void testEventReportIndication()	{
		EventReportIndication e = new EventReportIndication();
		RATType rat = null;
		Vector<EventTrigger> et = null;
		for(int i =0;i<NUMBER_OF_TESTS;i++)
		{
			rat = getRandomRATType();
			e.setRATType(rat);
			et = new Vector<EventTrigger>();
			for(int j=0;j<NUMBER_OF_EVENT_TRIGGERS;j++)
			{
				EventTrigger eventT = getRandomEventTrigger();
				et.add(eventT);
				e.addEventTrigger(eventT);
				
			}
			Iterator<EventTrigger>itET = et.iterator();
			while(itET.hasNext())
			{
				EventTrigger current = itET.next();
				for(Iterator<EventTrigger> it = e.getEventTriggerIterator();it.hasNext();)
				{
					EventTrigger local = it.next();
					if(local.equals(current))
					{
						e.deleteChildAVP(local);
						current = null;
						break;
					}
				}
				assertEquals(null, current);
			}
			assertEquals(false, e.getEventTriggerIterator().hasNext());
			assertEquals(rat, e.getRATType());
		}
	}

}

