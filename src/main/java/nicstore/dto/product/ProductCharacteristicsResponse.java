package nicstore.dto.product;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductCharacteristicsResponse {

    private String name;
    private String description;
    private List<ImageResponse> images;
    private Integer quantity;
    private BigDecimal price;
    private Integer numberRatings;
    private Double averageRating;
    private Integer numberReviews;
    private List<ReviewResponse> reviews;
}

