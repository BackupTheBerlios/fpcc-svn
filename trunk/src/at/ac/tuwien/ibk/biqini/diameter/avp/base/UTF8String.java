/* 
* UTF8String.java
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
package at.ac.tuwien.ibk.biqini.diameter.avp.base;

import java.io.UnsupportedEncodingException;

/**
 * @author mhappenhofer
 *
 */
public class UTF8String extends OctetString {

	public UTF8String() {
		super();
	}
	/**
	 * @param Code
	 * @param Mandatory
	 * @param Vendor_id
	 */
	public UTF8String(int Code, boolean Mandatory, int Vendor_id) {
		super(Code, Mandatory, Vendor_id);
	}

	public void setUTF8String(String _UTF8String) 	{
		try {
			this.setData(_UTF8String.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	public String getUTF8String() 	{
		try {
			return new String(data,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see de.fhg.fokus.diameter.DiameterPeer.data.AVP#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append(commonInfo());
		sbf.append(" UTF8String="+this.getUTF8String());
		return sbf.toString();
	}
}
