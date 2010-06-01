/* 
* CodecData.java
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
package at.ac.tuwien.ibk.biqini.diameter.avp.threegpp;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.ac.tuwien.ibk.biqini.common.CodeBook;
import at.ac.tuwien.ibk.biqini.common.Codec;
import at.ac.tuwien.ibk.biqini.diameter.I3GPPConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.OctetString;

/**
 * The Codec-Data AVP (AVP code 524) is of type OctetString.
 * The Codec-Data AVP shall contain codec related information known at the AF. 
 * This information shall be encoded as follows:
 * 
 * -	The first line of the value of the Codec-Data AVP shall consist of 
 * 		either the word "uplink" or the word "downlink" (in ASCII, without quotes) 
 * 		followed by a new-line character. The semantics of these words are the 
 * 		following:
 * 		-	"uplink" indicates that the SDP was received from the UE and sent to 
 * 			the network.
 *		-	"downlink" indicates that the SDP was received from the network and 
 *			sent to the UE.
 * 		NOTE:	The first line indicates the direction of the source of the SDP 
 * 				used to derive the information. The majority of the information 
 * 				within the Codec-Data AVP indicating "downlink" describes 
 * 				properties, for	instance receiver capabilities, of the sender of 
 * 				the SDP, the network in this case and is therefore applicable for 
 * 				IP flows in the uplink direction. Similarly, the majority of the 
 * 				information within the Codec-Data AVP indicating "uplink" describes 
 * 				properties, for instance receiver capabilities, of the sender of 
 * 				the SDP, the UE in this case and is therefore applicable for IP 
 * 				flows in the downlink direction.
 * 
 * -	The second line of the value of the Codec-Data AVP shall consist of either 
 * 		the word "offer" or the word "answer", or the word "description" 
 * 		(in ASCII, without quotes) followed by a new-line character. The semantics 
 * 		of these words are the following:
 * 		-	"offer" indicates that SDP lines from an SDP offer according to 
 * 			RFC 3264 [18] are being provisioned in the Codec-Data AVP;
 * 		-	"answer" indicates that SDP lines from an SDP answer according to 
 * 			RFC 3264 [18] are being provisioned in the Codec-Data AVP;
 * 		-	"description" indicates that SDP lines from a SDP session description 
 * 			in a scenario where the offer-answer mechanism of RFC 3264 [18] is 
 * 			not being applied are being provisioned in the Codec-Data AVP. For 
 * 			instance, SDP from an RTSP "Describe" reply may be provisioned.
 * 
 * -	The rest of the value shall consist of SDP line(s) in ASCII encoding 
 * 		separated by new-line characters, as specified in IETF RFC 4566 [13]. 
 * 		The first of these line(s) shall be an "m" line. The remaining lines 
 * 		shall be any available SDP "a" and "b" lines related to that "m" line. 
 * 		However, to avoid duplication of information, the SDP "a=sendrecv", 
 * 		"a=recvonly ", "a=sendonly", "a=inactive", "b:AS", "b:RS" and "b:RR" 
 * 		lines do not need to be included.
 * 
 * 
 * TS 29.214
 * 
 * 
 * @author mhappenhofer
 *
 */
public class CodecData extends OctetString implements I3GPPConstants {

	public static final int AVP_CODE = 524;

	//private static Pattern CLINE = Pattern.compile("c=(\\S*) (\\S*) (\\S*)");
	//private static Pattern BLINE = Pattern.compile("b=(\\S*) (\\d*)");
	private static Pattern MLINE = Pattern.compile("m=(\\S*) (\\d*) (\\S*)( .*)");
	//a=rtpmap:96 L8/8000
	private static Pattern ARTPMAP = Pattern.compile("a=rtpmap:(\\d*) ((\\S*/\\S*/\\S*)|(\\S*/\\S*)|(\\S*))");
	
	public static final String AUDIO = "audio";
	public static final String VIDEO = "video";
	public static final String DATA = "data";
	public static final String APPLICATION = "application";

	
	/**
	 * Media type of this media e.g. audio, video, application or data
	 */
	private String type;
	/**
	 * receiving port for media
	 */
	private int port;
	/**
	 * kind of protocol
	 */
	private String protocol;
	/**
	 * supported codecs
	 */
	private Vector<Codec> codecs;
	
	private String offerAnswer = null;
	private String uplinkDownlink = null;
	
