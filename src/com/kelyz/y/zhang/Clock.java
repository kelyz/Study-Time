package com.kelyz.y.zhang;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Clock extends JFrame {
	
	private JTextField clocktimeField;
	private JTextField countdown;
	private JTextField timerEntry;
	private JTextField webLink;
	private int countdownMinute;
	private int countdownSecond;
	private int choice;
	private String timerMinute;
	private String test;
	private Timer s;

	public Clock() {
		
		setSize(500, 170);
		
		JPanel clockPanel = new JPanel();
		clockPanel.setLayout(new FlowLayout());
		
		clocktimeField = new JTextField(7);
		clocktimeField.setEditable(false);
		clocktimeField.setFont(new Font("Arial", Font.BOLD, 80));
		clocktimeField.setLayout(new FlowLayout(FlowLayout.CENTER));
		clocktimeField.setHorizontalAlignment(JTextField.CENTER);
		
		clockPanel.add(clocktimeField, BorderLayout.CENTER);
		
		JButton timerButton = new JButton("Timer");
	    	JButton alarmButton = new JButton("Alarm");
	    	JButton studyButton = new JButton("Study");
	    
		clockPanel.add(timerButton, BorderLayout.SOUTH);
		clockPanel.add(alarmButton, BorderLayout.SOUTH);
		clockPanel.add(studyButton, BorderLayout.SOUTH);
		this.add(clockPanel, BorderLayout.CENTER);
		
		Timer t = new Timer(1000, new TimeView());
		t.start();
		
		JPanel alarmPanel = new JPanel();
		JLabel setAlarm = new JLabel("Set alarm for");
		alarmPanel.add(setAlarm);
		
		JButton doneButton = new JButton("Done");
		JTextField hourField = new JTextField(2);
		
		alarmPanel.add(hourField);
		alarmPanel.add(new JLabel(":"));
		JTextField minuteField = new JTextField(2);
		alarmPanel.add(minuteField);

		String comboBoxItems[] = {"AM", "PM"};
		JComboBox <String> selection = new JComboBox<>(comboBoxItems);
		
		alarmPanel.add(selection);
		alarmPanel.add(doneButton, BorderLayout.SOUTH);
		
		JPanel timerPanel = new JPanel();
		timerEntry = new JTextField(5);
		timerEntry.setHorizontalAlignment(JTextField.CENTER);
		
		timerPanel.add(timerEntry);
		timerPanel.add(new JLabel("minutes"));
		
		JButton startButton = new JButton("Start");
		JButton setButton = new JButton("Set");
		JButton pauseButton = new JButton("Pause");
		timerPanel.add(setButton);
		timerPanel.add(startButton);
		timerPanel.add(pauseButton);
		
		countdown = new JTextField(4);
		countdown.setEditable(false);
		countdown.setFont(new Font("Arial", Font.BOLD, 80));
		countdown.setLayout(new FlowLayout(FlowLayout.CENTER));
		countdown.setHorizontalAlignment(JTextField.RIGHT);
		timerPanel.add(countdown);
		
		JPanel studyPanel = new JPanel();
		JLabel URL = new JLabel("URL");
		webLink = new JTextField(20);
		JButton save = new JButton("Save");
		studyPanel.add(URL);
		studyPanel.add(webLink);
		studyPanel.add(save);
		
		JPanel deck = new JPanel(new CardLayout());
		this.add(deck, BorderLayout.CENTER);
		deck.add(clockPanel, "clock");
		deck.add(alarmPanel, "alarm");
		deck.add(timerPanel, "timer");
		deck.add(studyPanel, "study");
	
	alarmButton.addActionListener(new ActionListener(){
		
		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout cl = (CardLayout) (deck.getLayout());
			cl.show(deck, "alarm");
		}
	});
	
	timerButton.addActionListener(new ActionListener(){
		
		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout cl = (CardLayout) (deck.getLayout());
			cl.show(deck, "timer");
		}
	});
	
	studyButton.addActionListener(new ActionListener(){
		
		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout cl = (CardLayout) (deck.getLayout());
			cl.show(deck, "study");
		}
	});
	
	selection.addActionListener(new ActionListener(){
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String value = (String)selection.getSelectedItem();
			choice = 0;
			choice = value.equals("PM") ? 1 : 0;
		}
	});
	
	save.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout cl = (CardLayout) (deck.getLayout());
			cl.show(deck, "clock");
	
			test = webLink.getText();
		}
	});
	
	doneButton.addActionListener(new ActionListener(){
		
		Boolean on = false;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout cl = (CardLayout) (deck.getLayout());
			cl.show(deck, "clock");	
			
			Runnable getAlarm = () -> {
				
				while (true) {
						
					try {
						String alarmHour = hourField.getText();
						String alarmMinute = minuteField.getText();
						Integer setAlarmHour = Integer.parseInt(alarmHour);
						Integer setAlarmMinute = Integer.parseInt(alarmMinute);
							
						Calendar time= Calendar.getInstance();
						int hour = time.get(Calendar.HOUR);
						int minute = time.get(Calendar.MINUTE);
						int dayornight = time.get(Calendar.HOUR_OF_DAY);
						
						if (hour == 0) {
							hour = 12;
						}
						
						if ((dayornight < 12 && choice == 0) || (dayornight > 12 && choice == 1)) {
							on = true;
						}
							while (setAlarmHour == hour && setAlarmMinute == minute && on == true) {	
								setAlarm();
							}	
							
						} catch (java.lang.NumberFormatException | java.lang.NullPointerException g) {
							JOptionPane.showMessageDialog(null, "Please Enter a Valid Time");
							break;
							
						} catch (IOException | URISyntaxException e1) {
							e1.printStackTrace();
						}
					}
				};
				
		s.stop();
		Thread alarm = new Thread(getAlarm);
		alarm.start();
			
		};
	});
	
	setButton.addActionListener(new ActionListener(){
		
		@Override
		public void actionPerformed(ActionEvent e) {
		
			try{
	 			timerMinute = timerEntry.getText();
	 			countdownMinute = Integer.parseInt(timerMinute);
	 			countdownSecond = 0;
	 			
		 			if (countdownMinute < 0 || countdownMinute > 1440) {
		 	 			setTime(countdown, 0, 0);
		 	 			JOptionPane.showMessageDialog(null, "Please Enter a Time Greater than 0 but less than 1440 minutes");
		 	 		}
		 			
		 			else {
		 				setTime(countdown, countdownMinute, countdownSecond);
		 			}
	 			
	 			} catch(java.lang.NumberFormatException | java.lang.NullPointerException d) {
	 				setTime(countdown, 0, 0);
	 				JOptionPane.showMessageDialog(null, "Please Enter a Number for the Timer");
	 			}
	 		
	 			setButton.setText("Reset");
			}
		});
	
	
	Clock.setTimer myTimer = new setTimer();
	startButton.addActionListener(myTimer);
	s = new Timer(1000, myTimer);
	

	pauseButton.addActionListener(new ActionListener(){
		
		@Override
		public void actionPerformed(ActionEvent e) {
			s.stop();
			setTime(countdown, countdownMinute, countdownSecond);
			}
		});
	}

	class setTimer implements ActionListener{

		Boolean alarm = false;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			s.start();
	
			try{
				timerMinute = timerEntry.getText();

				if (countdownMinute < 0 || countdownMinute > 1440) {
					s.stop();
					setTime(countdown, 0, 0);
					countdownSecond++;
				}
				
				else if (countdownMinute > 0 && countdownSecond == 0) {
					countdownSecond = 60;
					countdownMinute--;
					alarm = true;
				}
	
				countdownSecond--;
	
				setTime(countdown, countdownMinute, countdownSecond);
	
				while (countdownSecond == 0 && countdownMinute == 0 && alarm==true) {			
					setAlarm();
				}
	
			} catch(java.lang.NumberFormatException | java.lang.NullPointerException d) {
				s.stop();
				setTime(countdown, 0, 0);
				JOptionPane.showMessageDialog(null, "Please Enter a Valid Time");
				
			} catch (IOException | URISyntaxException e1) {
				e1.printStackTrace();
			}
		}	
}
	
	class TimeView implements ActionListener{
		
		Calendar time= Calendar.getInstance();
		int hour = time.get(Calendar.HOUR);
		int minute = time.get(Calendar.MINUTE);
		int dayornight = time.get(Calendar.HOUR_OF_DAY);
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (hour == 0) hour = 12;
			
			String ampm = dayornight < 12 ? "AM" : "PM";
			setTime(clocktimeField, hour, minute, ampm);
		}
	}
	
	public void setTime(JTextField field, int hour, int min, String ampm) {		
		field.setText(String.format("%01d:%02d %s", hour, min, ampm));
	}
	
	public void setTime(JTextField field, int hour, int min) {		
		field.setText(String.format("%01d:%02d", hour, min));
	}

	public void setAlarm() throws IOException, URISyntaxException {
		
		Runnable r = () -> {
			while (true) {
				Toolkit.getDefaultToolkit().beep();
			}
		};

		s.stop();
		Thread t = new Thread(r);
		t.start();
		
		JOptionPane.showMessageDialog(null, "Time's Up!");
		
		if (!(webLink.getText().equals(""))) {
			java.awt.Desktop.getDesktop().browse(new URI(test));
		}
		
		System.exit(0);
	}	
}
