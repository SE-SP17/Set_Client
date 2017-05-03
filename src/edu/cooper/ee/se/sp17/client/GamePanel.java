package edu.cooper.ee.se.sp17.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Container;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import javax.swing.plaf.metal.MetalToggleButtonUI;

public class GamePanel extends JPanel {
	//change to arraylist
	private ArrayList<JToggleButton> cardButtons = new ArrayList<JToggleButton>();
	private ArrayList<String> boardCards = new ArrayList<String>();
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
		createCards();

		// // layout.setRows(rows--);
		// // layout.setRows(rows++);

		
//		setVisible(true);
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
//						System.out.println(type);
						cardImages[a][b][c][d] = new ImageIcon("res/cards/" + type + ".png");
					}
				}
			}
		}
	}

	public void createCards() {
		
		for (int i = 0; i < 12; i++) {

			// create button with correct ImageIcon
			cardButtons.add(new JToggleButton());
			cardButtons.get(i).setUI(new MetalToggleButtonUI() {
				@Override
				protected Color getSelectColor() {
					return Color.CYAN;
				}
			});

			// Attach listeners
			cardButtons.get(i).addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent ev) {
					if (ev.getStateChange() == ItemEvent.SELECTED) {
						if (selected > 3) {
							// undo card selection if 3 are already chosen
							JToggleButton clicked = ((JToggleButton) ev.getItem());
							if(!clicked.isSelected()){
								clicked.doClick();
							}
						}
						else{
							selected++;
						}
					} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
						selected--;
					}
					System.out.println(selected);
				}
			});

			this.add(cardButtons.get(i));
		}
	}
	
	// fill board with current cards
	public void fillBoard(){
		String m = SetClient.client.send("BOARD\r\n");
		boardCards = new ArrayList<String>(Arrays.asList(m.split("\n")));
		System.out.println("number of cards " +boardCards.size());
		
		if(boardCards.size()-1 > 12){
			addCardButtons(boardCards.size()-1-12);
		}

		int[] type = new int[4];
		for (int i = 0; i < boardCards.size()-1; i++) {
			System.out.println(boardCards.get(i));
			int index = boardCards.get(i).indexOf(":");
			if(index > -1){
				for (int k = 0; k < 4; k++) {
					char c = boardCards.get(i).charAt(index + (k+1)*2); //location of digit
					System.out.println(c);
					type[k] = Character.getNumericValue(c);
				}
				// create button with correct ImageIcon
				cardButtons.get(i).setIcon(cardImages[type[0]] [type[1]] [type[2]] [type[3]]);
			}
		}
	}
	public void addCardButtons(int numAdd){
		//TODO
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		System.out.println("enabled: " + enabled);
	    super.setEnabled(enabled);
	    for (Component component : getComponents()) {
	        component.setEnabled(enabled);
	    }
	}


}
