/* 
* QoSRule.java
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
package at.ac.tuwien.ibk.biqini.pep;

import java.util.Iterator;
import java.util.Vector;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;

import at.ac.tuwien.ibk.biqini.diameter.avp.ietf.SessionID;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.ChargingRuleDefinition;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.FlowDescription;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthDL;
import at.ac.tuwien.ibk.biqini.diameter.avp.threegpp.MaxRequestedBandwidthUL;
/**
 * A single QoS Rule
 * @author Marco Happenhofer
 *
 */
public class QoSRule implements IBandwidthTracer {
	
	/** the definition of the rule */
	private ChargingRuleDefinition rule;
	/** the initiated SessionID, might be null if this rule is a predefined rule*/
	private SessionID sessionID;
	/** a static collection of all rules*/
	public static Vector<IBandwidthTracer> allRules;
	/** the value of MEGA */
	private static final long MEGABYTE = 1024*1024;
	/** the timeseries that stores the uplink bitrate usage*/
	private TimeSeries seriesUp;
	/** the timeseries that stores the downlink bitrate usage*/
	private TimeSeries seriesDown;
	/**the traffic controll Queue ID for deleting*/
	private long trafficControlID = 0;
	/**is this QoSRule still active*/
	private boolean active;

	static{
		allRules = new Vector<IBandwidthTracer>();
	}
	
	public QoSRule(SessionID sessinID){
		this.sessionID = sessinID;
		this.active = true;
		allRules.add(this);
	}
	/**
	 * checks if this QoSRule is active
	 * @return	true if the QoS Rule alloactes ressoures on the pep
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * changes the active state of the QoSRule
	 * @param active	
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	/**
	 * @return the rule
	 */
	public ChargingRuleDefinition getRule() {
		return rule;
	}

