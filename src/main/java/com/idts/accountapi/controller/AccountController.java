package com.idts.accountapi.controller;

import com.idts.accountapi.customHandler.AccountNotFoundException;
import com.idts.accountapi.dao.AccountRepository;
import com.idts.accountapi.dao.UserRepository;
import com.idts.accountapi.model.Account;
import com.idts.accountapi.model.User;
import com.idts.accountapi.model.assembler.AccountModelAssembler;
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

    public AccountController(AccountRepository accountRepository, AccountModelAssembler accountModelAssembler,
                             UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.accountModelAssembler = accountModelAssembler;
        this.userRepository = userRepository;
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
}
