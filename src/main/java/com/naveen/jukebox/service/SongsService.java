package com.naveen.jukebox.service;

import java.util.List;

import com.naveen.jukebox.model.Songs;
import com.naveen.jukebox.repository.SongsRepository;
import com.naveen.jukebox.utility.DataUtility;
import com.opencsv.exceptions.CsvException;

/**
 * Service class to manage song operations for the jukebox application.
 * <p>
 * This class provides functionalities to load and retrieve song data. It utilizes
 * {@link SongsRepository} to persist and manage song data, ensuring that song information
 * is centrally managed and accessible throughout the application.
 * </p>
 */
public class SongsService {

    private final SongsRepository repository = new SongsRepository();

    /**
     * Loads songs into the repository from a CSV file.
     * <p>
     * This method reads songs from a specified CSV file path, converts them into song objects,
     * and loads them into the repository. It handles CSV-related exceptions and passes
     * them up to the caller.
     * </p>
     *
     * @param filepath the path to the CSV file containing song data.
     * @throws CsvException if an error occurs during CSV file parsing.
     */
    public void loadSongs(String filepath) throws CsvException {
        List<String[]> res = DataUtility.readCsvFile(filepath);
        repository.loadSongs(DataUtility.convertDataSongs(res));
    }

    /**
     * Retrieves a song by its ID.
     * <p>
     * This method searches for a song within the repository using the provided song ID.
     * If a match is found, the song object is returned; otherwise, it returns null.
     * </p>
     *
     * @param songId the unique identifier of the song to retrieve.
     * @return the {@link Songs} object if found, or null if no song matches the given ID.
     */
    public Songs getSongsBySongId(int songId) {
        for (Songs song : repository.getSongs()) {
            if (song.getId() == songId) {
                return song;
            }
        }
        return null;
    }

}
