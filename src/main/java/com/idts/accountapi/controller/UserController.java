package com.idts.accountapi.controller;

import com.idts.accountapi.dao.UserRepository;
import com.idts.accountapi.model.Account;
import com.idts.accountapi.model.User;
import com.idts.accountapi.model.assembler.UserModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserModelAssembler userModelAssembler;

    public UserController(UserRepository userRepository, UserModelAssembler userModelAssembler) {
        this.userRepository = userRepository;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping("getAll")
    public CollectionModel<EntityModel<User>> getAll() {
        List<EntityModel<User>> users = userRepository.findAll().stream()
                .map(userModelAssembler::toModel)
                .toList();

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<Account>> getUserAccounts(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null
                ? ResponseEntity.ok(user.getAccounts())
                : ResponseEntity.notFound().build();
    }


}
