import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Robot;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.junit.Test;

public class GameTests {
	//There are a few parts of the game that should be tested (since their outputs aren't necessarily concrete):
	//these are the clearing of surrounding 0s and the changing of state when a given cell is pressed as well
	//as the didWin and didLose methods.  In order to test this, we will add a few custom methods to GameSetup
	//that we can use to setup the board in the desired testing configuration.  For example, we have a setmine
	//method that sets a mine at a given point, and so on.  We can then use t
	//germane methods.  
	JFrame frame = new JFrame(); 
	GameSetup setup = new GameSetup(frame, true); 
	
	
	
	//test neighbor count for a cell in the middle
	@Test
	public void testOneMineCounts() {
		//place mine at (3,3)
		int[] xCord = {3}; 
		int[] yCord = {3};		
		setup.setMineTesting(xCord, yCord);
		int[][] array = setup.getCellArray(); 
		assertEquals("Test count to up-and-left", array[2][2], 1);
		assertEquals("Test count to up-and-right", array[4][2], 1);
		assertEquals("Test count to down-and-left", array[2][4], 1);
		assertEquals("Test count to down-and-right", array[4][4], 1);
		assertEquals("Test count to left", array[3][2], 1);
		assertEquals("Test count to up", array[3][2], 1);
		assertEquals("Test count to down", array[3][4], 1);
		assertEquals("Test count to right", array[4][3], 1);
		assertEquals("Test bomb itself", array[3][3], 100);
		assertEquals("Test count to cell that should be non-relevant", array[5][2], 0);		
		
	}
	
	//test two mines separated by one diagonal space for neighbor count
	@Test
	public void testTwoOneSpacedDiagonalMines() {
		
		int[] xCord = {3,5}; 
		int[] yCord = {3,1};		
		setup.setMineTesting(xCord, yCord);
		int[][] array = setup.getCellArray(); 
	
		//location names relative to mine at (3,3) 
		assertEquals("Test count to up-and-left", array[2][2], 1);
		assertEquals("Test count to up-and-right", array[4][2], 2);
		assertEquals("Test count to down-and-left", array[2][4], 1);
		assertEquals("Test count to down-and-right", array[4][4], 1);
		assertEquals("Test count to left", array[3][2], 1);
		assertEquals("Test count to up", array[3][2], 1);
		assertEquals("Test count to down", array[3][4], 1);
		assertEquals("Test count to right", array[4][3], 1);
		assertEquals("Test (3,3) bomb itself", array[3][3], 100);
		assertEquals("Test (5,1) bomb itself", array[5][1], 100);
		assertEquals("Test count to cell that should be non-relevant", array[2][5], 0);		
		
	}
	
	//test four mines stacked for neighbor count
		@Test
		public void testFourStackedMines() {
			
			int[] xCord = {3,3,3,3}; 
			int[] yCord = {3,2,4,1};		
			setup.setMineTesting(xCord, yCord);
			
			int[][] array = setup.getCellArray(); 
		
			//location names relative to mine at (3,3) 
			assertEquals("Test count to up-and-left", array[2][2], 3);
			assertEquals("Test count to up-and-right", array[4][2], 3);
			assertEquals("Test count two below", array[3][5], 1);
			assertEquals("Test count to down-and-right", array[4][4], 2);
			assertEquals("Test count to up-and-two-right", array[4][1], 2);
			assertEquals("Test count to up-and-left", array[4][5], 1);
			
			
		}
		
		//test four mines laid-out horizontally for neighbor count
				@Test
				public void testFourHoriMines() {
					
					int[] xCord = {2,3,4,5}; 
					int[] yCord = {3,3,3,3};		
					setup.setMineTesting(xCord, yCord);
					int[][] array = setup.getCellArray(); 
				
					//location names relative to mine at (3,3) 
					assertEquals("Test count to up-and-left", array[2][2], 2);
					assertEquals("Test count to up-and-right", array[4][2], 3);
					assertEquals("Test count to down", array[3][4], 3);
					assertEquals("Test count to down and left", array[2][4], 2);
					assertEquals("Test count to up-and-far left", array[1][2], 1);



						
					
				}
			
	
	
	//test mine in corner
				@Test
				public void testMineInCorner() {
					
					int[] xCord = {0}; 
					int[] yCord = {0};		
					setup.setMineTesting(xCord, yCord);
					int[][] array = setup.getCellArray(); 
				
					//location names relative to mine at (3,3) 
					assertEquals("Test count to down-and-right", array[1][1], 1);
					assertEquals("Test count to right", array[0][1], 1);
					assertEquals("Test count to far-down-and-right", array[2][2], 0);
					assertEquals("Test count to right", array[1][0], 1);
						
					
				}				
	
