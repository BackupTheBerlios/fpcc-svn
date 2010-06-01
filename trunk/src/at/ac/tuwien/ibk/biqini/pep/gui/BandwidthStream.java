/* 
* BandwidthStream.java
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

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;

import at.ac.tuwien.ibk.biqini.pep.IBandwidthTracer;

public class BandwidthStream implements IBandwidthTracer{
	private double min;
	private double max;
	private String name;
	private TimeSeries seriesUp;
	private TimeSeries seriesDown;
	/**
	 * @param min
	 * @param max
	 */
	public BandwidthStream(String name, double min, double max) {
		super();
		this.min = min;
		this.max = max;
		this.name = name;
		this.seriesUp = new TimeSeries(name+" uplink", Millisecond.class);
		this.seriesDown = new TimeSeries(name+" downlink", Millisecond.class);
	}
	public double nextUpValue()	{
		return (max-min) * Math.random()+min;
	}
	
	public double nextDownValue()	{
		return (max-min) * Math.random()+min;
	}

	public double getMax() {
		return max;
	}

	public String getName() {
		return name;
	}

	public TimeSeries getSeriesUp() {
		return seriesUp;
	}

	public TimeSeries getSeriesDown() {
		return seriesDown;
	}
}
