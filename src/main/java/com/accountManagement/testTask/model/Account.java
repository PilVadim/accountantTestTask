package com.accountManagement.testTask.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "accounts")
public class Account {

    @Id
    @Column(name = "accountid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;

    @Column(name = "userid")
    private Integer userId;

    private String comment;

    @Column(name = "isdeleted")
    private Boolean isDeleted;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "openeddate")
    private LocalDateTime openedDate;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "closeddate")
    private LocalDateTime closedDate;

}
