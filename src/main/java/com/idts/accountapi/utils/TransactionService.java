package com.idts.accountapi.utils;

import com.idts.accountapi.dao.AccountRepository;
import com.idts.accountapi.dao.TransactionRepository;
import com.idts.accountapi.model.Account;
import com.idts.accountapi.model.Transaction;
import com.idts.accountapi.model.assembler.TransactionModelAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionModelAssembler transactionModelAssembler;
    private final AccountRepository accountRepository;
    private final AccountUtils accountUtils;

    public TransactionService(TransactionRepository transactionRepository,
                              TransactionModelAssembler transactionModelAssembler,
                              AccountRepository accountRepository,
                              AccountUtils accountUtils) {
        this.transactionRepository = transactionRepository;
        this.transactionModelAssembler = transactionModelAssembler;
        this.accountRepository = accountRepository;
        this.accountUtils = accountUtils;
    }

    public EntityModel<Transaction> createTransaction(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        Account fromAccount = accountUtils.validateAccountById(fromAccountId);
        Account toAccount = accountUtils.validateAccountById(toAccountId);

        BigDecimal newBalanceFrom = accountUtils.substractFromAccount(fromAccount, amount);
        accountRepository.updateBalance(fromAccountId, newBalanceFrom);
        fromAccount = accountRepository.save(fromAccount);

        BigDecimal newBalanceTo = accountUtils.addToAccount(toAccount, amount);
        accountRepository.updateBalance(toAccountId, newBalanceTo);
        toAccount = accountRepository.save(toAccount);

        Transaction transaction = new Transaction(fromAccount, toAccount, amount);
        transactionRepository.save(transaction);
        return transactionModelAssembler.toModelForTransferOfFunds(transaction);
    }

    @Transactional
    public EntityModel<Transaction> createTransaction(Long toAccountId, BigDecimal amount) {
        Account toAccount = accountUtils.validateAccountById(toAccountId);

        BigDecimal newBalance = accountUtils.addToAccount(toAccount, amount);
        accountRepository.updateBalance(toAccountId, newBalance);
        toAccount = accountRepository.save(toAccount);

        Transaction transaction = new Transaction(toAccount, amount);
        transactionRepository.save(transaction);
        return transactionModelAssembler.toModel(transaction);
    }
}
