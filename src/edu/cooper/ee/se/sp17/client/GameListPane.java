package edu.cooper.ee.se.sp17.client;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameListPane extends JPanel {

	public GameListPane() {
		setLayout(new FlowLayout());
		// TODO Auto-generated constructor stub
	}
	
	public void refresh(){
		String games = SetClient.client.send("GAMES\r\n");
		String g[] = games.split("\n");
		for(String cg : g){
			JLabel gl = new JLabel(cg);
			//gl.setFont(new Font("Arial", 18, Font.BOLD));
			add(gl);
		}
		// TODO
	}
}
