package academy.productstore.web.controllers;

import academy.productstore.service.AccountService;
import academy.productstore.web.assemblers.AccountAssembler;
import academy.productstore.web.dto.AccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AccountsController {

    private final AccountService accountService;
    private final AccountAssembler accountAssembler;

    public AccountsController(AccountService accountService,
                              AccountAssembler accountAssembler) {
        this.accountService = accountService;
        this.accountAssembler = accountAssembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountInfo(@PathVariable long id) {
        var account = accountService.getAccount(id);
        return ResponseEntity.ok(accountAssembler.toModel(account));
    }
}