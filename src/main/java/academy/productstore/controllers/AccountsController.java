package academy.productstore.controllers;

import academy.productstore.service.AccountService;
import academy.productstore.assemblers.AccountAssembler;
import academy.productstore.dto.AccountDTO;
import academy.productstore.dto.CreateAccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountsController {

    private final AccountService accountService;
    private final AccountAssembler accountAssembler;

    @PostMapping("/registration")
    public ResponseEntity<AccountDTO> registerNewAccount(@Valid CreateAccountDTO accountDTO) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/registration").toUriString());
        var account = accountService.createAccount(accountDTO);
        return ResponseEntity.created(uri).body(accountAssembler.toModel(account));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<AccountDTO> getAccountInfo(@PathVariable long id) {
        var account = accountService.getAccount(id);
        return ResponseEntity.ok(accountAssembler.toModel(account));
    }
}