package nicstore.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nicstore.dto.auth.UserInfoResponse;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartContentResponse {

    private UserInfoResponse userInfoResponse;
    private Map<ProductResponse, Integer> items;


}
