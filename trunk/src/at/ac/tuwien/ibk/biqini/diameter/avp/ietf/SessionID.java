/* 
* SessionID.java
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

import java.util.Calendar;

import at.ac.tuwien.ibk.biqini.diameter.IIETFConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.DiameterIdentity;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.UTF8String;

/**
 * The Session-Id AVP (AVP Code 263) is of type UTF8String and is used
 * to identify a specific session (see Section 8).  All messages
 * pertaining to a specific session MUST include only one Session-Id AVP
 * and the same value MUST be used throughout the life of a session.
 * When present, the Session-Id SHOULD appear immediately following the
 * Diameter Header (see Section 3).
 * The Session-Id MUST be globally and eternally unique, as it is meant
 * to uniquely identify a user session without reference to any other
 * information, and may be needed to correlate historical authentication
 * information with accounting information.  The Session-Id includes a
 * mandatory portion and an implementation-defined portion; a
 * recommended format for the implementation-defined portion is outlined
 * below.
 *
 * The Session-Id MUST begin with the sender's identity encoded in the
 * DiameterIdentity type (see Section 4.4).  The remainder of the
 * Session-Id is delimited by a ";" character, and MAY be any sequence
 * that the client can guarantee to be eternally unique; however, the
 * following format is recommended, (square brackets [] indicate an
 * optional element).
 * 
 * <DiameterIdentity>;<high 32 bits>;<low 32 bits>[;<optional value>]
 *
 * <high 32 bits> and <low 32 bits> are decimal representations of the
 * high and low 32 bits of a monotonically increasing 64-bit value.  The
 * 64-bit value is rendered in two part to simplify formatting by 32-bit
 * processors.  At startup, the high 32 bits of the 64-bit value MAY be
 * initialized to the time, and the low 32 bits MAY be initialized to
 * zero.  This will for practical purposes eliminate the possibility of
 * overlapping Session-Ids after a reboot, assuming the reboot process
 * takes longer than a second.  Alternatively, an implementation MAY
 * keep track of the increasing value in non-volatile memory.
 *
 * <optional value> is implementation specific but may include a modem's
 * device Id, a layer 2 address, timestamp, etc.
 *
 * Example, in which there is no optional value:
 *    accesspoint7.acme.com;1876543210;523
 *
 * Example, in which there is an optional value:
 *    accesspoint7.acme.com;1876543210;523;mobile@200.1.1.88
 *
 * The Session-Id is created by the Diameter application initiating the
 * session, which in most cases is done by the client.  Note that a
 * Session-Id MAY be used for both the authorization and accounting
 * commands of a given application.
 *
 * RFC3588
 *
 * @author mhappenhofer
 *
 */
public class SessionID extends UTF8String implements IIETFConstants{

	public static final int AVP_CODE = 263;

	public static int HIGH32 = 0;
	public static int LOW32 = 0;
	
	static{
		Long l = Calendar.getInstance().getTimeInMillis();
		HIGH32 = l.intValue();
	}
	
	public SessionID() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @param string
	 */
	public SessionID(String string) {
		super(AVP_CODE,true,VENDOR_ID);
		setUTF8String(string);
	}
	
	/**
	 * @param string
	 */
	public SessionID(DiameterIdentity _diameterIdentity,String identifier) {
		super(AVP_CODE,true,VENDOR_ID);
		setOctetString(_diameterIdentity.getOctetString()+";"+String.valueOf(HIGH32)+";"+String.valueOf(LOW32++)+";"+identifier);
	}

}
