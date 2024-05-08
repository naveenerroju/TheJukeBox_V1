package com.naveen.jukebox.model;

public class CurrentlyPlaying {
    private int playlistId;
    private int songId;
    
    public int getPlaylistId() {
        return playlistId;
    }
    public void setPlaylistId(final int playlistId) {
        this.playlistId = playlistId;
    }
    public int getSongId() {
        return songId;
    }
    public void setSongId(final int songId) {
        this.songId = songId;
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
