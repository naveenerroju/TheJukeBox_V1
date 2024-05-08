package com.naveen.jukebox.repository;

import java.util.ArrayList;
import java.util.List;
import com.naveen.jukebox.model.Songs;

/**
 * A repository class for managing a collection of songs.
 * <p>
 * This class acts as an in-memory repository that stores and retrieves song information.
 * It provides methods to access the entire list of songs, add a collection of songs,
 * and retrieve a specific song by its ID.
 * </p>
 */
public class SongsRepository {
    /**
     * A list that holds all the songs.
     */
    private static final List<Songs> songs = new ArrayList<>();

    /**
     * Adds a list of songs to the repository.
     * <p>
     * This method appends all songs in the given list to the current list of songs in the repository.
     * If the list contains existing songs, they will be duplicated.
     * </p>
     *
     * @param songs A list of {@link Songs} to be added to the repository.
     */
    public void loadSongs(List<Songs> songs) {
        SongsRepository.songs.addAll(songs);
    }

    /**
     * Retrieves a song by its unique ID.
     * <p>
     * Searches through the stored songs and returns the first song that matches the given ID.
     * If no song matches the ID, this method returns null.
     * </p>
     *
     * @param id The unique identifier of the song to retrieve.
     * @return The {@link Songs} object with the specified ID, or null if no such song exists in the repository.
     */
    public Songs getSongBySongId(int id) {
        for (Songs song : SongsRepository.songs) {
            if (song.getId() == id) {
                return song;
            }
        }
        return null;
    }
}
