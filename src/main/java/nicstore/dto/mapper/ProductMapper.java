package nicstore.dto.mapper;

import lombok.RequiredArgsConstructor;
import nicstore.Models.Product;
import nicstore.dto.product.ProductResponse;
import org.modelmapper.ModelMapper;


@RequiredArgsConstructor
public class ProductMapper {

    private final ModelMapper modelMapper;

    private ProductResponse convertProductToProductResponse(Product product, ProductResponse productResponse) {
        return modelMapper.map(product, ProductResponse.class);
    }
}
