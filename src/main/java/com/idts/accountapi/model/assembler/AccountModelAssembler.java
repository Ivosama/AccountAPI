package com.idts.accountapi.model.assembler;

import com.idts.accountapi.controller.AccountController;
import com.idts.accountapi.model.Account;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@NonNullApi
public class AccountModelAssembler  implements RepresentationModelAssembler<Account, EntityModel<Account>> {

    private static final String ACCOUNT_REL = "accountsOnUser";
    @Override
    public EntityModel<Account> toModel(Account account) {
        return EntityModel.of(account,
                linkTo(methodOn(AccountController.class).getById(account.getId())).withSelfRel(),
                linkTo(methodOn(AccountController.class).getAllOnUser(account.getUser().getUsername())).withRel(ACCOUNT_REL));
    }
}
