/* 
* Unsigned32.java
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
* fpcc@ftw.at ÊÊ
*
* BIQINI is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. ÊSee the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License 
* along with this program; if not, write to the Free Software 
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA Ê02111-1307 ÊUSA
*/
package at.ac.tuwien.ibk.biqini.diameter.avp.base;

import de.fhg.fokus.diameter.DiameterPeer.data.AVP;

/**
 * @author mhappenhofer
 *
 */
public abstract class Unsigned32 extends AVP {
	
	public Unsigned32() {
		super();
	}
	
	public Unsigned32(int _Code, boolean _Mandatory, int _VendorId) {
		super(_Code, _Mandatory, _VendorId);
	}
	

	public void setUnsigned32(long _Unsigned32)	{
		data = new byte[4];
		data[0] = (byte)((_Unsigned32>>24) &0xFF);
		data[1] = (byte)((_Unsigned32>>16) &0xFF);
		data[2] = (byte)((_Unsigned32>> 8) &0xFF);
		data[3] = (byte)((_Unsigned32    ) &0xFF);
		//unsignedData = _Unsigned32;
	}
		
	public long getUnsigned32()	{
		long unsignedData = 0;
		for(int i=0;i<4&&i<data.length;i++)
			unsignedData = (unsignedData<<8)|(0xFF & data[i]);
		return unsignedData;
	}
	
	/* (non-Javadoc)
	 * @see de.fhg.fokus.diameter.DiameterPeer.data.AVP#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append(commonInfo());
		sbf.append(" Unsigned32="+this.getUnsigned32());
		return sbf.toString();
	}

}
