package com.accountManagement.testTask.repository;

import com.accountManagement.testTask.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role,String> {}
