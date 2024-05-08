package com.naveen.jukebox.command;

import com.naveen.jukebox.model.Constants;
import com.naveen.jukebox.service.SongsService;
import com.naveen.jukebox.service.UserService;
import com.naveen.jukebox.utility.DataUtility;

/**
 * Implements a command processing system for a jukebox application.
 * <p>
 * This class adheres to the Command design pattern and is responsible for interpreting
 * and executing commands related to various jukebox operations. It interprets commands
 * as strings, parses them, and invokes the appropriate functionality in the system.
 * The class handles operations such as loading data, creating and managing users and playlists,
 * and playing songs or playlists.
 * </p>
 *
 * <p>Operations and their corresponding functionalities are:</p>
 * <ul>
 *   <li>{@code LOAD_DATA_OPERATION}: Loads songs from a specified file path.</li>
 *   <li>{@code CREATE_PLAYLIST_OPERATION}: Creates a new playlist with given songs.</li>
 *   <li>{@code CREATE_USER_OPERATION}: Adds a new user with a specified username.</li>
 *   <li>{@code DELETE_PLAYLIST_OPERATION}: Removes a specified playlist.</li>
 *   <li>{@code MODIFY_PLAYLIST_OPERATION}: Modifies an existing playlist by adding or removing songs.</li>
 *   <li>{@code PLAY_SONG_OPERATION}: Plays a specific song immediately.</li>
 *   <li>{@code PLAY_PLAYLIST_OPERATION}: Starts playback of a specified playlist.</li>
 * </ul>
 *
 * <p>Each command may involve one or more parameters that specify details required for execution.</p>
 *
 * @implNote The class uses instances of {@link UserService} and {@link SongsService} to execute
 * actions on data based on commands. It ensures flexibility and scalability in command processing,
 * making it easy to add new commands as the application grows.
 */
public class JukeboxCommands implements IJukeCommands {
    UserService userService = new UserService();

    /**
     * Executes a specified operation on the jukebox based on the provided command.
     * <p>
     * This method processes a string command by first splitting it into an array of strings. Each operation
     * corresponds to a specific functionality within the system, such as loading data, creating users, managing
     * playlists, and playing songs or playlists. The command is expected to start with an operation identifier
     * that dictates which action to perform. If the operation identifier is not recognized or is missing,
     * an "Invalid Operation" message is printed to the console.
     * </p>
     *
     * @param command A string representing the full command to be executed. The command string should start
     *                with an operation keyword followed by necessary parameters for that operation. The expected
     *                operations are managed internally and checked against known constants.
     *
     * <p>Supported operations include:</p>
     * <ul>
     *   <li>{@code LOAD_DATA_OPERATION}: Load specific data into the system.</li>
     *   <li>{@code CREATE_PLAYLIST_OPERATION}: Create a new playlist.</li>
     *   <li>{@code CREATE_USER_OPERATION}: Create a new user.</li>
     *   <li>{@code DELETE_PLAYLIST_OPERATION}: Delete an existing playlist.</li>
     *   <li>{@code MODIFY_PLAYLIST_OPERATION}: Modify details of an existing playlist.</li>
     *   <li>{@code PLAY_SONG_OPERATION}: Play a specific song.</li>
     *   <li>{@code PLAY_PLAYLIST_OPERATION}: Play a playlist.</li>
     * </ul>
     *
     * <p>Each operation requires different parameters which should be included in the command string after the operation keyword.</p>
     */
    public void mainOperations(String command){
        String[] commands  = command.split(" ");
        if(Constants.OPERATIONS.contains( commands[0])){
            switch (commands[0]) {
                case Constants.LOAD_DATA_OPERATION -> //load data
                        loadData(commands);
                case Constants.CREATE_PLAYLIST_OPERATION -> //create playlist
                        createPlaylist(commands);
                case Constants.CREATE_USER_OPERATION -> //create user
                        createUser(commands);
                case Constants.DELETE_PLAYLIST_OPERATION -> //delete playlist
                        deletePlaylist(commands);
                case Constants.MODIFY_PLAYLIST_OPERATION -> //modify playlist
                        modifyPlaylist(commands);
                case Constants.PLAY_SONG_OPERATION -> //play song
                        playSong(commands);
                case Constants.PLAY_PLAYLIST_OPERATION -> playPlaylist(commands);
                default -> DataUtility.printOutput("Invalid Operation");
            }
        } else {
            DataUtility.printOutput("Invalid Operation");
        }
    }

    private void loadData(String[] commands){
        SongsService songsService = new SongsService();
        String filepath = commands[1];
        try {
            songsService.loadSongs(filepath);
            DataUtility.printOutput("Songs Loaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPlaylist(String[] commands){
        int userId = Integer.parseInt(commands[1]);
        int[] songIds = DataUtility.stringArrayToIntArray(commands);
        String result = userService.createPlaylist(userId, commands[2], songIds);
        DataUtility.printOutput(result);
    }

    private void createUser(String[] commands){
        String username = commands[1];
        DataUtility.printOutput(userService.createUser(username));
        
    }

    private void deletePlaylist(String[] commands){
        int userId = Integer.parseInt(commands[1]);
        int playlistId = Integer.parseInt(commands[2]);
        String result = userService.deletePlaylist(userId, playlistId);
        DataUtility.printOutput(result);
    }

    private void playPlaylist(String[] commands){
        String result = userService.playPlaylist(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
        DataUtility.printOutput(result);
    }

    private void modifyPlaylist(String[] commands){
        String result = "";
        if(commands[1].equals(Constants.ADD_SONG_IN_PLAYLIST_OPERATION)){
            result = userService.addSongInPlaylist(Integer.parseInt(commands[2]), Integer.parseInt(commands[3]), Integer.parseInt(commands[4]));
        } else if(commands[1].equals(Constants.DELETE_SONG_IN_PLAYLIST_OPERATION)) {
            result = userService.deleteSongInPlaylist(Integer.parseInt(commands[2]), Integer.parseInt(commands[3]), Integer.parseInt(commands[4]));
        }
        DataUtility.printOutput(result);
    }

    private void playSong(String[] commands){
        String result = null;
        try {
            result = userService.playSong(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
        } catch (NumberFormatException e) {
            result = userService.playSong(Integer.parseInt(commands[1]), commands[2]);
        }
        DataUtility.printOutput(result);
        
    }
    
}
