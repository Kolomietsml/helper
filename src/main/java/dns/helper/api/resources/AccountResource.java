package dns.helper.api.resources;

import dns.helper.api.assemblers.AccountAssembler;
import dns.helper.api.dto.request.RegistrationRequest;
import dns.helper.api.dto.response.AccountResponse;
import dns.helper.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AccountResource {

    private final AccountService accountService;
    private final AccountAssembler accountAssembler;

    @PostMapping("/registration")
    public ResponseEntity<AccountResponse> registerNewAccount(@Valid RegistrationRequest registrationRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/registration").toUriString());
        var account = accountService.createAccount(registrationRequest);
        return ResponseEntity.created(uri).body(accountAssembler.toModel(account));
    }

    @GetMapping("/admin/api/v1/accounts/{id}")
    public ResponseEntity<AccountResponse> getAccountInfo(@PathVariable long id) {
        var account = accountService.getAccount(id);
        return ResponseEntity.ok(accountAssembler.toModel(account));
    }
}