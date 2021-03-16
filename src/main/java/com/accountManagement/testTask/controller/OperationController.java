package com.accountManagement.testTask.controller;

import com.accountManagement.testTask.model.Operation;
import com.accountManagement.testTask.service.OperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api(value="Operation controller class", description="Работа с Операциями")
@RestController
@RequestMapping("/api/v1/operations")
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @ApiOperation(value = "Получить все Операции счета", response = Operation.class)
    @GetMapping("/all/{page}/{accountId}")
    public Page<Operation> getAllByAccountId(@PathVariable Integer accountId, @PathVariable Integer page){
        return operationService.findAllByAccountId( accountId, page );
    }

    @ApiOperation(value = "Создать Операцию", response = Operation.class)
    @PostMapping("/")
    public Operation saveOperation(@RequestBody Operation operation){
        return operationService.save( operation );
    }

    @ApiOperation(value = "Получить получить итог по Счету", response = Float.class)
    @GetMapping("/account/total/{accountId}")
    public Double getTotalByAccountId(@PathVariable Integer accountId){
        return operationService.totalByAccountId( accountId );
    }

    @ApiOperation(value = "Получить получить итог по Пользователю", response = Float.class)
    @GetMapping("/user/total/{userId}")
    public Double getTotalByUserId(@PathVariable Integer userId){
        return operationService.totalByUserId( userId );
    }

}
