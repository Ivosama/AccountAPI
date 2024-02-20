package com.idts.accountapi.utils;

import com.idts.accountapi.customHandler.AccountNotFoundException;
import com.idts.accountapi.dao.AccountRepository;
import com.idts.accountapi.model.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountUtils {
    private final AccountRepository accountRepository;

    public AccountUtils(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean isAccountValid(Account account) {
        return accountRepository.findById(account.getId()).isPresent();
    }

    public Account validateAccount(Account account) {
        return validateAccountById(account.getId());
    }

    public Account validateAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    public BigDecimal substractFromAccount(Account account, BigDecimal amount) {
        return account.getBalance().subtract(amount);
    }

    public BigDecimal addToAccount(Account account, BigDecimal amount) {
        return account.getBalance().add(amount);
    }
}
