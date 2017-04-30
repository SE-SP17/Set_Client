package edu.cooper.ee.se.sp17.client;
import javax.imageio.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class SetClient {

	   private JFrame mainFrame;
	   private JLabel headerLabel;
	   private JLabel statusLabel;
	   
	   private JPanel loginPanel;
	   private JPanel gamePanel;
	   private JPanel boardPanel;
	   private JPanel controlPanel;

	   private JLabel nameLabel;
	   private JLabel passwordLabel;
	   private JLabel createGameLabel;
	   private JLabel joinGameLabel;
	   private Socket socket;
	   private String statusText;
	   private String[] setCards = {"0", "1", "2"};
	   
	   private boolean showControlPanel = true;
	   private boolean showLoginPanel = true;
	   private boolean showGamePanel = false;
	   private boolean showBoardPanel = false;
	   
   	  JButton card0button;
      JButton card1button;
      JButton card2button;
      JButton card3button;
      JButton card4button;
      JButton card5button;
      JButton card6button;
      JButton card7button;
      JButton card8button;
      JButton card9button;
      JButton card10button;
      JButton card11button;

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
	      mainFrame.setSize(1000,1000);
	      mainFrame.setLayout(new GridLayout(1, 3));
	      
	      mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });    
//	      headerLabel = new JLabel("", JLabel.LEFT);        
	      statusLabel = new JLabel("",JLabel.LEFT);    
	      statusLabel.setSize(350,100);

	      controlPanel = new JPanel();
	      controlPanel.setLayout(new FlowLayout());
	      loginPanel = new JPanel();
	      loginPanel.setLayout(new GridLayout(4,2));
	      gamePanel = new JPanel();
	      gamePanel.setLayout(new GridLayout(8,2));
	      boardPanel = new JPanel();
	      boardPanel.setLayout(new GridLayout(4,4,3,3));

//	      mainFrame.add(headerLabel);
	      mainFrame.add(controlPanel);
	      mainFrame.add(loginPanel);
	      mainFrame.add(gamePanel);
	      mainFrame.add(boardPanel);
	      mainFrame.add(statusLabel);
