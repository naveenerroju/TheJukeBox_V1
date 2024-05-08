package com.naveen.jukebox.model;

import java.util.List;

public class Constants {
    public static final String LOAD_DATA_OPERATION = "LOAD-DATA";
    public static final String CREATE_USER_OPERATION = "CREATE-USER";
    public static final String CREATE_PLAYLIST_OPERATION = "CREATE-PLAYLIST";
    public static final String DELETE_PLAYLIST_OPERATION = "DELETE-PLAYLIST";
    public static final String MODIFY_PLAYLIST_OPERATION = "MODIFY-PLAYLIST";
    public static final String PLAY_SONG_OPERATION = "PLAY-SONG";
    public static final String PLAY_PLAYLIST_OPERATION = "PLAY-PLAYLIST";
    public static final List<String> OPERATIONS = List.of(Constants.LOAD_DATA_OPERATION, Constants.CREATE_USER_OPERATION, Constants.CREATE_PLAYLIST_OPERATION, Constants.DELETE_PLAYLIST_OPERATION, Constants.PLAY_PLAYLIST_OPERATION, Constants.MODIFY_PLAYLIST_OPERATION, Constants.PLAY_SONG_OPERATION);
    public static final String ADD_SONG_IN_PLAYLIST_OPERATION = "ADD-SONG";
    public static final String DELETE_SONG_IN_PLAYLIST_OPERATION = "DELETE-SONG";
    public static final String PLAY_NEXT_SONG_OPERATION = "NEXT";
    public static final String PLAY_PREVIOUS_SONG_OPERATION = "BACK";
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String PLAYLIST_NOT_FOUND = "Playlist not found.";
    public static final String SONG_NOT_FOUND = "Song not found.";
    private Constants() {
    }
}