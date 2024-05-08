package com.naveen.jukebox.utility;

import com.naveen.jukebox.model.Playlist;
import com.naveen.jukebox.model.Songs;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DataUtilityTest {

    @Test
    @DisplayName("Method should read txt file contents")
    void readFileInList() {
        String filePath = "jukebox-input.txt";
        List<String> output = DataUtility.readFileInList(filePath);
        String expected = """
                LOAD-DATA songs.csv
                CREATE-USER Kiran
                CREATE-PLAYLIST 1 MY_PLAYLIST_1 1 4 5 6
                CREATE-PLAYLIST 1 MY_PLAYLIST_2 1
                DELETE-PLAYLIST 1 2
                PLAY-PLAYLIST 1 1
                MODIFY-PLAYLIST ADD-SONG 1 1 7
                MODIFY-PLAYLIST DELETE-SONG 1 1 7
                PLAY-SONG 1 5
                PLAY-SONG 1 NEXT
                PLAY-SONG 1 NEXT
                PLAY-SONG 1 BACK
                PLAY-SONG 1 BACK
                PLAY-SONG 1 19
                
                """;
        List<String> expectedList = Arrays.stream(expected.split("\n")).collect(Collectors.toList());
        assertEquals(expectedList, output);
    }

    @Test
    @DisplayName("Method should read csv file contents")
    void readCsvFile() throws CsvException {
        String csvFile = "songs.csv";
        List<String[]> songs = DataUtility.readCsvFile(csvFile);
        assertEquals(30, songs.size());
    }

    @Test
    void convertDataSongs() {
        List<String[]> songsString = new ArrayList<>();
        songsString.add(new String[]{"12","Lithium","Rock","Nevermind","Nirvana","Nirvana"});

        List<Songs> songs = DataUtility.convertDataSongs(songsString);
        assertEquals(12, songs.get(0).getId());
        assertEquals("Rock", songs.get(0).getGenre());
        assertEquals("Lithium", songs.get(0).getTitle());
        assertEquals("Nevermind", songs.get(0).getAlbum());
        assertEquals("Nirvana", songs.get(0).getOwner());
        assertEquals("Nirvana", songs.get(0).getCollaboration().get(0));
    }

    @Test
    void stringArrayToIntArray() {
        String[] numbers = new String[]{"a","b","c","1","2"};
        int[] expected = new int[]{1, 2};

        assertArrayEquals(expected, DataUtility.stringArrayToIntArray(numbers));
    }

    @Test
    void constructSongResponse() {
        Songs song = new Songs(1, "Hey there Delilah", "Melody", "Album", "Artist", new ArrayList<String>());

        String resposne = DataUtility.constructSongResponse(song);
        String expected = """
                Current Song Playing
                Song - Hey there Delilah
                Album - Album
                Artists -\s""";
        assertEquals(expected, resposne);
    }

    @Test
    void constructPlaylistResponse() {
        Songs song = new Songs(1, "Hey there Delilah", "Melody", "Album", "Artist", new ArrayList<String>());
        Playlist playlist = new Playlist(1, "favorites", List.of(song));

        String expected = """
                Playlist ID - 1
                Playlist Name - favorites
                Song IDs - 1""";

        assertEquals(expected, DataUtility.constructPlaylistResponse(playlist));
    }

}