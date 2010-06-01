/* 
* DiameterURITest.java
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

import java.util.regex.Matcher;

import at.ac.tuwien.ibk.biqini.diameter.avp.base.DiameterURI;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.exceptions.IllegalAVPDataException;


/**
 * @author mhappenhofer
 *
 */
public class DiameterURITest extends AVPJunit{
	
	public static String okURIs[] = { 
			"aaa://host.example.com",
			"aaa://host.domain.example.com",
			"aaa://host1.example.com",
			"aaa://h-ost1.example.com",
			"aaas://host.example.com",
			"aaa://host.example.com:5555",
			"aaa://host.example.com;transport=tcp",
			"aaa://host.example.com:6666;transport=tcp",
			"aaa://host.example.com;protocol=diameter",
			"aaa://host.example.com:6666;protocol=diameter",
			"aaa://host.example.com:6666;transport=tcp;protocol=diameter",
			"aaa://host.example.com:1813;transport=udp;protocol=radius",
			"aaa://pcrf.telekom.com:5908"};
	
	public static String failedURIs[] = { 
			"http://host.example.com",
			"aaa://host..example.com",
			"aaas://host.example.com::",
			"aaa://host.example.com:5555:6666",
			"aaa://host.example.com;transport=tcp,SCTP",
			"aaa://host.example.com:6666;transport=tcp?user=testUser",
			"aaa://host.example.com;protocol=sip",
			"aaa://host.example.com:6666;protocol=diameter;transport=udp",
			"aaa://host.example.com:6666,6667;transport=tcp;protocol=diameter",
			"aaa://host.example.com:1813;transport=radius;protocol=udp"};
	
	
	public final void testParser()	{
		//System.out.println(DiameterURI.DIAMETERURI.toString());
		for(int i = 0;i<okURIs.length;i++)
		{
			Matcher m = DiameterURI.DIAMETERURI.matcher(okURIs[i]);
			//System.out.println(okURIs[i]+" "+m.matches());
			assertEquals(true, m.matches());
		}
		for(int i = 0;i<failedURIs.length;i++)
		{
			Matcher m = DiameterURI.DIAMETERURI.matcher(failedURIs[i]);
			//System.out.println(failedURIs[i]+" "+m.matches());
			assertEquals(false, m.matches());
		}
	}
	
	public final void testDiameterURI()	{
		String newFQN = "anyhost.anydomain.anyTLD";
		String trans = "tcp";
		String protocol = "diameter";
		int port = 222;
		try {
			for(int i = 0; i<okURIs.length;i++)
			{
				//System.out.println(okURIs[i]);
				DiameterURI d = new DiameterURI(okURIs[i]);
				//System.out.println(d.getOctetString());
				assertEquals(okURIs[i], d.getOctetString());
				d.setFqn(newFQN);
				assertEquals(newFQN, d.getFqn());
				d.setSecure(true);
				assertEquals(true, d.isSecure());
				d.setPort(port);
				assertEquals(port, d.getPort());
				d.setTransport(trans);
				assertEquals(trans, d.getTransport());
				d.setProtocol(protocol);
				assertEquals(protocol, d.getProtocol());
			}
		} catch (IllegalAVPDataException e) {
			fail("A exception occured "+e.getLocalizedMessage());
		}
	}
	
	public final void testRandomDiameterURI()	{
		try {
			for(int i = 0; i<NUMBER_OF_TESTS;i++)
			{
					String test = getRandomDiameterURIString();
					DiameterURI d = new DiameterURI(test);
					assertEquals(test, d.getOctetString());
					//System.out.println(test+" - "+d.getOctetString());		
			}
		} catch (Exception e) {
			fail("A exception occured "+e.getLocalizedMessage());
		}
	}
	
	
}
