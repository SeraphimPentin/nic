package nicstore.dto.product;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {

    Long productId;
    Integer value;
}
