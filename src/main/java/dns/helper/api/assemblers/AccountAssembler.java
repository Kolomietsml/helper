package dns.helper.api.assemblers;

import dns.helper.api.dto.response.AccountResponse;
import dns.helper.api.resources.AccountResource;
import dns.helper.db.domain.Account;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountAssembler implements RepresentationModelAssembler<Account, AccountResponse> {

    @Override
    public AccountResponse toModel(Account account) {
        var accountResponse = AccountResponse.builder()
                .email(account.getEmail())
                .build();

        accountResponse.add(WebMvcLinkBuilder.linkTo(methodOn(AccountResource.class)
                .getAccountInfo(account.getId()))
                .withSelfRel()
                .withType("GET"));

        return accountResponse;
    }
}