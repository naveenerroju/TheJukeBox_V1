package com.naveen.jukebox.model;

import java.util.List;

public class User {
    private int id;
    private String name;
    private List<Playlist> playlists;
    private CurrentlyPlaying currentlyPlaying;
    
    public CurrentlyPlaying getCurrentlyPlaying() {
        return currentlyPlaying;
    }
    public void setCurrentlyPlaying(CurrentlyPlaying currentlyPlaying) {
        this.currentlyPlaying = currentlyPlaying;
    }
    public int getId() {
        return id;
    }
    public List<Playlist> getPlaylists() {
        return playlists;
    }
    public User(int id, String name, List<Playlist> playlists, CurrentlyPlaying currentlyPlaying) {
        this.id = id;
        this.name = name;
        this.playlists = playlists;
        this.currentlyPlaying = currentlyPlaying;
    }
    public User() {}
    @Override
    public String toString() {
        return "User [currentlyPlaying=" + currentlyPlaying + ", id=" + id + ", name=" + name
                + ", playlists=" + playlists + "]";
    }

    
}
