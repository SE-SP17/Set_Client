package edu.cooper.ee.se.sp17.client;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	int gid;
	public GameFrame(String title, int gid) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack();
		setLocationRelativeTo(null);
		//setResizable(false);
		setVisible(true);
	}

}
