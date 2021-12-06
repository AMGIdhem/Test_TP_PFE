package com.example.demo.web;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.AnnonceRepository;
import com.example.demo.dao.DossierRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.entities.Annonce;
import com.example.demo.entities.Dossier;
import com.example.demo.entities.Quartier;
import com.example.demo.entities.TypeLogement;
import com.example.demo.entities.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/etudiant")
@Secured(value={"ROLE_ETUDIANT"})
public class EtudiantController 
{
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AnnonceRepository annonceurRepository;
	@Autowired
	DossierRepository dossierRepository;

	
	@RequestMapping("/home")
	public String Index(Model model) {
		return "homeEtudiant";
	}
	
	@RequestMapping("/deposer")
	public String deposer(Model model, Long id) {
		model.addAttribute("idAnnonce", id);
		System.out.println(id);
		model.addAttribute("dossier", new Dossier());
		return "formDossier";
	}
	
	@RequestMapping(value="/profile")
	public String monProfileAnnonceur(Model model, HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession();
		SecurityContext securityContext=(SecurityContext) 
				httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		User monProfil = userService.findByUsername(username);
		model.addAttribute("user", monProfil);
		model.addAttribute("idOldUs", monProfil.getUsername());
		return "profileEtudiant";
	}
	
	
	
	@RequestMapping(value="/saveDossier", method=RequestMethod.POST)
	public String saveDossier(@Valid Dossier d,
			HttpServletRequest httpServletRequest,
			@RequestParam(name="garantRelation") String garantRelation,
			@RequestParam(name="idAnnonce") Long idAnnonce) throws Exception {
		HttpSession httpSession = httpServletRequest.getSession();
		SecurityContext securityContext=(SecurityContext) 
				httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		User user = userRepository.findById(username).get();
		Annonce annonce = annonceurRepository.findById(idAnnonce).get();
		//couplage entre les entites
		d.setUser(user);
		d.setAnnonce(annonce);
		List<Dossier> dossiers = new LinkedList<>();
		dossiers.add(d);
		annonce.setDossiers(dossiers);
		dossierRepository.save(d);
		return "redirect:home";
		
	}
	
	@RequestMapping(value="/editDossier", method=RequestMethod.POST)
	public String editDossier( Dossier newD,
			@RequestParam(name="idOldD")Long idOldD,
			HttpServletRequest httpServletRequest) throws Exception {
		
		Dossier oldD = dossierRepository.getOne(idOldD);
		oldD.setEtablissement(newD.getEtablissement());
		oldD.setGarantNom(newD.getGarantNom());
		oldD.setGarantPrenom(newD.getGarantPrenom());
		oldD.setGarantTel(newD.getGarantTel());
		oldD.setGarantEmail(newD.getGarantEmail());
		dossierRepository.save(oldD);
		//oldAn.setSurface((int) newAn.getSurface());
		return "redirect:mesDossiers";
		
	}
	
	@RequestMapping(value="/mesDossiers")
	public String mesDossiers(Model model, HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession();
		SecurityContext securityContext=(SecurityContext) 
				httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		List<Dossier> mesDossiers = dossierRepository.findByUser_username(username);
		model.addAttribute("mesDossiers", mesDossiers);
		return "mesDossiers";
	}
	
	@RequestMapping(value="/supprimerDossier")
	public String supprimerDossier(Long id) {
		System.out.println("ID DU DOSSIER EST :" + id);
		dossierRepository.deleteById(id);
		return "redirect:mesDossiers";
	}
	
	@RequestMapping(value="/edit")
	public String edit(Long id,Model model) {
		Dossier dossier = dossierRepository.getOne(id);
		model.addAttribute("dossier", dossier);
		model.addAttribute("idOldD",dossier.getId());
		return "editDossier";
	}
	
	
}
