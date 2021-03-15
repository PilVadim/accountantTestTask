package com.accountManagement.testTask.repository;

import com.accountManagement.testTask.model.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface OperationRepo  extends PagingAndSortingRepository<Operation, Integer> {

    public Page<Operation> findAllByAccountId(Integer accountId, Pageable pageable);

    @Query("SELECT SUM(op.difference) FROM Operation op WHERE op.accountId = :accountId")
    public Float getSumByAccountId(@Param("accountId") Integer accountId);

}
