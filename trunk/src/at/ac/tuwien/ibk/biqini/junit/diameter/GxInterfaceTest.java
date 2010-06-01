/* 
* GxInterfaceTest.java
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

import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxCCA;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxCCR;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxRAA;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.gx.GxRAR;
import de.fhg.fokus.diameter.DiameterPeer.data.Codec;
import de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage;
import de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessageDecodeException;


/**
 * @author mhappenhofer
 *
 */
public class GxInterfaceTest extends DiameterMessageJunit{
	
	public final void testOutput()	{
		System.out.println(getRandomGxCCA());
		System.out.println(getRandomGxCCR());
	}
	
	public final void testCCA()	{
		try {
			for(int i = 0;i<NUMBER_OF_TESTS;i++)
			{
				GxCCA original = getRandomGxCCA();
				byte data[] = Codec.encodeDiameterMessage(original);
				DiameterMessage d = Codec.decodeDiameterMessage(data, 0);
				if (d instanceof GxCCA) {
					GxCCA parsed = (GxCCA) d;
					assertEquals(original, parsed);
				}
				else
					fail("Parser failed because he did not parse the right type");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception occured!");
		}
	}
	
	public final void testCCR()	{
		try {
			for(int i = 0;i<NUMBER_OF_TESTS;i++)
			{
				GxCCR original = getRandomGxCCR();
				byte data[] = Codec.encodeDiameterMessage(original);
				DiameterMessage d = Codec.decodeDiameterMessage(data, 0);
				if (d instanceof GxCCR) {
					GxCCR parsed = (GxCCR) d;
					assertEquals(original, parsed);
				}
				else
					fail("Parser failed because he did not parse the right type");
				
			}
		} catch (DiameterMessageDecodeException e) {
			e.printStackTrace();
			fail("Exception occured!");
		}
	}
	
	public final void testRAR()	{
		try {
			for(int i = 0;i<NUMBER_OF_TESTS;i++)
			{
				GxRAR original = getRandomGxRAR();
				byte data[] = Codec.encodeDiameterMessage(original);
				DiameterMessage d = Codec.decodeDiameterMessage(data, 0);
				if (d instanceof GxRAR) {
					GxRAR parsed = (GxRAR) d;
					assertEquals(original, parsed);
				}
				else
					fail("Parser failed because he did not parse the right type");
				
			}
		} catch (DiameterMessageDecodeException e) {
			e.printStackTrace();
			fail("Exception occured!");
		}
	}
	
	public final void testRAA()	{
		try {
			for(int i = 0;i<NUMBER_OF_TESTS;i++)
			{
				GxRAA original = getRandomGxRAA();
				byte data[] = Codec.encodeDiameterMessage(original);
				DiameterMessage d = Codec.decodeDiameterMessage(data, 0);
				if (d instanceof GxRAA) {
					GxRAA parsed = (GxRAA) d;
					assertEquals(original, parsed);
				}
				else
					fail("Parser failed because he did not parse the right type");
				
			}
		} catch (DiameterMessageDecodeException e) {
			e.printStackTrace();
			fail("Exception occured!");
		}
	}

}
