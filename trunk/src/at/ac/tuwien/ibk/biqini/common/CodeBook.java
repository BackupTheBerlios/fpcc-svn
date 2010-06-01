/* 
* CodeBook.java
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

import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class CodeBook extends DefaultHandler {

	public static String DEFAULT_CODEBOOK = "./cfg/codebook.xml";
	private static CodeBook instance = null;

	private Hashtable<Integer, Codec> rtpMap;
	
	private Hashtable<String,Vector<Codec>> codecmap;
	
	private CodeBook() {
		rtpMap = new Hashtable<Integer, Codec>();
		codecmap = new Hashtable<String, Vector<Codec>>();
	}

	public static void init(String filename) throws SAXException, IOException {
		instance = new CodeBook();
		XMLReader xr = XMLReaderFactory.createXMLReader();
		xr.setContentHandler(instance);
		xr.setErrorHandler(instance);
		FileReader r = new FileReader(filename);
		xr.parse(new InputSource(r));

	}
	public static CodeBook getInstance()	{
		if(instance==null){
			try {
				init(DEFAULT_CODEBOOK);
			} catch (SAXException e) {
				System.err.println("Could not initiate CodeBook(SAXException)! "+e.getMessage());
			} catch (IOException e) {
				System.err.println("Could not initiate CodeBook(IOException)! "+e.getMessage());
			} 
		}
		return instance;
	}
	
	public Codec getCodecByPayloadType(int payloadType) {
		return rtpMap.get(payloadType);
		
	}
	
	public static Codec getCodecByPT(int payloadType) {
		if(payloadType>-1 && payloadType<35)	{
			getInstance();
			return instance.getCodecByPayloadType(payloadType);	
		}
		return null;
	}
	
	public static Codec getCodecByRTPMAP(String name, String mediatype, int clockrate) {
		getInstance();
		if(instance.codecmap.containsKey(mediatype))	{
			Vector<Codec> v = instance.codecmap.get(mediatype);
			Codec temp = new Codec(name,mediatype, clockrate);
			int pos = v.indexOf(temp);
			if(pos>=0)
				return v.get(pos);
		}
		// create the codec
		return new Codec(name,mediatype,clockrate);
	}
	
	/*
	public static Codec getCodecByRTPMAP(String name, int clockrate, int channels) {
		return null;
	}
	*/
	public void startElement(String uri, String name, String qName,
			Attributes atts) {
		if (qName.equals("codec"))	{
			Codec newCodec = new Codec(atts);
			if(newCodec.getPayloadType()>=0)	{
				rtpMap.put(newCodec.getPayloadType(), newCodec);
			}
			Vector<Codec> actual = null;
			if(!codecmap.containsKey(newCodec.getType()))	{
				actual = new Vector<Codec>();
				codecmap.put(newCodec.getType(), actual);
			}else
				actual=codecmap.get(newCodec.getType());
			actual.add(newCodec);
		}
	}

}
