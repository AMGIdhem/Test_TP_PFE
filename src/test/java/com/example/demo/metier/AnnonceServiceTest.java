package com.example.demo.metier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.demo.entities.Annonce;
import com.example.demo.service.impl.AnnonceServiceImpl;

public class AnnonceServiceTest {
	
	@Test
    void testAddAnnonce() {
        AnnonceServiceImpl annonceServiceImpl = mock(AnnonceServiceImpl.class);
        Annonce annonce = new Annonce();
        annonce.setId(1L);
        annonce.setTitre("titre annonce1");
        annonce.setPrix(1500);
        annonce.setTel("057657688");

        Mockito.when(annonceServiceImpl.addAnnonce(annonce)).thenReturn(annonce);
        assertThat(annonceServiceImpl.addAnnonce(annonce)).isEqualTo(annonce);
    }
}
