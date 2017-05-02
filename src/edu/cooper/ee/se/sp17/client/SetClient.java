package edu.cooper.ee.se.sp17.client;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class SetClient {
	
	final static String HOST = "199.98.20.117";
	final static int PORT = 6666;
	
	public static SetClient client;
	private Socket server;
	private BufferedReader s_in;
	private DataOutputStream s_out;
	
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
		
		LoginFrame login = new LoginFrame("Set Game Client 0xFF");
		
		/** Testing **/
		try {
			System.out.println(s_in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	/* TODO Send command to server and return output */
	public String send(String cmd){
		String o = "";
		try{
			s_out.write(cmd.getBytes());
			s_out.flush();
			return s_in.readLine(); /* change this line depending on command */
		}catch(IOException e){
			System.err.println("Cannot talk to the server!");
			System.exit(-2);
		}
		return o;
	}
}