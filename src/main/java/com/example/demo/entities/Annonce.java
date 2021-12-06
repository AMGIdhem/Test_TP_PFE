package com.example.demo.entities;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Annonce {
	@Id
	@GeneratedValue
	private Long id;
	private String adresse;
	private String titre;
	private int prix;
	private String tel;
	private String email;
	private String description;
	private String photo;
	private String photo1;
	private String photo2;
	private String etage;
	private String meuble;
	private int surface;
	private int nombrePersonnes;
	@ManyToOne
	private User user;
	@ManyToOne
	TypeLogement typeLogement;
	@ManyToOne
	Quartier quartier;
	@OneToMany(mappedBy = "annonce", cascade = CascadeType.REMOVE)
	
	private Collection<Dossier> dossiers;
	
	public Annonce() {
		
	}
	
	public Collection<Dossier> getDossiers() {
		return dossiers;
	}

	public void setDossiers(Collection<Dossier> dossiers) {
		this.dossiers = dossiers;
	}


	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getEtage() {
		return etage;
	}



	public void setEtage(String etage) {
		this.etage = etage;
	}



	public String getMeuble() {
		return meuble;
	}



	public void setMeuble(String meuble) {
		this.meuble = meuble;
	}



	public int getSurface() {
		return surface;
	}



	public void setSurface(int surface) {
		this.surface = surface;
	}



	public int getNombrePersonnes() {
		return nombrePersonnes;
	}



	public void setNombrePersonnes(int nombrePersonnes) {
		this.nombrePersonnes = nombrePersonnes;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public String getPhoto1() {
		return photo1;
	}

	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

	public String getPhoto2() {
		return photo2;
	}

	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}

	public TypeLogement getTypeLogement() {
		return typeLogement;
	}

	public void setTypeLogement(TypeLogement typeLogement) {
		this.typeLogement = typeLogement;
	}

	public Quartier getQuartier() {
		return quartier;
	}

	public void setQuartier(Quartier quartier) {
		this.quartier = quartier;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
	
}
