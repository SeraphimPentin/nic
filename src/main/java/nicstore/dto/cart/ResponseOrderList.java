package nicstore.dto.cart;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOrderList {

    String name;
    Integer quantity;
    Double prise;

    @Override
    public String toString() {
        return  name + " в количестве: " + quantity + " шт. Цена за один товар: " +  prise + " ₽";
    }
}
