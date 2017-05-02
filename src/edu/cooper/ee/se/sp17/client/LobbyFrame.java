package edu.cooper.ee.se.sp17.client;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class LobbyFrame extends JFrame {
	public LobbyFrame(String title){
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel jl = new JLabel("TESTasdf	ASDHFJKASDAJKLSDFHASJKLDHDFJKLAHSDKLF");
		getContentPane().add(jl, BorderLayout.CENTER);
		
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
}
