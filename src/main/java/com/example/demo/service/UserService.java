package com.example.demo.service;

import com.example.demo.entities.User;

public interface UserService {
	public void verification();
	public User addUser(User user, String role);
	public User findByUsername(String username);
}
