package com.idts.accountapi.dao;

import com.idts.accountapi.model.User;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@NonNullApi
public interface UserRepository extends JpaRepository<User, Long> {
    //Todo could add queries
    User findByUsername(String username);
}
