/* 
* DomainPolicy.java
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
package at.ac.tuwien.ibk.biqini.pdp.policy;

import java.util.Iterator;
import java.util.Vector;

import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.CodecData;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MediaComponentDescription;
import at.ac.tuwien.ibk.biqini.diameter.messages.threegpp.rx.RxAAR;
import de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage;

/**
 * @author Christoph Egger
 *
 */
public class DomainPolicy implements IfPolicy {
	
	private static DomainPolicy INSTANCE = null;
	private Vector<Integer> allowedCodecs;
	private Vector<Integer> allowedMedia;
	
	private DomainPolicy(){
		allowedCodecs = new Vector<Integer>();
		//audio
/*
		allowedCodecs.add(0);//PCMU
		allowedCodecs.add(3);//GSM
		allowedCodecs.add(8);//PCMA
		allowedCodecs.add(9);//G722
		allowedCodecs.add(14);//MPA
		allowedCodecs.add(101);//Telephone-Event

		//Video
		allowedCodecs.add(31);//H261
		allowedCodecs.add(32);//MPV
		allowedCodecs.add(102);//H263
		allowedCodecs.add(103);//H263
		allowedCodecs.add(104);//H263
		allowedCodecs.add(105);//H263
		allowedCodecs.add(106);//H263
		allowedCodecs.add(107);//H263
		allowedCodecs.add(108);//H263
		allowedCodecs.add(109);//H263
*/
		for (int i = 0; i<265;i++){
			allowedCodecs.add(i);
		}

		
		allowedMedia = new Vector<Integer>();
		allowedMedia.add(0);//audio;
		allowedMedia.add(1);//video;

	}
	public static DomainPolicy getInstance(){
		if (INSTANCE == null){
			INSTANCE = new DomainPolicy();
		}
		return INSTANCE;
	}
	/* (non-Javadoc)
	 * @see at.ac.tuwien.ibk.biqini.pdp.policy.IfPolicy#checkPolicy(de.fhg.fokus.diameter.DiameterPeer.data.DiameterMessage)
	 */
	//TODO QoS Class identifier zurückliefern
	public void checkPolicy(DiameterMessage _msg) throws CodecNotAllowedException, MediaTypeNotAllowedException{
		if (_msg instanceof RxAAR){
			RxAAR aar = (RxAAR)_msg;
			Iterator<MediaComponentDescription> mcdIt = aar.getMediaComponentDescriptionIterator();
			while(mcdIt.hasNext()){
				MediaComponentDescription mcD = mcdIt.next();
				if (!allowedMedia.contains(mcD.getMediaType().getIntData())){
					throw new MediaTypeNotAllowedException("Media Type "+mcD.getMediaType().getIntData()+" is not allowed in this domain!");
				}
				Iterator<CodecData> cdIt = mcD.getCodecDataIterator();
				while(cdIt.hasNext()){
					CodecData cd = cdIt.next();
					Vector<Integer> codecs = cd.getCodecs();
					for (int i = 0; i<codecs.size();i++){
						if (!allowedCodecs.contains(codecs.elementAt(i))){
							throw new CodecNotAllowedException("Codec "+codecs.elementAt(i)+" is not allowed in this Domain!");
						}
					}
				}
			}
		}
	}
}
