/* 
* Enumerated.java
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

import java.util.Hashtable;

/**
 * @author mhappenhofer
 *
 */
public class Enumerated extends Integer32 {

	protected Hashtable<Integer,String> mapping ;
	
	public Enumerated(){
		mapping = new Hashtable<Integer, String>();
	}

	/**
	 * @param Code
	 * @param Mandatory
	 * @param Vendor_id
	 */
	public Enumerated(int Code, boolean Mandatory, int Vendor_id) {
		super(Code, Mandatory, Vendor_id);
		mapping = new Hashtable<Integer, String>();
	}

	public int getEnumerated(){
		return this.int_data;
	}
	
	public void setEnumerated(int _Enumerated){
		this.setData(_Enumerated);
	}
	
	/* (non-Javadoc)
	 * @see de.fhg.fokus.diameter.DiameterPeer.data.AVP#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append(commonInfo());
		if(mapping.containsKey(this.getEnumerated()))
			sbf.append(" Enumerated="+mapping.get(this.getEnumerated())+"("+this.getEnumerated()+")");
		else
			sbf.append(" Enumerated= Unknown ("+this.getEnumerated()+")");
		return sbf.toString();
	}
	
	
}