//	      mainFrame.pack();
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
				   	String[] shape = {"a", "b", "c"};
					String[] color = {"blue", "green", "pink"};
					String[] fill = {"", "fill", "lines"};
					String[] amount = {"1", "2", "3"};
					try{
				   for(int i=0;i<12;i++){
					   readMessage = readMessage.replaceAll("[^0-9]+", " ");
					   String[] cardProperties = readMessage.trim().split(" ");
					   String imagePath = "";
					   System.out.println("Card: " + cardProperties[0]);
					   for(int j=0;j<cardProperties.length; j++){
						   if(j == 1){ // color
							   imagePath += shape[Integer.parseInt(cardProperties[1])-1];
						   }
						   else if(j == 2){ // shape
							   imagePath += color[Integer.parseInt(cardProperties[2])-1];
						   }
						   else if(j == 3){ // fill
							   imagePath += fill[Integer.parseInt(cardProperties[3])-1];
						   }
						   else if(j == 4){ // amount
							   imagePath += amount[Integer.parseInt(cardProperties[4])-1];	
						   }
					   }
					   imagePath += ".png";
					   File f = new File("cards/" + imagePath);
					   if(!(f.exists() && !f.isDirectory())) { 
						   statusText = "Board Generation Failure";
						   return;
					   }
					   Image img = ImageIO.read(getClass().getResource("cards/" + imagePath));
					   if(i == 0){
				    	   card0button.setIcon(new ImageIcon(img));
					   }
					   else if(i == 1){
						   card1button.setIcon(new ImageIcon(img));
					   }
					   else if(i == 2){
						   card2button.setIcon(new ImageIcon(img));
					   }
					   else if(i == 3){
						   card3button.setIcon(new ImageIcon(img));
					   }
					   else if(i == 4){
						   card4button.setIcon(new ImageIcon(img));
					   }
					   else if(i == 5){
						   card5button.setIcon(new ImageIcon(img));
					   }
					   else if(i == 6){
						   card6button.setIcon(new ImageIcon(img));
					   }
					   else if(i == 7){
						   card7button.setIcon(new ImageIcon(img));
					   }
					   else if(i == 8){
						   card8button.setIcon(new ImageIcon(img));
					   }
					   else if(i == 9){
						   card9button.setIcon(new ImageIcon(img));
					   }
					   else if(i == 10){
						   card10button.setIcon(new ImageIcon(img));
					   }
					   else if(i == 11){
						   card11button.setIcon(new ImageIcon(img));
					   }
					   System.out.println("path:" + imagePath);
					   
					   statusText += readMessage + " ";
					   if(i != 11){
						   readMessage = br.readLine();
					   }
					   System.out.println(readMessage);
				   }
				   		System.out.println("gothere1");
					   boardPanel.revalidate();
					   System.out.println("gothere2");
					   boardPanel.repaint();
					   System.out.println("gothere3");
					   statusText = "Board Loaded";
					}
					catch(Exception e){
						System.out.println(e);
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
	   private void addClickedCardToSetCall(String cardNumber){
		   setCards[0] = setCards[1];
		   setCards[1] = setCards[2];
		   setCards[2] = cardNumber;
	   }
	   private void showLogin(){
		  sendServerMessage("ServerStart", "", "", "");
//	      headerLabel.setText("Swing SET GUI"); 

	      nameLabel = new JLabel("User ID: ", JLabel.CENTER);
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
	      
	      card0button = new JButton("0");
	      card1button = new JButton("1");
	      card2button = new JButton("2");
	      card3button = new JButton("3");
	      card4button = new JButton("4");
	      card5button = new JButton("5");
	      card6button = new JButton("6");
	      card7button = new JButton("7");
	      card8button = new JButton("8");
	      card9button = new JButton("9");
	      card10button = new JButton("10");
	      card11button = new JButton("11");

	      try {
	    	    Image img1 = ImageIO.read(getClass().getResource("cards/abluefill1.png"));
	    	    Image img2 = ImageIO.read(getClass().getResource("cards/abluefill2.png"));
	    	    Image img3 = ImageIO.read(getClass().getResource("cards/abluefill3.png"));
	    	    
	    	    card0button.setIcon(new ImageIcon(img1));
	    	    card0button.setMargin(new Insets(0, 0, 0, 0));
	    	    card0button.setPreferredSize(new Dimension(183,279));
	    	    
	    	    card1button.setIcon(new ImageIcon(img2));
	    	    card1button.setMargin(new Insets(0, 0, 0, 0));
	    	    card1button.setPreferredSize(new Dimension(183,279));
	    	    
	    	    card2button.setIcon(new ImageIcon(img3));
	    	    card2button.setMargin(new Insets(0, 0, 0, 0));
	    	    card2button.setPreferredSize(new Dimension(183,279));
	    	    
	    	    card3button.setIcon(new ImageIcon(img1));
	    	    card3button.setMargin(new Insets(0, 0, 0, 0));
	    	    card3button.setPreferredSize(new Dimension(183,279));
	    	    
	    	    card4button.setIcon(new ImageIcon(img2));
	    	    card4button.setMargin(new Insets(0, 0, 0, 0));
	    	    card4button.setPreferredSize(new Dimension(183,279));
	    	    
	    	    card5button.setIcon(new ImageIcon(img3));
	    	    card5button.setMargin(new Insets(0, 0, 0, 0));
	    	    card5button.setPreferredSize(new Dimension(183,279));
	    	    
	    	    card6button.setIcon(new ImageIcon(img1));
	    	    card6button.setMargin(new Insets(0, 0, 0, 0));
	    	    card6button.setPreferredSize(new Dimension(183,279));
	    	    
	    	    card7button.setIcon(new ImageIcon(img2));
	    	    card7button.setMargin(new Insets(0, 0, 0, 0));
	    	    card7button.setPreferredSize(new Dimension(183,279));
	    	    
	    	    card8button.setIcon(new ImageIcon(img3));
	    	    card8button.setMargin(new Insets(0, 0, 0, 0));
	    	    card8button.setPreferredSize(new Dimension(183,279));
	    	    
	    	    card9button.setIcon(new ImageIcon(img3));
	    	    card9button.setMargin(new Insets(0, 0, 0, 0));
	    	    card9button.setPreferredSize(new Dimension(183,279));
	    	    
	    	    card10button.setIcon(new ImageIcon(img3));
	    	    card10button.setMargin(new Insets(0, 0, 0, 0));
	    	    card10button.setPreferredSize(new Dimension(183,279));
	    	    
	    	    card11button.setIcon(new ImageIcon(img3));
	    	    card11button.setMargin(new Insets(0, 0, 0, 0));
	    	    card11button.setPreferredSize(new Dimension(183,279));
	     } 
	      catch (Exception ex) {
	    	    System.out.println(ex);
	      }
	      
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
		        	 sendServerMessage("SET", setCards[0], setCards[1], setCards[2]);
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
	      card0button.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 addClickedCardToSetCall("0");
	        	 updateStatusLabel();
	         }
	      });
	      card1button.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 addClickedCardToSetCall("1");
		        	 updateStatusLabel();
		         }
		      });
	      card2button.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 addClickedCardToSetCall("2");
		        	 updateStatusLabel();
		         }
		      });
	      card3button.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 addClickedCardToSetCall("3");
		        	 updateStatusLabel();
		         }
		      });
	      card4button.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 addClickedCardToSetCall("4");
		        	 updateStatusLabel();
		         }
		      });
	      card5button.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 addClickedCardToSetCall("5");
		        	 updateStatusLabel();
		         }
		      });
	      card6button.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 addClickedCardToSetCall("6");
		        	 updateStatusLabel();
		         }
		      });
	      card7button.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 addClickedCardToSetCall("7");
		        	 updateStatusLabel();
		         }
		      });
	      card8button.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 addClickedCardToSetCall("8");
		        	 updateStatusLabel();
		         }
		      });
	      card9button.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 addClickedCardToSetCall("9");
		        	 updateStatusLabel();
		         }
		      });
	      card10button.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	 addClickedCardToSetCall("10");
		        	 updateStatusLabel();
		         }
		      });
	      card11button.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 addClickedCardToSetCall("11");
	        	 updateStatusLabel();
	         }
	      });

	      loginPanel.add(nameLabel);
	      loginPanel.add(userText);
	      loginPanel.add(passwordLabel);       
	      loginPanel.add(passwordText);
	      
	      loginPanel.add(loginButton);
	      loginPanel.add(logoutButton);
	      loginPanel.add(whoAmIButton);
	      
	      gamePanel.add(gamesButton);
	      gamePanel.add(createGameButton);
	      gamePanel.add(createGameLabel);       
	      gamePanel.add(gameCapacity);
	      gamePanel.add(leaveGameButton);
	      gamePanel.add(joinGameButton);
	      gamePanel.add(joinGameLabel);
	      gamePanel.add(gameId);
	      gamePanel.add(startGameButton);
	      gamePanel.add(listGamesButton);
	      gamePanel.add(messageUserButton);
	      
	      gamePanel.add(boardButton);
	      gamePanel.add(setButton);
	      gamePanel.add(scoreButton);
	      gamePanel.add(endGameButton);
	      gamePanel.add(voteNoMoreSetButton);
	      
	      boardPanel.add(card0button);
	      boardPanel.add(card1button);
	      boardPanel.add(card2button);
	      boardPanel.add(card3button);
	      boardPanel.add(card4button);
	      boardPanel.add(card5button);
	      boardPanel.add(card6button);
	      boardPanel.add(card7button);
	      boardPanel.add(card8button);
	      boardPanel.add(card9button);
	      boardPanel.add(card10button);
	      boardPanel.add(card11button);
	      
//	      controlPanel.add(byeByeButton);
	      
	      mainFrame.setVisible(true);
	      loginPanel.setVisible(true);
	      gamePanel.setVisible(true);
	      boardPanel.setVisible(true);
	      controlPanel.setVisible(true);
	      
	   }
	
}