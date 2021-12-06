package com.example.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.AnnonceRepository;
import com.example.demo.entities.Annonce;


@RestController
@RequestMapping("api/")
public class AnnonceRestService {
	
	@Autowired
	private AnnonceRepository annonceRepository;
	
	@GetMapping("getAnnonces")
	public List<Annonce> getAnnonces() {
		return this.annonceRepository.findAll();
	}


}
