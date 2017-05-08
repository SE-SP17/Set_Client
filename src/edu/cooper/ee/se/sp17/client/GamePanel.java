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
	private ArrayList<JToggleButton> cardButtons;
	private ArrayList<String> cards;
	String allCards;
	private ImageIcon[][][][] cardImages;
	int rows, cols, numCards;
	int selected = 0;
	GridLayout layout;

	public GamePanel() {
		cardButtons = new ArrayList<JToggleButton>();
		cards = new ArrayList<String>();
		cardImages = new ImageIcon[4][4][4][4];
		rows = 3;
		cols = 4;
		numCards = rows * cols;
		allCards = "";

		this.setPreferredSize(new Dimension(1000, 800));
		this.setBackground(Color.GRAY);

		layout = new GridLayout(rows, cols);
		setLayout(layout);

		loadImages();
		createCardButtons(0, 12);
	}

	@Override
	public void setEnabled(boolean enabled) {
		System.out.println("enabled: " + enabled);
		super.setEnabled(enabled);
		for (Component component : getComponents()) {
			component.setEnabled(enabled);
		}
	}

	public void loadImages() {
		System.out.println("loading images");
		for (int a = 1; a < 4; a++) {
			for (int b = 1; b < 4; b++) {
				for (int c = 1; c < 4; c++) {
					for (int d = 1; d < 4; d++) {
						// cardImages[a][b][c][d] = new
						// ImageIcon(getClass().getResource("res/cards/" +
						// a+b+c+d +".png"));
						String type = "" + a + b + c + d;
						// System.out.println(type);
						cardImages[a][b][c][d] = new ImageIcon(this.getClass().getResource("/res/cards/" + type + ".png"));
					}
				}
			}
		}
	}

	// initial setup
	public void createCardButtons(int start, int end) {

		for (int i = start; i < end; i++) {

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
						selected++;
						if (selected >= 4) {
							// undo card selection if 3 are already chosen
							JToggleButton clicked = ((JToggleButton) ev.getItem());
							// clicked.doClick();
							clicked.setSelected(false);
							// System.out.println("more than 3");
						}
					} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
						selected--;
					}
					// System.out.println(selected);
				}
			});

			this.add(cardButtons.get(i));
		}
	}

	// add cards and update display
	public void addCardButtons() {
		// TODO add card buttons when everyone calls nomore

		// update display
		cols += (cards.size() - numCards) % cols;
		layout.setRows(rows);
		layout.setColumns(cols);

		createCardButtons(numCards, cards.size());
	}

	// remove cards and update display
	public void removeCardButtons() {
		// remove buttons from display
		for (int i = cards.size(); i < numCards; i++) {
			this.remove(cardButtons.get(i));
		}

		// remove buttons from arraylist
		cardButtons.subList(cards.size(), numCards).clear();

		// update display
		cols -= (cards.size() - numCards) % cols;
		layout.setRows(rows);
		layout.setColumns(cols);
	}

	// Update cards
	public void updateCards(String board) {
//		SetClient.client.send("BOARD\r\n");
//		String rply = SetClient.client.recv();
//		while (!rply.startsWith("--END--")) {
//			cards.add(rply);
//			rply = SetClient.client.recv();
//		}
		
		// Clear current cards
		cards.clear();
		
		String[] rep = board.split("\n");
		for(int i = 0; i < rep.length; i++){
			System.out.println(rep[i]);
			cards.add(rep[i]);
		}
	}

	// parse cards String representation into digit filenames for accessing
	// images
	public void updateImages() {
		int[] type = new int[4];
		for (int i = 0; i < cards.size(); i++) {
			System.out.println(cards.get(i));
			int index = cards.get(i).indexOf(":");
			for (int k = 0; k < 4; k++) {
				char c = cards.get(i).charAt(index + (k + 1) * 2); // location
																	// of digits

				System.out.println(c);
				type[k] = Character.getNumericValue(c);
			}
			// create button with correct ImageIcon
			cardButtons.get(i).setIcon(cardImages[type[0]][type[1]][type[2]][type[3]]);

		}
	}

	// refresh display
	public void refresh(String board) {
		setEnabled(true);
		
		// update cards if the board has changed
		if(!board.equals(allCards)){
			allCards = board;
			// add new cards to arraylist
			updateCards(board);
	
			System.out.println("number of cards " + cards.size());
	
			// add buttons if there are more than the previous number of cards
			if (cards.size() > numCards) {
				addCardButtons();
			}
			// remove buttons if there are less than 12 cards
			else if (cards.size() < numCards) {
				removeCardButtons();
				this.revalidate();
				this.repaint();
			}
			numCards = cards.size();
	
			updateImages();
		}

	}


	public String getSelectedCards() {
		int index = 0;
		String selected = ""; // return index of card
		for (JToggleButton card : cardButtons) {
			if (card.isSelected()) {
				if (cards.get(index).charAt(1) == ':') {
					selected += cards.get(index).substring(0, 1) + " ";
					cardButtons.get(index).setSelected(false);
				} else {
					selected += cards.get(index).substring(0, 2) + " ";
					cardButtons.get(index).setSelected(false);
				}
			}
			index++;
		}
		return selected;
	}
}
