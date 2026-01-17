package it.matteoroxis.grapholizer.mutation;

import it.matteoroxis.grapholizer.model.Product;
import it.matteoroxis.grapholizer.model.dto.CreateProduct;
import it.matteoroxis.grapholizer.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductMutationResolverUnitTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductMutationResolver productMutationResolver;

    private CreateProduct createProductInput;
    private Product expectedProduct;

    @BeforeEach
    void setUp() {
        createProductInput = new CreateProduct();
        createProductInput.setName("Laptop");
        createProductInput.setPrice(999.99);

        expectedProduct = new Product("Laptop", 999.99);
        expectedProduct.setId("prod-1");
    }

    @Test
    void createProduct_shouldReturnCreatedProduct() {
        when(productService.createProduct(createProductInput)).thenReturn(expectedProduct);

        Product result = productMutationResolver.createProduct(createProductInput);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("prod-1");
        assertThat(result.getName()).isEqualTo("Laptop");
        assertThat(result.getPrice()).isEqualTo(999.99);
    }

    @Test
    void createProduct_shouldCallProductServiceWithInput() {
        when(productService.createProduct(createProductInput)).thenReturn(expectedProduct);

        productMutationResolver.createProduct(createProductInput);

        verify(productService, times(1)).createProduct(createProductInput);
        verifyNoMoreInteractions(productService);
    }

    @Test
    void createProduct_shouldPassInputCorrectly() {
        when(productService.createProduct(createProductInput)).thenReturn(expectedProduct);

        productMutationResolver.createProduct(createProductInput);

        verify(productService).createProduct(argThat(input ->
                input.getName().equals("Laptop") &&
                        input.getPrice().equals(999.99)
        ));
    }
}