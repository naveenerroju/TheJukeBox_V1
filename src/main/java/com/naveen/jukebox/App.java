package com.naveen.jukebox;

import java.util.*;
import java.util.stream.Collectors;

import com.naveen.jukebox.command.JukeboxCommands;
import com.naveen.jukebox.utility.DataUtility;

/**
 * The main application class for the Jukebox system.
 * <p>
 * This class is responsible for initializing the application, processing command-line arguments,
 * and executing operations based on commands read from a specified input file. It uses the
 * {@link JukeboxCommands} class to execute these commands, which include actions like managing playlists,
 * users, and playback operations.
 * </p>
 *
 * @implNote This class is designed to be run with Gradle, where command-line arguments specify the input file
 * containing commands to be executed. It processes these commands sequentially as they appear in the file.
 *<br>
 * <p>For more details, you can reach out to the author:</p>
 * <ul>
 * <li><strong>Author:</strong> Naveen Kumar Errojula</li>
 * <li><strong>Email:</strong> <a href="mailto:naveenkumarerroju@gmail.com">naveenkumarerroju@gmail.com</a></li>
 * <li><strong>GitHub:</strong> <a href="https://github.com/naveenerroju">https://github.com/naveenerroju/</a></li>
 * </ul>
 * <p>
 * @author Naveen Kumar
 */
public class App {

    static JukeboxCommands commands = new JukeboxCommands();

    /**
     * The main entry point of the application.
     * <p>
     * This method reads the command-line arguments to find an input file specification. It expects
     * arguments in the format "INPUT_FILE=filename". If the arguments match this expected format,
     * it proceeds to execute commands from the specified file.
     * </p>
     *
     * @param args the command-line arguments, expected to contain the input file path.
     */
    public static void main(String[] args) {
        List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));
        String expectedSequence = "INPUT-FILE";
        String actualSequence =
                commandLineArgs.stream().map(a -> a.split("=")[0]).collect(Collectors.joining("$"));
        if (expectedSequence.equals(actualSequence)) {
            run(commandLineArgs);
        } else {
            DataUtility.printOutput("Error: Incorrect command line argument format. Expecting INPUT_FILE=filename");
        }
    }

    /**
     * Processes the commands specified in the input file and executes them using the JukeboxCommands object.
     * <p>
     * This method iterates over each command found in the provided input file, executing them sequentially
     * to manipulate the state of the Jukebox as specified by each command.
     * </p>
     *
     * @param commandLineArgs A list containing the command line arguments specifying the input file.
     *                        Each argument is expected to follow the "INPUT_FILE=filename" format.
     */
    public static void run(List<String> commandLineArgs) {
        for (String command : commandLineArgs) {
            List<String> operations = DataUtility.readFileInList(command.substring(11));
            for (String operation : operations) {
                commands.mainOperations(operation);
            }
        }
    }
}
