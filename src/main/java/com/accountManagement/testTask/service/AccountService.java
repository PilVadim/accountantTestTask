package com.accountManagement.testTask.service;

import com.accountManagement.testTask.model.Account;
import com.accountManagement.testTask.repository.AccountRepo;
import com.accountManagement.testTask.repository.UserRepo;
import com.accountManagement.testTask.service.exceptions.MyBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.accountManagement.testTask.service.SecurityService.currentUserId;
import static com.accountManagement.testTask.service.SecurityService.currentUserIsAdmin;

@Service
@SessionScope
public class AccountService {

    private final AccountRepo accountRepo;
    private final UserRepo userRepo;

    @Autowired
    public AccountService(AccountRepo accountRepo,
                          UserRepo userRepo) {
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
    }

    public List<Account> findAllByUserId(Integer userId){

        if ( currentUserIsAdmin() || currentUserId().equals(userId) ) {
            return accountRepo.findAllByUserId(userId);
        } else {
            return new ArrayList<>();
        }

    }

    public synchronized Account save( Account account ){

        checkAccount( account );
        fillAccount( account );
        return accountRepo.save( account );

    }

    private void fillAccount( Account account ) {

        if (account.getAccountId() != null) {
            Account accountFromDb = accountRepo.findById(account.getAccountId())
                    .orElseThrow( () -> new MyBadRequestException( "Account not found") );
            if ( ! accountFromDb.getUserId().equals( account.getUserId() ) ) {
                throw new MyBadRequestException( "UserId cannot be changed");
            }
        } else {
            account.setOpenedDate( LocalDateTime.now() );
        }

    }

    private void checkAccount(Account account) {

        if ( account.getUserId() == null ) {
            throw new MyBadRequestException( "UserId should be filled");
        }

        userRepo.findById( account.getUserId() ).orElseThrow( () -> new MyBadRequestException( "User not found") );

    }

    public synchronized String delete(Integer accountId) {

        Account account = accountRepo.findById(accountId).orElseThrow( () -> new MyBadRequestException( "Account not found" ) );

        account.setIsDeleted(true);
        account.setClosedDate( LocalDateTime.now() );
        accountRepo.save(account);

        return "Account deleted successfully - " + accountId;

    }

}
