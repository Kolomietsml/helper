package dns.helper.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LinkRequest {

    @NotBlank(message = "Field is mandatory")
    private String url;

    @NotBlank(message = "Field is mandatory")
    private String title;

    @NotBlank(message = "Field is mandatory")
    private String description;

    @NotBlank(message = "Field is mandatory")
    private String command;
}
