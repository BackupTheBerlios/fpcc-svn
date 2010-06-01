/* 
* AVPFactoryTest.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.CalledStationId;
import de.fhg.fokus.diameter.DiameterPeer.data.Codec;
import de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage;
import de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessageDecodeException;

/**
 * @author Christoph Egger
 *
 */
public class AVPFactoryTest extends junit.framework.TestCase{

	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.AVPFactory#parse(int)}.
	 */
	@Test
	public void testParse() {
		
		byte[] original={};
		
		DiameterMessage msg = new DiameterMessage();
		DiameterMessage msg2 = null;
		
		CalledStationId csi = new CalledStationId("TestStation ID");	
		msg.addAVP(csi);
		//System.out.println(msg.getAVPCount());
		original = Codec.encodeDiameterMessage(msg);
		try {
			msg2 = Codec.decodeDiameterMessage(original, 0);
		} catch (DiameterMessageDecodeException e) {
			// TODO Auto-generated catch block
			fail(e.getLocalizedMessage());
			
		}
/*		System.out.println(msg2.getAVPCount());
		for (int i = 0; i<msg2.getAVPCount(); i++){
			System.out.println(msg2.getAVP(i).getClass().toString());
		}*/
		CalledStationId csi2 = (CalledStationId) msg2.getAVP(0);
		
		assertEquals(csi.getUTF8String(), csi2.getUTF8String());
	}

}
