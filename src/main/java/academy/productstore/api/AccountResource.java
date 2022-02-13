package academy.productstore.api;

import academy.productstore.assemblers.AccountAssembler;
import academy.productstore.dto.request.RegistrationRequest;
import academy.productstore.dto.response.AccountResponse;
import academy.productstore.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class AccountResource {

    private final AccountService accountService;
    private final AccountAssembler accountAssembler;

    @PostMapping("/registration")
    public ResponseEntity<AccountResponse> registerNewAccount(@Valid RegistrationRequest registrationRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/registration").toUriString());
        var account = accountService.createAccount(registrationRequest);
        return ResponseEntity.created(uri).body(accountAssembler.toModel(account));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<AccountResponse> getAccountInfo(@PathVariable long id) {
        var account = accountService.getAccount(id);
        return ResponseEntity.ok(accountAssembler.toModel(account));
    }
}
