package com.louie.authentication.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.louie.authentication.models.LoginUser;
import com.louie.authentication.models.User;
import com.louie.authentication.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
    private UserRepository userRepo;
   
	
    public User register(User newUser, BindingResult result) {

    	//Reject if email is taken (present in database)
//    	if(userRepo.findByEmail(newUser.getEmail()).isPresent() ) {
//    		result.rejectValue("email", "Unique", "This email is taken"); // message is for development purposes, be more vague like "login error" in the real world
//    	}
//    	
    	Optional<User> potentialUser = userRepo.findByEmail(newUser.getEmail());
    	if(potentialUser.isPresent()) {
    		result.rejectValue("email", "Unique", "This email is taken");
    	}
    	
    	
    	//Reject if password doesn't match confirmation
    	if(!newUser.getPassword().equals(newUser.getConfirm())) {
    		result.rejectValue("confirm", "Matches", "passwords do not match");
    	}
    	//Return null if result has errors
    	if(result.hasErrors()) {
    		return null;
    	}else {
    		//Hash and set password, save user to database
    		String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
    		newUser.setPassword(hashed);
    		//Save user to the DB
    		return userRepo.save(newUser);
    	}
        
    }
    public User login(LoginUser newLoginObject, BindingResult result) {
    	
    	if(result.hasErrors()) {
    		return null;
    	}
    	
    	//Find user in the DB by email
    	Optional<User> potentialUser = userRepo.findByEmail(newLoginObject.getEmail());
    	if(!potentialUser.isPresent()) {
    		result.rejectValue("email", "Unique", "unknown email");
    		return null;
    	}
    	
    	User user = potentialUser.get();
    	//Reject if BCrypt password match fails
    	if(!BCrypt.checkpw(newLoginObject.getPassword(), user.getPassword() )) {
    		//Reject if not present
    		result.rejectValue("password", "Matches", "invalid password");
    	}
    	
    	//Return null if result has errors
    	if(result.hasErrors()) {
    		return null;
    	}else {
    		//Otherwise return user Object
    		return user;
    	}
    }
    
    //CRUD
    //READ ONE
    public User findOne(Long id) {
    	Optional<User> optionalUser = userRepo.findById(id);
    	if(optionalUser.isPresent()) {
    		return optionalUser.get();
    	}else {
    		return null;
    	}
    }
}