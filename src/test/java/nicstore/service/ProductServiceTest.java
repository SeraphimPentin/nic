 package nicstore.service;

import nicstore.Models.Category;
import nicstore.Models.Product;
import nicstore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testFindProductById() {
        Long existingProductId = 1L;
        Long noExistingProductId = 2L;
        Product product = new Product();
        when(productRepository.findById(existingProductId)).thenReturn(Optional.of(product));
        Product result = productService.findProductById(existingProductId);
        assertEquals(product, result);
        verify(productRepository).findById(existingProductId);

        when(productRepository.findById(noExistingProductId)).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> productService.findProductById(noExistingProductId));
        verify(productRepository).findById(noExistingProductId);
    }

    @Test
    void testFindProductsByCategory() {
        Category category = new Category();
        category.setId(1L);
        List<Product> products = new ArrayList<>();
        when(productRepository.findProductsByCategoryId(category.getId())).thenReturn(products);
        List<Product> expected = productService.findProductsByCategory(category);
        assertEquals(products, expected);
        verify(productRepository).findProductsByCategoryId(category.getId());
    }
}