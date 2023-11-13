package nicstore.dto.cart;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOrderList {

    String name;
    Integer quantity;
    Double prise;
}
