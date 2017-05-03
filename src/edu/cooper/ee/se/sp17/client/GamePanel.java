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
	private ArrayList<JToggleButton> cardButtons = new ArrayList<JToggleButton>();
	private ArrayList<String> boardCards = new ArrayList<String>();
	private ImageIcon[][][][] cardImages = new ImageIcon[4][4][4][4];
	int rows = 4;
	int cols = 3;
	int currentNumber = 12;

	int selected = 0;
	GridLayout layout;

	public GamePanel() {
		// TODO Auto-generated constructor stub
		this.setPreferredSize(new Dimension(600, 600));
		this.setBackground(Color.GRAY);

		layout = new GridLayout(rows, cols);

		setLayout(layout);
		System.out.println("hello");
		loadImages();
		createCards();

		// setVisible(true);
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
						// System.out.println(type);
						cardImages[a][b][c][d] = new ImageIcon("res/cards/" + type + ".png");
					}
				}
			}
		}
	}

	// initial setup
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
						selected++;
						if (selected >= 4) {
							// undo card selection if 3 are already chosen
							JToggleButton clicked = ((JToggleButton) ev.getItem());
							// clicked.doClick();
							clicked.setSelected(false);
							System.out.println("more than 3");
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
	public void fillBoard() {
		// Get board from server
		String m = SetClient.client.send("BOARD\r\n");
		boardCards = new ArrayList<String>(Arrays.asList(m.split("\n")));
		System.out.println("number of cards " + (boardCards.size()-1));

//		// add buttons if there are more than 12 cards
		if (boardCards.size() - 1 > currentNumber) {
			addCardButtons(boardCards.size() - 1 - 12);
			
		}
		else if(boardCards.size() - 1 < currentNumber){
			
			rows--;
			layout.setRows(rows);
			layout.setColumns(cols);
			System.out.println("rows " + rows);
			System.out.println("cols " + cols);
			
			this.remove( cardButtons.get(cardButtons.size()-1) );
			 cardButtons.remove( cardButtons.size()-1);
			
			this.remove( cardButtons.get(cardButtons.size()-1));
			 cardButtons.remove( cardButtons.size()-1);
			
			this.remove( cardButtons.get(cardButtons.size()-1));
			 cardButtons.remove( cardButtons.size()-1);
			this.revalidate();
			this.repaint();
			
		}
		currentNumber = boardCards.size()-1;

		// parse card type into digits for accessing images
		int[] type = new int[4];
		for (int i = 0; i < boardCards.size() - 1; i++) {
			System.out.println(boardCards.get(i));
			int index = boardCards.get(i).indexOf(":");
			if (index > -1) {
				for (int k = 0; k < 4; k++) {
					char c = boardCards.get(i).charAt(index + (k + 1) * 2); // location
																			// of
																			// digit
					System.out.println(c);
					type[k] = Character.getNumericValue(c);
				}
				// create button with correct ImageIcon
				cardButtons.get(i).setIcon(cardImages[type[0]][type[1]][type[2]][type[3]]);
			}
		}
	}

	public int[] getSelectedCards() {
		int i = 0;
		int index = 0;
		int[] selected = new int[3]; // return index of card
		for (JToggleButton card : cardButtons) {
			if (card.isSelected()) {
				if(boardCards.get(index).charAt(1) == ':'){
					selected[i++] = Integer.parseInt(boardCards.get(index).substring(0,1));
				}
				else {
					selected[i++] = Integer.parseInt(boardCards.get(index).substring(0,2));
				}
			}
			index++;
		}
		return selected;
	}

	public void addCardButtons(int numAdd) {
		// TODO add card buttons when everyone calls nomore
		// // layout.setRows(rows--);
		//currentNumber += numAdd;
		rows += numAdd % 3;
		layout.setRows(rows);
		for (int i = currentNumber; i < currentNumber+numAdd; i++) {
			
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
							System.out.println("more than 3");
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

	// unselect all current card selections
	public void unselect(int[] selected) {
		for (int i = 0; i < selected.length; i++) {
			cardButtons.get(selected[i]).setSelected(false);
		}
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
