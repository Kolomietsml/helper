package dns.helper.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class OrderRequest {

    @NotBlank(message = "Field is mandatory")
    private String content;

    @NotBlank(message = "Field is mandatory")
    private String url;
}
