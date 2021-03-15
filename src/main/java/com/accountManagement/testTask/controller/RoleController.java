package com.accountManagement.testTask.controller;

import com.accountManagement.testTask.model.Role;
import com.accountManagement.testTask.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="Roles controller class", description="Работа с Ролями")
@RestController
@RequestMapping("/api/1.0/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "Получить все Роли", response = Role.class)
    @GetMapping("/admin/all")
    public List<String> getAll(){

        return roleService.findAll();

    }

}
