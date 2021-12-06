package com.example.demo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	RoleRepository roleRepository;
	
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	public String addUser(@Valid User user,
			BindingResult bindingResult,
			@RequestParam(name="role")String role,
			Model model) {
		if(bindingResult.hasErrors() || !((user.getPassword().equals(user.getMatchingPassword())))) {
			model.addAttribute("roles", roleRepository.findAll());
			return "inscription";
		}
		userService.addUser(user,role);
		return "login";
	}
	
	@RequestMapping(value="/findUsers")
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@RequestMapping(value="/getLogedUser")
	public String getLogedUser(HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession();
		SecurityContext securityContext=(SecurityContext) 
				httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		List<String> roles = new ArrayList<>();
		for(GrantedAuthority ga:securityContext.getAuthentication().getAuthorities()) {
			roles.add(ga.getAuthority());
		}
		for(String r : roles) {
			if(r.equals("ETUDIANT")) {
				return "homeEtudiant";
			}
			else if(r.equals("ANNONCEUR")) {
				return "homeAnnonceur";
			}
			else if(r.equals("ADMIN")) {
				return "homeAdmin";
			}
		}
		Map<String, Object> params=new HashMap<>();
		params.put("username", username);
		params.put("roles", roles);
		return "login";
	}
}
