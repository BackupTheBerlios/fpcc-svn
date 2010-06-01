/* 
* ReportingLevel.java
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
package at.ac.tuwien.ibk.biqini.diameter.avp.threegpp;

import at.ac.tuwien.ibk.biqini.diameter.I3GPPConstants;
import at.ac.tuwien.ibk.biqini.diameter.avp.base.Enumerated;

/**
 * The Reporting-Level AVP (AVP code 1011) is of type Enumerated, and 
 * it defines on what level the PCEF reports the usage for the related 
 * PCC rule. The following values are defined:
 * If the Reporting-Level AVP is omitted but has been supplied 
 * previously, the previous information remains valid. If the 
 * Reporting-Level AVP is omitted and has not been supplied previously, 
 * the reporting level pre-configured at the PCEF is applicable as 
 * default reporting level.
 * 
 * TS 29.212
 * 
 * @author mhappenhofer
 *
 */
public class ReportingLevel extends Enumerated implements I3GPPConstants {

	public static final int AVP_CODE = 1011;
	
	/**
	 * This value shall be used to indicate that the usage shall be 
	 * reported on service id and rating group combination level.
	 */
	public static final int SERVICE_IDENTIFIER_LEVEL = 0;
	/**
	 * This value shall be used to indicate that the usage shall be 
	 * reported on rating group level.
	 */
	public static final int RATING_GROUP_LEVEL = 1;

	public ReportingLevel() {
		super(AVP_CODE,true,VENDOR_ID);
		init();
	}

	/**
	 * @param enumerated
	 */
	public ReportingLevel(int enumerated) {
		super(AVP_CODE,true,VENDOR_ID);
		setEnumerated(enumerated);
		init();
	}
	
	private void init()	{
		mapping.put(0,"SERVICE_IDENTIFIER_LEVEL");
		mapping.put(1,"RATING_GROUP_LEVEL");
	}

}
