package edu.cooper.ee.se.sp17.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class LoginFrame extends JFrame {
	private JTextField tf_user;
	private JPasswordField tf_pass;
	private JButton btn_login, btn_reg;
	
	public LoginFrame(String title){
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Add graphical elements
		Container contentPane = getContentPane();
		contentPane.setPreferredSize(new Dimension(450, 260));
		
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		
		JLabel logo = new JLabel(new ImageIcon("res/logo.png"));
		logo.setPreferredSize(new Dimension(215, 108));
		contentPane.add(logo, BorderLayout.CENTER);
		layout.putConstraint(SpringLayout.NORTH, logo, 5, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, logo, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
		
		tf_user = new JTextField("Username", 14);
		contentPane.add(tf_user);
		layout.putConstraint(SpringLayout.NORTH, tf_user, 5, SpringLayout.SOUTH, logo);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tf_user, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);

		tf_pass = new JPasswordField("abcd", 14);
		contentPane.add(tf_pass);
		layout.putConstraint(SpringLayout.NORTH, tf_pass, 5, SpringLayout.SOUTH, tf_user);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tf_pass, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);

		btn_reg = new JButton("Register");
		contentPane.add(btn_reg);
		layout.putConstraint(SpringLayout.NORTH, btn_reg, 5, SpringLayout.SOUTH, tf_pass);
		layout.putConstraint(SpringLayout.WEST, btn_reg, 140, SpringLayout.WEST, contentPane); // HACKy
		
		btn_login = new JButton("Login");
		contentPane.add(btn_login);
		layout.putConstraint(SpringLayout.NORTH, btn_login, 5, SpringLayout.SOUTH, tf_pass);
		layout.putConstraint(SpringLayout.WEST, btn_login, 5, SpringLayout.EAST, btn_reg);
		
		getRootPane().setDefaultButton(btn_login);
		
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		// Attach listeners
		btn_login.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = SetClient.client.send(String.join(" ", "LOGIN", tf_user.getText(), 
						String.valueOf(tf_pass.getPassword()), "\r\n"));
				JOptionPane.showMessageDialog(contentPane, msg);
				if(msg.equals("User logged in successfully")){
					LobbyFrame lobby = new LobbyFrame("Set Game Client 0xFF", SetClient.client.send("WHOAMI\r\n"));
					close();
				}
			}
		});

		btn_reg.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = SetClient.client.send(String.join(" ", "REGISTER", tf_user.getText(), 
						String.valueOf(tf_pass.getPassword()), "\r\n"));
				JOptionPane.showMessageDialog(contentPane, msg);
			}
		});
	}
	
	public void close(){
		setVisible(false);
		dispose();
	}
}
