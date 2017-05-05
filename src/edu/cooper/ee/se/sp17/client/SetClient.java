package edu.cooper.ee.se.sp17.client;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class SetClient {
	
	final static String HOST = "199.98.20.117";
//	final static String HOST = "localhost";
	final static int PORT = 6666;
	
	public static SetClient client;
	private Socket server;
	private BufferedReader s_in;
	private DataOutputStream s_out;
	private LoginFrame login;
	
	public SetClient(String host, int port){
		System.out.println("Attempting to connect to the game server...");
		try {
			server = new Socket(host, port);
			s_in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			s_out = new DataOutputStream(server.getOutputStream());
		} catch (UnknownHostException e) {
			System.err.println("Error: Host not found!");
			System.exit(-1);
		} catch (IOException e) {
			System.err.println("Error: Could not connect!");
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println("Connected!");
		
		try{
			System.out.println("Server says " + s_in.readLine());
		}catch(IOException e){
			System.err.println("Cannot talk to server");
			e.printStackTrace();
			System.exit(-2);
		}
		
		login = new LoginFrame("Set Game Client 0xFF");
	}
	
	public static void main(String[] args){
		if(args.length < 3){
			System.err.println("Not enough arguments provided.");
			System.err.println("Defaulting to sable08.ee.cooper.edu:6666");
			client = new SetClient(HOST, PORT);
		}else{
			client = new SetClient(args[1], Integer.parseInt(args[2]));
		}
	}
	
	public String recv(){
		try{
			return s_in.readLine();
		}catch(IOException e){
			System.err.println("Cannot talk to the server!");
			System.exit(-2);
		}
		return null;
	}
	
	public void send(String cmd){
		try{
			s_out.write(cmd.getBytes());
			s_out.flush();

			// Add Board/Set/Etc
//			String o = s_in.readLine();
//			if(o.startsWith("From")){
//				JOptionPane.showMessageDialog(login.getRootPane(), o);
//				return s_in.readLine();
//			}
//			} else if(cmd.toUpperCase().startsWith("BOARD")){
//				String tmp = o;
//				
//				while(!tmp.startsWith("--END--")){
//					if(tmp.equals("You're not playing a game")){
//						return o;
//					} 
//					tmp = s_in.readLine();
//					System.out.println(tmp);
//					o += "\n" + tmp;
//				}
//				System.out.println("returning board");
//				return o;
//			}
//			return o;
		}catch(IOException e){
			System.err.println("Cannot talk to the server!");
			System.exit(-2);
		}
	}
}