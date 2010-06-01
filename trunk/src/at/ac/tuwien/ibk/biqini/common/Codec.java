/* 
* Codec.java
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
package at.ac.tuwien.ibk.biqini.common;

import org.xml.sax.Attributes;

public class Codec {
	
	public static double DEFAUL_CODEC_SAMPLE_INTERVAL = 0.02;
	public static int DEFAUL_IP_UDP_RTCP_OFFSET = 40*8;
	public static int DEFAULT_SAMPLE_RESOLUTION = 8;
	
	/**
	 * name of the codec
	 */
	private String name = null;
	/**
	 * media Type for this codec
	 */
	private String type = null;
	/**
	 * payloadType according to http://www.iana.org/assignments/rtp-parameters
	 */
	private int pt=-1;
	/**
	 * clockrate 
	 */
	private int clockrate=-1;
	/**
	 * required bandwidth
	 */
	private int bandwidth = -1;
	/**
	 * bandwidth was estimated
	 */
	private int bandwithSource;
	
	public static final int BANDWIDTH_FROM_CODEBOOK = 0;
	public static final int BANDWIDTH_ESTIMATED_BY_CLOCKRATE = 1;
	public static final int BANDWIDTH_ESTIMATED_BY_MEDIA = 2;
	public static final int NO_BANDWIDTH_INFORMATION = 3;
	
	public Codec(Attributes attributes)	{
		for(int i = 0;i<attributes.getLength();i++)	{
			try {
				String attributeName = attributes.getQName(i);
				String attributeValue = attributes.getValue(i);
				if(attributeName.equals("encodingName"))
					name=attributeValue;
				else if(attributeName.equals("payloadType"))
					pt=Integer.parseInt(attributeValue);
				else if(attributeName.equals("type"))
					type = attributeValue;
				else if(attributeName.equals("clockrate"))
					clockrate=Integer.parseInt(attributeValue);
				else if(attributeName.equals("bandwidth"))
					bandwidth = Integer.parseInt(attributeValue);
			} catch (NumberFormatException e) {
				// Could not parse
			}
		}
		if(name==null)
			name="unknown";
		if(bandwidth==-1)
			bandwithSource = NO_BANDWIDTH_INFORMATION;
	}
	/**
	 * @param name
	 * @param type
	 * @param pt
	 * @param clockrate
	 * @param bandwidth
	 */
	public Codec(String name, String type, int pt, int clockrate, int bandwidth) {
		super();
		this.name = name;
		this.type = type;
		this.pt = pt;
		this.clockrate = clockrate;
		this.bandwidth = bandwidth;
		bandwithSource= BANDWIDTH_FROM_CODEBOOK;
	}
	
	/**
	 * @param name
	 * @param type
	 * @param clockrate
	 */
	public Codec(String name, String type, int clockrate) {
		super();
		this.name = name;
		this.type = type;
		this.clockrate = clockrate;
		bandwithSource=NO_BANDWIDTH_INFORMATION;
	}
	
	public void estimateBandwidth()	{
		/*if(clockrate!=-1)	{
			this.bandwidth = (int)((clockrate*DEFAULT_SAMPLE_RESOLUTION*DEFAUL_CODEC_SAMPLE_INTERVAL+DEFAUL_IP_UDP_RTCP_OFFSET)/DEFAUL_CODEC_SAMPLE_INTERVAL);
			if(type.equals("video"))	{
				this.bandwidth=this.bandwidth*4;
			}
			bandwithSource=BANDWIDTH_ESTIMATED_BY_CLOCKRATE;
		}else {*/
			if(type.equals("audio"))
				bandwidth = 64000;
			else if(type.equals("video"))
				bandwidth = 128000;
			else 
				bandwidth = 64000;
			bandwithSource= BANDWIDTH_ESTIMATED_BY_MEDIA;
		//}
			
	}

	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public int getClockrate() {
		return clockrate;
	}
	public int getBandwidth() {
		return bandwidth;
	}
	public int getSourceOfBandwidth() {
		return bandwithSource;
	}
	public int getPayloadType() {
		return pt;
	}
	
	public boolean equals(Object other) {
		if (other instanceof Codec) {
			Codec otherCodec = (Codec) other;
			if(!this.name.equals(otherCodec.name))
				return false;
			if(!this.type.equals(otherCodec.type))
				return false;
			if(this.clockrate!=-1 && otherCodec.clockrate!=-1 && this.clockrate!= otherCodec.clockrate)
				return false;
			
			return true;
		}
		return false;
	}
	
}
