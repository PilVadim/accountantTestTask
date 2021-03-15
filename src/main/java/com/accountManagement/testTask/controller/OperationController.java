package com.accountManagement.testTask.controller;

import com.accountManagement.testTask.model.Operation;
import com.accountManagement.testTask.service.OperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Api(value="Operation controller class", description="Работа с Операциями")
@RestController
@RequestMapping("/api/1.0/operations")
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @ApiOperation(value = "Получить все Операции счета", response = Operation.class)
    @GetMapping("/all/{accountId}")
    public Page<Operation> getAllByAccountId(@PathVariable Integer accountId){
        return operationService.findAllByAccountId( accountId );
    }

    @ApiOperation(value = "Получить получить итог по Счету", response = Operation.class)
    @GetMapping("/account/total/{accountId}")
    public Float getTotal(@PathVariable Integer accountId){
        return operationService.totalByAccountId( accountId );
    }

}
