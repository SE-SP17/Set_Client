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
				if(m.startsWith("Player") || m.startsWith("Game started") || m.startsWith("You can't")){
					JOptionPane.showMessageDialog(null, m);
				}else if(m.endsWith("ended the game")){
					String msg = "";
						while(!m.startsWith("--END--")){
							msg += m+"\n";
							m = br.readLine();
						}
						JOptionPane.showMessageDialog(null, msg);
				}else{
					lines.add(m);
				}
			}catch(IOException e){
				System.err.println("Cannot talk to the server!");
				System.exit(-2);
			}
		}
	}

	synchronized public String readline() {
		if(lines.size() != 0)
			return lines.remove(0);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return readline();
	}

}
