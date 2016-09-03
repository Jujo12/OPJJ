package hr.fer.zemris.java.tecaj.hw6.observer2;

/**
 * The demonstration program for the first problem.<br>
 * The program does not accept command line arguments.
 *
 * @author Juraj Juričić
 */
public class ObserverExample {
	
	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);

		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new SquareValue());

		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
