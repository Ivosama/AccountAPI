package com.idts.accountapi;

import com.idts.accountapi.controller.AccountController;
import com.idts.accountapi.controller.TransactionController;
import com.idts.accountapi.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {
    @Autowired
    private UserController userController;
    @Autowired
    private AccountController accountController;
    @Autowired
    private TransactionController transactionController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(userController).isNotNull();
        assertThat(accountController).isNotNull();
        assertThat(transactionController).isNotNull();
    }
}
