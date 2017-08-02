package com.JamesJennings.JGauges;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class JGauge extends Component{
	//defaults
	String 	gTitle = "";	//title of gauge IE "Speed"
	String 	gUnits = "";	//input unit of gauge IE "KM/h"
	int gIncrements = 5;	//increment for gauge dashes (default 5)
	double	gMin = 0;		//minimum value, lowest value shown on gauge face
	double 	gMax = 0;		//maximum value, highest value shown on gauge face
	double 	gRange = 0;		//range of gauge (gMax-gMin)
	int gMinAngle = 0;
	int gMaxAngle = 0;
	int 	gSweep = SHORT_SWEEP;
	double 	gValue = 0;
	int 	needlePointX=0;
	int 	needlePointY=0;
	double	needleAngle=0;
	int needleBaseX = 0;
	int needleBaseY = 0;
	static Color bgColor = Color.WHITE;
	static Color fgColor = Color.BLACK;
	static Color needleColor = Color.RED;
	JGauge(String title, String units, double min, double max, int sweep){
		gTitle = title;
		gUnits = units;
		gMin = min;
		gValue = min;
		gMax = max;
		gSweep = sweep;
		gRange = Math.abs(gMin-gMax);
		repaint();
	}
	public static final int SHORT_SWEEP = 0;
	public static final int SHORT_SWEEP_MIN_ANGLE = 225;
	public static final int SHORT_SWEEP_MAX_ANGLE = 315;

	public static final int LONG_SWEEP = 1;
	public static final int LONG_SWEEP_MIN_ANGLE = 135;
	public static final int LONG_SWEEP_MAX_ANGLE = 405;

	
	public void paint(Graphics g) {         
		// Dynamically calculate size information
		Dimension size = getSize();         // diameter
		int d = Math.min(size.width, size.height);
        int x = (size.width - d)/2;         
        int y = (size.height - d)/2;          // draw circle (color already set to foreground)
        needleBaseX = getWidth() / 2;
        g.setColor(bgColor);         
        g.fillOval(x, y, d, d);
        g.setColor(fgColor);
        drawCenteredString(gTitle, size.width, size.height, g);  
        if(gSweep==SHORT_SWEEP){ 
        	gMinAngle = SHORT_SWEEP_MIN_ANGLE;
        	gMaxAngle = SHORT_SWEEP_MAX_ANGLE;	//set range for needle
        	needleBaseY= (int)(size.height*0.75);	//start needle almost at base of gauge
        }else{
        	gMinAngle = LONG_SWEEP_MIN_ANGLE;
        	gMaxAngle = LONG_SWEEP_MAX_ANGLE;	//set range for needle
        	needleBaseY=(int)(size.height*0.6);	//start needle near middle of gauge
        }
        	int a = getWidth() / 2;
            int b = getHeight() / 2;
            
            for(int i=gMinAngle;i<gMaxAngle;i+=5){
            double t = 2 * Math.PI + DegToRad(i);
        	int px = (int) Math.round(a + d/2 * Math.cos(t));
            int py = (int) Math.round(b + d/2 * Math.sin(t));
            System.out.println("X: "+px+" Y:"+py);
            
            //dots around gauge
            g.fillOval(px-5,py-5,10,10);
            }
            //needle points
            int nx = (int) (Math.round(a + d/2 * Math.cos(DegToRad(needleAngle))));
            int ny = (int) (Math.round(b + d/2 * Math.sin(DegToRad(needleAngle))));
            g.setColor(needleColor);
            g.drawLine(needleBaseX, needleBaseY, nx, ny);
        
	}
	public void calcNeedleAngle(){
		double valPercent = gValue/gRange;	//value percentage of scale
		needleAngle = gMinAngle+(valPercent*(gMinAngle-gMaxAngle));
	}
	//borrowed from http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Centertext.htm
	public void drawCenteredString(String s, int w, int h, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int x = (w - fm.stringWidth(s)) / 2;
		int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
		g.drawString(s, x, y);
	}
	//colour attribute access
	public void setForegroundColor(Color setting){
		fgColor = setting;
		repaint();
	}
	public void setBackgroundColor(Color setting){
		bgColor = setting;
		repaint();
	}
	public void setNeedleColor(Color setting){
		needleColor = setting;
		repaint();
	}
	//value access
	public double getValue(){
		return gValue;
	}
	public void setValue(double inValue){
		if(inValue<=gMax){	//If requested value is below maximum
			gValue=inValue;	//set gauge value to requested
		}else{
			gValue=gMax;	//otherwise, set value to max
		}
		if(gValue<=gMin){	//If requested value is below minimum
			gValue=gMin;	//Set gauge value to minimum
		}
		calcNeedleAngle();
		repaint();
	}
	//quick convert Degrees to Radians
	public double DegToRad(double indeg){
		return indeg*=(Math.PI/180);
	}
}
