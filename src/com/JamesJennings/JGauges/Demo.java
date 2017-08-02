package com.JamesJennings.JGauges;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/*
 * Demo program for JGauge Library by James Jennings (2016)
 * Allows gauge values to be manually set, in the same way that they may be updated
 * 	by a timer or other ActionEvent
 */
public class Demo extends JFrame implements ActionListener{
	//Gauge definitions
	JGauge speedGauge = new JGauge("Speed","KM/h",0,200,JGauge.LONG_SWEEP);
	JGauge tachGauge  = new JGauge("Tach","RPM",0,6000,JGauge.LONG_SWEEP);
	JGauge tempGauge  = new JGauge("Coolant Temp","C",70,150, JGauge.SHORT_SWEEP);
	//End Gauge definitions
	
	//Inputs
	JTextField speedInput = new JTextField();
	JTextField tachInput  = new JTextField();
	JTextField tempInput  = new JTextField();
	JButton updateButton = new JButton("Update All");
	//End Inputs
	
	//Display Setup
	JPanel gaugePanel = new JPanel();
	JPanel inputPanel = new JPanel();
	//End Display Setup
	Demo(){		
		//Setup gaugePanel and add gauges
		gaugePanel.setLayout(new GridLayout(0,3));
		gaugePanel.add(speedGauge);
		gaugePanel.add(tachGauge);
		gaugePanel.add(tempGauge);
		//End gaugePanel setup
		
		//Setup inputPanel
		inputPanel.setLayout(new GridLayout(3,3));
		
			//Add labels
			inputPanel.add(new JLabel("Speedometer Value:"));
			inputPanel.add(new JLabel("Tachometer Value:"));
			inputPanel.add(new JLabel("Thermometer Value:"));
			//End add labels
			
			//Add text boxes
			inputPanel.add(speedInput);
			inputPanel.add(tachInput);
			inputPanel.add(tempInput);
			//End add text boxes
			
			//Fillers, for layout purposes
			inputPanel.add(new JLabel());
			inputPanel.add(new JLabel());
			//end Fillers
		
		updateButton.addActionListener(this);	//initialize update button
		inputPanel.add(updateButton); //Add update button
		
		//End inputPanel setup
		
		//Screen Setup
		setTitle("JGauge Demo -- James Jennings 2016");
		setSize(1000,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2,1));
		add(gaugePanel);
		add(inputPanel);
		
		setVisible(true);
		//End Screen Setup
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Demo();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//handle gauge values
		if(arg0.getSource()==updateButton){	//Executes on button press
			//set speedometer
			try{
				speedGauge.setValue(Double.parseDouble(speedInput.getText()));
			}catch(Exception e){}
			try{
				tachGauge.setValue(Double.parseDouble(tachInput.getText()));
			}catch(Exception e){}
			try{
				tempGauge.setValue(Double.parseDouble(tempInput.getText()));
			}catch(Exception e){}
			
		}
	}

}
