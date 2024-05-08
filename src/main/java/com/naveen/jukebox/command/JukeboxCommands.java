package com.naveen.jukebox.command;

import com.naveen.jukebox.model.Constants;
import com.naveen.jukebox.service.SongsService;
import com.naveen.jukebox.service.UserService;
import com.naveen.jukebox.utility.DataUtility;

public class JukeboxCommands implements IJukeCommands {
    UserService userService = new UserService();

    public void mainOperations(String command){
        String[] commands  = command.split(" ");
        if(Constants.OPERATIONS.contains( commands[0])){
            switch(commands[0]){
                case Constants.LOAD_DATA_OPERATION : //load data
                    loadData(commands);
                    break;
                case Constants.CREATE_PLAYLIST_OPERATION : //create playlist
                    createPlaylist(commands);
                    break;
                case Constants.CREATE_USER_OPERATION : //create user
                    createUser(commands);
                    break;
                case Constants.DELETE_PLAYLIST_OPERATION : //delete playlist
                    deletePlaylist(commands);
                    break;
                case Constants.MODIFY_PLAYLIST_OPERATION : //modify playlist
                    modifyPlaylist(commands);
                    break;
                case Constants.PLAY_SONG_OPERATION : //play song
                    playSong(commands);
                    break;
                case Constants.PLAY_PLAYLIST_OPERATION:
                    playPlaylist(commands);
                    break;
                default:
                    System.out.println("Invalid Operation");
                    break;
            }
        } else {
            System.out.println("Invalid Operation");
        }
    }

    private void loadData(String[] commands){
        SongsService songsService = new SongsService();
        String filepath = commands[1];
        try {
            songsService.loadSongs(filepath);
            System.out.println("Songs Loaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPlaylist(String[] commands){
        int userId = Integer.parseInt(commands[1]);
        int[] songIds = DataUtility.stringArrayToIntArray(commands);
        String result = userService.createPlaylist(userId, commands[2], songIds);
        System.out.println(result);
    }

    private void createUser(String[] commands){
        String username = commands[1];
        System.out.println(userService.createUser(username));
        
    }

    private void deletePlaylist(String[] commands){
        int userId = Integer.parseInt(commands[1]);
        int playlistId = Integer.parseInt(commands[2]);
        String result = userService.deletePlaylist(userId, playlistId);
        System.out.println(result);
    }

    private void playPlaylist(String[] commands){
        String result = userService.playPlaylist(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
        System.out.println(result);
    }

    private void modifyPlaylist(String[] commands){
        String result = "";
        if(commands[1].equals(Constants.ADD_SONG_IN_PLAYLIST_OPERATION)){
            result = userService.addSongInPlaylist(Integer.parseInt(commands[2]), Integer.parseInt(commands[3]), Integer.parseInt(commands[4]));
        } else if(commands[1].equals(Constants.DELETE_SONG_IN_PLAYLIST_OPERATION)) {
            result = userService.deleteSongInPlaylist(Integer.parseInt(commands[2]), Integer.parseInt(commands[3]), Integer.parseInt(commands[4]));
        }
        System.out.println(result);
    }

    private void playSong(String[] commands){
        String result = null;
        try {
            result = userService.playSong(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
        } catch (NumberFormatException e) {
            result = userService.playSong(Integer.parseInt(commands[1]), commands[2]);
        }
        System.out.println(result);
        
    }
    
}
