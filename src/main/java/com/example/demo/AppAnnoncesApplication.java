package com.example.demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.demo.dao.QuartierRepository;
import com.example.demo.dao.RoleRepository;
import com.example.demo.dao.TypeLogementRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dao.VilleRepository;
import com.example.demo.entities.Quartier;
import com.example.demo.entities.Role;
import com.example.demo.entities.TypeLogement;
import com.example.demo.entities.User;
import com.example.demo.entities.Ville;

@SpringBootApplication
public class AppAnnoncesApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(AppAnnoncesApplication.class, args);
		UserRepository userRepo = ctx.getBean(UserRepository.class);
		RoleRepository roleRepo = ctx.getBean(RoleRepository.class);
//		
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepo.getOne("ADMIN"));
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		userRepo.save(new User("admin", "123", "123", true, roles, "admin@gmail.com", date, "0670803893", "marocaine", "agadir rue205", "emploi", "D13288", "ensa", "4"));
//		TypeLogementRepository typeRepo = ctx.getBean(TypeLogementRepository.class);
//		typeRepo.save(new TypeLogement("Studio"));
//		typeRepo.save(new TypeLogement("Chambre"));
//		typeRepo.save(new TypeLogement("Appartement"));
//		QuartierRepository quartRepo = ctx.getBean(QuartierRepository.class);
//		quartRepo.save(new Quartier("Tilila"));
//		quartRepo.save(new Quartier("El Houda"));
//		quartRepo.save(new Quartier("Hay Salam"));
//		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
//		roleRepository.save(new Role("ETUDIANT", null));
//		roleRepository.save(new Role("ANNONCEUR", null));
//		roleRepository.save(new Role("ADMIN", null));
//		VilleRepository villeRepo = ctx.getBean(VilleRepository.class);
//		villeRepo.save(new Ville("Agadir"));
//		villeRepo.save(new Ville("Marrakech"));
//		villeRepo.save(new Ville("Casablance"));
	}

}
