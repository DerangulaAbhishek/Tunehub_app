package com.example.demo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Playlist;
import com.example.demo.repository.PlaylistRepository;

@Service
public class PlaylistServiceImplementation implements PlaylistService {

    @Autowired
    private PlaylistRepository repo;

    @Override
    public Playlist addPlaylist(Playlist playlist) {
        return repo.save(playlist);
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return repo.findAll();
    }

    @Override
    public Playlist getPlaylistById(int id) {
        return repo.findById(id).orElse(null);
    }
}
