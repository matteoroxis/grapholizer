package it.matteoroxis.grapholizer.service;

import it.matteoroxis.grapholizer.model.Product;
import it.matteoroxis.grapholizer.model.dto.CreateProduct;
import it.matteoroxis.grapholizer.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceUnitTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product1;
    private Product product2;
    private CreateProduct createProductInput;

    @BeforeEach
    void setUp() {
        product1 = new Product("Laptop", 999.99);
        product1.setId("prod-1");

        product2 = new Product("Mouse", 29.99);
        product2.setId("prod-2");

        createProductInput = new CreateProduct();
        createProductInput.setName("Keyboard");
        createProductInput.setPrice(79.99);
    }

    @Test
    void getAllProducts_shouldReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> result = productService.getAllProducts();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(Arrays.asList(product1, product2));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getAllProducts_shouldReturnEmptyListWhenNoProducts() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<Product> result = productService.getAllProducts();

        assertThat(result).isEmpty();
        verify(productRepository).findAll();
    }

    @Test
    void getProductById_shouldReturnProductWhenFound() {
        when(productRepository.findById("prod-1")).thenReturn(Optional.of(product1));

        Product result = productService.getProductById("prod-1");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("prod-1");
        assertThat(result.getName()).isEqualTo("Laptop");
        assertThat(result.getPrice()).isEqualTo(999.99);
        verify(productRepository).findById("prod-1");
    }

    @Test
    void getProductById_shouldReturnNullWhenNotFound() {
        when(productRepository.findById("unknown-id")).thenReturn(Optional.empty());

        Product result = productService.getProductById("unknown-id");

        assertThat(result).isNull();
        verify(productRepository).findById("unknown-id");
    }

    @Test
    void getProductsByIds_shouldReturnMatchingProducts() {
        List<String> productIds = Arrays.asList("prod-1", "prod-2");
        when(productRepository.findByIdIn(productIds)).thenReturn(Arrays.asList(product1, product2));

        List<Product> result = productService.getProductsByIds(productIds);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(Arrays.asList(product1, product2));
        verify(productRepository).findByIdIn(productIds);
    }

    @Test
    void getProductsByIds_shouldReturnEmptyListWhenNoMatch() {
        List<String> productIds = Arrays.asList("unknown-1", "unknown-2");
        when(productRepository.findByIdIn(productIds)).thenReturn(Collections.emptyList());

        List<Product> result = productService.getProductsByIds(productIds);

        assertThat(result).isEmpty();
        verify(productRepository).findByIdIn(productIds);
    }

    @Test
    void createProduct_shouldCreateAndReturnProduct() {
        Product savedProduct = new Product("Keyboard", 79.99);
        savedProduct.setId("prod-3");
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = productService.createProduct(createProductInput);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("prod-3");
        assertThat(result.getName()).isEqualTo("Keyboard");
        assertThat(result.getPrice()).isEqualTo(79.99);

        verify(productRepository).save(argThat(product ->
                product.getName().equals("Keyboard") &&
                        product.getPrice().equals(79.99)
        ));
    }

    @Test
    void createProduct_shouldCallRepositoryWithCorrectInput() {
        Product savedProduct = new Product("Keyboard", 79.99);
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        productService.createProduct(createProductInput);

        verify(productRepository, times(1)).save(any(Product.class));
        verifyNoMoreInteractions(productRepository);
    }
}