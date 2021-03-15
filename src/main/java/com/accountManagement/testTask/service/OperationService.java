package com.accountManagement.testTask.service;

import com.accountManagement.testTask.model.Operation;
import com.accountManagement.testTask.repository.OperationRepo;
import com.accountManagement.testTask.repository.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.math.BigDecimal;

@Service
public class OperationService {

    private final OperationRepo operationRepo;

    public OperationService(OperationRepo operationRepo) {
        this.operationRepo = operationRepo;
    }

    public Page<Operation> findAllByAccountId(Integer accountId ) {
        return operationRepo.findAllByAccountId(accountId,
                PageRequest.of(0, 20, Sort.by("accountId").ascending()));
    }

    public Float totalByAccountId(Integer accountId ) {
        return operationRepo.getSumByAccountId(accountId);
    }

}
