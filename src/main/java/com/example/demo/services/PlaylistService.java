package com.example.demo.services;

import java.util.List;
import com.example.demo.entity.Playlist;

public interface PlaylistService {
    Playlist addPlaylist(Playlist playlist);
    List<Playlist> getAllPlaylists();
    Playlist getPlaylistById(int id);
}
