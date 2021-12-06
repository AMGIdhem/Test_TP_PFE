package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Quartier;

public interface QuartierRepository extends JpaRepository<Quartier, Long> {
	public List<Quartier> findByVille_id(Long idVille);
}
