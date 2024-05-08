package com.naveen.jukebox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.naveen.jukebox.command.JukeboxCommands;
import com.naveen.jukebox.utility.DataUtility;
import com.opencsv.exceptions.CsvException;


public class App {

    static JukeboxCommands commands = new JukeboxCommands();

    // To run the application ./gradlew run --args="INPUT_FILE=jukebox-input.txt"
    public static void main(String[] args) throws FileNotFoundException, IOException, CsvException {
        List<String> arguments= new ArrayList<>(List.of("INPUT_FILE=jukebox-input.txt"));
        
        // List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));
        // String expectedSequence = "INPUT-FILE";
        // String actualSequence =
        //         commandLineArgs.stream().map(a -> a.split("=")[0]).collect(Collectors.joining("$"));
        // if (expectedSequence.equals(actualSequence)) {
        //     run(commandLineArgs);
        // }
        run(arguments);

    }

    public static void run(List<String> commandLineArgs) {

        for (String command : commandLineArgs) {
            List<String> operations = DataUtility.readFileInList(command.substring(11));

            Iterator<String> ops = operations.iterator();
            while (ops.hasNext()){
                commands.mainOperations(ops.next());
            }
        } 
    }

}
