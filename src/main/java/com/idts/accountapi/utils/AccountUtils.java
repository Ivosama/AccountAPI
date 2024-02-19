package com.idts.accountapi.utils;

import com.idts.accountapi.customHandler.AccountNotFoundException;
import com.idts.accountapi.dao.AccountRepository;
import com.idts.accountapi.model.Account;
import org.springframework.stereotype.Component;

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
        Long accountId = account.getId();
        account = accountRepository.findById(account.getId())
                        .orElseThrow(() -> new AccountNotFoundException(accountId));

        return account;
    }

    public Double substractFromAccount(Account account, double amount) {
        return account.getBalance() - amount;
    }

    public Double addToAccount(Account account, double amount) {
        return account.getBalance() + amount;
    }
}
