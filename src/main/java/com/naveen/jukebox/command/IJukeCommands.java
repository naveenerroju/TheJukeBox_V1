package com.naveen.jukebox.command;

@FunctionalInterface
public interface IJukeCommands {
    /**
     * Executes a specified operation on the jukebox based on the provided command.
     * <p>
     * This method processes a string command by first splitting it into an array of strings. Each operation
     * corresponds to a specific functionality within the system, such as loading data, creating users, managing
     * playlists, and playing songs or playlists. The command is expected to start with an operation identifier
     * that dictates which action to perform.
     * </p>
     *
     * @param command A string representing the full command to be executed. The command string should start
     *                with an operation keyword followed by necessary parameters for that operation. The expected
     *                operations are managed internally and checked against known constants.
     *
     */
    void mainOperations(String command);
}
