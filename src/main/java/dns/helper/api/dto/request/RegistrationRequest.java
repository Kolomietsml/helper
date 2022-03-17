package dns.helper.api.dto.request;

import dns.helper.validation.PasswordMatches;
import dns.helper.validation.ValidEmail;
import dns.helper.validation.ValidPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@PasswordMatches
public class RegistrationRequest {

    @ValidEmail
    @NotBlank(message = "Field is mandatory")
    private String email;

    @ValidPassword
    @NotBlank(message = "Field is mandatory")
    private String password;

    @NotBlank(message = "Field is mandatory")
    private String passwordConfirm;
}