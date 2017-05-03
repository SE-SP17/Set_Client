package edu.cooper.ee.se.sp17.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Container;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.ImageIcon;

 import javax.swing.plaf.metal.MetalToggleButtonUI;

public class GamePanel extends JPanel {
	private JToggleButton[] cards = new JToggleButton[12];
	String[] board = new String[12];
	private ImageIcon[][][][] cardImages = new ImageIcon[4][4][4][4];
	int rows = 4;
	int cols = 3;

	int selected = 0;

	public GamePanel() {
		// TODO Auto-generated constructor stub
		this.setPreferredSize(new Dimension(600, 600));
		this.setBackground(Color.GRAY);


		GridLayout layout = new GridLayout(rows, cols);

		setLayout(layout);
		System.out.println("hello");
		loadImages();

		fillBoard();

		// // layout.setRows(rows--);
		// // layout.setRows(rows++);

		
		setVisible(true);
	}

	public void loadImages() {
		System.out.println("loading images");
		for (int a = 0; a < 4; a++) {
			for (int b = 0; b < 4; b++) {
				for (int c = 0; c < 4; c++) {
					for (int d = 0; d < 4; d++) {
						// cardImages[a][b][c][d] = new
						// ImageIcon(getClass().getResource("res/cards/" +
						// a+b+c+d +".png"));
						String type = "" + a + b + c + d;
						System.out.println(type);
						cardImages[a][b][c][d] = new ImageIcon("res/cards/" + type + ".png");
					}
				}
			}
		}
	}

	public void fillBoard() {
		// board = SetClient.client.send("BOARD").split("/n");
		board = new String[] { "1111", "2222", "3333", "1232", "1312", "3212", "3211", "2311", "1232", "1222", "1333",
				"1223" };

		int[] type = new int[4];
		for (int i = 0; i < cards.length; i++) {
			for (int k = 0; k < 4; k++) {
				char c = board[i].charAt(k);
				type[k] = Character.getNumericValue(c);
			}
			// create button with correct ImageIcon
			cards[i] = new JToggleButton(cardImages[type[0]][type[1]][type[2]][type[3]]);
			cards[i].setUI(new MetalToggleButtonUI() {
				@Override
				protected Color getSelectColor() {
					return Color.BLUE;
				}
			});

			// Attach listeners
			cards[i].addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent ev) {
					if (ev.getStateChange() == ItemEvent.SELECTED) {
						selected++;
						if (selected >= 3) {
							// disable selection of all other buttons
						}
					} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
						selected--;
					}
					System.out.println(selected);
				}
			});

			this.add(cards[i]);
		}

	}

}
