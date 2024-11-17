package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.*;

/**
 * 
 */
@Entity
public class Playlist {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String username;
	String email;
	List<Song> Songs;
	public Playlist() {
		super();
	}
	public Playlist(int id, String username, String email, List<Song> songs) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		Songs = songs;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Song> getSongs() {
		return Songs;
	}
	public void setSongs(List<Song> songs) {
		Songs = songs;
	}
	@Override
	public String toString() {
		return "Playlist [id=" + id + ", username=" + username + ", email=" + email + ", Songs=" + Songs + "]";
	}
	

}
