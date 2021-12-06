package com.example.demo.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Quartier {
	@Id
	@GeneratedValue
	private Long id;
	private String nom;
	@ManyToOne 
	Ville ville;
	
	public Quartier(String nom, Ville ville) {
		super();
		this.nom = nom;
		this.ville = ville;
	}

	public Ville getVille() {
		return ville;
	}

	public void setVille(Ville ville) {
		this.ville = ville;
	}

	public Quartier() {
		
	}

	public Quartier(String nom) {
		super();
		this.nom = nom;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	
	
}
