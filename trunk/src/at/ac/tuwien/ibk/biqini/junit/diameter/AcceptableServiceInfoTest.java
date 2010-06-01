/* 
* AcceptableServiceInfoTest.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AcceptableServiceInfo;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthUL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaComponentDescription;

/**
 * @author mhappenhofer
 *
 */
public class AcceptableServiceInfoTest extends junit.framework.TestCase {

	
	public static final int NUMBER_OF_TESTS = 1024;
	public static final int NUMBER_OF_MEDIA_DESCRIPTIONS = 3;
	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AcceptableServiceInfo#addMediaComponentDescription(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaComponentDescription)}.
	 */
	@Test
	public final void testAddMediaComponentDescription() {
		AcceptableServiceInfo asi = null;
		for(int i =0;i<NUMBER_OF_TESTS;i++)
		{
			asi = new AcceptableServiceInfo();
			Vector<MediaComponentDescription> mcd = new Vector<MediaComponentDescription>();
			long upload =0;
			long download = 0;
			//insert media Component Descriptions
			for(int j = 0;j<NUMBER_OF_MEDIA_DESCRIPTIONS;j++)
			{
				MediaComponentDescription curr = MediaComponentDescriptionTest.getRandomMediaComponentDescription();
				MaxRequestedBandwidthDL mD =curr.getMaxRequestedBandwidthDL();
				MaxRequestedBandwidthUL mU =curr.getMaxRequestedBandwidthUL();
				upload = upload+mU.getUnsigned32();
				download = download + mD.getUnsigned32();
				mcd.add(curr);
				asi.addMediaComponentDescription(curr);
			}
			//check if all are stored and if deleting works fine
			Iterator<MediaComponentDescription> mit = mcd.iterator();
			while(mit.hasNext())
			{
				MediaComponentDescription curr = mit.next();
				for(Iterator<MediaComponentDescription>it = asi.getMediaComponentDescriptionIterator();it.hasNext();)
				{
					MediaComponentDescription local = it.next();
					if(local.equals(curr))
					{
						asi.deleteChildAVP(local);
						curr = null;
						break;
					}
				}
				assertEquals(null, curr);
			}
			assertEquals(false, asi.getMediaComponentDescriptionIterator().hasNext());
			//check Bandwidth
			assertEquals(upload, asi.getMaxRequestedBandwidthUL().getUnsigned32());
			assertEquals(download, asi.getMaxRequestedBandwidthDL().getUnsigned32());
		}
	}

}
