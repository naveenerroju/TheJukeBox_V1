package com.naveen.jukebox.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.naveen.jukebox.model.Constants;
import com.naveen.jukebox.model.Playlist;
import com.naveen.jukebox.model.Songs;
import com.naveen.jukebox.model.User;
import com.naveen.jukebox.repository.UserRepository;
import com.naveen.jukebox.utility.DataUtility;

public class UserService {

    private final UserRepository userRepository = new UserRepository();
    private final SongsService songsService = new SongsService();

    /**
     * Attempts to create a new user with the specified username.
     * <p>
     * This method checks if the username is neither null nor empty. If the username is valid,
     * it delegates the creation process to the repository. If the username is invalid,
     * an {@link IllegalArgumentException} is thrown to indicate the error clearly.
     * </p>
     *
     * @param username The username for the new user. Must not be null or empty.
     * @return A string indicating the result of the user creation process. Typically, this might
     *         include a success message or a user ID.
     * @throws IllegalArgumentException if the username is null or empty, indicating that the input
     *         was not valid.
     */
    public String createUser(String username) {
        // Check for null or empty username before processing
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty or null.");
        }

        // Call to repository to create user with the provided username
        return userRepository.createUser(username);
    }

    /**
     * Creates a new playlist for a specified user and adds selected songs to it.
     * <p>
     * Validates the user ID, playlist name, and song IDs before creating a playlist. If any validation fails,
     * an appropriate error message is returned. Each song ID is verified to ensure it corresponds to an existing song.
     * If all inputs are valid, a new playlist is created, songs are added, and the playlist is registered in the repository.
     * </p>
     *
     * @param userId The ID of the user for whom the playlist is being created.
     * @param playlistName The name of the new playlist. Must not be null or empty.
     * @param songIds An array of song IDs to be included in the playlist. Must not be null or contain invalid song IDs.
     * @return A string indicating the outcome, either the ID of the newly created playlist or an error message.
     * @throws IllegalArgumentException if the playlist name is null or empty, or if songIds array is null or empty.
     */
    public String createPlaylist(int userId, String playlistName, int[] songIds) {
        // Validate input parameters
        if (playlistName == null || playlistName.isBlank()) {
            return "Playlist name cannot be empty or null.";
        }
        if (songIds == null || songIds.length == 0) {
            return "Song IDs list cannot be empty.";
        }

        User user = userRepository.getUserByUserId(userId);
        if (user == null) {
            return "User not found.";
        }

        Playlist playlist = new Playlist();
        int playlistId = user.getPlaylists().size() + 1;
        playlist.setId(playlistId);
        playlist.setTitle(playlistName);
        playlist.setSongs(new ArrayList<>());  // Initialize with an empty list

        // Add songs to the playlist, check if each song exists
        for (int id : songIds) {
            Songs song = songsService.getSongsBySongId(id);
            if (song == null) {
                return "Song with ID " + id + " not found.";
            }
            playlist.getSongs().add(song);
        }

        userRepository.createPlaylist(userId, playlist);
        return "Playlist ID - " + playlistId;
    }


    /**
     * Deletes the provided playlist.
     * @param userId existing user id
     * @param playlistId existing playlist id in the user repository
     * @return "Delete Successful" on successful deletion or "NO PLAYLIST FOUND WITH THE GIVEN PLAYLISTID"
     */
    public String deletePlaylist(int userId, int playlistId) {
        return userRepository.deletePlaylist(userId, playlistId);
    }

    /**
     * Initiates playback of a specified playlist for a given user and returns the details of the first song.
     * <p>
     * This method first verifies the existence of the user and the specified playlist. It also checks if the
     * playlist contains any songs. If all validations pass, the playlist is set as currently playing for the user,
     * and the first song's details are returned. If any validation fails or there are no songs in the playlist,
     * appropriate error messages are returned.
     * </p>
     *
     * @param userId The ID of the user who wants to play the playlist.
     * @param playlistId The ID of the playlist to be played.
     * @return A string detailing the first song playing from the playlist or an error message if the user, playlist,
     *         or songs cannot be found or if the playlist is empty.
     * @throws IllegalArgumentException if the inputs do not meet the validation criteria or if no corresponding user or playlist exists.
     */
    public String playPlaylist(int userId, int playlistId) {
        // Validate if the user and playlist exist
        User user = userRepository.getUserByUserId(userId);
        if (user == null) {
            return "User not found.";
        }

        Playlist playlist = user.getPlaylists().stream()
                .filter(p -> p.getId() == playlistId)
                .findFirst()
                .orElse(null);
        if (playlist == null) {
            return "Playlist not found.";
        }

        if (playlist.getSongs().isEmpty()) {
            return "Playlist is empty. No songs to play.";
        }

        // Attempt to play the playlist and retrieve the currently playing song ID
        int currentlyPlayingSongId = userRepository.playPlaylist(userId, playlistId);
        Songs song = songsService.getSongsBySongId(currentlyPlayingSongId);

        if (song == null) {
            return "Error retrieving the currently playing song.";
        }

        return DataUtility.constructSongResponse(song);
    }


    /**
     * Plays the next or previous song in the current playlist based on the provided navigation command.
     * <p>
     * This method determines the song to be played next or previously within the user's currently playing playlist,
     * based on the navigation command provided ('next' or 'previous'). It retrieves the current playlist and song
     * from the user's profile and computes the index for the next song to be played, cycling back to the start
     * or end of the playlist as necessary.
     * </p>
     *
     * @param userId The ID of the user whose playlist is being manipulated.
     * @param navi   The navigation command, either {@link Constants#PLAY_NEXT_SONG_OPERATION} for the next song or
     *               {@link Constants#PLAY_PREVIOUS_SONG_OPERATION} for the previous song. If an unrecognized command
     *               is provided, the current song will remain playing.
     * @return A string representation of the currently playing song after executing the navigation command. If an error occurs,
     * such as the user or playlist not being found, a corresponding error message is returned.
     * @throws IllegalArgumentException  If {@code userId} does not correspond to a valid user or if the user has no active playlist.
     * @throws IndexOutOfBoundsException If calculated song index is out of playlist bounds, though internal calculations
     *                                   should prevent this from occurring.
     */
    public String playSong(int userId, String navi) {
        User user = userRepository.getUserByUserId(userId);
        if (user == null || user.getCurrentlyPlaying() == null) {
            return "User or current playing information not found.";
        }

        Playlist currentPlaylist = user.getPlaylists().stream()
                .filter(playlist -> playlist.getId() == user.getCurrentlyPlaying().getPlaylistId())
                .findFirst()
                .orElse(null);
        if (currentPlaylist == null) {
            return "Current playlist not found.";
        }

        List<Songs> songs = currentPlaylist.getSongs();
        if (songs.isEmpty()) {
            return "No songs in the playlist.";
        }

        // Get current song index and calculate next or previous song index
        int currentSongIndex = IntStream.range(0, songs.size())
                .filter(i -> songs.get(i).getId() == user.getCurrentlyPlaying().getSongId())
                .findFirst()
                .orElse(-1);

        if (currentSongIndex == -1) {
            return "Current song not found in the playlist.";
        }

        int nextSongIndex = calculateNextIndex(songs.size(), currentSongIndex, navi);
        Songs nextSong = songs.get(nextSongIndex);

        userRepository.playSong(userId, currentPlaylist.getId(), nextSong.getId());
        return DataUtility.constructSongResponse(nextSong);
    }

    /**
     * Calculates the index of the next song to play based on navigation direction.
     *
     * @param size         The total number of songs in the playlist.
     * @param currentIndex The current index of the song being played.
     * @param navi         The navigation command indicating the direction to move within the playlist.
     * @return The index of the next song to be played.
     */
    private int calculateNextIndex(int size, int currentIndex, String navi) {
        if (Constants.PLAY_NEXT_SONG_OPERATION.equals(navi)) {
            return (currentIndex + 1) % size; // wraps around to the first song
        } else if (Constants.PLAY_PREVIOUS_SONG_OPERATION.equals(navi)) {
            return (currentIndex - 1 + size) % size; // wraps around to the last song
        }
        return currentIndex; // stays on the current song if navigation command is unknown
    }

    /**
     * Attempts to play a specific song for a user, checking first if the song is part of the user's currently active playlist.
     * <p>
     * This method first verifies whether the specified song ID is included in the currently active playlist of the user.
     * If the song is not part of the active playlist, it returns a message indicating so. If the song is part of the playlist,
     * the method proceeds to update the current playing status to this song and returns details about the now playing song.
     * </p>
     *
     * @param userId The unique identifier of the user who is attempting to play the song.
     * @param songId The unique identifier of the song that is requested to be played.
     * @return A string response which either confirms the song is now playing, with details of the song, or an error message
     * stating that the song is not part of the active playlist. The response includes the title, album, and artist(s)
     * of the song.
     * @throws IllegalArgumentException if either the user ID or song ID does not correspond to a valid user or song.
     */
    public String playSong(int userId, int songId) {
        if (!userRepository.isSongPartOfPlaylist(userId, songId)) {
            return "Given song id is not a part of the active playlist";
        }
        int currentPlaylistId = userRepository.getUserByUserId(userId).getCurrentlyPlaying().getPlaylistId();

        int result = userRepository.playSong(userId, currentPlaylistId, songId);
        Songs song = songsService.getSongsBySongId(result);
        return DataUtility.constructSongResponse(song);
    }

    /**
     * Adds a song to a user's playlist and returns the updated playlist details.
     * <p>
     * This method first verifies the existence of the user, the specified playlist, and the song.
     * If any of these do not exist, it returns an appropriate error message. If all entities are valid,
     * the song is added to the specified playlist.
     * </p>
     *
     * @param userId     The unique identifier of the user whose playlist is to be modified.
     * @param playlistId The unique identifier of the playlist to which the song is to be added.
     * @param songId     The unique identifier of the song that is to be added to the playlist.
     * @return A string containing the updated playlist information or an error message if the user,
     * playlist, or song cannot be found.
     * @throws IllegalArgumentException if the parameters are not valid identifiers.
     */
    public String addSongInPlaylist(int userId, int playlistId, int songId) {
        // Retrieve user or return error if not found
        User user = userRepository.getUserByUserId(userId);
        if (user == null) {
            return "User not found.";
        }

        // Retrieve playlist or return error if not found
        Playlist userPlaylist = user.getPlaylists().stream()
                .filter(playlist -> playlist.getId() == playlistId)
                .findFirst()
                .orElse(null);
        if (userPlaylist == null) {
            return "Playlist not found.";
        }

        // Retrieve song or return error if not found
        Songs addSong = songsService.getSongsBySongId(songId);
        if (addSong == null) {
            return "Song not found.";
        }

        // Add song to the playlist
        userPlaylist.getSongs().add(addSong);
        return DataUtility.constructPlaylistResponse(userPlaylist);
    }

    /**
     * Removes a song from a user's playlist and returns the updated playlist details.
     * <p>
     * This method checks for the existence of the user, the specified playlist, and the song.
     * If any of these do not exist, it returns an appropriate error message. It then attempts to remove
     * the song from the playlist. If the song is not found in the playlist or cannot be removed,
     * an error message is returned. Otherwise, it returns the updated playlist details.
     * </p>
     *
     * @param userId     The unique identifier of the user whose playlist is to be modified.
     * @param playlistId The unique identifier of the playlist from which the song is to be removed.
     * @param songId     The unique identifier of the song that is to be removed from the playlist.
     * @return A string containing the updated playlist information or an error message if the user,
     * playlist, or song cannot be found, or if the song cannot be removed from the playlist.
     * @throws IllegalArgumentException if the parameters are not valid identifiers or if the operation
     *                                  cannot be completed as specified.
     */
    public String deleteSongInPlaylist(int userId, int playlistId, int songId) {
        // Retrieve user or return error if not found
        User user = userRepository.getUserByUserId(userId);
        if (user == null) {
            return "User not found.";
        }

        // Retrieve playlist or return error if not found
        Playlist userPlaylist = user.getPlaylists().stream()
                .filter(playlist -> playlist.getId() == playlistId)
                .findFirst()
                .orElse(null);
        if (userPlaylist == null) {
            return "Playlist not found.";
        }

        // Retrieve song or return error if not found
        Songs removableSong = songsService.getSongsBySongId(songId);
        if (removableSong == null) {
            return "Song not found.";
        }

        // Attempt to remove the song from the playlist; check if successful
        boolean removed = userPlaylist.getSongs().remove(removableSong);
        if (!removed) {
            return "Song was not found in the playlist.";
        }

        return DataUtility.constructPlaylistResponse(userPlaylist);
    }


}
