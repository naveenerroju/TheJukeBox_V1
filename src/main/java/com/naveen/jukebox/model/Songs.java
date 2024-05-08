package com.naveen.jukebox.model;

import java.util.List;

public class Songs {
    private int id;
    private String title;
    private String genre;
    private String album;
    private String owner;
    private List<String> collaboration;
    
    public int getId() {
        return id;
    }

    public Songs(int id, String title, String genre, String album, String owner,
            List<String> collaboration) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.album = album;
        this.owner = owner;
        this.collaboration = collaboration;
    }
    public Songs() {}
    
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public List<String> getCollaboration() {
        return collaboration;
    }
    public void setCollaboration(List<String> collaboration) {
        this.collaboration = collaboration;
    }

    @Override
    public String toString() {
        return "Song - "+this.title+"\n"+
        "Album - "+this.album+"\n"+
        "Artists - "+this.owner+"\n";
    }
}
