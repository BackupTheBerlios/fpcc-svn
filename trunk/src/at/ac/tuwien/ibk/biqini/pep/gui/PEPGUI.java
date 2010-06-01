/* 
* PEPGUI.java
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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import at.ac.tuwien.ibk.biqini.pep.IBandwidthTracer;

public class PEPGUI extends ApplicationFrame implements ActionListener, Action {

	private static final long serialVersionUID = -6206843291292187702L;
	/**
	 * Number of charts in the GUI
	 */
	private static final int MAXCHARTS = 3;
	/**
	 * Time in milliseconds that should be displayed at the charts
	 */
	private static final double DISPLAYEDTIME = 300000;

	
	private JPanel content;
	private JList qoSRuleList;
	private BandwidthGenerator bandwidthGenerator;
	private GridBagLayout gridBagLayout;
	
	/**
	 * the names of all listentities
	 */
	public String[] listItems;
	/**
	 * a mapping from names (maybe coming from the List) to the QoSRules
	 */
	private Hashtable<String,IBandwidthTracer> allSessions;
	/**
	 * a collection of all free positions for the charts
	 */
	private Vector<Integer> positions;
	/**
	 * the thread that reads in periodic time intervall the current bitrates
	 */
	private Thread periodicBandwidthReader;
	/**
	 * a mapping from streamname to the poistion where it is currently displayed
	 */
	private Hashtable<String, Integer> chartPosition;
	
	private TimeSeriesCollection[] tsc;
	private JFreeChart [] charts;
	
	public PEPGUI(String arg0, Vector<IBandwidthTracer> qosRules) {
		super(arg0);
		
		// initiate all collections
		tsc = new TimeSeriesCollection[MAXCHARTS];
		charts = new JFreeChart[MAXCHARTS];
		positions = new Vector<Integer>();
		chartPosition = new Hashtable<String, Integer>();
		for (int i = 0;i<MAXCHARTS;i++)
			positions.add(i);
		allSessions = new Hashtable<String, IBandwidthTracer>();
		
		// fill the BandwidthGenerator with the ongoing QoSRules
		bandwidthGenerator = new BandwidthGenerator();
		Enumeration<IBandwidthTracer> enbandwidth =qosRules.elements();
		while(enbandwidth.hasMoreElements()){
			IBandwidthTracer b = enbandwidth.nextElement();
			allSessions.put(b.getName(), b);
			bandwidthGenerator.add(b);
		}
		
        gridBagLayout = new GridBagLayout();
		
        //insert the list with all QoS rules
        GridBagConstraints c = new GridBagConstraints();
		c.weighty = 9; 
		c.weightx = 2.0;
		c.gridheight=MAXCHARTS;
		c.fill = GridBagConstraints.BOTH;
		content = new JPanel(gridBagLayout);
		qoSRuleList = new JList(allSessions.keySet().toArray());
		qoSRuleList.setPreferredSize(new java.awt.Dimension(200, 600));
		qoSRuleList.setBorder(BorderFactory.createRaisedBevelBorder());
		
		// set a MouseListner on the List
		MouseListener mouseListener = new MouseAdapter() {
		     public void mouseClicked(MouseEvent e) {
		    	 
		    	 if (e.getButton()==MouseEvent.BUTTON1) {
		    		 int index = qoSRuleList.locationToIndex(e.getPoint());
		    		 addStream(allSessions.get(allSessions.keySet().toArray()[index]));
		    	 }
		    	 if (e.getButton()==MouseEvent.BUTTON3) {
		             int index = qoSRuleList.locationToIndex(e.getPoint());
		             removeStream(allSessions.get(allSessions.keySet().toArray()[index]));
		          }
		     }
		 };
		qoSRuleList.addMouseListener(mouseListener);
		
		// place all parts at the content pane
		gridBagLayout.setConstraints(qoSRuleList,c);
		content.add(qoSRuleList);
		setContentPane(content);
		content.setSize(1000, 800);

		//create all GUI aspects for our Charts
		insertAllCharts();
		
		//Start the thread that fills up our time series
		periodicBandwidthReader = new Thread(bandwidthGenerator);
		periodicBandwidthReader.setName("data-collector");
        periodicBandwidthReader.start();
    }
	
	protected void processWindowEvent(WindowEvent e)	{
		// while closing this window avoid the termination of the complete program
		if(e.getID()==WindowEvent.WINDOW_CLOSING)	{
			this.dispose();
		}
		else
			super.processWindowEvent(e);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}

	public Object getValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void putValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub
	}
	/**
	 * Display another stream in any chart
	 * @param stream	the stream that should be displayed 
	 */
	public void addStream(IBandwidthTracer stream)	{
		// no chart is free so exit
		if(positions.isEmpty())
			return;
		// this stream is already displayed
		if(chartPosition.containsKey(stream.getName()))
			return;
		// find a position to display
		int pos = positions.remove(0);
		// redraw the chart
		redrawChart(stream,pos);
		// store at which chart position the stream is dispalyed now
		chartPosition.put(stream.getName(), pos);
	}
	/**
	 * remove a Stream form the charts
	 * @param stream	the stream that should be removed
	 */
	public void removeStream(IBandwidthTracer stream) {
		// if this stream is not displayed currently then exit 
		if(!chartPosition.containsKey(stream.getName()))
			return;
		// find the position where the stream is displayed
		int pos = chartPosition.get(stream.getName());
		// redraw chart
		redrawChart(null, pos);
        // add the position to the free poistions for other streams
        positions.add(pos);
        // remove the mapping from chart position to stream
        chartPosition.remove(stream.getName());
	}
	/**
	 * Redraws a Chart with a certain stream at a certain position 
	 * @param stream	the stream which should be displayed 
	 * @param pos		the position where the stream should be displayed
	 */
	public void redrawChart(IBandwidthTracer stream, int pos)	{
        // remove any other streams currently displayed at this poistion 
		tsc[pos].removeAllSeries();
		if(stream == null) {
			// set the correct name
	        charts[pos].setTitle("unused");
	        // adjust the y axis
	        charts[pos].getXYPlot().getRangeAxis().setRange(0.0, 1.0);
		}else{
			// add the uplink and downlink stream
	        tsc[pos].addSeries(stream.getSeriesUp());
	        tsc[pos].addSeries(stream.getSeriesDown());
	        // set the correct name
	        charts[pos].setTitle(stream.getName());
	        // adjust the y axis
	        charts[pos].getXYPlot().getRangeAxis().setRange(0.0, stream.getMax()/1000);
		}
    }
	/**
	 * sets up all charts for later usage
	 */
	public void insertAllCharts()	{
		for(int i = 0;i<MAXCHARTS;i++){
			tsc[i] = new TimeSeriesCollection(new TimeSeries("unused",Millisecond.class));
            charts[i] = ChartFactory.createTimeSeriesChart(
                    "unused", 
                    null, 
                    "bitrate kbit/sec",
                    tsc[i], 
                    false, 
                    true, 
                    false
                );
            
            XYPlot plot = charts[i].getXYPlot();
            ValueAxis axis = plot.getDomainAxis();
            axis.setAutoRange(true);
            axis.setFixedAutoRange(DISPLAYEDTIME);  
            axis = plot.getRangeAxis();
            axis.setRange(0.0, 128); 
            ChartPanel chartPanel = new ChartPanel(charts[i]);
            
            // do the layout stuff
            GridBagConstraints c = new GridBagConstraints();
    		c.weighty = 3; 
    		c.weightx = 10.0;
    		c.fill = GridBagConstraints.BOTH;
    		c.insets = new Insets(0,0,0,0);
    		c.gridx=1;
    		c.gridy=i;
    		c.gridwidth = 1;
    		c.gridheight = 1;
    		gridBagLayout.setConstraints(chartPanel,c);
    		
    		// paste it
            content.add(chartPanel);
            chartPanel.setPreferredSize(new java.awt.Dimension(1000, 270));
        }
	}
	
	public static void startGUI(Vector<IBandwidthTracer> qosRules)	{
		final PEPGUI demo = new PEPGUI("Actual Bandwidth consumption on PEP",qosRules);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
	}
}
