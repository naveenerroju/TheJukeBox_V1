package com.naveen.jukebox.model;

import java.util.List;

public class Playlist {
    private int id;
    private String title;
    private List<Songs> songs;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<Songs> getSongs() {
        return songs;
    }
    public void setSongs(List<Songs> songs) {
        this.songs = songs;
    }
    public Playlist(int id, String title, List<Songs> songs) {
        this.id = id;
        this.title = title;
        this.songs = songs;
    }
    public Playlist() {}
    @Override
    public String toString() {
        return "Playlist [id=" + id + ", songs=" + songs + ", title=" + title + "]";
    }
    
    
}