	private boolean compiled = false;
	
	public CodecData() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @param octetString
	 */
	public CodecData(String octetString) {
		super(AVP_CODE,true,VENDOR_ID);
		setOctetString(octetString);
	}
	//TODO use getMedia and getCodecs
	public Vector<Integer> getCodecs(){
		Vector<Integer> codecVector = new Vector<Integer>();
		String codecString = getOctetString();
		String[] lines = codecString.split("\n");
		for (int i = 0; i<lines.length;i++){
			lines[i] = lines[i].trim();
			if (lines[i].startsWith("m=")){
				String[] linetokens = lines[i].split(" ");
				for (int j = linetokens.length-1; j>=0;j--){
					try{
						int codec = Integer.parseInt(linetokens[j]);
						codecVector.add(codec);
					}
					catch(NumberFormatException e){
						j=-1;
					}
				}
			}
		}
		return codecVector;
	}
	
	public String getType() {
		return type;
	}
	public int getPort() {
		return port;
	}
	public String getProtocol() {
		return protocol;
	}
	
	public void parseCodecData() throws Exception	{
		if(compiled) return;
		String[] lines = getOctetString().split("\n");
		for(int i = 0;i<lines.length;i++)
			lines[i]=lines[i].trim();
		codecs = new Vector<Codec>();
		if(lines.length<3)
			throw new Exception("not enough information in Codec Data");
		int i = 2;
		try {
			// read uplink/Downlink offer/answer
			uplinkDownlink = lines[0];
			offerAnswer = lines[1];
			Matcher mMatcher = MLINE.matcher(lines[2]);
			if(mMatcher.matches())
			{
				/*for(int k = 0;k<=mMatcher.groupCount();k++)	{
					System.out.println(k+": "+mMatcher.group(k));
				}*/
				type = mMatcher.group(1);
				port = Integer.parseInt(mMatcher.group(2));
				protocol = mMatcher.group(3);
				String[] cod = mMatcher.group(4).trim().split(" ");
				Vector<String> codecVector = new Vector<String>();
				for(int j = 0;j<cod.length;j++){
					int no = Integer.parseInt(cod[j]);
					if( no>=0 && no <35)	{
						Codec c = CodeBook.getCodecByPT(no);
						if(c!= null)
							codecs.add(c);
						else
							throw new Exception("COuld not reslove Codec for "+no);
					} else
						codecVector.add(cod[j]);
				}
				for(i=3;i<lines.length;i++){
					if(lines[i].startsWith("b="))	{
						// ignore b lines
					}
					else if(lines[i].startsWith("a=")){
						//attr.add(lines[i]);
						Matcher artp = ARTPMAP.matcher(lines[i]);
						if(artp.matches())
						{
							String artpNo = artp.group(1);
							String artpSpec = artp.group(2);
							String[] specs = artpSpec.split("/");
							String name = specs[0];
							int clockrate = -1;
							if(specs.length>1)
								clockrate = Integer.parseInt(specs[1]);
							Codec c = CodeBook.getCodecByRTPMAP(name,type, clockrate);
							//System.out.println(c.getName()+" "+c.getBandwidth());
							if(codecVector.contains(artpNo))	{
								codecs.add(c);
								codecVector.remove(artpNo);
							}else	{
								int id = Integer.parseInt(artpNo);
								if(id>34)
									throw new Exception("missing RTPMAP!");
							}
						}
					}
					else
						throw new Exception("Invalid lines in codec Data "+lines[i]);
					
				}
				if(codecVector.size()!=0)
					throw new Exception("not all codecs have a rtpmap!");
			}
			else
				throw new Exception("Could not parse "+lines[i]);
			
		} catch (NumberFormatException e) {
			throw new Exception("Could not convert "+lines[i]);
		}
		compiled=true;
	}
	
	
	public boolean isOffer()	{
		return offerAnswer.compareTo("offer")==0;
	}
	
	public boolean isAnswer()	{
		return offerAnswer.compareTo("answer")==0;
	}
	
	public boolean isUplink()	{
		return uplinkDownlink.compareTo("uplink")==0;
	}
	
	public boolean isDownlink()	{
		return uplinkDownlink.compareTo("downlink")==0;
	}
	
	public Vector<Codec> getMediaCodecs(){
		return codecs;
	}

	

}
