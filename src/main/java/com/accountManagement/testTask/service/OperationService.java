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

import java.util.ArrayList;

import static com.accountManagement.testTask.service.SecurityService.currentUserId;
import static com.accountManagement.testTask.service.SecurityService.currentUserIsAdmin;

@Service
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

    public Operation save( Operation operation ){
        return operation; //TODO complete it
    }


    public Float totalByAccountId(Integer accountId ) {

        if ( checkAccess(accountId) ) {
            return vUsersOperationsRepo.getSumByAccountId(accountId);
        } else {
            return null;
        }

    }

    public Float totalByUserId(Integer userId ) {

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
