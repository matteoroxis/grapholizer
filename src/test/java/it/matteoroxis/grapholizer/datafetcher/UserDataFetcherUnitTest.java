package it.matteoroxis.grapholizer.datafetcher;

import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import it.matteoroxis.grapholizer.model.Order;
import it.matteoroxis.grapholizer.model.User;
import it.matteoroxis.grapholizer.service.OrderService;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class UserDataFetcherUnitTest {

    @Mock
    private OrderService orderService;

    @Mock
    private DgsDataFetchingEnvironment dfe;

    @InjectMocks
    private UserDataFetcher userDataFetcher;

    private User user;
    private List<Order> orders;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("user-1");
        user.setEmail("test@example.com");
        user.setName("Mario Rossi");

        Order order1 = new Order("user-1", Arrays.asList("prod-1", "prod-2"), 150.00);
        order1.setId("order-1");

        Order order2 = new Order("user-1", Arrays.asList("prod-3"), 50.00);
        order2.setId("order-2");

        orders = Arrays.asList(order1, order2);
    }

    @Test
    void orders_shouldReturnOrdersForUser() {
        when(dfe.getSource()).thenReturn(user);
        when(orderService.getOrdersByUserId("user-1")).thenReturn(orders);

        List<Order> result = userDataFetcher.orders(dfe);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(orders);
        verify(orderService).getOrdersByUserId("user-1");
    }

    @Test
    void orders_shouldReturnEmptyListWhenUserHasNoOrders() {
        when(dfe.getSource()).thenReturn(user);
        when(orderService.getOrdersByUserId("user-1")).thenReturn(Collections.emptyList());

        List<Order> result = userDataFetcher.orders(dfe);

        assertThat(result).isEmpty();
        verify(orderService).getOrdersByUserId("user-1");
    }

    @Test
    void orders_shouldCallOrderServiceWithCorrectUserId() {
        when(dfe.getSource()).thenReturn(user);
        when(orderService.getOrdersByUserId("user-1")).thenReturn(orders);

        userDataFetcher.orders(dfe);

        verify(orderService, times(1)).getOrdersByUserId("user-1");
        verifyNoMoreInteractions(orderService);
    }
}
