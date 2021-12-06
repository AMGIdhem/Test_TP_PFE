package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Annonce;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
	public List<Annonce> findByUser_username(String username);
	@Query("SELECT a FROM Annonce a WHERE a.titre LIKE %?1% AND a.prix>?2 AND a.prix<?3 ")
	public List<Annonce> findAll(String motCle, int prixMin, int prixMax);
	
}
