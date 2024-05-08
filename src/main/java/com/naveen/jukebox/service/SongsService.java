package com.naveen.jukebox.service;

import java.util.List;
import com.naveen.jukebox.model.Songs;
import com.naveen.jukebox.repository.SongsRepository;
import com.naveen.jukebox.utility.DataUtility;
import com.opencsv.exceptions.CsvException;

public class SongsService {

    private final SongsRepository repository = new SongsRepository();
    
    public void loadSongs(String filepath) throws CsvException{
        List<String[]> res = DataUtility.readCsvFile(filepath);
        repository.loadSongs(DataUtility.convertDataSongs(res));
    }

    public Songs getSongsBySongId(int songId){
        for(Songs song: repository.getSongs()){
            if(song.getId()==songId){
                return song;
            }
        }
        return null;
    }

}
