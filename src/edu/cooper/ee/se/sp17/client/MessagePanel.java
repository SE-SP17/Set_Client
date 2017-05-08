package edu.cooper.ee.se.sp17.client;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Dimension;

public class MessagePanel extends JPanel {

	JTextArea screen;
	public MessagePanel() {		
		this.setPreferredSize(new Dimension(400, 800));
		
		screen = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(screen); 
		scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(400, 700));
		screen.setEditable(false);
		
		this.add(scrollPane);
	}
	
	public void addMessage(String m){
		try {
			screen.append(m+"\n");
		}
		catch(Exception e) { System.out.println(e); }
	}
}