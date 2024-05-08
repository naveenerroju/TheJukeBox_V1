package com.naveen.jukebox.utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.naveen.jukebox.model.Playlist;
import com.naveen.jukebox.model.Songs;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class DataUtility {

    public static List<String> readFileInList(String fileName){
 
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(
                Paths.get(fileName),
                StandardCharsets.UTF_8);
        }
 
        catch (IOException e) {
 
            // do something
            e.printStackTrace();
        }
        return lines;
    }

    public static List<String[]> readCsvFile(String filePath) throws FileNotFoundException, IOException, CsvException{
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = csvReader.readAll();
            // for (String[] record : records) {
            //     for (String field : record) {
            //         System.out.print(field + " ");
            //     }
            //     System.out.println();
            // }
            return records;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Songs> convertDataSongs(List<String[]> data){
        List<Songs> songs = new ArrayList<>();

        for (String[] s : data) {
            Songs song = new Songs();
            for (int i = 0; i < s.length; i++) {
                switch (i) {
                    case 0:
                        song.setId(Integer.parseInt(s[i]));
                        break;
                    case 1:
                        song.setTitle(s[i]);
                        break;
                    case 2:
                        song.setGenre(s[i]);
                        break;
                    case 3:
                        song.setAlbum(s[i]);
                        break;
                    case 4:
                        song.setOwner(s[i]);
                        break;
                    case 5:
                        List<String> collabs = List.of(s[i].split("#"));
                        song.setCollaboration(collabs);
                        break;
                    default:
                        break;
                }
            }

            songs.add(song);
        }

        return songs;
    }


    public static void printSongs(List<Songs> songs){
        for (Songs song : songs) {
            System.out.println(song.toString());
        }
    }

    public static int[] stringArrayToIntArray(String[] sarr){
        int[] result = new int[sarr.length-3];
        for(int i = 3; i<sarr.length; i++){
            result[i-3]=Integer.parseInt(sarr[i]);
        }
        return result;
    }

    public static String constructSongResponse(Songs song){
        return "Current Song Playing\n"+
                "Song - "+song.getTitle()+"\n"+
                "Album - "+song.getAlbum()+"\n"+
                "Artists - "+getCollaboratorsString(song);
    }

    private static String getCollaboratorsString(Songs song){
        List<String> collabs = song.getCollaboration();
        String result = String.join(",", collabs);
        return result;
    }

    public static String constructPlaylistResponse(Playlist playlist){
        return "Playlist ID - "+playlist.getId()+"\n"+
        "Playlist Name - "+playlist.getTitle()+"\n"+
        "Song IDs - "+songsOfPlaylist(playlist.getSongs());
    }

    private static String songsOfPlaylist(List<Songs> songs){
        StringBuilder sb = new StringBuilder();
        for (Songs songs2 : songs) {
            sb.append(songs2.getId());
            sb.append(" ");
        }

        return sb.toString().substring(0, sb.length()-1);
    }
}
