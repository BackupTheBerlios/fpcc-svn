/* 
* SSDP.java
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

import java.util.Calendar;
import java.util.Random;

public class SSDP {
	
	private static Random RANDOMIZER;
	static{
		RANDOMIZER = new Random(Calendar.getInstance().getTimeInMillis());
	}
	
	
	/**
	 * @param ipAddress
	 * @param id
	 */
	public SSDP(String ipAddress, long id) {
		super();
		this.ipAddress = ipAddress;
		this.id = id;
	}

	/**
	 * @param ipAddress
	 */
	public SSDP(String ipAddress) {
		super();
		this.ipAddress = ipAddress;
		this.id = Math.abs(RANDOMIZER.nextLong());
	}
	
	public String ipAddress=null;
	public Media audio=null;
	public Media video=null;
	public long id;
	
	public String toString()	{
		StringBuffer sbf = new StringBuffer();
		sbf.append("v=0\n");
		sbf.append("o=- "+id+" "+id+" IN IP4 "+ipAddress+"\n");
		sbf.append("s=-\n");
		sbf.append("c=IN IP4 "+ipAddress+"\n");
		if(audio!=null)
			sbf.append(audio);
		if(video!=null)
			sbf.append(video);
		return sbf.toString();
	}

	

	
}
