package com.senthil.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.senthil.model.Role;
import com.senthil.model.User;
import com.senthil.service.UserService;


@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public String login(@RequestParam("emailId") String emailId, @RequestParam("password") String password,
			ModelMap modelMap, HttpSession session) {
		
		//System.out.println("UserController->login");

		User user = userService.findByEmailAndPassword(emailId, password);
		if (user != null) {
			session.setAttribute("LOGGED_IN_USER", user);

			return "/home"; 
		} else {
			modelMap.addAttribute("ERROR_MESSAGE", "Invalid EmailID/Password");
			return "/index";
		}
	}
	@GetMapping("/create")
	public String create(){
		return "user/register";
	}
	
	@GetMapping("/register")
	public String register(@RequestParam("name") String name,@RequestParam("password") String password,
			 @RequestParam("role") Long role,
			@RequestParam("email") String email, 
			@RequestParam("mobileNo") Long mobileNo, ModelMap modelMap, HttpSession session) throws Exception {
		try {
			
			User user = new User();
			
			user.setName(name);			
			user.setEmail(email);
			user.setPassword(password);
			user.setMobileNo(mobileNo);
			user.setActive(true);

			Role r = new Role();
			r.setId(role); // user
			user.setRole(r);
			System.out.println(user);

			userService.register(user);
			
			return "redirect:../";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.addAttribute("ERROR_MESSAGE", e.getMessage());
			return "user/register";
		}

	}
	@GetMapping("/Logout")
	public String logout(HttpSession session) {

		session.invalidate();
		return "redirect:/";
	}
}
