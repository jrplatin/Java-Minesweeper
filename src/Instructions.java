import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder; 

public class Instructions {
	private JFrame frame;
	
	public Instructions(JFrame frame) {
		this.frame = frame; 
		frame.getContentPane().repaint();
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		JLabel text = new JLabel(); 
		text.setFont(new Font("Dialog", Font.PLAIN, 45)); 
		text.setText("<html> Minesweeper is fairly straight-forward game where you are presented with a board of squares. Some squares contain mines, and others"
				+ " don't. If you click on a square containing a mine, you lose. If you manage to click all the squares"
				+ " (without clicking on any bombs), you win!\r\n" + 
				"Clicking a square which doesn't have a bomb reveals the number of neighbouring squares containing bombs,"
				+ "and you can use this information plus some guess work to avoid the bombs. You can also right-click"
				+ "on cells that you suspect to be bombs, but once you right click, the cell cannot be changed.  If you right"
				+ "click all the  mines correctly, then you win!</html>");
		text.setBorder(new EmptyBorder(0,50,0,0));
		
		JButton back = new JButton("Back"); 
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				text.setVisible(false);
				
				frame.getContentPane().removeAll();
				frame.add(new MenuScreen(frame));
			    frame.pack();

			}
		});
		
		frame.add(back);		
		frame.add(text);
		frame.pack(); 

		
		
	
		
		
	}
}
