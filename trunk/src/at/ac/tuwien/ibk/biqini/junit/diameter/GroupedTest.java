/* 
* GroupedTest.java
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

import java.util.Arrays;

import at.ac.tuwien.ibk.biqini.diameter.avp.base.Grouped;
import de.fhg.fokus.diameter.DiameterPeer.data.AVP;


/**
 * @author mhappenhofer
 *
 */
public class GroupedTest extends AVPJunit{
	
	public static final int MAXDEEP = 7;
	public static final int MAXBREATH = 7;
	
	
	public static final int AVPS = 12;
	/**
	 * make small compares 
	 */
	public final void testEquals()	{
		AVP a1 = new AVP(55,true,777);
		AVP a2 = new AVP(55,true,777);
		AVP b1 = new AVP(11,true,777);
		AVP b2 = new AVP(22,true,777);
		AVP c1 = new AVP(11,true,777);
		AVP c2 = new AVP(22,true,777);
		
		assertEquals(true,a1.equals(a2));
		b1.setData("HALLO");
		b2.setData("BYE");
		c1.setData("HALLO");
		c2.setData("BYE");
		a1.addChildAVP(b1);
		a1.addChildAVP(b2);
		a2.addChildAVP(c1);
		a2.addChildAVP(c2);
		assertEquals(false, b1.equals(b2));
		assertEquals(true,a1.equals(a2));
	}
	
	
	
	public final void testGrouped()	{
		Grouped g1;
		Grouped g2;
		for(int i = 0; i<NUMBER_OF_TESTS;i++)
		{
			AVP a[] = new AVP[AVPS];
			for(int j = 0;j<AVPS;j++)
				a[j] = generateRandomAVP(10);
			g1 = new Grouped();
			g2 = new Grouped();
			insertRandom(g1, a);
			insertRandom(g2, a);
			assertEquals(true,g1.equals(g2));
		}
		
	}
	
	public static void insertRandom(Grouped _g,AVP[] _avps){
		boolean set[] = new boolean[AVPS];
		boolean fin[] = new boolean[AVPS]; 
		for(int i = 0;i<AVPS;i++)
		{
			set[i] = false;
			fin[i] = true;
		}
		while (!Arrays.equals(set, fin))
		{
			int pos = RANDOMIZER.nextInt(AVPS);
			if(!set[pos])
			{
				_g.addChildAVP(_avps[pos]);
				set[pos]= true;
			}
		}
	}
	
	public static AVP generateRandomAVP(int deep)	{
		AVP avp = new AVP(RANDOMIZER.nextInt(),true,0);
		/*avp.flag_mandatory = RANDOMIZER.nextBoolean();
		avp.flag_protected = RANDOMIZER.nextBoolean();
		avp.flag_vendor_specific  = RANDOMIZER.nextBoolean();
		avp.vendor_id = RANDOMIZER.nextInt();*/
		if(deep >MAXDEEP || RANDOMIZER.nextBoolean())
		{
			int size = RANDOMIZER.nextInt(1024);
			byte[] data = new byte[size];
			RANDOMIZER.nextBytes(data);
			avp.setData(data);
		}
		else
		{
			int breath = RANDOMIZER.nextInt(MAXBREATH);
			for(int i= 0;i<breath;i++)
				avp.addChildAVP(generateRandomAVP(deep+1));
		}
		return avp;
	}

}
