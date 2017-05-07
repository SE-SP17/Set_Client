package edu.cooper.ee.se.sp17.client;

import java.awt.Container;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.TimerTask;
import java.util.Timer;

public class GameFrame extends JFrame {
	int gid;
	JButton btn_leave, btn_start, btn_end;
	GamePanel gp;
	JButton btn_nomore, btn_set, btn_refresh;
	Timer timer;

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

		// game display
		JPanel p = new JPanel(new FlowLayout());
		contentPane.add(p);

		gp = new GamePanel();
		gp.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		p.add(gp);

		// TODO SCORES
		JLabel lbl_scores = new JLabel("    ");
		p.add(lbl_scores);

		// bottom menu controls - gameplay
		JPanel bot_menu = new JPanel(new FlowLayout());
		bot_menu.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		contentPane.add(bot_menu);

		btn_set = new JButton("SET");
		bot_menu.add(btn_set);
		btn_nomore = new JButton("NOMORE");
		bot_menu.add(btn_nomore);
		btn_refresh = new JButton("Refresh");
		bot_menu.add(btn_refresh);

		pack();
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);

//		// check if board returns anything to see if game has started
//		SetClient.client.send("BOARD\r\n");
//		String rply = SetClient.client.recv();
//		StringBuffer board = new StringBuffer();
//		if (rply.equals("You're not playing a game")) {
//			gp.setEnabled(false);
//		} else {
//			// Clear out read buffer
//			while (!rply.startsWith("--END--")) {
//				rply = SetClient.client.recv();
//				board.append(rply);
//			}
//			gp.setEnabled(true);
//			gp.refresh(board.toString());
//		}

		btn_leave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.cancel();
				timer.purge();
				SetClient.client.send("LEAVE\r\n");
				String rply = SetClient.client.recv();

				// Clear out read buffer
				while (!rply.equals("You left a game")) {
					rply = SetClient.client.recv();
				}

				JOptionPane.showMessageDialog(gp.getRootPane(), rply);

				SetClient.client.send("WHOAMI\r\n");
				LobbyFrame l = new LobbyFrame("Set Game Client 0xFF", SetClient.client.recv());
				close();
			}
		});

		btn_start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SetClient.client.send("START\r\n");
//				String rply = SetClient.client.recv();
				// JOptionPane.showMessageDialog(gp.getRootPane(), rply);
//				System.out.println(rply);

//				if (rply.equals("Game started!")) {
//					gp.setEnabled(true);
//					gp.refresh();
//				}
			}
		});

		btn_end.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SetClient.client.send("END\r\n");
				String rply = SetClient.client.recvprev();

				String msg = "";
				if (rply.equals("You're not playing a game")) {
					msg = rply;
					JOptionPane.showMessageDialog(getRootPane(), msg);
				}
				gp.setEnabled(false);
			}
		});

		btn_refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				SetClient.client.send("BOARD\r\n");
//				String rply = SetClient.client.recv();
//				if (!rply.equals("You're not playing a game")) {
//					// Clear out read buffer
//					while (!rply.startsWith("--END--")) {
//						rply = SetClient.client.recv();
//					}
//					gp.refresh();
//				}
			}
		});

		btn_set.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gp.selected == 3) {

					SetClient.client.send("SET " + gp.getSelectedCards() + "\r\n");
//					String m = SetClient.client.recv();

//					JOptionPane.showMessageDialog(gp.getRootPane(), m);
//					gp.refresh();

					// TODO handle scoring, update board
				}
			}
		});

		btn_nomore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SetClient.client.send("NOMORE\r\n");
				// TODO display when other players call nomore,
				// update board with more cards when everyone calls it
//				String m = SetClient.client.recv();
//				JOptionPane.showMessageDialog(gp.getRootPane(), m);
//				if (equals("You're not playing a game")) {
//					return;
//				}
//
//				else if (m.startsWith("Player")) {
//
//					gp.refresh();
//				}
			}
		});

		timer = new Timer();
		timer.scheduleAtFixedRate(new UpdateTimerTask(), 0, 1000); // run every one second
																
	}

	public void close() {
		setVisible(false);
		dispose();
	}

	private class UpdateTimerTask extends TimerTask {
		public void run() {
			System.out.println("timer");

			// check if board returns anything to see if game has started
			SetClient.client.send("BOARD\r\n");
			String rply = SetClient.client.recv();
			StringBuffer board = new StringBuffer();
			if (rply.equals("You're not playing a game")) {
				gp.setEnabled(false);
			} else {
				// Clear out read buffer
				while (!rply.startsWith("--END--")) {
					board.append(rply+"\n");
					rply = SetClient.client.recv();
				}
				gp.setEnabled(true);
				gp.refresh(board.toString());
			}
			
			//check for any messages from other clients for gamestate or player changes
			String msg = SetClient.client.readMessage();
			// START - started, START - you do not own
			if( msg.startsWith("Game started") || msg.startsWith("You can't")){
				
				System.out.println(msg);
				
			}
			// someone called NOMORE
			else if(msg.endsWith("there are NO MORE sets")){
				System.out.println(msg);
			}
			// New player joined the game
			else if(msg.endsWith("has joined the game")){
				// GameFrame title
				
				//show message
				System.out.println(msg);
				
			}
			// a player called a SET
			else if(msg.endsWith(")")){
				System.out.println("set detected: " + msg);
			}
			else{
				System.out.println("else: "+msg);
			}
			
			
			
		}
	}

}
