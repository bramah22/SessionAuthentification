package org.sid.web;

import org.sid.entities.AppUser;
import org.sid.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@PostMapping("/register")
	public AppUser register(@RequestBody RegisterForm userForm) {
		
		if (! userForm.getPassword().equals(userForm.getConfirmPassword())) {
			throw new RuntimeException("You must confirm your Password");
		}
		
		AppUser user = accountService.findUserByUsername(userForm.getUsername());
		if (user != null) {
			throw new RuntimeException("this user already exists!");
		}
		AppUser appUser = new AppUser();
		appUser.setUsername(userForm.getUsername());
		appUser.setPassword(userForm.getPassword());
		
		accountService.saveUser(appUser);
		accountService.addRoleToUse(userForm.getUsername(), "USER");
		
		return appUser;
	}
	
}
