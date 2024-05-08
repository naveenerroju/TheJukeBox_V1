package com.naveen.jukebox.repository;

import java.util.ArrayList;
import java.util.List;
import com.naveen.jukebox.model.CurrentlyPlaying;
import com.naveen.jukebox.model.Playlist;
import com.naveen.jukebox.model.Songs;
import com.naveen.jukebox.model.User;

/**
 * A repository class for managing user data within the Jukebox application.
 * <p>
 * This class acts as an in-memory repository that stores and manages users and their related data,
 * including playlists and playback states. It provides methods to handle creation, retrieval,
 * modification, and deletion of user and playlist data.
 * </p>
 */
public class UserRepository {

    static List<User> users =  new ArrayList<>();

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return The {@link User} object if found, or null if no user with the given ID exists.
     */
    public User getUserByUserId(int id){
        for (User user2 : users) {
            if(user2.getId() == id){
                return user2;
            }
        }
        return null;
    }

    /**
     * Creates a new user with the specified username.
     *
     * @param username The username for the new user.
     * @return A string containing the user ID and username, indicating successful creation.
     */
    public String createUser(String username){
        int userId = users.size() + 1; // Simplistic way to generate a new user ID
        User user = new User(userId, username, new ArrayList<>(), null);
        users.add(user);
        return userId + " " + username;
    }

    /**
     * Adds a new playlist to a specific user's account.
     *
     * @param userId The ID of the user to whom the playlist will be added.
     * @param playlist The {@link Playlist} to be added.
     * @return A string indicating the playlist ID if the operation is successful, or null if the user is not found.
     */
    public String createPlaylist(int userId, Playlist playlist){
        for (User user : users) {
            if(user.getId() == userId){
                user.getPlaylists().add(playlist);
                return "Playlist ID - " + playlist.getId();
            }
        }
        return null;
    }

    /**
     * Deletes a playlist from a user's account.
     *
     * @param userId The ID of the user whose playlist is to be deleted.
     * @param playlistId The ID of the playlist to be removed.
     * @return A message indicating whether the deletion was successful or if the playlist was not found.
     */
    public String deletePlaylist(int userId, int playlistId){
        List<Playlist> playlists = getUserByUserId(userId).getPlaylists();
        for (Playlist playlist : playlists) {
            if (playlist.getId() == playlistId) {
                playlists.remove(playlist);
                return "Delete Successful";
            }
        }
        return "NO PLAYLIST FOUND WITH THE GIVEN PLAYLISTID";
    }

    /**
     * Sets a song as currently playing for a user.
     *
     * @param userId The user ID.
     * @param playlistId The playlist ID from which the song is played.
     * @param songId The song ID that is to be played.
     * @return The song ID of the currently playing song.
     */
    public int playSong(int userId, int playlistId, int songId){
        CurrentlyPlaying currentlyPlaying = new CurrentlyPlaying(playlistId, songId);
        getUserByUserId(userId).setCurrentlyPlaying(currentlyPlaying);
        return songId;
    }

    /**
     * Initiates playback of the first song in a specified playlist for a user.
     *
     * @param userId The ID of the user.
     * @param playlistId The ID of the playlist.
     * @return The song ID of the first song that is played from the playlist.
     */
    public int playPlaylist(int userId, int playlistId){
        Playlist playlist = getUserByUserId(userId).getPlaylists().stream()
                .filter(plylst -> plylst.getId() == playlistId)
                .findFirst()
                .orElse(null);
        if (playlist == null || playlist.getSongs().isEmpty()) {
            throw new IllegalStateException("Playlist is empty or not found.");
        }
        return playSong(userId, playlistId, playlist.getSongs().get(0).getId());
    }

    /**
     * Checks if a specific song is part of any playlist belonging to a user.
     *
     * @param userId The user's ID.
     * @param songId The song's ID to check.
     * @return true if the song is part of at least one of the user's playlists, false otherwise.
     */
    public boolean isSongPartOfPlaylist(int userId, int songId){
        for (Playlist playlist : getUserByUserId(userId).getPlaylists()) {
            for(Songs song : playlist.getSongs()){
                if(song.getId() == songId){
                    return true;
                }
            }
        }
        return false;
    }

}
