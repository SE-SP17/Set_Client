package edu.cooper.ee.se.sp17.client;


import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameFrame extends JFrame {
	int gid;
	JButton btn_leave, btn_start, btn_end, btn_nomore;
	GamePanel gp;
	
	public GameFrame(String title, int gid) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = getContentPane();
		setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		JPanel menu = new JPanel(new FlowLayout());
		menu.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		contentPane.add(menu);
		
		btn_leave = new JButton("LEAVE");
		menu.add(btn_leave);
		btn_start = new JButton("START");
		menu.add(btn_start);
		btn_end = new JButton("END");
		menu.add(btn_end);
		btn_nomore = new JButton("NOMORE");
		menu.add(btn_nomore);
		
		gp = new GamePanel();
		gp.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		contentPane.add(gp);
		
		//check if board returns anything
		String m = SetClient.client.send("BOARD\r\n");
		if(m.equals("You're not playing a game")){
			gp.setEnabled(false);
		}
		else{
			gp.setEnabled(true);
			gp.fillBoard();
		}
		System.out.println(m);

		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		btn_leave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String m = SetClient.client.send("LEAVE\r\n");
				JOptionPane.showMessageDialog(gp.getRootPane(), m);
				LobbyFrame l = new LobbyFrame("Set Game Client 0xFF", SetClient.client.send("WHOAMI\r\n"));
				close();
			}
		});
		btn_start.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String m = SetClient.client.send("START\r\n");
				JOptionPane.showMessageDialog(gp.getRootPane(), m);
				if(m.equals("Game started!")){
					System.out.println(m);
					gp.setEnabled(true);
					gp.fillBoard();
				}
			}
		});	
	}

	public void close(){
		setVisible(false);
		dispose();
	}

}
