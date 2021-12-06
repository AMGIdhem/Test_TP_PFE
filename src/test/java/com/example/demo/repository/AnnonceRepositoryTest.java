package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.dao.AnnonceRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.entities.Annonce;
import com.example.demo.entities.User;

@SpringBootTest
class AnnonceRepositoryTest {

	@MockBean
	private AnnonceRepository annonceRepository;

	Annonce annonce;

	@BeforeEach
	public void setUp() {
		annonce = new Annonce();
		annonce.setId(1L);
		annonce.setTitre("annonce1");
		annonce.setPrix(2000);
		annonce.setTel("056464566");
		annonce.setNombrePersonnes(3);
	}

	@Test
	public void testFindByIdAnnonce() {
		when(annonceRepository.findById(1L)).thenReturn(Optional.of(annonce));
		Annonce a = annonceRepository.findById(1L).get();
		assertEquals("annonce1", a.getTitre());
		assertEquals("056464566", a.getTel());
		assertEquals(2000, a.getPrix());

	}
	
	@Test
	public void testSaveAnnonce() {
		when(annonceRepository.save(annonce)).thenReturn(annonce);
		Annonce a = annonceRepository.save(annonce);
		assertEquals(a, annonce);
	}

}
