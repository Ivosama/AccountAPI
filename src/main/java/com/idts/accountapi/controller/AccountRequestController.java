//package com.idts.accountapi.controller;
//
//import com.idts.accountapi.customHandler.AccountNotFoundException;
//import com.idts.accountapi.dao.AccountRepository;
//import com.idts.accountapi.dao.UserRepository;
//import com.idts.accountapi.model.Account;
//import com.idts.accountapi.model.AccountRequest;
//import com.idts.accountapi.model.User;
//import com.idts.accountapi.model.assembler.AccountModelAssembler;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/accountRequest")
//public class AccountRequestController {
//    private final AccountRepository accountRepository;
//    private final UserRepository userRepository;
//    private final AccountModelAssembler accountModelAssembler;
//
//    public AccountRequestController(AccountRepository accountRepository, AccountModelAssembler accountModelAssembler, UserRepository userRepository) {
//        this.accountRepository = accountRepository;
//        this.accountModelAssembler = accountModelAssembler;
//        this.userRepository = userRepository;
//    }
//
//    @PostMapping("/newAccount")
//    public ResponseEntity<?> newAccount(@RequestBody AccountRequest accountRequest) {
//        String username = accountRequest.getUsername();
//        Optional<User> user = Optional.of(userRepository.getByUsername(username));
//
//        if(user.isPresent()) {
//            Account account = new Account(user.get());
//
////            EntityModel<Account> accountEntityModel = accountModelAssembler.toModel(accountRepository.save(account));
//            return new ResponseEntity<>("Account created and associated to user", HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
//        }
//    }
//}
