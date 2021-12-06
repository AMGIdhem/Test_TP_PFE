package com.example.demo.web;


import java.io.File;
import java.io.FileInputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.AnnonceRepository;
import com.example.demo.dao.DossierRepository;
import com.example.demo.dao.QuartierRepository;
import com.example.demo.dao.TypeLogementRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dao.VilleRepository;
import com.example.demo.entities.Annonce;
import com.example.demo.entities.Dossier;
import com.example.demo.entities.Quartier;
import com.example.demo.entities.TypeLogement;
import com.example.demo.entities.User;
import com.example.demo.entities.Ville;
import com.example.demo.service.UserService;


@Controller
@RequestMapping("/annonceur")
@Secured(value={"ROLE_ANNONCEUR"})
public class AnnonceurController {
	@Autowired
	DossierRepository dossierRepository;
	@Autowired
	TypeLogementRepository typeLogementRepository;
	@Autowired
	VilleRepository villeRepository;
	@Autowired
	QuartierRepository quartierRepository;
	@Autowired
	AnnonceRepository annonceRepository;
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Value("${dir.images}")
	String imageDir;
	
	@RequestMapping("/home")
	public String espaceAnnonceur(Model model) {
		return "homeAnnonceur";
	}
	
	@RequestMapping(value = "/formAnnonce", method=RequestMethod.POST)
	public String formAnnonce(Model model,
			@RequestParam(name="ville")Long idVille) {
		List<TypeLogement> types = typeLogementRepository.findAll();
		List<Quartier> quartiers = quartierRepository.findByVille_id(idVille);
		model.addAttribute("quartiers", quartiers);
		model.addAttribute("types", types);
		model.addAttribute("annonce", new Annonce());
		return "formAnnonce";
	}
	
	@RequestMapping(value="/formAnnonce0")
	public String formAnnonce0( Model model ) throws Exception {
		List<Ville> villes = villeRepository.findAll();
		model.addAttribute("villes", villes);
		return "formAnnonce0";
	}
	
	
	@RequestMapping(value="/saveAnnonce", method=RequestMethod.POST)
	public String save(@Valid Annonce an,
			BindingResult bindingResult,
			@RequestParam(name="picture")MultipartFile file,
			@RequestParam(name="picture1")MultipartFile file1,
			@RequestParam(name="picture2")MultipartFile file2,
			@RequestParam(name="typeLogement")Long idType,
			@RequestParam(name="quartier")Long idQuartier,
			HttpServletRequest httpServletRequest) throws Exception {
		if(bindingResult.hasErrors()) {
			return "formAnnonce";
		}
		if(!(file.isEmpty())) { an.setPhoto(file.getOriginalFilename());}
		if(!(file1.isEmpty())) { an.setPhoto1(file1.getOriginalFilename());}
		if(!(file2.isEmpty())) { an.setPhoto2(file2.getOriginalFilename());}
		Optional<TypeLogement> typeLogement = typeLogementRepository.findById(idType);
		Optional<Quartier> quartier = quartierRepository.findById(idQuartier);
		an.setTypeLogement(typeLogement.get());
		an.setQuartier(quartier.get());
		HttpSession httpSession = httpServletRequest.getSession();
		SecurityContext securityContext=(SecurityContext) 
				httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		User user = userRepository.findById(username).get();
		an.setUser(user);
		
		List<Annonce> annonces = new LinkedList<>();
		annonces.add(an);
		user.setAnnonces(annonces);
		annonceRepository.save(an);
		if(!(file.isEmpty())) {
			an.setPhoto(file.getOriginalFilename());
			file.transferTo(new File(imageDir+an.getId()));
		}
		if(!(file1.isEmpty())) {
			an.setPhoto(file1.getOriginalFilename());
			file1.transferTo(new File(imageDir+an.getId()+"_1"));
		}
		if(!(file2.isEmpty())) {
			an.setPhoto(file2.getOriginalFilename());
			file2.transferTo(new File(imageDir+an.getId()+"_2"));
		}

		return "redirect:home";
		
	}
	
