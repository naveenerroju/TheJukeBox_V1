package com.naveen.jukebox;

import java.util.*;
import java.util.stream.Collectors;

import com.naveen.jukebox.command.JukeboxCommands;
import com.naveen.jukebox.utility.DataUtility;


public class App {

    static JukeboxCommands commands = new JukeboxCommands();

    // To run the application ./gradlew run --args="INPUT_FILE=jukebox-input.txt"
    public static void main(String[] args){
         List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));
         String expectedSequence = "INPUT-FILE";
         String actualSequence =
                 commandLineArgs.stream().map(a -> a.split("=")[0]).collect(Collectors.joining("$"));
         if (expectedSequence.equals(actualSequence)) {
             run(commandLineArgs);
         }

    }

    public static void run(List<String> commandLineArgs) {

        for (String command : commandLineArgs) {
            List<String> operations = DataUtility.readFileInList(command.substring(11));

            for (String operation : operations) {
                commands.mainOperations(operation);
            }
        } 
    }

}
