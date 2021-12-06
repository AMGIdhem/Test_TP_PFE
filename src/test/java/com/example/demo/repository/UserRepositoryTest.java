package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.dao.UserRepository;
import com.example.demo.entities.User;

@SpringBootTest
class UserRepositoryTest {
	
	@MockBean
	private UserRepository userRepository;

	User user;

	@BeforeEach
	public void setUp() {
		user = new User();
		user.setUsername("user1");
		user.setEmail("user1@gmail.com");
		user.setCne("JB333446");
		user.setEtablissement("ensa");
	}

	@Test
	public void testFindByIdUser() {
		when(userRepository.findById("user1")).thenReturn(Optional.of(user));
		User u = userRepository.findById("user1").get();
		assertEquals("user1", u.getUsername());
		assertEquals("user1@gmail.com", u.getEmail());
		assertEquals("ensa", u.getEtablissement());

	}
	
	@Test
	public void testSaveUser() {
		when(userRepository.save(user)).thenReturn(user);
		User u = userRepository.save(user);
		assertEquals(u, user);
	}

}