	@RequestMapping(value="/editAnnonce", method=RequestMethod.POST)
	public String editAnnonce(@Valid Annonce newAn,
			@RequestParam(name="idOldAn")Long idOldAn,
			HttpServletRequest httpServletRequest) throws Exception {

		Annonce oldAn = annonceRepository.getOne(idOldAn);
//		File oldFile = new File(imageDir+oldAn.getId());
//		oldFile.delete();
//		File oldFile1 = new File(imageDir+oldAn.getId()+"_1");
//		oldFile1.delete();
//		File oldFile2 = new File(imageDir+oldAn.getId()+"_2");
//		oldFile2.delete();
//		Files.delete(Paths.get(imageDir+oldAn.getId()));
//		Files.delete(Paths.get(imageDir+oldAn.getId()+"_1"));
//		Files.move(Paths.get(imageDir+oldAn.getId()+"_2"), Paths.get("C:\\Users\\Moi\\Desktop\\1.txt"), REPLACE_EXISTING);
//		Files.delete(Paths.get(imageDir+oldAn.getId()+"_2"));
//		if(!(file.isEmpty())) { oldAn.setPhoto(file.getOriginalFilename());}
//		if(!(file1.isEmpty())) { oldAn.setPhoto1(file1.getOriginalFilename());}
//		if(!(file2.isEmpty())) { oldAn.setPhoto2(file2.getOriginalFilename());}
		oldAn.setAdresse(newAn.getAdresse());
		oldAn.setTitre(newAn.getTitre());
		oldAn.setPrix((int) newAn.getPrix());
		oldAn.setDescription(newAn.getDescription());
		oldAn.setTel(newAn.getTel());
		oldAn.setEtage(newAn.getEtage());
		oldAn.setMeuble(newAn.getMeuble());
		//oldAn.setSurface((int) newAn.getSurface());
		oldAn.setNombrePersonnes(newAn.getNombrePersonnes());
		
		
		
//		if(!(file.isEmpty())) {
//			oldAn.setPhoto(file.getOriginalFilename());
//			file.transferTo(new File(imageDir+oldAn.getId()));
//		}
//		if(!(file1.isEmpty())) {
//			oldAn.setPhoto(file1.getOriginalFilename());
//			file1.transferTo(new File(imageDir+oldAn.getId()+"_1"));
//		}
//		if(!(file2.isEmpty())) {
//			oldAn.setPhoto(file2.getOriginalFilename());
//			file2.transferTo(new File(imageDir+oldAn.getId()+"_2"));
//		}

		annonceRepository.save(oldAn);

		return "redirect:mesAnnonces";
		
	}
	
	@RequestMapping(value="/mesAnnonces")
	public String mesAnnonces(Model model, HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession();
		SecurityContext securityContext=(SecurityContext) 
				httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		List<Annonce> mesAnnonces = annonceRepository.findByUser_username(username);
		model.addAttribute("mesAnnonces", mesAnnonces);
		return "mesAnnonces";
	}
	
	@RequestMapping(value="/getPhoto", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(Long id) throws Exception {
		File f = new File(imageDir+id+"_2");
		return IOUtils.toByteArray(new FileInputStream(f));
	}
	

	@RequestMapping(value="/supprimer")
	public String supprimer(Long id) {
		annonceRepository.deleteById(id);
		return "redirect:mesAnnonces";
	}
	
	@RequestMapping(value="/edit")
	public String edit(Long id,Model model) {
		Annonce an = annonceRepository.getOne(id);
		model.addAttribute("annonce", an);
		model.addAttribute("idOldAn",an.getId());
		return "editAnnonce";
	}
	
	
	@RequestMapping(value="/show")
	public String show(Long id,Model model) {
		Annonce an = annonceRepository.getOne(id);
		model.addAttribute("annonce", an);
		model.addAttribute("idOldAn",an.getId());
		return "showAnnonce";
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
		return "profileAnnonceur";
	}
	
	@RequestMapping(value="/profilem")
	public String modifierProfileAnnonceur(Model model, HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession();
		SecurityContext securityContext=(SecurityContext) 
				httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		User monProfil = userService.findByUsername(username);
		model.addAttribute("user", monProfil);
		model.addAttribute("idOldUs", monProfil.getUsername());
		return "profileAnnonceurModifier";
	}
	
	
	@RequestMapping(value="/editAnnonceur", method=RequestMethod.POST)
	public String editAnnonceur(@Valid User newUs,
			@RequestParam(name="idOldUs")String idOldUs,
			HttpServletRequest httpServletRequest) throws Exception {
		
		User oldUs = userRepository.getOne(idOldUs);
		oldUs.setUsername(newUs.getUsername());
		oldUs.setPassword(newUs.getPassword());
		oldUs.setMatchingPassword(newUs.getMatchingPassword());
		oldUs.setTel(newUs.getTel());
		oldUs.setEmail(newUs.getEmail());
		oldUs.setAdresse(newUs.getAdresse());
		oldUs.setEmploi(newUs.getEmploi());
		oldUs.setDateNaissance(newUs.getDateNaissance());
		oldUs.setNationalite(newUs.getNationalite());

		userRepository.save(oldUs);

		return "redirect:/annonceur/home";
		
	}
	
	@RequestMapping(value="/getDossiers")
	public String getDossiers(Long id,Model model) {
		List<Dossier> dossiers = dossierRepository.findByAnnonce_id(id);
		model.addAttribute("dossiers", dossiers);
		return "dossiersAnnonce";
	}
	
}
