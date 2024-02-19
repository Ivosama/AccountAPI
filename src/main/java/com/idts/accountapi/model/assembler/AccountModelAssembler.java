package com.idts.accountapi.model.assembler;

import com.idts.accountapi.model.Account;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@NonNullApi
public class AccountModelAssembler  implements RepresentationModelAssembler<Account, EntityModel<Account>> {

    @Override
    public EntityModel<Account> toModel(Account account) {
        return EntityModel.of(account);
    }

    @Override
    public CollectionModel<EntityModel<Account>> toCollectionModel(Iterable<? extends Account> accounts) {
        return RepresentationModelAssembler.super.toCollectionModel(accounts);
    }
}
