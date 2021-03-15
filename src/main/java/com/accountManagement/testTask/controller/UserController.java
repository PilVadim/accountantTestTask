package com.accountManagement.testTask.controller;

import com.accountManagement.testTask.model.User;
import com.accountManagement.testTask.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Api(value="Users controller class", description="Работа с Пользователями")
@RestController
@RequestMapping("/api/1.0/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Получить всех Пользователей", response = User.class)
    @GetMapping("/all/{page}")
    public Page<User> getAll(@PathVariable Integer page){
        return userService.findAll(page);
    }

    @ApiOperation(value = "Добавить/изменить Пользователя", response = User.class)
    @PostMapping("/admin/")
    public User save(@RequestBody User user){
        return userService.save(user);
    }

    @ApiOperation(value = "Удалить Пользователя", response = User.class)
    @DeleteMapping("/admin/{userId}")
    public String delete(@PathVariable Integer userId){
        return userService.delete(userId);
    }

}
