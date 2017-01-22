package com.kelyz.y.zhang;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class ClockGUI {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(() -> {
			JFrame frame = new Clock();
	     		frame.setTitle("Study Alarm");
	     		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     		frame.setVisible(true);
	     		frame.setResizable(false);
		});
	}
}
