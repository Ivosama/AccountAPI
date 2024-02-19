package com.idts.accountapi.customHandler;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super("Could not find account: " + id);
    }
}
