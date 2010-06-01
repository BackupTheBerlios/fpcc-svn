/* 
* IPFilterRuleTest.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.base.IPFilterRule;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.exceptions.IllegalAVPDataException;


public class IPFilterRuleTest extends AVPJunit{
	
	
	public void testParserIPFilter()	{
		String test = "permit in 17 from 10.0.0.1/2 20 to 10.0.0.2/2 40,60";
		//System.out.println(test);
		IPFilterRule ipFilterRule = new IPFilterRule();
		ipFilterRule.setIPFilterRule(test);
		assertEquals(ipFilterRule.getOctetString(),test);
		try {
			ipFilterRule.setActionDeny();
			ipFilterRule.setDirectionOut();
			ipFilterRule.setProto("ip");
			ipFilterRule.setSrcHost("192.168.1.0/24");
			ipFilterRule.setSrcPort("5060,5061");
			ipFilterRule.setDstHost("192.168.1.0/24");
			ipFilterRule.setDstPort("5060,5061");
			ipFilterRule.setOptions("frag");
		} catch (IllegalAVPDataException e) {
			fail(e.getLocalizedMessage());
		}
		assertEquals(ipFilterRule.getOctetString(),"deny out ip from 192.168.1.0/24 5060,5061 to 192.168.1.0/24 5060,5061 frag");
	}
	
	
	
	public void testIMSCall()	{
		String test = "permit in ip from 192.168.1.80 to 192.168.1.81 17834 ";
		//System.out.println(test);
		IPFilterRule ipFilterRule = new IPFilterRule();
		ipFilterRule.setIPFilterRule(test);
		assertEquals(ipFilterRule.getDstPort(),"17834");
		assertEquals(ipFilterRule.getOctetString(),test);
		
	}
	
	public void testRandomParser()	{
		String testString;
		IPFilterRule ipFilterRule;
		for(int i = 0;i<NUMBER_OF_TESTS;i++)
		{
			testString = getRandomIPRuleString();
			//System.out.println("Test : "+testString);
			ipFilterRule = new IPFilterRule();
			ipFilterRule.setIPFilterRule(testString);
			assertEquals(testString, ipFilterRule.getOctetString());
		}
	}
	public void testCollection()	{
		String[] tests={"permit in 17 from 10.0.0.1 to 1.1.1.1",
				"permit in 17 from 10.0.0.1 to 0.0.0.0"};
		for(int i = 0;i< tests.length;i++)
		{
			try {
				IPFilterRule ipFilterRule = new IPFilterRule();
				ipFilterRule.setIPFilterRule(tests[i]);
				System.out.println("Test :"+tests[i]);
			} catch (Exception e) {
				fail(e.getLocalizedMessage());
			}
		}
	}
	
	
	public void testSocketParser()	{
		String sock1[] = {"192.168.1.99","192.168.1.0/24","192.168.1.1 55","192.168.1.1 44-46","192.168.1.1 88,88","192.168.1.1 88-77 77-99"};
		boolean accept[] ={true,true,true,false,true,false};
		for(int i = 0;i<sock1.length;i++)
			assertEquals(IPFilterRule.SOCKET.matcher(sock1[i]).matches(),accept[i]);
	}
	
	
	
	
	

}
