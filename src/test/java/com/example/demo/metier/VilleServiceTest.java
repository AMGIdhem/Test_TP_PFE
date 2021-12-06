package com.example.demo.metier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.demo.entities.Ville;
import com.example.demo.service.impl.VilleServiceImpl;

class VilleServiceTest {

	@Test
    void testSaveVille() {
        VilleServiceImpl villeServiceImpl = mock(VilleServiceImpl.class);
        Ville ville = new Ville();
        ville.setId(1L);
        ville.setVille("agadir");

        Mockito.when(villeServiceImpl.saveVille(ville)).thenReturn(ville);;
        assertThat(villeServiceImpl.saveVille(ville)).isEqualTo(ville);
    }

}
