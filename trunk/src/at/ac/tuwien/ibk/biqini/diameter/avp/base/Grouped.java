/* 
* Grouped.java
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

import de.fhg.fokus.diameter.DiameterPeer.data.AVP;
import de.fhg.fokus.diameter.DiameterPeer.data.AVPDecodeException;

/**
 * @author mhappenhofer
 *
 */
public class Grouped extends AVP {

	public Grouped() {
	}
	
	public Grouped(int Code, boolean Mandatory, int Vendor_id) {
		super(Code, Mandatory, Vendor_id);
	}
	
	/* (non-Javadoc)
	 * @see de.fhg.fokus.diameter.DiameterPeer.data.AVP#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append(commonInfo());
		try {
			ungroup();
		} catch (AVPDecodeException e) {
			// error while ungrouping
			e.printStackTrace();
		}
		int size = this.getChildCount();
		sbf.append(" Grouped \n");
		//sbf.append("\t===================================="+AVPFactory.queryAVPName(code)+"_Beginn\n");
		for(int i = 0;i<size;i++)
		{
			String b = this.getChildAVP(i).toString();
			if(b.indexOf("\n")<0)
				b="\t"+b+"\n";
			else	{
				b = "\t"+b.replace("\n", "\n\t");
				b=b.substring(0,b.length()-1);
			}
			sbf.append("\t"+b);
		}
		//sbf.append("\t===================================="+AVPFactory.queryAVPName(code)+"_Ende\n");
		//if(sbf.lastIndexOf("\n")==sbf.length()-1)
		//	sbf.deleteCharAt(sbf.length()-1);
		return sbf.toString();
	}

}
