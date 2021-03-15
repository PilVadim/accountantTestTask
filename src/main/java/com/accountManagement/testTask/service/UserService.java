package com.accountManagement.testTask.service;

import com.accountManagement.testTask.model.Role;
import com.accountManagement.testTask.model.User;
import com.accountManagement.testTask.repository.UserRepo;
import com.accountManagement.testTask.service.exceptions.MyBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.accountManagement.testTask.configuration.Security.USER_ROLE;

@Service
@SessionScope
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    public Page<User> findAll( Integer page ){

        return userRepo.findAll( PageRequest.of(page, 20, Sort.by("username").ascending()) );

    }


    public synchronized User save( User user ){

        checkUser( user );
        fillUser( user );
        return userRepo.save( user );

    }

    private void checkUser(User user) {

        if ( user.getUserId() == null ) {

            if (userRepo.findByUsernameAndEnabledAndDeleted(user.getUsername(), true, false).isPresent()) {
                throw new MyBadRequestException("User with name - " + user.getUsername() + " already exists ");
            }

        }

        if ( user.getUsername() == null || user.getUsername().isEmpty() ) {
            throw new MyBadRequestException( "Name should be filled");
        }

        if ( user.getPassword() == null || user.getPassword().isEmpty() ) {
            throw new MyBadRequestException( "Password should be filled" );
        }

    }

    private void fillUser(User user) {

        if ( user.getUserId() != null ) {

            User userFromBase = userRepo.findById(user.getUserId()).orElseThrow(() -> new MyBadRequestException("User not found"));
            user.setCreatedDate( userFromBase.getCreatedDate() );

            if (user.getPassword() != null && ! user.getPassword().isEmpty() ){
                user.setPassword( passwordEncoder.encode(user.getPassword()) );
            }else{
                user.setPassword( userFromBase.getPassword() );
            }
            user.setCreatedDate( userFromBase.getCreatedDate() );
        }

        if (user.getCreatedDate() == null ) {
            user.setCreatedDate( LocalDateTime.now() );
        }

        if (user.getAuthorities().size() == 0 ) {
            user.setAuthorities( Collections.singletonList( new Role(USER_ROLE)) );
        }

    }


    public synchronized String delete(Integer userId) {

        User user = userRepo.findById(userId).orElseThrow( () -> new MyBadRequestException( "User not found" ) );

        user.setDeleted(true);
        user.setDeletedDate( LocalDateTime.now() );
        userRepo.save(user);

        return "User deleted successfully - " + userId;

    }

}
