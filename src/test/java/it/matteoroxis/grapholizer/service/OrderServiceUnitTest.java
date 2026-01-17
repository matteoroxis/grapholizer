package it.matteoroxis.grapholizer.service;

import it.matteoroxis.grapholizer.model.Order;
import it.matteoroxis.grapholizer.model.Product;
import it.matteoroxis.grapholizer.model.dto.CreateOrder;
import it.matteoroxis.grapholizer.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceUnitTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    private CreateOrder createOrderInput;
    private List<Product> products;
    private Order savedOrder;

    @BeforeEach
    void setUp() {
        createOrderInput = new CreateOrder();
        createOrderInput.setUserId("user-1");
        createOrderInput.setProductIds(Arrays.asList("prod-1", "prod-2"));

        Product product1 = new Product("Laptop", 999.99);
        product1.setId("prod-1");
        Product product2 = new Product("Mouse", 29.99);
        product2.setId("prod-2");
        products = Arrays.asList(product1, product2);

        savedOrder = new Order("user-1", Arrays.asList("prod-1", "prod-2"), 1029.98);
        savedOrder.setId("order-1");
    }

    @Test
    void getOrdersByUserId_shouldReturnOrdersList() {
        List<Order> expectedOrders = Arrays.asList(savedOrder);
        when(orderRepository.findByUserId("user-1")).thenReturn(expectedOrders);

        List<Order> result = orderService.getOrdersByUserId("user-1");

        assertThat(result).hasSize(1);
        assertThat(result).containsExactlyElementsOf(expectedOrders);
        verify(orderRepository, times(1)).findByUserId("user-1");
    }

    @Test
    void getOrdersByUserId_shouldReturnEmptyListWhenNoOrders() {
        when(orderRepository.findByUserId("user-1")).thenReturn(Collections.emptyList());

        List<Order> result = orderService.getOrdersByUserId("user-1");

        assertThat(result).isEmpty();
        verify(orderRepository).findByUserId("user-1");
    }

    @Test
    void createOrder_shouldCalculateTotalAmountAndSaveOrder() {
        when(productService.getProductsByIds(createOrderInput.getProductIds())).thenReturn(products);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        Order result = orderService.createOrder(createOrderInput);

        assertThat(result).isNotNull();
        assertThat(result.getTotalAmount()).isEqualTo(1029.98);
        assertThat(result.getUserId()).isEqualTo("user-1");
        assertThat(result.getProductIds()).containsExactly("prod-1", "prod-2");

        verify(productService).getProductsByIds(createOrderInput.getProductIds());
        verify(orderRepository).save(argThat(order ->
                order.getUserId().equals("user-1") &&
                        order.getTotalAmount().equals(1029.98) &&
                        order.getProductIds().size() == 2
        ));
    }

    @Test
    void createOrder_shouldHandleEmptyProductList() {
        createOrderInput.setProductIds(Collections.emptyList());
        when(productService.getProductsByIds(Collections.emptyList())).thenReturn(Collections.emptyList());

        Order emptyOrder = new Order("user-1", Collections.emptyList(), 0.0);
        when(orderRepository.save(any(Order.class))).thenReturn(emptyOrder);

        Order result = orderService.createOrder(createOrderInput);

        assertThat(result.getTotalAmount()).isEqualTo(0.0);
        assertThat(result.getProductIds()).isEmpty();
        verify(orderRepository).save(argThat(order ->
                order.getTotalAmount().equals(0.0)
        ));
    }

    @Test
    void createOrder_shouldCallRepositoryWithCorrectOrder() {
        when(productService.getProductsByIds(createOrderInput.getProductIds())).thenReturn(products);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        orderService.createOrder(createOrderInput);

        verify(orderRepository, times(1)).save(any(Order.class));
        verifyNoMoreInteractions(orderRepository);
    }
}
