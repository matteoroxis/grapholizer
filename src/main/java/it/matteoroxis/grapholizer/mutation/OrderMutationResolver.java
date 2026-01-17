package it.matteoroxis.grapholizer.mutation;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import it.matteoroxis.grapholizer.model.Order;
import it.matteoroxis.grapholizer.model.dto.CreateOrder;
import it.matteoroxis.grapholizer.service.OrderService;

@DgsComponent
public class OrderMutationResolver {

    private final OrderService orderService;

    public OrderMutationResolver(OrderService orderService) {
        this.orderService = orderService;
    }

    @DgsMutation
    public Order createOrder(@InputArgument CreateOrder input) {
        return orderService.createOrder(input);
    }
}