	/**
	 * @param rule the rule to set
	 */
	public void setRule(ChargingRuleDefinition rule) {
		this.rule = rule;
		this.seriesUp = new TimeSeries(rule.getChargingRuleName().getOctetString()+" uplink", Millisecond.class);
		this.seriesDown = new TimeSeries(rule.getChargingRuleName().getOctetString()+" downlink", Millisecond.class);
		seriesUp.setMaximumItemCount(1000);
		seriesDown.setMaximumItemCount(1000);
	}
	/**
	 * a short info about this rule
	 */
	public String toString()	{
		StringBuffer sbf = new StringBuffer();
		sbf.append(rule.getChargingRuleName().getOctetString());
		sbf.append(" ");
		StringBuffer uplink = new StringBuffer();
		StringBuffer downlink = new StringBuffer();
		Iterator<FlowDescription> fds = rule.getFlowDescriptionIterator();
		while(fds.hasNext())	{
			FlowDescription fd = fds.next();
			if(fd.isDirectionIn())	{
				uplink.append(fd.getOctetString()+" uplink ");
				uplink.append(getCurrentBitrateUplink()/1000+"/");
				uplink.append(rule.getQoSInformation().getMaxRequestedBandwidthUL().getUnsigned32()/1000+" kbit/sec");
				uplink.append(" total: "+humanRaedableSize(getTotalTrafficUplink()));
			}
			else	{
				downlink.append(fd.getOctetString()+" downlink ");
				downlink.append(getCurrentBitrateDownlink()/1000+"/");
				downlink.append(rule.getQoSInformation().getMaxRequestedBandwidthDL().getUnsigned32()/1000+" kbit/sec");
				downlink.append(" total: "+humanRaedableSize(getTotalTrafficDownlink()));

			}
		}	
		sbf.append("\n\t\t"+uplink+"\n\t\t"+downlink);
		return sbf.toString();
	}
	/**
	 * provides the rulename
	 * @return	rulename
	 */
	public String getRuleName()	{
		return rule.getChargingRuleName().getOctetString();
	}
	/**
	 * a simple combination of SessionID and RuleName for unique id in a PEP
	 * @return
	 */
	public String getIdentifier()	{
		if(sessionID==null)
			return "static";
		return sessionID.getUTF8String()+"/"+getRuleName();
	}
	/**
	 * the ID for accessing the correct Queue in TC
	 * @return	TC queue ID
	 */
	public long getTrafficControlID() {
		return trafficControlID;
	}
	public void setTrafficControlID(long trafficControlID) {
		this.trafficControlID = trafficControlID;
	}
	/**
	 * queries actual bitrate in uplink
	 * @return	uplink bitrate
	 */
	public long getCurrentBitrateUplink()	{
		if(TrafficControl.getInstance().isEmulation())	{
			long maxUL = rule.getQoSInformation().getMaxRequestedBandwidthUL().getUnsigned32();
			return (long)((0.7*maxUL) * Math.random()+maxUL*0.3);
		}
		else
			return TrafficControl.getInstance().getBitrate(false, (int)trafficControlID);
	}
	/**
	 * queries actual bitrate in downlink
	 * @return	downlink bitrate
	 */
	public long getCurrentBitrateDownlink()	{
		if(TrafficControl.getInstance().isEmulation())	{
			long maxDL = rule.getQoSInformation().getMaxRequestedBandwidthDL().getUnsigned32();
			return (long)((0.7*maxDL) * Math.random()+maxDL*0.3);
		}
		else
			return TrafficControl.getInstance().getBitrate(true, (int)trafficControlID);
	}
	/**
	 * queries the total traffic in uplink
	 * @return	total uplink traffic
	 */
	public long getTotalTrafficUplink()	{
		return TrafficControl.getInstance().getTotalBandwidth(false, (int)trafficControlID);
	}
	/**
	 * queries the total traffic in downlink
	 * @return	total downlink traffic
	 */
	public long getTotalTrafficDownlink()	{
		return TrafficControl.getInstance().getTotalBandwidth(true, (int)trafficControlID);
	}
	/**
	 * changes a total traffic in a human readable format
	 * @param traffic	traffic
	 * @return	human readable traffic
	 */
	private String humanRaedableSize(long traffic){
		double bytes = traffic;
		if(bytes > MEGABYTE)
			return trimAfterTwoDecimals(((double)traffic/MEGABYTE))+" MByte";
		else if(bytes > 1024)	
			return trimAfterTwoDecimals(((double)traffic/1024))+" kByte"; 
		else
			return traffic+" Bytes";
	}
	/**
	 * trim a traffic after the second decimal value
	 * @param traffic	traffic
	 * @return	trimmed value
	 */
	private String trimAfterTwoDecimals(double traffic)	{
		String t = Double.toString(traffic);
		int pos = t.indexOf(".");
		if(pos >0 && pos +3<t.length())
			return t.substring(0,pos+3);
		return t;
	}
	/**
	 * queries the maximum traffic due to configuration
	 */
	public double getMax() {
		MaxRequestedBandwidthUL ul = rule.getQoSInformation().getMaxRequestedBandwidthUL();
		MaxRequestedBandwidthDL dl = rule.getQoSInformation().getMaxRequestedBandwidthDL();
		if(dl==null)
			return ((double) (ul.getUnsigned32())/1000);
		if(ul==null)
			return ((double) (dl.getUnsigned32())/1000);
		
		return ((double)Math.max(ul.getUnsigned32(), dl.getUnsigned32())/1000);
	}
	/**
	 * name of the name
	 */
	public String getName() {
		return getRuleName();
	}
	/**
	 * timeSeries in Downlink
	 */
	public TimeSeries getSeriesDown() {
		return seriesDown;
	}
	/**
	 * timeSeries in Uplink
	 */
	public TimeSeries getSeriesUp() {
		return seriesUp;
	}
	/**
	 * queries next current Downlink value
	 */
	public double nextDownValue() {
		double t =  ((double)getCurrentBitrateDownlink());
		return t;
	}
	/**
	 * queries next current Uplink value
	 */
	public double nextUpValue() {
		return ((double)getCurrentBitrateUplink());
	}
}
