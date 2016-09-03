package hr.fer.zemris.java.simplecomp;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * The Class Simulator.
 *
 * @author Juraj Juričić
 */
public class Simulator {

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args Command line arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		// Stvori računalo s 256 memorijskih lokacija i 16 registara
		Computer comp = new ComputerImpl(256, 16);
		
		// Stvori objekt koji zna stvarati primjerke instrukcija
		InstructionCreator creator = new InstructionCreatorImpl(
				"hr.fer.zemris.java.simplecomp.impl.instructions"
		);
		
		String filePath;
		Scanner sc = null;
		
		if (args.length > 0){
			filePath = args[0];
		}else{
			sc = new Scanner(System.in);
			System.out.println("Enter input file path:");
			filePath = sc.nextLine();
		}
		
		Path p = Paths.get(filePath);
		if (!p.toFile().exists()){
			System.err.println("Can't find file " + filePath);
			return;
		}
		
		// Napuni memoriju računala programom iz datoteke; instrukcije stvaraj
		// uporabom predanog objekta za stvaranje instrukcija
		ProgramParser.parse(
				filePath,
				comp,
				creator
		);
		
		// Stvori izvršnu jedinicu
		ExecutionUnit exec = new ExecutionUnitImpl();
		
		// Izvedi program
		try{
			exec.go(comp);
		}catch(Exception e){
			System.err.println("An error occured while executing program " + filePath);
			e.printStackTrace(System.err);
		}
		
		try{
			sc.close();
		}catch(Exception ignorable){}
		
		comp.getMemory();
	}

}
