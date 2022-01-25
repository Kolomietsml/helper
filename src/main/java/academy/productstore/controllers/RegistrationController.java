package academy.productstore.controllers;

import academy.productstore.service.AccountService;
import academy.productstore.dto.CreateAccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    private final AccountService service;

    public RegistrationController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> registerNewAccount(@Valid CreateAccountDTO accountDTO) {
        var account = service.createAccount(accountDTO);
        return ResponseEntity.status(201).build();
    }

    //registrationConfirm
    // ...
}