package com.example.demo.repository;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dao.DossierRepository;

@SpringBootTest
class DossierRepositoryTest {

	DossierRepository dossierRepository;

	@BeforeEach
	void setUp() throws Exception {
		DossierRepository dossierRepository = mock(DossierRepository.class);

	}

	@AfterEach
	void tearDown() throws Exception {
		dossierRepository = null;
	}

	@Test
	void test() {


	}

}
