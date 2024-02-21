package com.idts.accountapi.model.assembler;

import com.idts.accountapi.controller.AccountController;
import com.idts.accountapi.model.Transaction;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@NonNullApi
public class TransactionModelAssembler implements RepresentationModelAssembler<Transaction, EntityModel<Transaction>> {

    private static final String ACCOUNTS_RELATION = "accounts";

    @Override
    public EntityModel<Transaction> toModel(Transaction transaction) {
        return EntityModel.of(transaction,
                linkTo(methodOn(AccountController.class).getById(transaction.getId())).withSelfRel(),
                linkTo(methodOn(AccountController.class).getById(transaction.getToAccount().getId())).withRel(ACCOUNTS_RELATION));
    }

    public EntityModel<Transaction> toModelForTransferOfFunds(Transaction transaction) {
        return EntityModel.of(transaction,
                linkTo(methodOn(AccountController.class).getById(transaction.getId())).withSelfRel(),
                linkTo(methodOn(AccountController.class).getById(transaction.getFromAccount().getId())).withRel(ACCOUNTS_RELATION),
                linkTo(methodOn(AccountController.class).getById(transaction.getToAccount().getId())).withRel(ACCOUNTS_RELATION));
    }
}
