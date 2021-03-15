package com.accountManagement.testTask.model.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Immutable
@Table(name = "v_users_operations")
public class VUsersOperations {

    @Id
    @Column(name = "operationid")
    Integer operationId;

    @Column(name = "userid")
    Integer userId;

    @Column(name = "accountid")
    Integer accountId;

    BigDecimal difference;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "proceededdate")
    LocalDateTime proceededDate;

}
