package academy.productstore.web.controllers;

import academy.productstore.persistence.entity.Account;
import academy.productstore.service.AccountService;
import academy.productstore.web.assemblers.AccountAssembler;
import academy.productstore.web.dto.response.AccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AccountsController {

    private final AccountService accountService;
    private final AccountAssembler accountAssembler;

    public AccountsController(AccountService accountService, AccountAssembler accountAssembler) {
        this.accountService = accountService;
        this.accountAssembler = accountAssembler;
    }

    @GetMapping()
    public ResponseEntity<AccountDTO> getAccountInfo(@AuthenticationPrincipal Account a) {
        var account = accountService.getAccount(a.getId());
        return ResponseEntity.ok(accountAssembler.toModel(account));
    }
}