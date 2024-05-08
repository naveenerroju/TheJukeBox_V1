package com.naveen.jukebox.service;

import java.util.ArrayList;

import com.naveen.jukebox.model.Constants;
import com.naveen.jukebox.model.Playlist;
import com.naveen.jukebox.model.Songs;
import com.naveen.jukebox.model.User;
import com.naveen.jukebox.repository.UserRepository;
import com.naveen.jukebox.utility.DataUtility;

public class UserService {

    private final UserRepository userRepository = new UserRepository();
    private final SongsService songsService = new SongsService();

    public String createUser(String username){
        if(!username.isBlank()){
            return userRepository.createUser(username);
        } else {
            return "Username cannot be empty or null";
        }
    }   

    public User getUserByUserId(int id){
        if(id>=1){
            return userRepository.getUserByUserId(id);
        }
        return null;
    }

    public String createPlaylist(int userId, String playlistName, int[] songIds){
        Playlist playlist = new Playlist();
        int playlistId = userRepository.getUserByUserId(userId).getPlaylists().size()+1;
        playlist.setId(playlistId);
        playlist.setTitle(playlistName);
        if(null==playlist.getSongs()){
            playlist.setSongs(new ArrayList<>());
        }
        for (int id : songIds) {
            playlist.getSongs().add(songsService.getSongsBySongId(id));
        }
        userRepository.createPlaylist(userId, playlist);
        return "Playlist ID - "+playlistId;
    }

    public String deletePlaylist(int userId, int playlistId){
        return userRepository.deletePlaylist(userId, playlistId);
    }

    public String playPlaylist(int userId, int playlistId){
        int currentlyPlayingSongId = userRepository.playPlaylist(userId, playlistId);
        Songs song = songsService.getSongsBySongId(currentlyPlayingSongId);
        return DataUtility.constructSongResponse(song);
    }

    public String playSong(int userId, String navi) {
        int songId = 0;
        User user = userRepository.getUserByUserId(userId);
        Playlist currentPlaylist = user.getPlaylists().stream()
                .filter(plylst -> plylst.getId() == user.getCurrentlyPlaying().getPlaylistId())
                .findFirst().get();
        int currentSongId = user.getCurrentlyPlaying().getSongId();
        if (navi.equals(Constants.PLAY_NEXT_SONG_OPERATION)) {
            for (int i = 0; i < currentPlaylist.getSongs().size(); i++) {

                if (currentPlaylist.getSongs().get(i).getId() == currentSongId && i == currentPlaylist.getSongs().size() - 1) {
                    songId = currentPlaylist.getSongs().get(0).getId();
                }else if (currentPlaylist.getSongs().get(i).getId() == currentSongId
                        && i != currentPlaylist.getSongs().size()) {
                    songId = currentPlaylist.getSongs().get(i + 1).getId();
                }
            }
        } else if (navi.equals(Constants.PLAY_PREVIOUS_SONG_OPERATION)) {
            for (int i = 0; i < currentPlaylist.getSongs().size(); i++) {

                if (i == 0) {
                    songId = currentPlaylist.getSongs().get(currentPlaylist.getSongs().size()-1).getId();
                } else if (currentPlaylist.getSongs().get(i).getId() == currentSongId) {
                    songId = currentPlaylist.getSongs().get(i - 1).getId();
                } 
            }
        }
        int result =
                userRepository.playSong(userId, user.getCurrentlyPlaying().getPlaylistId(), songId);
        Songs song = songsService.getSongsBySongId(result);
        return DataUtility.constructSongResponse(song);
    }

    public String playSong(int userId, int songId){
        if(!userRepository.isSongPartOfPlaylist(userId, songId)){
            return "Given song id is not a part of the active playlist";
        }
        int currentPlaylistId = userRepository.getUserByUserId(userId).getCurrentlyPlaying().getPlaylistId();

        int result = userRepository.playSong(userId, currentPlaylistId, songId);
        Songs song = songsService.getSongsBySongId(result);
        return DataUtility.constructSongResponse(song);
    }

    public String addSongInPlaylist(int userId, int playlistId, int songId) {
        User user = userRepository.getUserByUserId(userId);
        Playlist userPlaylist = user.getPlaylists().stream().filter(playlist-> playlist.getId()==playlistId).findFirst().get();
        Songs addSong = songsService.getSongsBySongId(songId);
        
        userPlaylist.getSongs().add(addSong);
        return DataUtility.constructPlaylistResponse(userPlaylist);
    }

    public String deleteSongInPlaylist(int userId, int playlistId, int songId) {
        User user = userRepository.getUserByUserId(userId);
        Playlist userPlaylist = user.getPlaylists().stream().filter(playlist-> playlist.getId()==playlistId).findFirst().get();
        Songs removableSong = songsService.getSongsBySongId(songId);
        
        userPlaylist.getSongs().remove(removableSong);
        return DataUtility.constructPlaylistResponse(userPlaylist);
    }

}
