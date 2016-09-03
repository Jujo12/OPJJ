package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * The demonstration program for {@link CalcLayout}.
 *
 * @author Juraj Juričić
 */
public class CalcLayoutDemo extends JFrame {
	
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new calc layout demo frame.
	 */
	public CalcLayoutDemo() {
		JPanel p = new JPanel(new CalcLayout(3));
		p.add(new JLabel("x"), "1,1");
		p.add(new JLabel("y"), "2,3");
		p.add(new JLabel("z"), "2,7");
		p.add(new JLabel("w"), "4,2");
		p.add(new JLabel("a"), "4,5");
		p.add(new JLabel("b"), "4,7");
		
		for(Component c : p.getComponents()){
			JLabel l = (JLabel) c;
			l.setBorder(BorderFactory.createLineBorder(Color.black));
		}
		
		this.add(p);
		
		pack();
	}
	
	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args Command line arguments
	 */
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				CalcLayoutDemo window = new CalcLayoutDemo();
				window.setVisible(true);
			}
		});
	}
}
