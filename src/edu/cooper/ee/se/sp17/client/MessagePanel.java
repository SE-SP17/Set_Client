package edu.cooper.ee.se.sp17.client;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessagePanel extends JPanel {

	JLabel scores;
	JTextArea screen;
	JTextField textbox;
//	GridLayout layout;
	public MessagePanel() {		
		this.setPreferredSize(new Dimension(400, 800));
		
//		layout = new GridLayout(3, 1);
//		setLayout(layout);
		
		screen = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(screen); 
		scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(400, 700));
		screen.setEditable(false);
		
		this.add(scrollPane);
		
		JPanel messenger = new JPanel(new FlowLayout());
		messenger.setPreferredSize(new Dimension(400, 100));
		this.add(messenger);
		
		textbox = new JTextField();
		textbox.setColumns(30);
		messenger.add(textbox);
		
		JButton btn_send = new JButton("Send");
		messenger.add(btn_send);
		
		btn_send.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				SetClient.client.send(String.join(" ", "MSG", textbox.getText(), "\r\n"));
				//clear textbox
				textbox.setText("");
			}
		});
		
		scores = new JLabel("          ");
		this.add(scores);
		
	}
	
	public void addMessage(String m){
		try {
			screen.append(m+"\n");
		}
		catch(Exception e) { System.out.println(e); }
	}
	
	public void setScores(String m){
		scores.setText(m);
	}
}