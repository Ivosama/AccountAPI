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
    @Override
    public EntityModel<Transaction> toModel(Transaction transaction) {
        return EntityModel.of(transaction,
                linkTo(methodOn(AccountController.class).getById(transaction.getId())).withSelfRel());
    }
}
