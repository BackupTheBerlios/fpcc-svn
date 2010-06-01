/* 
* Media.java
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
package at.ac.tuwien.ibk.biqini.af;

public class Media {
	public static final int AUDIO = 1; 
	public static final int VIDEO = 2;
	
	public static final int INACTIVE = 0;
	public static final int ACTIVE = 1;
	
	public int type;
	public int recvPort;
	public int[] codec;
	public int bandwidth;
	public int status;
	
	/**
	 * @param type
	 * @param recvPort
	 * @param codec
	 * @param bandwidth
	 * @param status
	 */
	public Media(int type, int recvPort, int[] codec, int bandwidth,
			int status) {
		super();
		this.type = type;
		this.recvPort = recvPort;
		this.codec = codec;
		this.bandwidth = bandwidth;
		this.status = status;
	}

	public String toString()	{
		StringBuffer sbf = new StringBuffer();
		if(type == AUDIO)
			sbf.append("m=audio "+recvPort+" RTP/AVP ");
		if(type == VIDEO)
			sbf.append("m=video "+recvPort+" RTP/AVP ");
		for(int i = 0;i<codec.length;i++)
		{
			sbf.append(codec[i]);
			if(i<codec.length-1)
				sbf.append(" ");
			else
				sbf.append("\n");
		}
		sbf.append("b=AS:"+(bandwidth/1000)+"\n");
		if(status==INACTIVE)
			sbf.append("a=inactive\n");
		return sbf.toString();
	}
}
