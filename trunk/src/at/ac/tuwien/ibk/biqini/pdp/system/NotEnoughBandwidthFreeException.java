/* 
* NotEnoughBandwidthFreeException.java
* Christoph Egger
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
package at.ac.tuwien.ibk.biqini.pdp.system;

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.AcceptableServiceInfo;

/**
 * @author Christoph Egger
 *
 */
public class NotEnoughBandwidthFreeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AcceptableServiceInfo acceptableServiceInfo;

	/**
	 * 
	 */
	public NotEnoughBandwidthFreeException() {
	}

	/**
	 * @param message
	 */
	public NotEnoughBandwidthFreeException(String message) {
		super(message);
	}
	
	/**
	 * @param message
	 */
	public NotEnoughBandwidthFreeException(String message, AcceptableServiceInfo _acceptableServiceInfo) {
		super(message);
		acceptableServiceInfo = _acceptableServiceInfo;
	}

	/**
	 * @param cause
	 */
	public NotEnoughBandwidthFreeException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NotEnoughBandwidthFreeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AcceptableServiceInfo getAcceptableServiceInfo(){
		return acceptableServiceInfo;
	}

}
