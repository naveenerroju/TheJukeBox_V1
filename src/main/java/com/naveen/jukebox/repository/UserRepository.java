package com.naveen.jukebox.repository;

import java.util.ArrayList;
import java.util.List;
import com.naveen.jukebox.model.CurrentlyPlaying;
import com.naveen.jukebox.model.Playlist;
import com.naveen.jukebox.model.Songs;
import com.naveen.jukebox.model.User;

public class UserRepository {

    static List<User> users =  new ArrayList<>();

    public User getUserByUserId(int id){
        for (User user2 : users) {
            if(user2.getId()==id){
                return user2;
            }
        }
        return null;
    }

    public String createUser(String username){
        int userId = users.size()+1;
        User user  = new User(userId, username, new ArrayList<>(), null);
        users.add(user);

        return userId+" "+username;
    }

    public List<User> getUsers(){
        return UserRepository.users;
    }

    public String createPlaylist(int userId, Playlist playlist){
        for (User user : users) {
            if(user.getId()==userId){
                user.getPlaylists().add(playlist);
                return "Playlist ID - "+playlist.getId();
            }
        }
        return null;
    }

    public String deletePlaylist(int userId, int playlistId){
        List<Playlist> playlists = getUserByUserId(userId).getPlaylists();
        for(int i = 0; i<playlists.size(); i++){
            Playlist current = (playlists.get(i));
            if(current.getId()==playlistId){
                playlists.remove(current);
                return "Delete Successful";
            }
        }
        return "NO PLAYLIST FOUND WITH THE GIVEN PLAYLISTID";
    }

    //TODO: CHANGE THE SONGSEQUAL TO THE SONGID
    public int playSong(int userId, int playlistId, int songId){
        CurrentlyPlaying currentlyPlaying = new CurrentlyPlaying(playlistId, songId);
        getUserByUserId(userId).setCurrentlyPlaying(currentlyPlaying);
        return songId;
    }

    

    public int playPlaylist(int userId, int playlistId){
        Playlist playlist = getUserByUserId(userId).getPlaylists().stream().filter(plylst -> plylst.getId() == playlistId)
                            .findFirst().get();
        int result = playSong(userId, playlistId, playlist.getSongs().get(0).getId());
        return result;
    }

    public boolean isSongPartOfPlaylist(int userId, int songId){

        for (Playlist playlist : getUserByUserId(userId).getPlaylists()) {
            for(Songs song : playlist.getSongs()){
                if(song.getId()==songId){
                    return true;
                }
            }
        }
        return false;
    }

}
