package com.example.demo.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dao.AnnonceRepository;
import com.example.demo.dao.QuartierRepository;
import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.entities.Annonce;
import com.example.demo.entities.Dossier;
import com.example.demo.entities.User;

@Controller
public class MainController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	AnnonceRepository annonceRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	QuartierRepository quartierRepository;
	
	@Value("${dir.images}")
	String imageDir;
	
	@GetMapping("/login")
	public String account(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(Model model) {
		return "home";
	}
	
	@GetMapping(value={"/annonceur/logout","/etudiant/logout","/admin/logout"})
	public String logoutRedirect(Model model) {
		return "redirect:/logout";
	}
	
	
	
	
	
	@GetMapping("/")
	public String index(Model model) {
		//model.addAttribute("annonces", annonceRepository.findAll());
		return "home";
	}
	
	//----
	@GetMapping("/annonces")
	public String annonces(Model model,
			@RequestParam(name="page",defaultValue="0") int page, 
			@RequestParam(name="size",defaultValue="5") int size) 
	{
		Page<Annonce> annonces =  annonceRepository.findAll(PageRequest.of(page, size));
		model.addAttribute("annonces", annonces.getContent() );
		model.addAttribute("pages", new int[annonces.getTotalPages()]);
		model.addAttribute("size", size);
		model.addAttribute("quartiers", quartierRepository.findAll());
		//pour savoir page demande et appliquer style css aux bouttons
		model.addAttribute("currentPage", page);
		
		return "index";
	}
	
	/*
	@GetMapping("/annonces")
	public String annonces(Model model) {
		model.addAttribute("annonces", annonceRepository.findAll());
		return "index";
	}
	 */
	
	@GetMapping("/inscription")
	public String inscription(Model model) {
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("user", new User());
		return "inscription";
	}
	
	@RequestMapping(value="/getPhoto", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(Long id) throws Exception {
		File f = new File(imageDir+id);
		return IOUtils.toByteArray(new FileInputStream(f));
	}
	
	@RequestMapping(value="/getPhoto1", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto1(Long id) throws Exception {
		File f = new File(imageDir+id+"_1");
		return IOUtils.toByteArray(new FileInputStream(f));
	}
	
	@RequestMapping(value="/getPhoto2", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto2(Long id) throws Exception {
		File f = new File(imageDir+id+"_2");
		return IOUtils.toByteArray(new FileInputStream(f));
	}
	
	@RequestMapping(value="/getAnnonce")
	public String getAnnonce(Long id, Model model) throws Exception {
		Annonce annonce = annonceRepository.getOne(id);
		model.addAttribute("annonce", annonce);
		return "annonce";
	}
	
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(Model model,
			@RequestParam(name="motCle", required = false) String motCle,
			@RequestParam(name="prixMin", required = false) String prixMin,
			@RequestParam(name="prixMax", required = false) String prixMax) throws Exception {
		model.addAttribute("annonces", annonceRepository.findAll(motCle, Integer.parseInt(prixMin), Integer.parseInt(prixMax)) );
		return "index";
		
	}
	
//	@RequestMapping(value="/user", method = RequestMethod.POST)
//	public String verif(@RequestParam(name="email") String email, @RequestParam(name="mdp") String mdp) {
//		List<User> usrs = userRepository.findAll();
//		for(User u : usrs) {
//			if(u.getEmail().equals(email) && u.getMdp().equals(mdp)) {
//				return "index";
//			}
//		}
//		return "account";
//	}
	
	

}
