package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Playlist;
import com.example.demo.entity.Song;
import com.example.demo.repository.SongRepository;
import com.example.demo.services.PlaylistService;

@RestController
@RequestMapping("/api/playlists")
@CrossOrigin(origins = "http://localhost:3000")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private SongRepository songRepository;

    // ✅ Create Playlist using song IDs
    @PostMapping
    public Playlist addPlaylist(@RequestBody PlaylistRequest request) {
        Playlist playlist = new Playlist();
        playlist.setName(request.getName());

        // Find songs by IDs
        List<Song> songs = new ArrayList<>();
        for (Integer id : request.getSongIds()) {
            songRepository.findById(id).ifPresent(songs::add);
        }

        playlist.setSongs(songs);
        return playlistService.addPlaylist(playlist);
    }

    // ✅ Get all playlists
    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    // ✅ Get playlist by ID
    @GetMapping("/{id}")
    public Playlist getPlaylistById(@PathVariable int id) {
        return playlistService.getPlaylistById(id);
    }

    // Inner static DTO class for frontend request
    public static class PlaylistRequest {
        private String name;
        private List<Integer> songIds;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public List<Integer> getSongIds() { return songIds; }
        public void setSongIds(List<Integer> songIds) { this.songIds = songIds; }
    }
}
