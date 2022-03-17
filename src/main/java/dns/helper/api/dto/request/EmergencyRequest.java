package dns.helper.api.dto.request;

import dns.helper.validation.ValidPhoneNumber;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EmergencyRequest {

    @ValidPhoneNumber
    @NotBlank(message = "Field is mandatory")
    private String phone;

    @NotBlank(message = "Field is mandatory")
    private String title;
}
