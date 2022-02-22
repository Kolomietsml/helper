package academy.productstore.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequest {

    private long productId;

    private int quantity;
}
