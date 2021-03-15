package com.accountManagement.testTask.service;

import com.accountManagement.testTask.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;

import static com.accountManagement.testTask.configuration.Security.ADMIN_ROLE;

@Service
@SessionScope
public class SecurityService {

    public static boolean currentUserIsAdmin(){

        try {
            Collection<? extends GrantedAuthority> authentications = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getAuthorities();

            return authentications.stream()
                    .anyMatch(a -> (a.getAuthority().equals(ADMIN_ROLE)));
        } catch (Exception e) {
            throw new SecurityException("Unauthorised access");
        }
    }

    public static Integer currentUserId(){
        try {
            return ((User)SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal())
                    .getUserId();
        } catch (Exception e) {
            throw new SecurityException("Unauthorised access");
        }
    }

}
