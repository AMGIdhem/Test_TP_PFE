package com.example.demo.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ville {
	@Id
	@GeneratedValue
	private long id;
	private String ville;
	
	public Ville() {
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public Ville(String ville) {
		super();
		this.ville = ville;
	}
	public Ville(long id, String ville) {
		this.id = id;
		this.ville = ville;
	}
	

}
