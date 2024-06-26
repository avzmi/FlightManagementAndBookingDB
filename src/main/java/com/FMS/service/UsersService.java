package com.FMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FMS.model.Admin;
import com.FMS.model.Users;
import com.FMS.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class UsersService {

	private final UsersRepository usersRepository;
	
	@Autowired
	public UsersService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}
	
	public void addNewUser(Users user) {
		usersRepository.save(user);
	}
	
	public List<Users> getAllUsers(){
		return usersRepository.findAll();
	}

	public Users getUserByUsername(String username) {
		return usersRepository.getReferenceById(username);
	}
	
	public void deleteUser(String username) {
		usersRepository.deleteById(username);
	}
	
	public Users getUserByUsernameAndPassword(String username, String password) {
		return usersRepository.findByUsernameAndPassword(username, password);
	}
	
	public boolean checkLogin(Users user) {
		if(user == null) {
			return false;
		}
		return true;
	
	}
	
	@Transactional
	public void updateUser(String username, String email, String firstName, String lastName, String password) {
		Users user = usersRepository.findById(username).get();
		
		if(email != null) {
			user.setEmail(email);
		}
		if(firstName != null) {
			user.setFirstName(firstName);
		}
		if(lastName != null) {
			user.setLastName(lastName);
		}
		if(password != null) {
			user.setPassword(password);
		}
	}


}
