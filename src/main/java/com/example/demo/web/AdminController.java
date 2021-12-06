package com.example.demo.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.AnnonceRepository;
import com.example.demo.dao.QuartierRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dao.VilleRepository;
import com.example.demo.entities.Quartier;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.entities.Ville;

@Controller
@RequestMapping("/admin")
@Secured(value={"ROLE_ADMIN"})
public class AdminController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AnnonceRepository annonceRepository;
	
	@Autowired
	QuartierRepository quartierRepository;
	
	@Autowired
	VilleRepository villeRepository;
	
	@RequestMapping("/home")
	public String Index(Model model) {
		return "homeAdmin";
	}
	
	
	@RequestMapping(value="/getAllUsersAnnonceur")
	public String getAllUsersAnnonceur(Model model) {
		List<User> users = userRepository.findAll();
		List<User> usersAnnonceur = new ArrayList<User>();
		for(User u:users) {
			Collection<Role> roles = u.getRoles();
			for(Role r:roles) {
				if(r.getRole().equals("ANNONCEUR")) {
					usersAnnonceur.add(u);
					model.addAttribute("usersAn", usersAnnonceur);

				}
			}
		}
		model.addAttribute("users", users);
		return "usersAnnonceur";
	}
	
	@RequestMapping(value="/getAllUsersEtudiant")
	public String getAllUsersEtudiant(Model model) {
		List<User> users = userRepository.findAll();
		List<User> usersEtudiant = new ArrayList<User>();
		for(User u:users) {
			Collection<Role> roles = u.getRoles();
			for(Role r:roles) {
				if(r.getRole().equals("ETUDIANT")) {
					usersEtudiant.add(u);

				}
			}
		}
		model.addAttribute("usersEt", usersEtudiant);
		model.addAttribute("users", users);
		return "usersEtudiant";
	}
	
	@RequestMapping(value="/supprimer")
	public String supprimer(String username) {
		userRepository.deleteById(username);
		return "redirect:getAllUsersAnnonceur";
	}
	
	@RequestMapping(value="/getAnnoncesOfUser")
	public String getAnnoncesOfUser(String username) {
		userRepository.deleteById(username);
		annonceRepository.findByUser_username(username);
		return "AnnoncesOfUser";
	}
	
	@RequestMapping(value="/getQuartiersByVille")
	public String getQuartiersByVille(Long id, Model model) {
		model.addAttribute("quartiers", quartierRepository.findByVille_id(id));
		model.addAttribute("id", id);
		return "Quartiers";
	}
	
	@RequestMapping(value="/getAllVilles")
	public String getAllVilles(Model model) {
		model.addAttribute("villes", villeRepository.findAll());
		return "Villes";
	}
	
	@RequestMapping(value="/supprimerQuartier")
	public String supprimerQuartier(Long id, Long id2, RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("id", id2);
		quartierRepository.deleteById(id);
		return "redirect:getQuartiersByVille";
	}
	
	@RequestMapping(value="/addQuartier", method=RequestMethod.POST)
	public String addQuartier(@RequestParam(name="quartier")String quartier,
							  @RequestParam(name="id")Long id,
							  RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("id", id);
		quartierRepository.save(new Quartier(quartier, villeRepository.findById(id).get()));
		return "redirect:getQuartiersByVille";
	}

}
