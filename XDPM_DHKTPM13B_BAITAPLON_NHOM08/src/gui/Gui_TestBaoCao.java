/**
 * 
 */
package gui;
/*
* (C) Copyright 2020 . All rights reserved.
*
* @author: Phan Bach
* @date: Nov 26, 2020
* @version: 1.0
*/

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

import dao.DVDDao;
import entity.DVD;

/**
 * @author Phan Bach
 *
 */
public class Gui_TestBaoCao extends JFrame{
	private static int count1 =0;
	private static int count2 =0;
	private static int count3 =0;
	private List<DVD>list = new ArrayList<DVD>();
	private DVDDao dvdDao = new DVDDao();
	private JFrame frame;
	private DefaultPieDataset dataset = new DefaultPieDataset();
	private JFreeChart jFreeChart;
	private ChartPanel chartPanel;
	private JPanel panel;
	private JPanel panelTable;
	private JTable table;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Gui_TestBaoCao window = new Gui_TestBaoCao();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}
	
    public Gui_TestBaoCao() {
        initialize();
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
	
	private void initialize() {

		dvdDao.getAllDVD().forEach(p->{
			if(p.getTrangThai()==1) {

				count1++;
			}
			if(p.getTrangThai()==2) {

				count2++;
			}
			if(p.getTrangThai()==3) {

				count3++;
			}
		});
		

		
		
		frame = new JFrame();
        frame.setBounds(400, 200, 1400, 1000); //set size frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Set gia tr cho PieChart
        dataset.setValue("On Self ("+ count1 + " DVD)", count1);
        dataset.setValue("Rented (" + count2 + " DVD)", count2);
        dataset.setValue("On Hold (" + count3 + " DVD)", count3);
        
        jFreeChart = ChartFactory.createPieChart3D("BIỂU ĐỒ THỐNG KÊ TRẠNG THÁI DVD ", dataset, true, true, false);
        PiePlot3D plot = (PiePlot3D) jFreeChart.getPlot();
       // plot.setForegroundAlpha((float) 0.6 );
       //  plot.setSectionPaint("Không đạt ", new Color(255, 0, 0)); //Set color cho PieChart
       // plot.setSectionPaint("Đạt", new Color(0, 255, 0)); //Set color cho PieChart
        plot.setBackgroundPaint(new Color(255, 255, 255)); //Set background cho PieChart

        //panel Container chartPanel
        panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        // add chartPanel PieChart vao panel
        chartPanel = new ChartPanel(jFreeChart);
        chartPanel.setBounds(330, 200, 740, 400); //set size PieChart
        panel.add(chartPanel);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setZoomAroundAnchor(true);
        chartPanel.setBackground(SystemColor.menu);
        
        
	}
}
