/* 
* DiameterAnswer.java
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
package at.ac.tuwien.ibk.biqini.diameter.messages;

import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.AuthApplicationId;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ErrorMessage;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ErrorReportingHost;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.FailedAVP;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.ResultCode;
import de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage;

/**
 * @author Christoph Egger
 *
 */
public class DiameterAnswer extends DiameterMessage implements IIETFResponseCodes{

	public static final boolean REQUEST_FLAG = false; 
	
	public DiameterAnswer(int _CommandCode, int _ApplicationId) {
		super(_CommandCode, false, _ApplicationId);
	}
	
	public DiameterAnswer(DiameterMessage _msg){
		super(_msg.commandCode, _msg.flagRequest, _msg.flagProxiable, _msg.applicationID, _msg.hopByHopID, _msg.endToEndID);
		this.avps = _msg.avps;
	}
	
	/**
	 * Searches for the ResultCode AVP inside a message
	 * @return the found ResultCode AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public ResultCode getResultCode() {
		return (ResultCode)findAVP(ResultCode.AVP_CODE);
	}
	/**
	 * sets the ResultCode and overrides a existing one
	 * @param _resultCode	the new ResultCode
	 * 
	 * @author mhappenhofer
	 */
	public void setResultCode(ResultCode _resultCode)	{
		this.setSingleAVP( _resultCode);
	}
	
	/**
	 * Searches for the FailedAVP AVP inside a message
	 * @return the found FailedAVP AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public FailedAVP getFailedAVP() {
		return (FailedAVP)findAVP(FailedAVP.AVP_CODE);
	}
	/**
	 * sets the FailedAVP and overrides a existing one
	 * @param _failedAVP	the new FailedAVP
	 * 
	 * @author mhappenhofer
	 */
	public void setFailedAVP(FailedAVP _failedAVP)	{
		this.setSingleAVP( _failedAVP);
	}
	
	/**
	 * Searches for the ErrorMessage AVP inside a message
	 * @return the found ErrorMessage AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public ErrorMessage getErrorMessage() {
		return (ErrorMessage)findAVP(ErrorMessage.AVP_CODE);
	}
	/**
	 * sets the ErrorMessage and overrides a existing one
	 * @param _errorMessage	the new ErrorMessage
	 * 
	 * @author mhappenhofer
	 */
	public void setErrorMessage(ErrorMessage _errorMessage)	{
		this.setSingleAVP( _errorMessage);
	}
	
	/**
	 * Searches for the ErrorReportingHost AVP inside a message
	 * @return the found ErrorReportingHost AVP, null if not found
	 * 
	 * @author mhappenhofer
	 */
	public ErrorReportingHost getErrorReportingHost() {
		return (ErrorReportingHost)findAVP(ErrorReportingHost.AVP_CODE);
	}
	/**
	 * sets the ErrorReportingHost and overrides a existing one
	 * @param _errorReportingHost	the new ErrorReportingHost
	 * 
	 * @author mhappenhofer
	 */
	public void setErrorReportingHost(ErrorReportingHost _errorReportingHost)	{
		this.setSingleAVP( _errorReportingHost);
	}
	
	/**
	 * Searches for the AuthApplicationId AVP inside a message
	 * @return the found AuthApplicationId AVP, null if not found
	 * 
	 * @author cegger
	 */
	public AuthApplicationId getAuthApplicationId() {
		return (AuthApplicationId)findAVP(AuthApplicationId.AVP_CODE);
	}
	/**
	 * sets the AuthApplicationId and overrides a existing one
	 * @param _authApplicationId the new AuthApplicationId
	 * 
	 * @author cegger
	 */
	public void setAuthApplicationId(AuthApplicationId _authApplicationId)	{
		this.setSingleAVP(_authApplicationId);
	}
}
