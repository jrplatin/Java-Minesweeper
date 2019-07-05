import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class Game implements Runnable{
	
	private MenuScreen menu; 
	

	
	
	
	public void run() {
		
		final JFrame frame = new JFrame("Minesweeper Redux");
		frame.setLocation(200, 200);	 


	    menu = new MenuScreen(frame); 
	    frame.add(menu); 
	    frame.setPreferredSize(new Dimension(1200, 1200));
	    frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		
		
		//TODO ADD USER INPUT WHEN USER STARTS GAME!
	
			
	}
	
	
	public static void main (String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
	
	
	
	
	
	
	
}