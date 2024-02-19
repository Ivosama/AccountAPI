package com.idts.accountapi.dao;

import com.idts.accountapi.model.Account;
import com.idts.accountapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AccountRepository extends JpaRepository<Account, Long> {
    //Todo could add queries
    List<Account> findByUser(@Param("user")User user);
}
