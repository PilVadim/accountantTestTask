package com.accountManagement.testTask.service;

import com.accountManagement.testTask.model.User;
import com.accountManagement.testTask.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetail implements UserDetailsService {

    @Autowired
    public UserRepo userRepo;


    public User loadUserByUsername(String userName){
        Optional<User> userOptional = userRepo.findByUsernameAndEnabledAndDeleted(userName,true,false);
        if (!userOptional.isPresent()){
            throw new UsernameNotFoundException("User not found");
        } else {
            return userOptional.get();

        }
    }
}
