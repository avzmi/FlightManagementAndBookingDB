package com.FMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FMS.model.Users;
import java.util.List;


public interface UsersRepository extends JpaRepository<Users, String>{
	  Users findByUsernameAndPassword(String username, String password);
}
