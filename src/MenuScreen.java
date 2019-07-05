import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MenuScreen extends JPanel {
	private JFrame frame;
	public MenuScreen(JFrame frame) {
		this.frame = frame; 
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JButton start = new JButton("Start");
		JButton instructions = new JButton("Instructions");
		JButton winners = new JButton("View Winners");

		
		JLabel titleText = new JLabel("Minesweeper");
		titleText.setFont(new Font("Impact", Font.PLAIN, 100)); 
		titleText.setAlignmentX(Component.CENTER_ALIGNMENT);
		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
		winners.setAlignmentX(Component.CENTER_ALIGNMENT);

		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.getContentPane().removeAll();
				new GameSetup(frame, false); 
			
			}
		});
		instructions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				new Instructions(frame);
			}
		});
		winners.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				new Winners(frame);
			}
		});
		add(Box.createRigidArea(new Dimension(0,20))); 
		add(titleText);
		add(Box.createRigidArea(new Dimension(0,20))); 
		add(start);
		add(Box.createRigidArea(new Dimension(0,20))); 
		add(instructions); 
		add(Box.createRigidArea(new Dimension(0,20))); 
		add(winners); 
		frame.pack();
	 
	}

}
