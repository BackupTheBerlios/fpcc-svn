/* 
* MediaType.java
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

import at.ac.tuwien.ibk.biqini.diameter.I3GPPConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Enumerated;

/**
 * The Media-Type AVP (AVP code 520) is of type Enumerated, and it determines 
 * the media type of a session component. The media types indicate the type of 
 * media in the same way as the SDP media types with the same names defined in 
 * RFC 4566 [13]. 
 * 
 * TS 29.214
 * 
 * @author mhappenhofer
 *
 */
public class MediaType extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE = 520;

	
	public static final int AUDIO = 0;
	public static final int VIDEO = 1;
	public static final int DATA  = 2;
	public static final int	APPLICATION = 3;
	public static final int CONTROL = 4;
	public static final int TEXT = 5;
	public static final int	MESSAGE = 6;
	public static final int	OTHER = 0xFFFFFFFF;

	
	
	public MediaType() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}
	

	public MediaType(int _mediaType) {
		super(AVP_CODE,true,VENDOR_ID);
		setInteger32(_mediaType);
		init();
	}

	public static String getMediaTypeName(MediaType _mediaType)	{
		switch(_mediaType.getEnumerated())	{
		case AUDIO:
			return "audio";
		case VIDEO:
			return "video";
		case DATA:
			return "data";
		case APPLICATION:
			return "application";
		case CONTROL:
			return "control";
		case TEXT:
			return "text";
		case MESSAGE:
			return "message";
		default:	
			return "other";
		}
	}
	private void init()	{
		mapping.put(0, "AUDIO");
		mapping.put(1, "VIDEO");
		mapping.put(2, "DATA");
		mapping.put(3, "APPLICATION");
		mapping.put(4, "CONTROL");
		mapping.put(5, "TEXT");
		mapping.put(6, "MESSAGE");
		mapping.put(0xFFFFFFFF, "OTHER");

	}
}
