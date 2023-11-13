package nicstore.dto.cart;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTotalOrder {

    List<ResponseOrderList> orderLists;
    Double totalCost;
}
