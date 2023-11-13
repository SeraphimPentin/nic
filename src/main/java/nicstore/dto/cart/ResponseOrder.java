package nicstore.dto.cart;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOrder {

    String email;
    String answer;
}
