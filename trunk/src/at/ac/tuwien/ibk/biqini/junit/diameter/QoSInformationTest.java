/* 
* QoSInformationTest.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.BearerIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.GuaranteedBitrateDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.GuaranteedBitrateUL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthUL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSClassIdentifier;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.QoSInformation;


/**
 * @author mhappenhofer
 *
 */
public class QoSInformationTest  extends AVPJunit{
	
	
	public static final int NUMBER_OF_RULES = 5;
	
	public final void testQoSInformation()	{
		QoSInformation q = new QoSInformation();
		BearerIdentifier b = null;
		MaxRequestedBandwidthDL mrd = null;
		MaxRequestedBandwidthUL mru = null;
		GuaranteedBitrateDL gbd = null;
		GuaranteedBitrateUL gbu = null;
		QoSClassIdentifier qci = null;
		for(int i = 0;i<NUMBER_OF_TESTS;i++)
		{
			b = getRandomBearerIdentifier();
			q.setBearerIdentifier(b);
			gbd = getRandomGuaranteedBitrateDL();
			q.setGuaranteedBitrateDL(gbd);
			gbu = getRandomGuaranteedBitrateUL();
			q.setGuaranteedBitrateUL(gbu);
			mrd = getRandomMaxRequestedBandwidthDL(); 
			q.setMaxRequestedBandwidthDL(mrd);
			mru = getRequestedBandwidthUL();
			q.setMaxRequestedBandwidthUL(mru);
			qci = getRandomQoSClassIdentifier();
			q.setQoSClassIdentifier(qci);
			assertEquals(b, q.getBearerIdentifier());
			assertEquals(gbd, q.getGuaranteedBitrateDL());
			assertEquals(gbu, q.getGuaranteedBitrateUL());
			assertEquals(mru, q.getMaxRequestedBandwidthUL());
			assertEquals(mrd, q.getMaxRequestedBandwidthDL());
			assertEquals(qci, q.getQoSClassIdentifier());
		}
		
	}
	

}
