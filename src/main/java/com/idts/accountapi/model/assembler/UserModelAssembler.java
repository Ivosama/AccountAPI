package com.idts.accountapi.model.assembler;

import com.idts.accountapi.model.User;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@NonNullApi
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {
    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(user);
    }
}
