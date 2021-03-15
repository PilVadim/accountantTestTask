package com.accountManagement.testTask.controller;

import com.accountManagement.testTask.model.Account;
import com.accountManagement.testTask.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="Account controller class", description="Работа со Счетами")
@RestController
@RequestMapping("/api/1.0/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ApiOperation(value = "Получить все Счета пользователя", response = Account.class)
    @GetMapping("/all/{userId}")
    public List<Account> getAll(@PathVariable Integer userId){
        return accountService.findAllByUserId( userId );
    }

    @ApiOperation(value = "Добавить/изменить Аккаунт", response = Account.class)
    @PostMapping("/admin/")
    public Account save(@RequestBody Account account){
        return accountService.save( account );
    }

    @ApiOperation(value = "Удалить Аккаунт", response = Account.class)
    @DeleteMapping("/admin/{accountId}")
    public String delete(@PathVariable Integer accountId){
        return accountService.delete(accountId);
    }

}
