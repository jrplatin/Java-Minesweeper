

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class RelTimer extends JComponent {


    private ClockListener clock = new ClockListener();
    private  Timer timer = new Timer(53, clock);
    private  JLabel clockLabel = new JLabel("0");
    private  SimpleDateFormat date = new SimpleDateFormat("mm:ss:SSS");
    private long startTime;
    private JFrame frame; 
    private Container lay; 

    public RelTimer(JFrame frame, Container lay) {
    	
    	this.lay = lay; 
    	this.frame = frame;
        timer.setInitialDelay(0);
        
        clockLabel.setHorizontalAlignment(JTextField.CENTER);
        clockLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        startTime = System.currentTimeMillis();
        timer.start();
        
        lay.add(clockLabel);       
        frame.add(lay, BorderLayout.NORTH); 
    }
    public void updateClock() {
        Date timeElapsed = new Date(System.currentTimeMillis() - startTime);
        clockLabel.setText(date.format(timeElapsed));
    }
    
    public void stopClock() {
    	timer.stop();
    	
    }
    public void reset() {
        startTime = System.currentTimeMillis();
    	timer.restart(); 
    	clockLabel.setText("0");
    }
    public String getTime() {
    	return clockLabel.getText(); 
    }
    public class ClockListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateClock();
        }
    }

    public static void main(String[] args) {
       
    }
}