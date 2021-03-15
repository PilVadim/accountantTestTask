package com.accountManagement.testTask.service;

import com.accountManagement.testTask.model.Role;
import com.accountManagement.testTask.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RoleService {

    private final RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public List<String> findAll(){
        return StreamSupport
                .stream( roleRepo.findAll().spliterator(),false)
                .map(Role::getName)
                .collect(Collectors.toList());
    }

}
