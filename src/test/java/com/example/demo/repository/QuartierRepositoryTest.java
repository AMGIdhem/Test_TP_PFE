package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.dao.QuartierRepository;
import com.example.demo.dao.VilleRepository;
import com.example.demo.entities.Quartier;
import com.example.demo.entities.Ville;

@SpringBootTest
class QuartierRepositoryTest {

	@MockBean
	private VilleRepository villeRepository;
	
	@MockBean
	private QuartierRepository quartierRepository;

	Ville ville;
	Quartier quartier;

	@BeforeEach
	public void setUp() {
		ville = new Ville(1L, "Tetouan");
		quartier = new Quartier();
		quartier.setId(1L);
		quartier.setNom("Al Farah");
		quartier.setVille(ville);
	}

	@Test
	public void findByIdQuartierTest() {
		when(quartierRepository.findById(1L)).thenReturn(Optional.of(quartier));
		Quartier q = quartierRepository.findById(1L).get();
		assertEquals("Al Farah", q.getNom());
		assertEquals("Tetouan", q.getVille().getVille());
	}
	
	@Test
	public void testSaveQuartier() {
		when(quartierRepository.save(quartier)).thenReturn(quartier);
		Quartier q = quartierRepository.save(quartier);
		assertEquals(q, quartier);
	}

}
