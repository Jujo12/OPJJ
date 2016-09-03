package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * The program PrimDemo. Display a windows with two lists of prime numebrs with the same model.
 *
 * @author Juraj Juričić
 */
public class PrimDemo extends JFrame {
	
	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new prim demo window and initializes the GUI.
	 */
	public PrimDemo() {
		initGUI();
	}
	
	/**
	 * Initializes the GUI. Creates two lists that point to the same model.
	 */
	private void initGUI(){
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		PrimListModel listModel = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(listModel);
		JList<Integer> list2 = new JList<>(listModel);
		
		this.setLayout(new BorderLayout());
		
		JPanel lists = new JPanel(new GridLayout(2, 1));
		lists.add(new JScrollPane(list1));
		lists.add(new JScrollPane(list2));
		
		JButton nextButton = new JButton("Sljedeći");
		nextButton.addActionListener(e -> {
			listModel.next();
		});
		
		this.add(lists, BorderLayout.CENTER);
		this.add(nextButton, BorderLayout.SOUTH);
		
		this.setSize(400, 400);
	}
	
	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args Command line arguments
	 */
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				PrimDemo window = new PrimDemo();
				window.setVisible(true);
			}
		});
	}
}