	//test seven cells mines
				@Test
				public void testSevenMines() {
					
					int[] xCord = {2,3,4,2,4,2,3,4}; 
					int[] yCord = {2,2,2,3,3,4,4,4};		
					setup.setMineTesting(xCord, yCord);
					int[][] array = setup.getCellArray(); 
				
					//location names relative to mine at (3,3) 
					assertEquals("Test count to down-and-right", array[3][3], 8);
					assertEquals("Test count to down-and-right", array[5][5], 1);
					assertEquals("Test count to down-and-right", array[2][5], 2);
					assertEquals("Test count to down-and-right", array[3][1], 3);
					

						
					
				}	
	

	//test didLose method on clicking mine
	//all mines should be disabled and red
	//all other disabled cells should remain disabled
	//any enabled enabled mines should remain

				@Test
				public void testDidLoseClickMine() {
					
					int[] xCord = {1,5, 19}; 
					int[] yCord = {2,10, 19};		
					setup.setMineTesting(xCord, yCord);
					setup.activateCell(1, 3);
					
					//check if button was clicked and is activated, and that mine was not activated
					assertFalse(setup.getButtonArray()[1][3].isEnabled());
					assertTrue(setup.getButtonArray()[1][2].isEnabled());
					
					//activate a mine, and see if conditions enumerated above hold
					setup.activateCell(1,2);
					assertFalse(setup.getButtonArray()[1][2].isEnabled());
					
					boolean allMinesRedAndDisabled = true;
					boolean allNotMinesEnabledAndTrue = true;
					
					
					int[][] cells = setup.getCellArray();
					JButton[][] buttons = setup.getButtonArray(); 
					
					for(int i = 0; i < cells.length; i++) {
						for(int j =0; j < cells[0].length; j++) {
							if(cells[i][j] == 100) {
								if(!(buttons[i][j].getBackground().equals(Color.red))) {
									allMinesRedAndDisabled = false; 
								}
							}
							else {
								if(i == 1 && j == 2) {
									
								}
								else {
									if(!(buttons[i][j].isEnabled())){
										allNotMinesEnabledAndTrue = false; 
									}
								}
							}
						}
					}
					
					assertTrue(allMinesRedAndDisabled  || allNotMinesEnabledAndTrue);
					assertFalse(buttons[1][2].isEnabled());
					
				}
	
	//test didLose method on clicking not mine (only click (non-mine) should be enabled)
				@Test
				public void testDidLoseNotMine() {
					
					int[] xCord = {1,5, 19}; 
					int[] yCord = {2,10, 19};		
					setup.setMineTesting(xCord, yCord);
					setup.activateCell(1, 3);
					
					
					boolean everyDisabledAndNotRed = true;
					
					
					int[][] cells = setup.getCellArray();
					JButton[][] buttons = setup.getButtonArray(); 
					
					for(int i = 0; i < cells.length; i++) {
						for(int j =0; j < cells[0].length; j++) {
							if(i == 1 && j == 3) {
								
							}
							else {
								if(!(buttons[i][j].isEnabled()) || buttons[i][j].getBackground().equals(Color.red)) {
									System.out.println(i);
									System.out.println(j);
									everyDisabledAndNotRed = false; 
								}
							}
						}
					}
					
					assertTrue(everyDisabledAndNotRed);
					assertFalse(buttons[1][3].isEnabled());
					
				}
	
