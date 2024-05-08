package com.naveen.jukebox.repository;

import java.util.ArrayList;
import java.util.List;
import com.naveen.jukebox.model.Songs;

public class SongsRepository {
    private static List<Songs> songs = new ArrayList<>();

    public List<Songs> getSongs(){
        return SongsRepository.songs;
    }

    public void loadSongs(List<Songs> songs){
        SongsRepository.songs.addAll(songs);
    }

    public Songs getSongBySongId(int id){
        for(Songs song: SongsRepository.songs){
            if(song.getId()==id){
                return song;
            }
        }

        return null;
    }

    
}
