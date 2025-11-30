package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Song;
import com.example.demo.services.SongService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // Allow React app
public class SongController {

    @Autowired
    private SongService service;

    @GetMapping("/songs")
    public List<Song> getAllSongs() {
        return service.fetchAllSongs();
    }
    	
}



