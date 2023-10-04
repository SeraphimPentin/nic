package nicstore.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nicstore.dto.auth.UserResponse;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartContentResponse {

    private UserResponse userResponse;
    private Map<ProductResponse, Integer> items;


}
