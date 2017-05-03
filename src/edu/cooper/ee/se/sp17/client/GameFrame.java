package edu.cooper.ee.se.sp17.client;


import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
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
	JButton btn_leave, btn_start, btn_end;
	GamePanel gp;
	JButton btn_nomore, btn_set;
	
	public GameFrame(String title, int gid) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = getContentPane();
		setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		JPanel top_menu = new JPanel(new FlowLayout());
		// top menu controls - control
		top_menu.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		contentPane.add(top_menu);
		
		btn_leave = new JButton("LEAVE");
		top_menu.add(btn_leave);
		btn_start = new JButton("START");
		top_menu.add(btn_start);
		btn_end = new JButton("END");
		top_menu.add(btn_end);
		
		
		//game display
		gp = new GamePanel();
		gp.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		contentPane.add(gp);
		
		//check if board returns anything to see if game has started
		String m = SetClient.client.send("BOARD\r\n");
		if(m.equals("You're not playing a game")){
			gp.setEnabled(false);
		}
		else{
			gp.setEnabled(true);
			gp.fillBoard();
		}
		System.out.println(m);
		
		JPanel bot_menu = new JPanel(new FlowLayout());
		//bottom menu controls - gameplay
		bot_menu.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		contentPane.add(bot_menu);
		
		btn_set = new JButton("SET");
		bot_menu.add(btn_set);
		btn_nomore = new JButton("NOMORE");
		bot_menu.add(btn_nomore);
		
		//set screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension pref = new Dimension((int)screenSize.getHeight()/2, (int)screenSize.getHeight()/4*3);
		contentPane.setPreferredSize(pref);

		pack();
		setLocationRelativeTo(null);
		setResizable(true);
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
		btn_set.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(gp.selected == 3){
					int[] select = gp.getSelectedCards();
					
					String m = SetClient.client.send("SET "+
							select[0]+" "+
							select[1]+" "+
							select[2]+" "+
							"\r\n");
					JOptionPane.showMessageDialog(gp.getRootPane(), m);
					if(m.startsWith("Cards ")){
						gp.unselect(select);
						
					}
					if(m.startsWith("Player ")){
						gp.unselect(select);
						gp.fillBoard();
						
					}
					//TODO handle scoring, update board
				}
			}
		});	
		
		btn_nomore.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String m = SetClient.client.send("NOMORE\r\n");
				//TODO display when other players call nomore, 
				//update board with more cards when everyone calls it
			}
		});	
	}

	public void close(){
		setVisible(false);
		dispose();
	}

}
