package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Song;
import com.example.demo.services.SongService;

@Controller
public class SongController {
	
	@Autowired
	SongService service;
	
	@PostMapping ("/addSong")
	public String addSong(@ModelAttribute Song song) {
		service.addSong(song);
		return "adminHome";
	}
	
	//To get All Songs 
	
	@GetMapping ("/viewSongs")
	public String viewSongs() {
		List<Song> songList= service.fetchAllSongs();
		System.err.println(songList);
		
		return "adminHome";
	}

}
