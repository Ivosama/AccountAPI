package com.idts.accountapi.dao;

import com.idts.accountapi.model.Account;
import com.idts.accountapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUser(@Param("user")User user);

    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.balance = :newBalance WHERE a.id = :accountId")
    void updateBalance(@Param("accountId") Long accountId, @Param("newBalance") BigDecimal newBalance);
}
