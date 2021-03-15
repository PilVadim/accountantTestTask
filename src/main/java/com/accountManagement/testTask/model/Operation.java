package com.accountManagement.testTask.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "operations")
public class Operation {

    @Id
    @Column(name = "operationid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer operationId;

    @Column(name = "accountid")
    private Integer accountId;
    private BigDecimal difference;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "proceededdate")
    private LocalDateTime proceededDate;

}
