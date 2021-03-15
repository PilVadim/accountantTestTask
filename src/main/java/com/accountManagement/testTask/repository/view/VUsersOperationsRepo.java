package com.accountManagement.testTask.repository.view;

import com.accountManagement.testTask.model.Account;
import com.accountManagement.testTask.model.view.VUsersOperations;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VUsersOperationsRepo extends PagingAndSortingRepository<VUsersOperations, Integer> {

    @Query("SELECT SUM(op.difference) FROM VUsersOperations op WHERE op.accountId = :accountId")
    public Float getSumByAccountId(@Param("accountId") Integer accountId);

    @Query("SELECT SUM(op.difference) FROM VUsersOperations op WHERE op.userId = :userId")
    public Float getSumByUserId(@Param("userId") Integer userId);


}
