package edu.cooper.ee.se.sp17.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Listener implements Runnable {

	BufferedReader br;
	ArrayList<String> lines;
	ArrayList<String> messages;
	
	public Listener(BufferedReader s_in) {
		br = s_in;
		lines = new ArrayList<String>();
		messages = new ArrayList<String>();
	}

	@Override
	public void run() {
		while(true){
			try{
				String m = br.readLine();
				System.out.println("Listener: " + m);
				// START - started, START - you do not own
				if( m.startsWith("Game started") || m.startsWith("You can't")){
					
					messages.add(m);
					
					// Is message being read too slow? 
					//shows empty message and hangs if JOption pane is init here
					//JOptionPane.showMessageDialog(null, m);
					
				}
				// someone called NOMORE
				else if(m.endsWith("there are NO MORE sets")){
					messages.add(m);
				}
				// New player joined the game
				else if(m.endsWith("has joined the game")){
					//update GameFrame title
					
					//show message
					messages.add(m);
					
				}
				//Player left the game
				else if(m.endsWith("has left the game")){
					messages.add(m);
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
					messages.add(msg);
				}
				// END or LEAVE if game master leaves
				else if(m.startsWith("Game has ended")){
					String msg = "";
					m = br.readLine();
					while(!m.startsWith("--END--")){
						msg += m+"\n";
						m = br.readLine();
					}
				System.out.println("Listener messsage: " + msg);
//				JOptionPane.showMessageDialog(null, msg);
				messages.add(m);
				}
				// a player called a SET
				else if(m.startsWith("Player") && m.endsWith(")")){
					messages.add(m);
				}
				// Player sent a message
				else if(m.startsWith("From ")){
					messages.add(m);
				}
				// Game ended due to win/out of cards
				else if(m.startsWith("There was no winner") || m.contains(" Won!")){
						String msg = "";
						while(!m.startsWith("--END--")){
							msg += m+"\n";
							m = br.readLine();
						}
					System.out.println("Listener messsage: " + msg);
					JOptionPane.showMessageDialog(null, msg);
					messages.add(msg);
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
//			System.out.println("reading line: " + lines.get(0));
			return lines.remove(0);
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return readline();
	}
	
	synchronized public String readMessage() {
		if(messages.size() != 0){
			System.out.println("reading messsage: " + messages.get(0));
			return messages.remove(0);
		}
		return "";
		
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		return readMessage();
	}

}
