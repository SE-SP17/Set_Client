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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetClient {

	   private JFrame mainFrame;
	   private JLabel headerLabel;
	   private JLabel statusLabel;
	   private JPanel controlPanel;
	   private JLabel nameLabel;
	   private JLabel passwordLabel;
	   private JLabel createGameLabel;
	   private JLabel joinGameLabel;
	   private Socket socket;
	   private String statusText;
	   
	   
	   public SetClient(){
	      try{
	    	  if(establishSocket("localhost", 6666)){
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
			   statusText = "Connecting to " + address + " on port " + port;
//			   updateStatusLabel();
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
	           System.out.println("Command Entered: " + action);
	           switch(action){
			   case "LOGIN":
				   message = "LOGIN " + param1 + " " + param2;
				   break;
			   case "REGISTER":
				   message = "REGISTER " + param1 + " " + param2;
				   break;
			   case "LOGOUT":
				   message = "LOGOUT";
				   break;
			   case "WHOAMI":
				   message = "WHOAMI";
				   break;
			   case "GAMES":
				   message = "GAMES";
				   break;
			   case "CREATE":
				   if(param1 == ""){
					   message = "CREATE";
				   }
				   else{
					   message = "CREATE " + param1;
				   }
				   break;
			   case "LEAVE":
				   message = "LEAVE";
				   break;
			   case "JOIN":
				   message = "JOIN " + param1;
				   break;
			   case "START":
				   message = "START";
				   break;
			   case "LIST":
				   message = "LIST";
				   break;
			   case "MSG":
				   message = "MSG " + param1 + " " + param2;
				   break;
			   case "BOARD":
				   message = "BOARD";
				   break;
			   case "SET":
				   message = "SET " + param1 + " " + param2 + " " + param3;
				   break;
			   case "SCORE":
				   message = "SCORE";
				   break;
			   case "END":
				   message = "END";
				   break;
			   case "NOMORE":
				   message = "NOMORE";
				   break;
			   case "BYEBYE":
				   message = "BYEBYE";
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
			   case "REGISTER":
				   statusText = readMessage;
				   break;
			   case "GAMES":
				   statusText = readMessage;
				   break;
			   case "CREATE":
				   statusText = readMessage;
				   break;
			   case "LEAVE":
				   statusText = readMessage;
				   break;
			   case "JOIN":
				   statusText = readMessage;
				   break;
			   case "START":
				   statusText = readMessage;
				   break;
			   case "LIST":
				   statusText = readMessage;
				   break;
			   case "MSG":
				   statusText = readMessage;
				   break;
			   case "BOARD":
				   for(int i=0;i<12;i++){
					   readMessage = readMessage.replaceAll("[^0-9]+", " ");
					   String[] cardProperties = readMessage.trim().split(" ");
					   for(int j=0;j<cardProperties.length; j++){
						   if(i == 11 && cardProperties[0] != "11"){
							   readMessage = "Invalid board";
						   }
					   }
					   System.out.println("done" + i);
					   
					   statusText += readMessage + " ";
					   readMessage = br.readLine();
					   System.out.println(readMessage);
				   }
				   break;
			   case "SET":
				   statusText = readMessage;
				   break;
			   case "SCORE":
				   statusText = readMessage;
				   break;
			   case "END":
				   statusText = readMessage;
				   break;
			   case "NOMORE":
				   statusText = readMessage;
				   break;
			   case "BYEBYE":
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
		   }
		   catch(Exception e)
		   {
			   System.out.println("Unable to establish socket stream");
			   return;
		   }
	   }
	   private void logIn(String username, String password){
		   sendServerMessage("LOGIN", username, password, "");
	   }
	   private void logOut(){
		   sendServerMessage("LOGOUT", "", "", "");
	   }
	   private void whoAmI(){
		   sendServerMessage("WHOAMI", "", "", "");
	   }
	   private void updateStatusLabel(){
		   statusLabel.setText(statusText);
	   }
	   private void showLogin(){
		  sendServerMessage("ServerStart", "", "", "");
	      headerLabel.setText("Swing SET GUI"); 

	      nameLabel = new JLabel("User ID: ", JLabel.RIGHT);
	      passwordLabel = new JLabel("Password: ", JLabel.CENTER);
	      createGameLabel = new JLabel("Game Capacity: ", JLabel.CENTER);
	      joinGameLabel = new JLabel("Game Id: ", JLabel.CENTER);
	      final JTextField userText = new JTextField(6);
	      final JPasswordField passwordText = new JPasswordField(6);
	      final JTextField gameCapacity = new JTextField(6);
	      final JTextField gameId = new JTextField(6);

	      JButton loginButton = new JButton("Login");
	      JButton logoutButton = new JButton("Logout");
	      JButton whoAmIButton = new JButton("Who Am I");
	      
	      JButton gamesButton = new JButton("Games");
	      JButton createGameButton = new JButton("Create Game");
	      JButton leaveGameButton = new JButton("Leave Game");
	      JButton joinGameButton = new JButton("Join Game");
	      JButton startGameButton = new JButton("Start Game");
	      JButton listGamesButton = new JButton("List Games");
	      JButton messageUserButton = new JButton("Message User");
	      
	      JButton boardButton = new JButton("Show Board");
	      JButton setButton = new JButton("Call Set");
	      JButton scoreButton = new JButton("Show Current Score");
	      JButton endGameButton = new JButton("End Game");
	      JButton voteNoMoreSetButton = new JButton("Vote No More Set");
	      
	      JButton byeByeButton = new JButton("Terminate Connection");

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
	      gamesButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {     
	        	 sendServerMessage("GAMES","", "", "");
	        	 updateStatusLabel();
	         }
	      }); 
	      createGameButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 String gameCap = gameCapacity.getText();
		        	 sendServerMessage("CREATE", gameCap, "", "");
		        	 updateStatusLabel();
		         }
		      }); 
	      leaveGameButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {     
		        	 sendServerMessage("LEAVE","", "", "");
		        	 updateStatusLabel();
		         }
		      }); 
	      joinGameButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 String game_id = gameId.getText();
		        	 sendServerMessage("JOIN", game_id, "", "");
		        	 updateStatusLabel();
		         }
		      }); 
	      gamesButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {     
		        	 sendServerMessage("GAMES","", "", "");
		        	 updateStatusLabel();
		         }
		      }); 
	      startGameButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {     
		        	 sendServerMessage("START","", "", "");
		        	 updateStatusLabel();
		         }
		      }); 
	      listGamesButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {     
		        	 sendServerMessage("LIST","", "", "");
		        	 updateStatusLabel();
		         }
		      }); 
	      boardButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {     
		        	 sendServerMessage("BOARD","", "", "");
		        	 updateStatusLabel();
		         }
		      }); 
	      setButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 String card1 = "1";
		        	 String card2 = "2";
		        	 String card3 = "3";
		        	 sendServerMessage("SET", card1, card2, card3);
		        	 updateStatusLabel();
		         }
		      }); 
	      scoreButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {     
		        	 sendServerMessage("SCORE","", "", "");
		        	 updateStatusLabel();
		         }
		      }); 
	      byeByeButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {     
		        	 sendServerMessage("BYEBYE","", "", "");
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
	      
	      controlPanel.add(gamesButton);
	      controlPanel.add(createGameButton);
	      controlPanel.add(createGameLabel);       
	      controlPanel.add(gameCapacity);
	      controlPanel.add(leaveGameButton);
	      controlPanel.add(joinGameButton);
	      controlPanel.add(joinGameLabel);
	      controlPanel.add(gameId);
	      controlPanel.add(startGameButton);
	      controlPanel.add(listGamesButton);
	      controlPanel.add(messageUserButton);
	      
	      controlPanel.add(boardButton);
	      controlPanel.add(setButton);
	      controlPanel.add(scoreButton);
	      controlPanel.add(endGameButton);
	      controlPanel.add(voteNoMoreSetButton);
	      
	      controlPanel.add(byeByeButton);
	      
	      mainFrame.setVisible(true);
	   }
	
}