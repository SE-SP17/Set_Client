package edu.cooper.ee.se.sp17.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Listener implements Runnable {

	BufferedReader br;
	ArrayList<String> lines;
	
	public Listener(BufferedReader s_in) {
		br = s_in;
		lines = new ArrayList<String>();
	}

	@Override
	public void run() {
		while(true){
			try{
				String m = br.readLine();
				System.out.println(m);
				// NOMORE, START, 
				if(m.startsWith("Player") || m.startsWith("Game started") || m.startsWith("You can't")){
					
					lines.add(m);
					
					// Is message being read too slow? 
					//shows empty message and hangs if JOption pane is init here
					//JOptionPane.showMessageDialog(null, m);
					
				}
				// END
				else if(m.endsWith("ended the game")){ 
					String msg = "";
						while(!m.startsWith("--END--")){
							msg += m+"\n";
							m = br.readLine();
						}
					System.out.println("Listener messsage: " + msg);
					JOptionPane.showMessageDialog(null, msg);
				}
				// END or LEAVE if only player
				else if(m.startsWith("Game has ended")){
					String msg = "";
					while(!m.startsWith("--END--")){
						msg += m+"\n";
						m = br.readLine();
					}
				System.out.println("Listener messsage: " + msg);
//				JOptionPane.showMessageDialog(null, msg);
				}
				else{
					lines.add(m);
				}
			}catch(IOException e){
				System.err.println("Cannot talk to the server!");
				System.exit(-2);
			}
		}
	}

	synchronized public String readline() {
		if(lines.size() != 0){
			System.out.println("reading line: " + lines.get(0));
			return lines.remove(0);
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return readline();
	}

}
