package edu.cooper.ee.se.sp17.client;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class SetClient {

	   private JFrame mainFrame;
	   private JLabel headerLabel;
	   private JLabel statusLabel;
	   private JPanel controlPanel;
	   private JLabel nameLabel;
	   private JLabel passwordLabel;
	   private Socket socket;
	   private String statusText;
	   
	   
	   public SetClient(){
	      try{
	    	  if(establishSocket("199.98.20.117", 6666)){
	    		  prepareGUI();
	    	  }
	    	  else{
	    		  System.exit(-1);
	    	  }
	      }
	      catch(Exception e){
	    	  System.exit(-1);
	      }
	   }
	   private boolean establishSocket(String address, Integer port){
		   try{
			   	 System.out.println("Connecting to " + address + " on port " + port);
		         socket = new Socket(address, port);
		   }
		   catch(Exception e){
			   System.out.println("error establishSocket");
			   e.printStackTrace();
			   return false;
		   }
		   return true;
	   }
	   public static void main(String[] args){
		   SetClient  swingControlDemo = new SetClient();
		   swingControlDemo.showLogin();
	   }
	   private void prepareGUI(){
	      mainFrame = new JFrame("");
	      mainFrame.setSize(400,400);
	      mainFrame.setLayout(new GridLayout(3, 1));
	      
	      mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });    
	      headerLabel = new JLabel("", JLabel.CENTER);        
	      statusLabel = new JLabel("",JLabel.CENTER);    
	      statusLabel.setSize(350,100);

	      controlPanel = new JPanel();
	      controlPanel.setLayout(new FlowLayout());

	      mainFrame.add(headerLabel);
	      mainFrame.add(controlPanel);
	      mainFrame.add(statusLabel);
	      mainFrame.setVisible(true);  
	   }
	   
	   private void sendServerMessage(String action, String param1, String param2, String param3){
		   //Send the message to the server
		   String message = "";
		   try{
			   OutputStream os = socket.getOutputStream();
			   OutputStreamWriter osw = new OutputStreamWriter(os);
	           BufferedWriter bw = new BufferedWriter(osw);
	           
	           switch(action){
			   case "LOGIN":
				   message = "LOGIN " + param1 + " " + param2;
				   break;
			   case "LOGOUT":
				   message = "LOGOUT";
				   break;
			   case "WHOAMI":
				   message = "WHOAMI";
				   break;
			   default:
				   System.out.println("Doing default, so nothing");
				break;
			   }
	           
	           String sendMessage = message  + "\n";
               bw.write(sendMessage);
               bw.flush();
               System.out.println("Message sent to the server : "+sendMessage);

               //Get the return message from the server
               InputStream is = socket.getInputStream();
               InputStreamReader isr = new InputStreamReader(is);
               BufferedReader br = new BufferedReader(isr);
               String readMessage = br.readLine();
               
               switch(action){
			   case "LOGIN":
				   statusText = readMessage;
				   break;
			   case "LOGOUT":
				   statusText = readMessage;
				   break;
			   case "WHOAMI":
				   statusText = readMessage;
				   break;
			   default:
				   System.out.println("Doing default, so nothing");
				break;
			   }
               System.out.println("Message received from the server : " +readMessage);
//            socket.close();
		   }
		   catch(Exception e)
		   {
			   System.out.println("Unable to establish socket stream");
			   return;
		   }
	   }
	   private void logIn(String username, String password){
		   sendServerMessage("LOGIN", username, password, "");
		   System.out.println("done logIn");
	   }
	   private void logOut(){
		   sendServerMessage("LOGOUT", "", "", "");
		   System.out.println("done logout");
	   }
	   private void whoAmI(){
		   sendServerMessage("WHOAMI", "", "", "");
		   System.out.println("done whoAmI");
	   }
	   private void updateStatusLabel(){
		   statusLabel.setText(statusText);
	   }
	   private void showLogin(){
		  sendServerMessage("ServerStart", "", "", "");
	      headerLabel.setText("Swing SET GUI"); 

	      nameLabel = new JLabel("User ID: ", JLabel.RIGHT);
	      passwordLabel = new JLabel("Password: ", JLabel.CENTER);
	      final JTextField userText = new JTextField(6);
	      final JPasswordField passwordText = new JPasswordField(6);      

	      JButton loginButton = new JButton("Login");
	      JButton logoutButton = new JButton("Logout");
	      JButton whoAmIButton = new JButton("Who Am I");
	      loginButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {     
	        	String username = userText.getText();
	        	String password = passwordText.getText();
	            logIn(username, password);
	            updateStatusLabel();        
	         }
	      }); 
	      logoutButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {     
	        	 logOut();
	        	 updateStatusLabel();
	         }
	      }); 
	      whoAmIButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {     
		        	 whoAmI();
		        	 updateStatusLabel();
		         }
		      }); 
	      controlPanel.add(nameLabel);
	      controlPanel.add(userText);
	      controlPanel.add(passwordLabel);       
	      controlPanel.add(passwordText);
	      controlPanel.add(loginButton);
	      controlPanel.add(logoutButton);
	      controlPanel.add(whoAmIButton);
	      mainFrame.setVisible(true);
	   }
	
}