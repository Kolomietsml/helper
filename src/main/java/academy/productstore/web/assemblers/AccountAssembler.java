package academy.productstore.web.assemblers;

import academy.productstore.persistence.entity.Account;
import academy.productstore.web.controllers.AccountsController;
import academy.productstore.web.dto.AccountDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
                .add(linkTo(methodOn(AccountsController.class).getAccountInfo(account.getId())).withSelfRel().withType("GET"));
    }
}