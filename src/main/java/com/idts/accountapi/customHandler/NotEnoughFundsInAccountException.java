package com.idts.accountapi.customHandler;

import com.idts.accountapi.model.Account;

public class NotEnoughFundsInAccountException extends RuntimeException {

    public NotEnoughFundsInAccountException(Account account) {
        super("The funds available in account " + account.getAccountName() +
                " are not enough for the requested operation.");
    }
}
