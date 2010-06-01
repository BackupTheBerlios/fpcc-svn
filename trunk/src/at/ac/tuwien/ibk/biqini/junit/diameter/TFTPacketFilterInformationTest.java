/* 
* TFTPacketFilterInformationTest.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.Precedence;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.TFTFilter;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.TFTPacketFilterInformation;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ToSTrafficClass;


/**
 * @author mhappenhofer
 *
 */
public class TFTPacketFilterInformationTest extends AVPJunit{

	public final void testTFTPacketFilterInformation()	{
		TFTPacketFilterInformation tft = new TFTPacketFilterInformation();
		Precedence pre = null;
		TFTFilter filter = null;
		ToSTrafficClass tos = null;
		for(int i=0;i<NUMBER_OF_TESTS;i++)
		{
			pre = getRandomPrecedence();
			tft.setPrecedence(pre);
			filter = getRandomTFTFilter();
			tft.setTFTFilter(filter);
			tos = getRandomToSTrafficClass();
			tft.setToSTrafficClass(tos);
			
			assertEquals(pre, tft.getPrecedence());
			assertEquals(filter, tft.getTFTFilter());
			assertEquals(tos, tft.getToSTrafficClass());
		}
	}
}
