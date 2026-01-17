package it.matteoroxis.grapholizer.mutation;

import it.matteoroxis.grapholizer.model.Order;
import it.matteoroxis.grapholizer.model.dto.CreateOrder;
import it.matteoroxis.grapholizer.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderMutationResolverUnitTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderMutationResolver orderMutationResolver;

    private CreateOrder createOrderInput;
    private Order expectedOrder;

    @BeforeEach
    void setUp() {
        List<String> productIds = Arrays.asList("prod-1", "prod-2");

        createOrderInput = new CreateOrder();
        createOrderInput.setUserId("user-1");
        createOrderInput.setProductIds(productIds);

        expectedOrder = new Order("user-1", productIds, 199.99);
        expectedOrder.setId("order-1");
    }

    @Test
    void createOrder_shouldReturnCreatedOrder() {
        when(orderService.createOrder(createOrderInput)).thenReturn(expectedOrder);

        Order result = orderMutationResolver.createOrder(createOrderInput);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("order-1");
        assertThat(result.getUserId()).isEqualTo("user-1");
        assertThat(result.getTotalAmount()).isEqualTo(199.99);
    }

    @Test
    void createOrder_shouldCallOrderServiceWithInput() {
        when(orderService.createOrder(createOrderInput)).thenReturn(expectedOrder);

        orderMutationResolver.createOrder(createOrderInput);

        verify(orderService, times(1)).createOrder(createOrderInput);
        verifyNoMoreInteractions(orderService);
    }

    @Test
    void createOrder_shouldPassProductIdsCorrectly() {
        when(orderService.createOrder(createOrderInput)).thenReturn(expectedOrder);

        Order result = orderMutationResolver.createOrder(createOrderInput);

        assertThat(result.getProductIds()).containsExactly("prod-1", "prod-2");
        verify(orderService).createOrder(argThat(input ->
                input.getProductIds().size() == 2 &&
                        input.getUserId().equals("user-1")
        ));
    }
}
