package com.example.demo.services;

import java.util.List;
import com.example.demo.entity.Song;

public interface SongService {
    void addSong(Song song);
    List<Song> fetchAllSongs();
    boolean songExist(String name);
    void updateSong(Song song);
}
