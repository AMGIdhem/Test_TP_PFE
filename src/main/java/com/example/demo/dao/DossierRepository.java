package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Annonce;
import com.example.demo.entities.Dossier;

public interface DossierRepository extends JpaRepository<Dossier, Long> {
	public List<Dossier> findByUser_username(String username);
	public List<Dossier> findByAnnonce_id(Long id);
}
