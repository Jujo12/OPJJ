package hr.fer.zemris.java.hw16.trazilica;

import hr.fer.zemris.java.hw16.console.ConsoleContext;
import hr.fer.zemris.java.hw16.console.commands.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The simple console for document search engine. Expects one command line argument:
 * path to directory containing articles. 
 *
 * @author Juraj Juričić
 */
public class Konzola {
    
    /** The console context. */
    private static ConsoleContext consoleContext;
    
    /** The used search engine. */
    private static SearchEngine engine;
    
    /** The commands map. */
    private static Map<String, AbstractCommand> commands = new HashMap<>();

    /**
     * The main method that is executed when the program is run.
     *
     * @param args Command line arguments
     */
    public static void main( String[] args ){
        if (args.length != 1){
            System.err.println("One argument excepted: path to documents' directory.");
            return;
        }

        Path dirPath = Paths.get(args[0]);
        File dirFile = dirPath.toFile();
        if (!dirFile.isDirectory() || !dirFile.canRead()){
            System.err.println("Given path is not an accessible directory.");
        }

        System.out.println("Building dictionary...");
        engine = new SearchEngine(dirPath);
        System.out.println("Dictionary size: " + engine.getDictionarySize() + " words.");

        consoleContext = new ConsoleContext(engine);
        commands.put("query", new QueryCommand(consoleContext));
        commands.put("type", new TypeCommand(consoleContext));
        commands.put("results", new ResultsCommand(consoleContext));
        commands.put("exit", new ExitCommand(consoleContext));

        try(Scanner sc = new Scanner(System.in)){
            while(true){
                System.out.print("Enter command > ");
                if (!sc.hasNext()){
                    break;
                }

                String line = sc.nextLine();
                String[] splitLine = line.split(" ", 2);

                AbstractCommand command = commands.get(splitLine[0]);
                if (command == null){
                    System.out.println("Unknown command.");
                    continue;
                }

                String commandArgs = (splitLine.length == 2) ? splitLine[1] : "";
                try{
                	command.execute(commandArgs);
                }catch(Exception e){
                	try{
                		command.execute(commandArgs);
                	}catch(Exception e1){
                		System.out.println("An error occured: " + e.getMessage());
                	}
                }
            }
        }
    }
}
