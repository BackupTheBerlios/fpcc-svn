/* 
* ServiceContextId.java
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
package at.ac.tuwien.ibk.biqini.diameter.avp.ietf;

import at.ac.tuwien.ibk.biqini.diameter.IIETFConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.UTF8String;

/**
 * The Service-Context-Id AVP is of type UTF8String (AVP Code 461) and
 * contains a unique identifier of the Diameter credit-control service
 * specific document that applies to the request (as defined in section
 * 4.1.2).  This is an identifier allocated by the service provider, by
 * the service element manufacturer, or by a standardization body, and
 * MUST uniquely identify a given Diameter credit-control service
 * specific document.  The format of the Service-Context-Id is:
 *
 * "service-context" "@" "domain"
 *
 * service-context = Token
 *
 * The Token is an arbitrary string of characters and digits.
 *
 * 'domain' represents the entity that allocated the Service-Context-Id.
 * It can be ietf.org, 3gpp.org, etc., if the identifier is allocated by
 * a standardization body, or it can be the FQDN of the service provider
 * (e.g., provider.example.com) or of the vendor (e.g.,
 * vendor.example.com) if the identifier is allocated by a private
 * entity.
 *
 * This AVP SHOULD be placed as close to the Diameter header as
 * possible.
 *
 * Service-specific documents that are for private use only (i.e., to
 * one provider's own use, where no interoperability is deemed useful)
 * may define private identifiers without need of coordination.
 * However, when interoperability is wanted, coordination of the
 * identifiers via, for example, publication of an informational RFC is
 * RECOMMENDED in order to make Service-Context-Id globally available.
 *
 * RFC4006
 * 
 * @author mhappenhofer
 *
 */
public class ServiceContextId extends UTF8String implements IIETFConstants{

	public static final int AVP_CODE = 461;

	public ServiceContextId() {
		super(AVP_CODE,true,VENDOR_ID);
	}
	
	/**
	 * @param _serviceContextId
	 */
	public ServiceContextId(String _serviceContextId) {
		super(AVP_CODE,true,VENDOR_ID);
		this.setUTF8String(_serviceContextId);
	}

}
