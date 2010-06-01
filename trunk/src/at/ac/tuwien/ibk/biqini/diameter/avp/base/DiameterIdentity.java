/* 
* DiameterIdentity.java
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
 * The DiameterIdentity format is derived from the OctetString AVP
 * Base Format.
 *
 *        DiameterIdentity  = FQDN
 *
 * DiameterIdentity value is used to uniquely identify a Diameter
 * node for purposes of duplicate connection and routing loop
 * detection.
 *
 *    The contents of the string MUST be the FQDN of the Diameter node.
 *    If multiple Diameter nodes run on the same host, each Diameter
 *    node MUST be assigned a unique DiameterIdentity.  If a Diameter
 *    node can be identified by several FQDNs, a single FQDN should be
 *    picked at startup, and used as the only DiameterIdentity for that
 *    node, whatever the connection it is sent on.
 *
 * RFC 3588
 *
 * @author mhappenhofer
 *
 */
public class DiameterIdentity extends OctetString {

	/**
	 * 
	 */
	public DiameterIdentity() {
	}

	/**
	 * @param Mandatory
	 * @param Vendor_id
	 */
	public DiameterIdentity(int Code, boolean Mandatory, int Vendor_id) {
		super(Code, Mandatory, Vendor_id);
	}

	/**
	 * @param octetString
	 */
	public DiameterIdentity(String octetString) {
		super();
		this.setOctetString(octetString);
	}
	
	/* (non-Javadoc)
	 * @see de.fhg.fokus.diameter.DiameterPeer.data.AVP#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append(commonInfo());
		sbf.append(" DiameterIdentity="+this.getOctetString());
		return sbf.toString();
	}
}
