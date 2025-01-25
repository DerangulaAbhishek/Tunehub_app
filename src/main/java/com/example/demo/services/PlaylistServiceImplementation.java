package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Playlist;
import com.example.demo.repository.PlaylistRepository;

@Service
public class PlaylistServiceImplementation implements PlaylistService{

	PlaylistRepository repo;
	
	@Override
	public void addPlaylist(Playlist playlist) {
		repo.save(playlist);
	}

	
}
