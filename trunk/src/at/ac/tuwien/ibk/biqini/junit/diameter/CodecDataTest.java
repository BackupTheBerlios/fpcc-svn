/* 
* CodecDataTest.java
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

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import org.junit.Test;

import at.ac.tuwien.ibk.biqini.common.Codec;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.CodecData;


public class CodecDataTest extends AVPJunit{
	

	
	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingInformation#setPrimaryChargingCollectionFunctionName(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.CodecData)}.
	 */
	@Test
	public final void testParseCodecData1()	{
		String sdp1="downlink\nanswer\nm=audio 49170 RTP/AVP 0\n";
					
		CodecData c = new CodecData(sdp1);
		try {
			c.parseCodecData();
		} catch (Exception e) {
			assertEquals("Could not parse Codec", null, e);
		}
		assertEquals("not the same MediaType", CodecData.AUDIO, c.getType());
		assertEquals("not the same direction", true, c.isDownlink());
		assertEquals("not the same offer/answer",true, c.isAnswer());
		assertEquals(49170, c.getPort());
		assertEquals("RTP/AVP", c.getProtocol());
		Iterator<Codec> codecIt = c.getMediaCodecs().iterator();
		while(codecIt.hasNext())	{
			assertEquals("PCMU", codecIt.next().getName());
		}
	}
	
	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingInformation#setPrimaryChargingCollectionFunctionName(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.CodecData)}.
	 */
	@Test
	public final void testParseCodecData2()	{
		String sdp1="uplink\noffer\nm=video 555 RTP/AVP 98\na=rtpmap:98 H263";
					
		CodecData c = new CodecData(sdp1);
		try {
			c.parseCodecData();
		} catch (Exception e) {
			assertEquals("Could not parse Codec", null, e);
		}
		assertEquals("not the same MediaType", CodecData.VIDEO, c.getType());
		assertEquals("not the same direction", false, c.isDownlink());
		assertEquals("not the same offer/answer",false, c.isAnswer());
		assertEquals(555, c.getPort());
		assertEquals("RTP/AVP", c.getProtocol());
		Iterator<Codec> codecIt = c.getMediaCodecs().iterator();
		while(codecIt.hasNext())	{
			assertEquals("H263", codecIt.next().getName());
		}
		
	}
	
	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingInformation#setPrimaryChargingCollectionFunctionName(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.CodecData)}.
	 */
	@Test
	public final void testParseCodecData3()	{
		String sdp1="downlink\nanswer\nm=audio 49170 RTP/AVP 0 3 98\na=rtpmap:98 AMR/8000";
					
		CodecData c = new CodecData(sdp1);
		try {
			c.parseCodecData();
		} catch (Exception e) {
			assertEquals("Could not parse Codec", null, e);
		}
		assertEquals("not the same MediaType", CodecData.AUDIO, c.getType());
		assertEquals("not the same direction", true, c.isDownlink());
		assertEquals("not the same offer/answer",true, c.isAnswer());
		assertEquals(49170, c.getPort());
		assertEquals("RTP/AVP", c.getProtocol());
		Vector<Codec> configuredCodecs = c.getMediaCodecs();
		Codec[] codecs = {new Codec("PCMU",CodecData.AUDIO,8000),
				new Codec("GSM",CodecData.AUDIO,8000),
				new Codec("AMR",CodecData.AUDIO,8000)};
		for(int i = 0;i<codecs.length;i++){
			assertEquals("Media does not contain codec "+codecs[i],true, configuredCodecs.contains(codecs[i]));
		}
	}
	
	
	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingInformation#setPrimaryChargingCollectionFunctionName(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.CodecData)}.
	 */
	@Test
	public final void testParseCodecData4()	{
		String sdp1="downlink\n"+
					"answer\n"+
					"m=audio 49230 RTP/AVP 96 97 98\n"+
					"a=rtpmap:96 G726-16/8000\n"+
					"a=rtpmap:97 G726-24/8000\n"+
					"a=rtpmap:98 PCMU/8000";
					
		CodecData c = new CodecData(sdp1);
		try {
			c.parseCodecData();
		} catch (Exception e) {
			assertEquals("Could not parse Codec", null, e);
		}
		assertEquals("not the same MediaType", CodecData.AUDIO, c.getType());
		assertEquals("not the same direction", true, c.isDownlink());
		assertEquals("not the same offer/answer",true, c.isAnswer());
		assertEquals(49230, c.getPort());
		assertEquals("RTP/AVP", c.getProtocol());
		Vector<Codec> configuredCodecs = c.getMediaCodecs();
		Codec[] codecs = {new Codec("G726-16",CodecData.AUDIO,8000),
				new Codec("G726-24",CodecData.AUDIO,8000),
				new Codec("PCMU",CodecData.AUDIO,8000)};
		for(int i = 0;i<codecs.length;i++){
			assertEquals("Media does not contain codec "+codecs[i],true, configuredCodecs.contains(codecs[i]));
		}
	}
	
	
	/**
	 * Test method for {@link at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingInformation#setPrimaryChargingCollectionFunctionName(at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.CodecData)}.
	 */
	@Test
	public final void testParseCodecDataXLite()	{
		File file = new File("/home/ibk/AF");
		FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    DataInputStream dis = null;
	    StringBuffer sbf =  new StringBuffer();
	    try {
	      fis = new FileInputStream(file);
	      bis = new BufferedInputStream(fis);
	      dis = new DataInputStream(bis);

	      while (dis.available() != 0) {

	    	  sbf.append((char)dis.read());
	      }
	      fis.close();
	      bis.close();
	      dis.close();

	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		
		System.out.println(sbf.toString());			
		CodecData c = new CodecData(sbf.toString());
		try {
			c.parseCodecData();
		} catch (Exception e) {
			assertEquals("Could not parse Codec", null, e);
		}
		assertEquals("not the same MediaType", CodecData.AUDIO, c.getType());
		assertEquals("not the same direction", true, c.isUplink());
		assertEquals("not the same offer/answer",true, c.isOffer());
		assertEquals(15998, c.getPort());
		assertEquals("RTP/AVP", c.getProtocol());
		Vector<Codec> configuredCodecs = c.getMediaCodecs();
		Codec[] codecs = {new Codec("BV32",CodecData.AUDIO,16000),
				new Codec("telephone-event",CodecData.AUDIO,8000),
				new Codec("PCMU",CodecData.AUDIO,8000),
				new Codec("PCMA",CodecData.AUDIO,8000)};
		for(int i = 0;i<codecs.length;i++){
			assertEquals("Media does not contain codec "+codecs[i],true, configuredCodecs.contains(codecs[i]));
		}
	}
}
