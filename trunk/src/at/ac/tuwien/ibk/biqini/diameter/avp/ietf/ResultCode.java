/* 
* ResultCode.java
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
package at.ac.tuwien.ibk.biqini.diameter.avp.ietf;

import at.ac.tuwien.ibk.biqini.diameter.IIETFConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Unsigned32;

/**
 * The Result-Code AVP (AVP Code 268) is of type Unsigned32 and
 * indicates whether a particular request was completed successfully or
 * whether an error occurred.  All Diameter answer messages defined in
 * IETF applications MUST include one Result-Code AVP.  A non-successful
 * Result-Code AVP (one containing a non 2xxx value other than
 * DIAMETER_REDIRECT_INDICATION) MUST include the Error-Reporting-Host
 * AVP if the host setting the Result-Code AVP is different from the
 * identity encoded in the Origin-Host AVP.
 *
 * The Result-Code data field contains an IANA-managed 32-bit address
 * space representing errors (see Section 11.4).  Diameter provides the
 * following classes of errors, all identified by the thousands digit in
 * the decimal notation:
 *
 *    -  1xxx (Informational)
 *    -  2xxx (Success)
 *    -  3xxx (Protocol Errors)
 *    -  4xxx (Transient Failures)
 *    -  5xxx (Permanent Failure)
 *
 * A non-recognized class (one whose first digit is not defined in this
 * section) MUST be handled as a permanent failure.
 *
 * RFC 3588
 *
 * 
 * @author mhappenhofer
 *
 */
public class ResultCode extends Unsigned32 implements IIETFConstants{

	public static final int AVP_CODE = 268;
	
	public ResultCode() {
		super(AVP_CODE,true,VENDOR_ID);
	}

	/**
	 * @param unsigned32
	 */
	public ResultCode(long unsigned32) {
		super(AVP_CODE,true,VENDOR_ID);
		setUnsigned32(unsigned32);
	}
	
	public boolean isInformational()	{
		return this.getUnsigned32()>999 && this.getUnsigned32()<2000; 
	}
	public boolean isSuccess()	{
		return this.getUnsigned32()>1999 && this.getUnsigned32()<3000; 
	}
	public boolean isProtocolErrors()	{
		return this.getUnsigned32()>2999 && this.getUnsigned32()<4000; 
	}
	public boolean isTransientFailures()	{
		return this.getUnsigned32()>3999 && this.getUnsigned32()<5000; 
	}
	public boolean isPermanentFailure()	{
		return this.getUnsigned32()>4999 && this.getUnsigned32()<6000; 
	}
}
