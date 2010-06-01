/* 
* DiameterMessageTest.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.BearerIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.EventTrigger;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxCCA;


/**
 * @author mhappenhofer
 *
 */
public class DiameterMessageTest extends junit.framework.TestCase{
	
	public final void testDynamicGenerics()	{
		GxCCA dia = new GxCCA();
		dia.addAVP(AVPJunit.getRandomEventTrigger());
		dia.addAVP(AVPJunit.getRandomEventTrigger());
		dia.addAVP(AVPJunit.getRandomEventTrigger());
		dia.addAVP(AVPJunit.getRandomEventTrigger());
		dia.addAVP(AVPJunit.getRandomBearerIdentifier());
		dia.addAVP(AVPJunit.getRandomBearerIdentifier());
		Iterator<EventTrigger> it = dia.getIterator(new EventTrigger());
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
		Iterator<BearerIdentifier> it2 = dia.getIterator(new BearerIdentifier());
		while(it2.hasNext())
		{
			System.out.println(it2.next());
		}
	}

}
