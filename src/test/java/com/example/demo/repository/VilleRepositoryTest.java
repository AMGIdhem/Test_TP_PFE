package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.dao.VilleRepository;
import com.example.demo.entities.Ville;

@SpringBootTest
public class VilleRepositoryTest {
	@MockBean
	private VilleRepository villeRepository;

	Ville ville;
	Ville villeSaved;

	@BeforeEach
	public void setUp() {
		ville = new Ville(1L, "Tetouan");
	}

	@Test
	public void findByIdVilleTest() {
		when(villeRepository.findById(1L)).thenReturn(Optional.of(ville));
		Ville v = villeRepository.findById(1L).get();
		assertEquals("Tetouan", v.getVille());

	}
	
	@Test
	public void testSaveVille() {
		when(villeRepository.save(ville)).thenReturn(ville);
		Ville v = villeRepository.save(ville);
		assertEquals(v, ville);
	}
}
