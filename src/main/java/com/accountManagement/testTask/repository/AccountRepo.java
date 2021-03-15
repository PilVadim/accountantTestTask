package com.accountManagement.testTask.repository;

import com.accountManagement.testTask.model.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AccountRepo extends PagingAndSortingRepository<Account, Integer> {

    List<Account> findAllByUserId(Integer userId);

}
