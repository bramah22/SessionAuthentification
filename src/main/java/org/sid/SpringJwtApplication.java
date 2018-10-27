package org.sid;

import java.util.stream.Stream;

import org.sid.dao.TaskRepository;
import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.sid.entities.Task;
import org.sid.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringJwtApplication implements CommandLineRunner{

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private AccountService accountService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringJwtApplication.class, args);
	}
	
	@Bean // pour instancier au demarrage de l'application 
	public BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		accountService.saveUser(new AppUser(null, "admin", "1234", null));
		accountService.saveUser(new AppUser(null, "user", "1234", null));
		
		accountService.saveRole(new AppRole(null , "ADMIN"));
		accountService.saveRole(new AppRole(null , "USER"));
		
		accountService.addRoleToUse("admin", "ADMIN");
		accountService.addRoleToUse("admin", "USER");
		accountService.addRoleToUse("user", "USER");

		Stream.of("T1" , "T2" , "T3" ,"T4").forEach(t->{
			taskRepository.save(new Task(null, t));
		});
	}
}
