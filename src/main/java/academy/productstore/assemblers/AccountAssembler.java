package academy.productstore.assemblers;

import academy.productstore.api.AccountResource;
import academy.productstore.domain.Account;
import academy.productstore.dto.response.AccountResponse;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountAssembler implements RepresentationModelAssembler<Account, AccountResponse> {

    @Override
    public AccountResponse toModel(Account account) {
        var accountResponse = AccountResponse.builder()
                .firstname(account.getFirstname())
                .lastname(account.getLastname())
                .email(account.getEmail())
                .phone(account.getPhone())
                .build();

        accountResponse.add(linkTo(methodOn(AccountResource.class)
                .getAccountInfo(account.getId()))
                .withSelfRel()
                .withType("GET"));

        return accountResponse;
    }
}