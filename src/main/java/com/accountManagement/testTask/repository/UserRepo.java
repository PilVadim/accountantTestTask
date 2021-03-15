package com.accountManagement.testTask.repository;

import com.accountManagement.testTask.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepo extends PagingAndSortingRepository<User,Integer> {

    Optional<User> findByUsernameAndEnabledAndDeleted( String username,
                                                       Boolean enabled,
                                                       Boolean deleted);

    void deleteUserByUserId( Integer userId );

}
