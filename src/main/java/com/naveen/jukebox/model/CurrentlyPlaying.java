package com.naveen.jukebox.model;

public class CurrentlyPlaying {
    private int playlistId;
    private int songId;
    
    public int getPlaylistId() {
        return playlistId;
    }
    public int getSongId() {
        return songId;
    }
    public CurrentlyPlaying(final int playlistId, final int songId) {
        this.playlistId = playlistId;
        this.songId = songId;
    }
    @Override
    public String toString() {
        return "CurrentlyPlaying [playlistId=" + playlistId + ", songId=" + songId + "]";
    }

    
}
