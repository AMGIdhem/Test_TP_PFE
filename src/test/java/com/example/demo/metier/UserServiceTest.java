package com.example.demo.metier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.service.impl.UserServiceImpl;

@SpringBootTest
class UserServiceTest {

	@Test
    void testAddUser() {
        UserServiceImpl userServiceImpl = mock(UserServiceImpl.class);
        User user2 = new User();
        Role role = new Role();
        role.setRole("etudiant");
        List<Role> roles = new LinkedList<>();
        roles.add(role);
        user2.setUsername("user2");
        user2.setEmail("user2@gmail.com");
        user2.setEtablissement("ensa");
        user2.setRoles(roles);

        Mockito.when(userServiceImpl.addUser(user2, "etudiant")).thenReturn(user2);;
        assertThat(userServiceImpl.addUser(user2, "etudiant")).isEqualTo(user2);
    }

}
