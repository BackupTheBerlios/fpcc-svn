/* 
* TestCase9.java
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
package at.ac.tuwien.ibk.biqini.af.testCases;

import at.ac.tuwien.ibk.biqini.af.Media;
import at.ac.tuwien.ibk.biqini.af.SSDP;
import at.ac.tuwien.ibk.biqini.af.SessionDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.FramedIPAddress;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ServiceInfoStatus;
import at.ac.tuwien.ibk.biqini.diameter.messages.DiameterAnswer;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxAAR;

public class TestCase9 extends AFMainTest implements AFTestCase {

	public String TestName() {
		return "Wrong Media";
	}

	public String getDescription() {
		StringBuffer sbf = new StringBuffer();
		sbf.append("AF send AAR (pre) with offer and answer (several codecs) for Audio\n");
		sbf.append("AF send AAR (final) with offer and answer (only 1 codec) for Audio and Video\n");
		sbf.append("PCRF reqjects  with 5012\n");
		return sbf.toString();
	}

	public boolean startTest() throws Exception {
		SessionDescription sd = new SessionDescription(afName,"sip:good@domain.at");
		sd.setLocalIP(new FramedIPAddress("10.0.0.1"));
		sd.setRemoteIP(new FramedIPAddress("10.0.0.2"));
		sd.setServiceInfoStatus(ServiceInfoStatus.PRELIMINARY_SERVICE_INFORMATION);
		
		int[] codecs = {0,3};
		
		SSDP offer = new SSDP(sd.getLocalIP().getOctetString());
		offer.audio = new Media(Media.AUDIO,1000,codecs,100000,Media.INACTIVE);
		SSDP answer = new SSDP(sd.getRemoteIP().getOctetString());
		answer.audio = new Media(Media.AUDIO,2000,codecs,100000,Media.INACTIVE);
		sd.setOffer(offer);
		sd.setAnswer(answer);
		
		//build AAR
		RxAAR aar = getRxAAR();
		this.addMedia(aar, sd);
		DiameterAnswer danswer=null;
		LOGGER.info(TestName()+" send AAR!");
		if(sendBlocking(aar, danswer)!=2001)
			return false;
		
		int[] singleCodec = {3};
		
		int[] vCodec = {31};
		sd.setServiceInfoStatus(ServiceInfoStatus.FINAL_SERVICE_INFORMATION);
		sd.getAnswer().audio.status=Media.ACTIVE;
		sd.getAnswer().audio.codec=singleCodec;
		sd.getAnswer().video = new Media(Media.VIDEO,5000,vCodec,100000,Media.ACTIVE);
		sd.getOffer().audio.status=Media.ACTIVE;
		sd.getOffer().video = new Media(Media.VIDEO,10000,vCodec,100000,Media.ACTIVE);
		sd.getOffer().audio.codec=singleCodec;
		aar = getRxAAR();
		this.addMedia(aar, sd);
		LOGGER.info(TestName()+" send AAR!");
		if(sendBlocking(aar, danswer)!=5012)
			return false;
		return true;
	}
	

}
