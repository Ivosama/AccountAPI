package com.idts.accountapi.controller;

import com.idts.accountapi.customHandler.AccountNotFoundException;
import com.idts.accountapi.customHandler.NotEnoughFundsInAccountException;
import com.idts.accountapi.dao.AccountRepository;
import com.idts.accountapi.dao.UserRepository;
import com.idts.accountapi.model.Account;
import com.idts.accountapi.model.User;
import com.idts.accountapi.model.assembler.AccountModelAssembler;
import com.idts.accountapi.utils.AccountUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountModelAssembler accountModelAssembler;
    private final AccountUtils accountUtils;

    public AccountController(AccountRepository accountRepository, AccountModelAssembler accountModelAssembler,
                             UserRepository userRepository, AccountUtils accountUtils) {
        this.accountRepository = accountRepository;
        this.accountModelAssembler = accountModelAssembler;
        this.userRepository = userRepository;
        this.accountUtils = accountUtils;
    }

    @PostMapping("/newAccount/{username}/{accountName}")
    public ResponseEntity<?> newAccount(@PathVariable String username, @PathVariable String accountName) {
        Optional<User> user = Optional.of(userRepository.findByUsername(username));

        if(user.isPresent()) {
            Account account = new Account();
            account.setUser(user.get());
            account.setAccountName(accountName);

            EntityModel<Account> accountEntityModel = accountModelAssembler.toModel(accountRepository.save(account));
            return ResponseEntity.created(accountEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(accountEntityModel);
        } else {
            return new ResponseEntity<>("The account could not be created because the user was not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getById/{id}")
    public EntityModel<Account> getById(@PathVariable Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        return accountModelAssembler.toModel(account);
    }

    @GetMapping("/getAllAccounts")
    public List<EntityModel<Account>> getAll() {
        return accountRepository.findAll().stream()
                .map(accountModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/getAllAccountsOnUser/{username}")
    public List<EntityModel<Account>> getAllOnUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        return accountRepository.findByUser(user).stream()
                .map(accountModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/transferFunds/{amount}/{fromAccount}/{toAccount}")
    public ResponseEntity<?> transferFunds(@PathVariable Account fromAccount,
                                           @PathVariable Account toAccount,
                                           @PathVariable Double amount) {
        fromAccount = accountUtils.validateAccount(fromAccount);
        toAccount = accountUtils.validateAccount(toAccount);

        if(amount > fromAccount.getBalance()) {
            throw new NotEnoughFundsInAccountException(fromAccount);
        } else {
            accountUtils.substractFromAccount(fromAccount, amount);
            accountRepository.save(fromAccount);
            accountUtils.addToAccount(toAccount, amount);
            accountRepository.save(toAccount);

            //TODO update if I manage to create a TransactionHistory table
            return new ResponseEntity<>("The amount was transferred ", HttpStatus.ACCEPTED);
        }
    }
}
