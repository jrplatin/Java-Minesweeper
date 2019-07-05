import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.omg.CORBA.portable.InputStream;

public class Winners {
	JFrame frame; 
	public Winners(JFrame frame)  {
		this.frame = frame; 
		JScrollPane mainPane = new JScrollPane(); 
		frame.getContentPane().repaint();
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		JLabel text = new JLabel(); 
		text.setFont(new Font("Dialog", Font.PLAIN, 45)); 
		
		String fileName = "Files/winners.txt";
		TreeSet<Map.Entry<String, String>> toDisplay = new TreeSet<Map.Entry<String, String>> (); 
		
		try {
			toDisplay = entriesSortedByValues(readFile(fileName));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String realDisplay = "";
		
		
		if(toDisplay.size() < 3) {
			realDisplay = "No winners yet!"; 
		}
		else {
			int num = 1; 
			for(Map.Entry<String, String> ent : toDisplay) {
				realDisplay += String.valueOf(num) + ". " + ent.toString().replaceAll("=", ",  ") + "<br></br>"; 
				num++; 
			}
		}
		
		text.setText("<html>" + realDisplay +  "</html>");
		text.setBorder(new EmptyBorder(0,50,0,0));
		
		JButton back = new JButton("Back"); 
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				text.setVisible(false);
				
				frame.getContentPane().removeAll();
				frame.add(new MenuScreen(frame));
				frame.repaint();
			    frame.pack();

			}
		});
		
		frame.add(back);		


		JScrollPane scroll = new JScrollPane (text, 
		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		frame.add(scroll);

		frame.pack(); 

	}
	public TreeMap<String, String> readFile (String fileName) throws IOException {
	String line = null; 
	TreeMap<String, String> names = new TreeMap<String, String>(); 
		
		try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {  
            	if(line.equals("No winners yet!")) {}
            	else {
                	if(names.containsKey((line.substring(0, line.lastIndexOf(","))))){
                		String bestTime = (line.substring(0, line.lastIndexOf(",")));
                		
                	}
                	else {
            		names.put((line.substring(0, line.lastIndexOf(","))), (line.substring(line.lastIndexOf(",") + 1)));     
                	}
            	}
            }   
            bufferedReader.close(); 
        }
        catch(FileNotFoundException ex) {
        	File file = new File("Files/winners.txt"); 
        	FileWriter writer = new FileWriter(file);
        	writer.write("No winners yet!");   
        
        	writer.close();
        }
		return names;
	}
	
	static <K,V extends Comparable<? super V>> TreeSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
	    TreeSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
	        new Comparator<Map.Entry<K,V>>() {
	            @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
	                int res = e1.getValue().compareTo(e2.getValue());
	                if (e1.getKey().equals(e2.getKey())) {
	                    return res; // Code will now handle equality properly
	                } else {
	                    return res != 0 ? res : 1; // While still adding all entries
	                }
	            }
	        }
	    );
	    sortedEntries.addAll(map.entrySet());
	    return sortedEntries;
	}
}
