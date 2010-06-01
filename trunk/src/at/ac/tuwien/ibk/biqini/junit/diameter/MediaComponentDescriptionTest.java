/* 
* MediaComponentDescriptionTest.java
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

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.CodecData;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowStatus;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaComponentDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaSubComponent;

/**
 * @author mhappenhofer
 *
 */
public class MediaComponentDescriptionTest extends AVPJunit {

	public static final int MAX_SUBMEDIAS = 7;
	public static final int MAX_CODEC_DATAS = 7;
	
	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaComponentDescription#addMediaSubComponent(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaSubComponent)}.
	 */
	@Test
	public final void testAll() {
		MediaComponentDescription md = null;
		for(int i = 0; i< NUMBER_OF_TESTS;i++)
		{
			md = new MediaComponentDescription(getRandomUnsigned32());
			long bUl=0;
			long bDl=0;
			FlowStatus f = null;
			//create several media Components
			Vector<MediaSubComponent> msc = new Vector<MediaSubComponent>();
			for (int j =0;j<MAX_SUBMEDIAS;j++)
			{
				MediaSubComponent media = MediaSubComponentTest.generateRandomMediaSubComponent(); 
				if(f == null)
					f = media.getFlowStatus();
				else
					media.setFlowStatus(f);
				msc.add(media);
				bUl = bUl + media.getMaxRequestedBandwidthUL().getUnsigned32();
				bDl = bDl + media.getMaxRequestedBandwidthDL().getUnsigned32();
			}
			//create several codecDatas
			Vector<CodecData> cdatas = new Vector<CodecData>();
			for(int j = 0;j< MAX_CODEC_DATAS;j++)
				cdatas.add(getRandomCodecData());
			//fill up the MediaSubCompoments
			Iterator<MediaSubComponent> mediaIT = msc.iterator();
			while(mediaIT.hasNext())
			{
				md.addMediaSubComponent(mediaIT.next());
			}
			//fill up the CodecData
			Iterator<CodecData> codecIT = cdatas.iterator();
			while(codecIT.hasNext())
			{
				md.addCodecData(codecIT.next());
			}
			//check the Bandwidth calculation
			long currBUL = md.getMaxRequestedBandwidthUL().getUnsigned32();
			assertEquals(bUl, currBUL);
			long currBDL = md.getMaxRequestedBandwidthDL().getUnsigned32();
			assertEquals(bDl, currBDL);
			//check if all medias are in there and deleting works fine
			mediaIT = msc.iterator();
			while(mediaIT.hasNext())
			{
				MediaSubComponent curr = mediaIT.next();
				for(Iterator<MediaSubComponent> it = md.getMediaSubComponentIterator();it.hasNext();)
				{
					MediaSubComponent mediaSub = it.next();
					if(mediaSub.equals(curr))
					{
						md.deleteChildAVP(mediaSub);
						curr=null;
					}
				}
				assertEquals(null,curr);
			}
			assertEquals(false,md.getMediaSubComponentIterator().hasNext());
			//check if all codec Datas are in there and deleting works fine
			codecIT = cdatas.iterator();
			while(codecIT.hasNext())
			{
				CodecData curr = codecIT.next();
				for(Iterator<CodecData> it = md.getCodecDataIterator();it.hasNext();)
				{
					CodecData codec = it.next();
					if(codec.equals(curr))
					{
						md.deleteChildAVP(codec);
						curr=null;
					}
				}
				assertEquals(null,curr);
			}
			assertEquals(false,md.getCodecDataIterator().hasNext());
		}
	}
	
	
}
