package academy.productstore.web.assemblers;

import academy.productstore.persistence.entity.Account;
import academy.productstore.web.controllers.AccountsController;
import academy.productstore.web.dto.response.AccountDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class AccountAssembler implements RepresentationModelAssembler<Account, AccountDTO> {

    @Override
    public AccountDTO toModel(Account account) {
        return AccountDTO.builder()
                .firstname(account.getFirstname())
                .lastname(account.getLastname())
                .email(account.getEmail())
                .phone(account.getPhone())
                .build()
                .add(linkTo(AccountsController.class).withSelfRel().withType("GET"));
    }
}