package edu.cooper.ee.se.sp17.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GameListPane extends JPanel {
	private Font f = new Font("Tahoma", Font.BOLD, 20);
	
	public GameListPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Color.GRAY);
	}
	
	public void refresh(){
		SetClient.client.send("GAMES\r\n");
		String rply = SetClient.client.recv();
		removeAll();
		
		if(rply.startsWith("--END--")){
			JLabel jl = new JLabel("No games");
			jl.setFont(f);
			add(jl);
		}else{
			while(!rply.startsWith("--END--")){
				JLabel gl = new JLabel(rply);
				gl.setFont(f);
				gl.setBorder(BorderFactory.createEmptyBorder(3, 1, 3, 1));
				add(gl);

				gl.addMouseListener(new MouseListener(){
					@Override
					public void mouseClicked(MouseEvent arg0) {
						JLabel jl = (JLabel)arg0.getSource();
						SetClient.client.send(String.join(" ", "JOIN", jl.getText().split(":")[0], "\r\n"));
						String msg = SetClient.client.recv();
						JOptionPane.showMessageDialog(jl.getRootPane(), msg);
						
						if(msg.equals("Game is already full")){
							return;
						}
						
						int gid = Integer.parseInt(jl.getText().split(":")[0]);
						SetClient.client.send("GAMES\r\n");
						msg = SetClient.client.recv();
						String name = "";
						while(!msg.startsWith("--END--")){
							if(msg.startsWith(String.valueOf(gid))){
								name = msg;
							}
							msg = SetClient.client.recv();
						}
						
						GameFrame game = new GameFrame(name, gid);
						LobbyFrame l = (LobbyFrame)SwingUtilities.getWindowAncestor((Component) arg0.getSource());
						l.close();
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {}
					@Override
					public void mouseExited(MouseEvent arg0) {}
					@Override
					public void mousePressed(MouseEvent arg0) {}
					@Override
					public void mouseReleased(MouseEvent arg0) {}
				});
				rply = SetClient.client.recv();
			}
		}
		revalidate();
		repaint();
	}
}
