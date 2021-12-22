package academy.productstore.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryDetailsDTO {

    private String firstname;
    private String lastname;
    private String phone;
    private String street;
    private String build;
    private String apartment;
}
