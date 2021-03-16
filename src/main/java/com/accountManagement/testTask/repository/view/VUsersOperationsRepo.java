package com.accountManagement.testTask.repository.view;

import com.accountManagement.testTask.model.view.VUsersOperations;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface VUsersOperationsRepo extends PagingAndSortingRepository<VUsersOperations, Integer> {

    @Query("SELECT SUM(op.difference) FROM VUsersOperations op WHERE op.accountId = :accountId")
    Double getSumByAccountId(@Param("accountId") Integer accountId);

    @Query("SELECT SUM(op.difference) FROM VUsersOperations op WHERE op.userId = :userId")
    Double getSumByUserId(@Param("userId") Integer userId);


}
