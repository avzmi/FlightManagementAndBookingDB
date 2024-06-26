package com.FMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.FMS.model.Airlines;
import com.FMS.model.Users;
import com.FMS.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {
	
	private final UsersService userService;
	
    @Autowired
    public UsersController(UsersService userService) {
        this.userService = userService;
    }
    
	@GetMapping("/user/dashboard")
	public String dashboard(HttpSession session) {
		if(session.getAttribute("username") == null) {
			return "userLogin";
		}
			
		return "dashboard";
	}

	@GetMapping("/user/login")
	public String login() {
		
		return "userLogin";
	}
	
	@PostMapping("/user/LoginAction")
	public String LoginConfirm(Users user, HttpSession session) {
		Users users = userService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
		boolean isLogin = userService.checkLogin(users);

		if(isLogin == true) {
			session.setAttribute("username", users.getUsername());
			System.out.println(users.getUsername());
			return "redirect:/user/dashboard";
		}
		else{
			return "redirect:/user/login";
		}

	}
	
	
	@GetMapping("/user/register")
	public String register() {
		
		return "registerUser";
	}

    @PostMapping("/user/add")
    public String addUser(Users user) {
    	userService.addNewUser(user);
        return "redirect:/user/login";
    }

    @GetMapping("/user/logout")
    public String userLogout(HttpSession session) {
		session.setAttribute("username", null);
		
		return "userLogin";
    }
    
    @GetMapping("/user/profile/")
    public String openProfile(HttpSession session) {
    	String username = (String) session.getAttribute("username");    	
    	if(username == null) {
    		return "userLogin";
    	}
    	session.setAttribute("username", username);
    	return "userProfile";

    }
    
    @PutMapping("/user/profile/update/")
    public String updateProfile(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
    	String username = (String)session.getAttribute("username");
    	userService.updateUser(username, email, firstName, lastName, password);
    	session.setAttribute("username", username);
    	return "dashboard";
    }

}
