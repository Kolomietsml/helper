package academy.productstore.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class CreateOrderDTO {

    private List<ItemDTO> items;
    private BigDecimal amount;

    @NotBlank(message = "Field is mandatory")
    private String firstname;

    @NotBlank(message = "Field is mandatory")
    private String lastname;

    @NotBlank(message = "Field is mandatory")
    private String phone;

    @NotBlank(message = "Field is mandatory")
    private String street;

    @NotBlank(message = "Field is mandatory")
    private String build;

    private String apartment;
}