package com.naveen.jukebox.model;

import java.util.List;

public class Constants {
    public final static List<String> OPERATIONS = List.of("LOAD-DATA", "CREATE-USER", "CREATE-PLAYLIST", "DELETE-PLAYLIST", "PLAY-PLAYLIST", "MODIFY-PLAYLIST","PLAY-SONG");
    public final static String LOAD_DATA_OPERATION = "LOAD-DATA";
    public final static String CREATE_USER_OPERATION = "CREATE-USER";
    public final static String CREATE_PLAYLIST_OPERATION = "CREATE-PLAYLIST";
    public final static String DELETE_PLAYLIST_OPERATION = "DELETE-PLAYLIST";
    public final static String MODIFY_PLAYLIST_OPERATION = "MODIFY-PLAYLIST";
    public final static String PLAY_SONG_OPERATION = "PLAY-SONG";
    public static final String PLAY_PLAYLIST_OPERATION = "PLAY-PLAYLIST";
    public static final String ADD_SONG_IN_PLAYLIST_OPERATION = "ADD-SONG";
    public static final String DELETE_SONG_IN_PLAYLIST_OPERATION = "DELETE-SONG";
    public static final String PLAY_NEXT_SONG_OPERATION = "NEXT";
    public static final String PLAY_PREVIOUS_SONG_OPERATION = "BACK";
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String PLAYLIST_NOT_FOUND = "Playlist not found.";
    public static final String SONG_NOT_FOUND = "Song not found.";
}