	//clear zeroes, all zeroes
           @Test
			public void testAllZeroesClear() {
        	   //note that clearMines is called automatically when activeMines is invoked
		     	setup.activateCell(1, 2);
		     	
		     	boolean relBool = true;
		     	//check if all cells (other than mine are disabled (which is the expected behavior)
		     	for(int i = 0; i < setup.getButtonArray().length; i++) {
		     		for(int j = 0; j < setup.getButtonArray()[0].length; j++) {
		     			if(setup.getButtonArray()[i][i].isEnabled()) {
		     				relBool = false; 
		     			}
		     		}
		     	}
		     	assertTrue(relBool);
			}		
	
				
	//clear zeroes, one mine; we have two cases here: either we hit a block next to the mine (and we don't win)
    // or we hit a block that is not, all the zeroes are cleared, and we win.  We will test both below:
           @Test
			public void testOneMineClear() {
       	   //note that clearMines is called automatically when activeMines is invoked
				int[] xCord = {0}; 
				int[] yCord = {0};		
				
				setup.setMineTesting(xCord, yCord);
		     	setup.activateCell(0,1);
		     	
		     	//we should not have won since the count of this cell is not zero (since we are next to a mine)
		     	//therefore, a cell that is not immediately next to the mine (and thus has a count of 0)
		     	//should still be enabled
		     	int[][] nCounts = setup.getCellArray(); 
		     	JButton[][] buttons = setup.getButtonArray(); 
		     	
				assertFalse(buttons[0][1].isEnabled());
				assertTrue(nCounts[0][1] == -1); //-1 indicates the cell has been clicked
				assertTrue(nCounts[1][0] == 1); //-1 indicates the cell has been clicked
				assertTrue(buttons[0][2].isEnabled());
				
				//activate a mine that we know has neighbor count of 0
				setup.activateCell(3,2);
		     	
		     	boolean relBool = true;
		     	//check if all cells (other than mine are disabled (which is the expected behavior)
		     	for(int i = 1; i < setup.getButtonArray().length; i++) {
		     		for(int j = 0; j < setup.getButtonArray()[0].length; j++) {
		     			if(setup.getButtonArray()[i][j].isEnabled()) {
		     				relBool = false; 
		     			}
		     		}
		     	}
		     	assertTrue(relBool);

			}		
	
				 
	//clear zeroes, two mines next to each other
           @Test
       			public void testTwoHorizontalMines() {
              	   //note that clearMines is called automatically when activeMines is invoked
       				int[] xCord = {1,2}; 
       				int[] yCord = {1,1};		
       				
       				setup.setMineTesting(xCord, yCord);
       		     	setup.activateCell(1,0);
       		     	
       		     	//we should not have won since the count of this cell is not zero (since we are next to a mine)
       		     	//therefore, a cell that is not immediately next to the mine (and thus has a count of 0)
       		     	//should still be enabled (see last assertTrue statement here)
       		     	int[][] nCounts = setup.getCellArray(); 
       		     	JButton[][] buttons = setup.getButtonArray(); 
       		     	
       				assertFalse(buttons[1][0].isEnabled());
       				assertTrue(nCounts[1][0] == -1); //-1 indicates the cell has been clicked
       				assertTrue(nCounts[2][0] == 2); //-1 indicates the cell has been clicked
       				assertTrue(buttons[0][5].isEnabled());
       				
       				//activate a mine that we know has neighbor count of 0
       				setup.activateCell(5,2);
       				
       				//activate the mines that still have a count greater than 0 (those around the mines)
       				setup.activateCell(0,0);
       				setup.activateCell(0, 1);
       				setup.activateCell(2, 0);
       		     	boolean relBool = true;
       		     	//check if all cells (other than mine are disabled (which is the expected behavior)
       		     	for(int i = 0; i < setup.getButtonArray().length; i++) {
       		     		for(int j = 0; j < setup.getButtonArray()[0].length; j++) {
       		     			if((i == 2 && j == 1) || (i == 1 && j == 1));
       		     			else {
	       		     			if(setup.getButtonArray()[i][j].isEnabled()) {
	       		     		     	relBool = false; 
	       		     			}
       		     			}
       		     		}
       		     	}
       		     	assertTrue(relBool);

       			}	
	
	//box of mines around empty cells 
           @Test
  			public void testBox() {
         	   //note that clearMines is called automatically when activeMines is invoked
  				int[] xCord = {1,2,3,1,3,1,2,3}; 
  				int[] yCord = {1,1,1,2,2,3,3,3};		
  				
  				setup.setMineTesting(xCord, yCord);
  		     	assertEquals(setup.getCellArray()[2][2], 8);
  		     	
  		     	setup.activateCell(1, 0);
  		     	
  		     	//even if we activate the cell at (1,1), nothing about the rest of the board should change
  		     	assertFalse(setup.getButtonArray()[1][0].isEnabled()); 
  		     	assertTrue(setup.getButtonArray()[2][1].isEnabled());
  		     	
  		     	//set-off the chain that clears all of the 0s on the board 
  		     	setup.activateCell(5, 2);
  		     	
  		     	//activate the remaining cells that don't have count 0, and are not mines
  		     	setup.activateCell(0, 0);
  		     	setup.activateCell(0, 1);
  		     	setup.activateCell(0, 2);
  		     	setup.activateCell(0, 3);
  		     	setup.activateCell(1, 2);
  		     	setup.activateCell(1, 3);
  		     	setup.activateCell(2, 0);
  		     	setup.activateCell(3, 3);
  		     	setup.activateCell(3, 2);
  		     	setup.activateCell(3, 0);
  		     	setup.activateCell(2, 2);


  		    	boolean relBool = true;
   		     	//check if all cells (other than mine are disabled (which is the expected behavior)
   		     	for(int i = 0; i < setup.getButtonArray().length; i++) {
   		     		for(int j = 0; j < setup.getButtonArray()[0].length; j++) {
   		     			if((i == 2 && j == 1) || (i == 1 && j == 1));
   		     			else {
       		     			if(setup.getButtonArray()[i][j].isEnabled()) {
       		     				System.out.println(i);
       		     				System.out.println(j);
       		     				relBool = false; 
       		     			}
   		     			}
   		     		}
   		     	}
   		     	assertTrue(relBool);
  		     	
  		     	
  		     	
           }
				
	//test didWin on losing board
						@Test
						public void testNotWin() {
							int[] xCord = {1}; 
							int[] yCord = {2};		
							setup.setMineTesting(xCord, yCord);
							setup.activateCell(1, 2);
							assertFalse(setup.didWin(frame));
						}
				
	//test didWin on winning board
						@Test
						public void testWin() {
							int[] xCord = {0}; 
							int[] yCord = {0};		
							setup.setMineTesting(xCord, yCord);
							//only need to activate one cell that has a cellCount value of 0
							//since we know the clearZeroes method is working
							setup.activateCell(1,3);
							
							assertTrue(setup.didWin(frame));
						}
}
