package academy.productstore.web.dto;

import academy.productstore.validation.PasswordMatches;
import academy.productstore.validation.ValidEmail;
import academy.productstore.validation.ValidPassword;
import academy.productstore.validation.ValidPhoneNumber;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@PasswordMatches
public class CreateAccountDTO {

    @NotBlank(message = "Field is mandatory")
    private String firstname;

    @NotBlank(message = "Field is mandatory")
    private String lastname;

    @ValidEmail
    @NotBlank(message = "Field is mandatory")
    private String email;

    @ValidPhoneNumber
    @NotBlank(message = "Field is mandatory")
    private String phone;

    @ValidPassword
    @NotBlank(message = "Field is mandatory")
    private String password;

    @NotBlank(message = "Field is mandatory")
    private String passwordConfirm;
}