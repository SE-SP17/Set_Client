package edu.cooper.ee.se.sp17.client;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LobbyFrame extends JFrame {
	JButton btn_logout, btn_create, btn_refresh;
	GameListPane glp;

	public LobbyFrame(String title, String msg){
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add graphical elements
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout()); // For the slight border

		JPanel panel = new JPanel();
		contentPane.add(panel);

		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();

		JLabel logo = new JLabel(new ImageIcon("res/logo.png"));
		//logo.setPreferredSize(new Dimension(107, 54));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		layout.setConstraints(logo, c);
		panel.add(logo);

		JLabel lbl_welcome = new JLabel(msg);
		lbl_welcome.setFont(new Font("Arial", Font.PLAIN, 14));
		lbl_welcome.setHorizontalAlignment(SwingConstants.LEFT);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		layout.setConstraints(lbl_welcome, c);
		panel.add(lbl_welcome);

		btn_logout =  new JButton("Logout");
		c.gridx = 3;
		c.gridy = 1;
		c.gridwidth = 1;
		layout.setConstraints(btn_logout, c);
		panel.add(btn_logout);

		glp = new GameListPane();
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 4;
		c.gridheight = 10;
		layout.setConstraints(glp, c);
		panel.add(glp);

		btn_create =  new JButton("Create");
		c.gridx = 0;
		c.gridy = 12;
		c.gridwidth = 1;
		c.gridheight = 1;
		layout.setConstraints(btn_create, c);
		panel.add(btn_create);

		btn_refresh =  new JButton("Refresh");
		c.gridx = 3;
		c.gridy = 12;
		layout.setConstraints(btn_refresh, c);
		panel.add(btn_refresh);

		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		// Attach listeners
		btn_logout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = SetClient.client.send("LOGOUT\r\n");
				JOptionPane.showMessageDialog(contentPane, msg);
				LoginFrame login = new LoginFrame("Set Game Client 0xFF");
				close();
			}
		});

		btn_refresh.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				glp.refresh();
			}
		});

		btn_create.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String m = JOptionPane.showInputDialog("Max users:");
				if(m == null) return;
				int max = Integer.parseInt(m);
				JOptionPane.showMessageDialog(contentPane, SetClient.client.send(String.join(" ", "CREATE", m, "\r\n")));
				// TODO Join game...
			}
		});
	}

	public void close(){
		setVisible(false);
		dispose();
	}
}
