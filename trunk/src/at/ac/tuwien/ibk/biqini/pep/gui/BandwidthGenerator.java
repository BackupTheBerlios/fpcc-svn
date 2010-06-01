/* 
* BandwidthGenerator.java
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
package at.ac.tuwien.ibk.biqini.pep.gui;

import java.util.Enumeration;
import java.util.Hashtable;

import org.jfree.data.time.Millisecond;

import at.ac.tuwien.ibk.biqini.pep.IBandwidthTracer;

public class BandwidthGenerator implements Runnable {
	
	/**
	 * all observed streams, these stresm will be checked for their actual bitrate
	 */
	private Hashtable<String,IBandwidthTracer> streams;
	/**
	 * intervall for checking bitrate
	 */
	public static long DELAY = 1000;
	/**
	 * Should the Thread be stopped?
	 */
	private boolean stop;
	/**
	 * 
	 */
	public BandwidthGenerator() {
		super();
		this.streams = new Hashtable<String, IBandwidthTracer>();
	}
	public void run() {
		while (!stop) {
			try {
				Thread.sleep(DELAY);
				// enumerate all streams and update their new bitrates
				Enumeration<IBandwidthTracer> enStreams = streams.elements();
				while(enStreams.hasMoreElements())
				{
					IBandwidthTracer stream = enStreams.nextElement();
					try {
						stream.getSeriesUp().add(new Millisecond(), stream.nextUpValue()/1000);
						stream.getSeriesDown().add(new Millisecond(), stream.nextDownValue()/1000);
					} catch (Exception e) {
						// TODO Auto-generated catch block
					}
				}
			} catch (InterruptedException e) {
				this.stop = true;
			}
		}
		
	}
	/**
	 * add a stream for bitrates updates
	 * @param stream	the straem that should be observed
	 */
	public void add(IBandwidthTracer stream)	{
		streams.put(stream.getName(), stream);
	}
	/**
	 * remove a stream from observation
	 * @param name	
	 */
	public void remove(String name){
		streams.remove(name);
	}
	
	public void halt()	{
		this.stop = true;
	}
	
	public void reset()	{
		this.stop = false;
	}
	public Hashtable<String,IBandwidthTracer> getStreams() {
		return streams;
	}
}
