package edu.cooper.ee.se.sp17.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameListPane extends JPanel {
	private LobbyFrame l;
	private Font f = new Font("Tahoma", Font.BOLD, 20);
	
	public GameListPane(LobbyFrame lf) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setBackground(Color.GRAY);
		l = lf;
	}
	
	public void refresh(){
		String games = SetClient.client.send("GAMES\r\n");
		String g[] = games.split("\n");
		
		removeAll();
		if(g.length == 1){
			add(new JLabel("No games"));
		}else{
			for(int i = 0; i < g.length-1; i++){
				JLabel gl = new JLabel(g[i]);
				gl.setFont(f);
				gl.setBorder(BorderFactory.createEmptyBorder(3, 1, 3, 1));
				add(gl);

				gl.addMouseListener(new MouseListener(){
					@Override
					public void mouseClicked(MouseEvent arg0) {
						JLabel jl = (JLabel)arg0.getSource();
						String m = SetClient.client.send(String.join(" ", "JOIN", jl.getText().split(":")[0], "\r\n"));
						JOptionPane.showMessageDialog(jl.getRootPane(), m);
						if(m.equals("Game is already full")){
							return;
						}
						
						int gid = Integer.parseInt(jl.getText().split(":")[0]);
						String gs = SetClient.client.send("GAMES\r\n");
						String u = "";
						for(String g : gs.split("\n")){
							if(g.startsWith(String.valueOf(gid)+": ")){
								u = g;
							}
						}
						GameFrame game = new GameFrame(u, gid);
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
			}
		}
		revalidate();
		repaint();
		l.resize(); // TODO
	}
}
