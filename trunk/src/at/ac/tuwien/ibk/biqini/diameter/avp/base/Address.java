/* 
* Address.java
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

/**
 * @author mhappenhofer
 *
 */
public class Address extends OctetString {

	/**
	 * @param Code
	 * @param Vendor_Specific
	 * @param Mandatory
	 * @param Protected
	 * @param Vendor_id
	 */
/*	public Address(int Code, boolean Vendor_Specific, boolean Mandatory,
			boolean Protected, int Vendor_id) {
		super(Code, Vendor_Specific, Mandatory, Protected, Vendor_id);
	}*/

	public Address() {
		super();
	}

	/**
	 * @param Code
	 * @param Mandatory
	 * @param Vendor_id
	 */
	public Address(int Code, boolean Mandatory, int Vendor_id) {
		super(Code, Mandatory, Vendor_id);
	}
	
	public void setAddress(String _Address)	{
		this.setOctetString(_Address);
	}
	
	public String getAddress() {
		return this.getOctetString();
	}
	
	/* (non-Javadoc)
	 * @see de.fhg.fokus.diameter.DiameterPeer.data.AVP#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append(commonInfo());
		sbf.append(" Address="+this.getOctetString());
		return sbf.toString();
	}

}
