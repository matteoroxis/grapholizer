package it.matteoroxis.grapholizer.datafetcher;


import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import it.matteoroxis.grapholizer.model.Order;
import it.matteoroxis.grapholizer.model.Product;
import it.matteoroxis.grapholizer.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderDataFetcherUnitTest {

    @Mock
    private ProductService productService;

    @Mock
    private DgsDataFetchingEnvironment dfe;

    @InjectMocks
    private OrderDataFetcher orderDataFetcher;

    private Order order;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId("order-1");
        order.setUserId("user-1");
        order.setProductIds(Arrays.asList("prod-1", "prod-2"));

        Product product1 = new Product("Laptop", 999.99);
        product1.setId("prod-1");

        Product product2 = new Product("Mouse", 29.99);
        product2.setId("prod-2");

        products = Arrays.asList(product1, product2);
    }

    @Test
    void products_shouldReturnProductsForOrder() {
        when(dfe.getSource()).thenReturn(order);
        when(productService.getProductsByIds(order.getProductIds())).thenReturn(products);

        List<Product> result = orderDataFetcher.products(dfe);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(products);
        verify(productService).getProductsByIds(order.getProductIds());
    }

    @Test
    void products_shouldReturnEmptyListWhenNoProducts() {
        order.setProductIds(Collections.emptyList());
        when(dfe.getSource()).thenReturn(order);
        when(productService.getProductsByIds(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<Product> result = orderDataFetcher.products(dfe);

        assertThat(result).isEmpty();
        verify(productService).getProductsByIds(Collections.emptyList());
    }

    @Test
    void products_shouldCallProductServiceWithCorrectIds() {
        List<String> productIds = Arrays.asList("prod-1", "prod-2", "prod-3");
        order.setProductIds(productIds);
        when(dfe.getSource()).thenReturn(order);
        when(productService.getProductsByIds(productIds)).thenReturn(products);

        orderDataFetcher.products(dfe);

        verify(productService, times(1)).getProductsByIds(productIds);
        verifyNoMoreInteractions(productService);
    }
}