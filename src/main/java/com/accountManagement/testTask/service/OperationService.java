package com.accountManagement.testTask.service;

import com.accountManagement.testTask.model.Account;
import com.accountManagement.testTask.model.Operation;
import com.accountManagement.testTask.repository.AccountRepo;
import com.accountManagement.testTask.repository.OperationRepo;
import com.accountManagement.testTask.repository.view.VUsersOperationsRepo;
import com.accountManagement.testTask.service.exceptions.MyBadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.accountManagement.testTask.service.SecurityService.currentUserId;
import static com.accountManagement.testTask.service.SecurityService.currentUserIsAdmin;

@Service
@SessionScope
public class OperationService {

    private final AccountRepo accountRepo;
    private final OperationRepo operationRepo;
    private final VUsersOperationsRepo vUsersOperationsRepo;

    public OperationService(AccountRepo accountRepo,
                            OperationRepo operationRepo,
                            VUsersOperationsRepo vUsersOperationsRepo) {
        this.accountRepo = accountRepo;
        this.operationRepo = operationRepo;
        this.vUsersOperationsRepo = vUsersOperationsRepo;
    }

    public Page<Operation> findAllByAccountId( Integer accountId, Integer page ) {

        if ( checkAccess(accountId) ){
            return operationRepo.findAllByAccountId( accountId,
                    PageRequest.of( page, 20, Sort.by("accountId").ascending()) );
        } else {
            return new PageImpl<>(new ArrayList<>());
        }

    }

    public synchronized Operation save( Operation operation ){

        checkOperation( operation );
        return operationRepo.save(operation);

    }

    private void checkOperation( Operation op ) {

        if ( op.getOperationId() != null ) {
            throw new MyBadRequestException( "You can't change operation" );
        }

        if ( op.getAccountId() == null ) {
            throw new MyBadRequestException( "AccountId should be filled" );
        }

        if ( op.getDifference() == null || op.getDifference().equals(BigDecimal.ZERO) ) {
            throw new MyBadRequestException( "Difference should be filled" );
        }

        Account acc = accountRepo.findById( op.getAccountId() )
                .orElseThrow(() -> new MyBadRequestException( "Account should be filled" ) );

        if ( ! (currentUserIsAdmin() || acc.getUserId().equals( currentUserId() )) ) {
            throw new MyBadRequestException( "Account doesn't belongs to current User" );
        }

        Double sum = vUsersOperationsRepo.getSumByAccountId( op.getAccountId() );

        if ( BigDecimal.valueOf( sum ).add( op.getDifference() ).compareTo(BigDecimal.valueOf( 0.0 )) < 0){
            throw new MyBadRequestException( "Operation couldn't be proceeded. Balance below zero" );
        }

    }


    public Double totalByAccountId(Integer accountId ) {

        if ( checkAccess(accountId) ) {
            return vUsersOperationsRepo.getSumByAccountId(accountId);
        } else {
            return null;
        }

    }

    public Double totalByUserId(Integer userId ) {

        if ( checkAccessUserId(userId) ) {
            return vUsersOperationsRepo.getSumByUserId(userId);
        } else {
            return null;
        }

    }

    private boolean checkAccessUserId(Integer userId) {

        return currentUserIsAdmin() || userId.equals( currentUserId() );

    }

    private boolean checkAccess( Integer accountId ) {

        Account acc = accountRepo.findById(accountId)
                .orElseThrow( () -> new MyBadRequestException( "Account not found") );

        return currentUserIsAdmin() || acc.getUserId().equals( currentUserId() );

    }

}
