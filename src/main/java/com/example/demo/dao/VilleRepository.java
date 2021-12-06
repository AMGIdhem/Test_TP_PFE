package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Ville;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Long> {

}
