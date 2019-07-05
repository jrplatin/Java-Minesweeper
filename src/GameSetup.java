import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

//Sets up the game after the user clicks "play"
@SuppressWarnings("serial")
public class GameSetup extends JPanel {
	private JFrame frame;
	private JButton[][] buttonCells = new JButton[20][20];
	private int[][] cellCounts = new int[20][20];
	private int numMines;
	private String userName;
	private boolean testing;
	private RelTimer clock;

	public GameSetup(JFrame frame, boolean testing) {
		this.frame = frame; 
		frame.getContentPane().repaint();
		this.testing = testing; 
		
		frame.setLayout(new BorderLayout());
		frame.getContentPane().setBackground(Color.white);
		
		JButton reset = new JButton("Reset");
		Container resetGrid = new Container(); 
		resetGrid.setLayout(new GridLayout(1,3));
		resetGrid.add(reset); 

		Container actualGrid = new Container(); 
		frame.add(resetGrid, BorderLayout.NORTH); 
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				clock.stopClock();
				clock.reset();
				frame.getContentPane().removeAll();
				frame.add(new MenuScreen(frame));
				frame.repaint();
			    frame.pack();

			}
		});
		
		resetGrid.add(back);
		
		//add reset button and allow it to reset the board
		actualGrid.setLayout(new GridLayout(20,20));
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clock.stopClock();

				for(int i = 0; i < buttonCells.length; i++) {
					for(int j = 0; j < buttonCells[0].length; j++) {
						buttonCells[i][j].setEnabled(true);
						buttonCells[i][j].setText("");
						
						buttonCells[i][j].setBackground(Color.GREEN); 
       				}
				}
				createMines(); 

				clock.reset();
			}
		});
		
		//add right-click flagging feature
		for(int i = 0; i < buttonCells.length; i++) {
			for(int j = 0; j < buttonCells[0].length; j++) {
				buttonCells[i][j] = new JButton(); 
				buttonCells[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) { 
						for(int i = 0; i < buttonCells.length; i++) {
							for(int j = 0; j < buttonCells[0].length; j++) {
								if(SwingUtilities.isRightMouseButton(e)) {
									if(e.getSource().equals(buttonCells[i][j])) {
										if(buttonCells[i][j].isEnabled()) {
										buttonCells[i][j].setBackground(Color.ORANGE);
										buttonCells[i][j].setEnabled(false);
										}
										else {
											buttonCells[i][j].setBackground(new JButton().getBackground());
											buttonCells[i][j].setEnabled(true);
										}
									}
								}
							}
						}
					}
				});
				//add the button listener to each cell, and add appropriate functionality when mine is clicked
				buttonCells[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for(int i = 0; i < buttonCells.length; i++) {
							for(int j = 0; j < buttonCells[0].length; j++) {
								
								System.out.println(e.getSource());
								
								if(e.getSource().equals(buttonCells[i][j])) {

									if(cellCounts[i][j] == 100) {
										
										
										buttonCells[i][j].setBackground(Color.RED);
										buttonCells[i][j].setEnabled(false);
										buttonCells[i][j].setText("X");
										lostGame(); 


									}
								
									else {
										buttonCells[i][j].setEnabled(false);
						                clearAfterZero(i,j);
										didWin(frame); 
									}
								}
							}
						}
						
					}
				});
				actualGrid.add(buttonCells[i][j]); 
			}
		}
		
		
		frame.add(actualGrid, BorderLayout.CENTER);
		String mines = null;  
		
		if(!testing) {
		userName = (String)JOptionPane.showInputDialog(frame, "Enter your name");
		while(userName == null || userName.trim().length() == 0) {
			userName = (String)JOptionPane.showInputDialog(frame, "Enter your name");

		}
		 mines = (String)JOptionPane.showInputDialog(frame, "Enter the number of mines you would like");
		}
		//check if user input is valid, if not then set 35 as defualt num. of mines
		try {
		numMines = Integer.valueOf(mines); 
		if(numMines < 0 || numMines > 400) {
			numMines = 35;
		}
		} catch (java.lang.NumberFormatException ex) {
			numMines = 35; 
		}
		frame.pack(); 
		
		if(!testing) {
		createMines();
		}
		
		clock = new RelTimer(frame, resetGrid); 
	
	}

	// check if the user won, if they did, we need to reset/stop the clock
	public boolean didWin(JFrame frame) {
		boolean didWin = true;
		for (int i = 0; i < cellCounts.length; i++) {
			for (int j = 0; j < cellCounts.length; j++) {
				if (cellCounts[i][j] != 100 && buttonCells[i][j].isEnabled()
						|| buttonCells[i][j].getBackground().equals(Color.ORANGE)) {
					didWin = false;
				}
			}

		}
		if (didWin) {
			clock.stopClock();
			try {
				FileWriter writer = new FileWriter("Files/winners.txt", true);
				writer.write("\n" + userName + ", " + clock.getTime());
				writer.close();
			} catch (FileNotFoundException ex) {
				File file = new File("Files/winners.txt");
				try {
					FileWriter writer = new FileWriter("Files/winners.txt", false);
					writer.write("\n" + userName);
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (!testing) {
				int res = JOptionPane.showOptionDialog(null, "You won!", "Test", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if (res == 0) {
					for (int i = 0; i < buttonCells.length; i++) {
						for (int j = 0; j < buttonCells[0].length; j++) {
							buttonCells[i][j].setEnabled(true);
							buttonCells[i][j].setText("");
							buttonCells[i][j].setBackground(new JButton().getBackground());
						}
					}
					createMines();
					clock.reset();
				}
			}

		}
		return didWin;
	}

	// check if the user lost game, if they did, the game is reset and a message
	// comes up
	public void lostGame() {
		for (int i = 0; i < buttonCells.length; i++) {
			for (int j = 0; j < buttonCells[0].length; j++) {
				if (buttonCells[i][j].isEnabled()) {
					if (cellCounts[i][j] == 100) {
						buttonCells[i][j].setBackground(Color.RED);
						buttonCells[i][j].setEnabled(false);
						buttonCells[i][j].setText("X");
					}

				} else {
					if (cellCounts[i][j] == 100) {
						buttonCells[i][j].setBackground(Color.RED);
						buttonCells[i][j].setEnabled(false);
						buttonCells[i][j].setText("X");
					}

				}
			}
		}

		clock.stopClock();

		if (!testing) {
			int res = JOptionPane.showOptionDialog(null, "You Lost!  Press OK to try again", "Test",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

			if (res == 0) {
				for (int i = 0; i < buttonCells.length; i++) {
					for (int j = 0; j < buttonCells[0].length; j++) {
						clock.reset();
						buttonCells[i][j].setEnabled(true);
						buttonCells[i][j].setText("");
						buttonCells[i][j].setBackground(new JButton().getBackground());
					}
				}
				createMines();
			}
		}
		clock.reset();

	}

	// clear all surrounding zeroes when a cell is clicked, assuming the cell itself
	// is 0
	public void clearAfterZero(int x, int y) {
		if (x < 0 || x > cellCounts.length - 1 || y < 0 || y > cellCounts.length - 1 || cellCounts[x][y] == 100
				|| buttonCells[x][y].getBackground() == Color.GREEN || cellCounts[x][y] == -1
				|| buttonCells[x][y].getBackground().equals(Color.ORANGE)) {
			return;
		}

		if (cellCounts[x][y] != 0) {

			buttonCells[x][y].setEnabled(false);
			buttonCells[x][y].setBackground(Color.yellow);
			buttonCells[x][y].setText(String.valueOf(cellCounts[x][y]));

			// If an indicator is visited, do not visit it again
			cellCounts[x][y] = -1;
			return;
		}

		else if (cellCounts[x][y] == 0) {

			buttonCells[x][y].setEnabled(false);
			cellCounts[x][y] = -1;

			// Squares

			clearAfterZero(x, y - 1);
			clearAfterZero(x - 1, y);
			clearAfterZero(x + 1, y);
			clearAfterZero(x, y + 1);

			// Diagonals

			clearAfterZero(x - 1, y - 1);
			clearAfterZero(x + 1, y - 1);
			clearAfterZero(x + 1, y + 1);
			clearAfterZero(x - 1, y + 1);

			return;
		} else {
			return;
		}

	}

	public void createMines() {
		// We want to ensure that two mines aren't placed in the same cell
		// so let's make an ArrayList that we can keep remove a cell if a
		// mine is placed there already

		ArrayList<Integer> mines = new ArrayList<Integer>();
		for (int i = 0; i < cellCounts.length; i++) {
			for (int j = 0; j < cellCounts[0].length; j++) {
				mines.add(i * 100 + j);
				// We will keep track of the mines location by
				// placing the x and y coordinates in the tens/ones spot

			}
		}
		cellCounts = new int[20][20];

		for (int i = 0; i < numMines; i++) {
			int randNum = (int) (Math.random() * mines.size());
			int xValue = mines.get(randNum) / 100;
			int yValue = mines.get(randNum) % 100;
			cellCounts[xValue][yValue] = 100;
			mines.remove(randNum);
		}
		// Set the neighbor counts
		for (int x = 0; x < cellCounts.length; x++) {
			for (int y = 0; y < cellCounts[0].length; y++) {
				int nCount = 0;

				if (cellCounts[x][y] != 100) {
					if (y > 0 && cellCounts[x][y - 1] == 100) {
						nCount++;
					}

					if (x < cellCounts.length - 1 && y < cellCounts[0].length - 1 && cellCounts[x + 1][y + 1] == 100) {
						nCount++;
					}

					if (x > 0 && y > 0 && cellCounts[x - 1][y - 1] == 100) {

						nCount++;
					}

					if (x < cellCounts.length - 1 && y > 0 && cellCounts[x + 1][y - 1] == 100) {
						nCount++;

					}
					if (x > 0 && cellCounts[x - 1][y] == 100) {
						nCount++;

					}

					if (x < cellCounts.length - 1 && cellCounts[x + 1][y] == 100) {
						nCount++;

					}
					if (x > 0 && y < cellCounts.length - 1 && cellCounts[x - 1][y + 1] == 100) {
						nCount++;

					}
					if (y < cellCounts.length - 1 && cellCounts[x][y + 1] == 100) {
						nCount++;

					}

					cellCounts[x][y] = nCount;
				}

			}
		}
	}

	// for testing purposes only
	public JButton[][] getButtonArray() {
		return buttonCells;

	}

	// for testing purposes only
	public int[][] getCellArray() {
		return cellCounts;

	}

	// for testing purposes only
	public void setMineTesting(int[] xReal, int[] yReal) {

		for (int i = 0; i < xReal.length; i++) {
			cellCounts[xReal[i]][yReal[i]] = 100;
		}

		// set the neighbor counts
		for (int x = 0; x < cellCounts.length; x++) {
			for (int y = 0; y < cellCounts[0].length; y++) {
				int nCount = 0;

				if (cellCounts[x][y] != 100) {
					if (y > 0 && cellCounts[x][y - 1] == 100) {
						nCount++;
					}

					if (x < cellCounts.length - 1 && y < cellCounts[0].length - 1 && cellCounts[x + 1][y + 1] == 100) {
						nCount++;
					}

					if (x > 0 && y > 0 && cellCounts[x - 1][y - 1] == 100) {

						nCount++;
					}

					if (x < cellCounts.length - 1 && y > 0 && cellCounts[x + 1][y - 1] == 100) {
						nCount++;

					}
					if (x > 0 && cellCounts[x - 1][y] == 100) {
						nCount++;

					}

					if (x < cellCounts.length - 1 && cellCounts[x + 1][y] == 100) {
						nCount++;

					}
					if (x > 0 && y < cellCounts.length - 1 && cellCounts[x - 1][y + 1] == 100) {
						nCount++;

					}
					if (y < cellCounts.length - 1 && cellCounts[x][y + 1] == 100) {
						nCount++;

					}

					cellCounts[x][y] = nCount;
				}

			}
		}

	}

	// for testing purposes only
	public void activateCell(int x, int y) {
		buttonCells[x][y].doClick();
	}

}
