package it.matteoroxis.grapholizer.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import it.matteoroxis.grapholizer.model.Order;
import it.matteoroxis.grapholizer.model.User;
import it.matteoroxis.grapholizer.service.OrderService;

import java.util.List;

@DgsComponent
public class UserDataFetcher {

    private final OrderService orderService;

    public UserDataFetcher(OrderService orderService) {
        this.orderService = orderService;
    }

    @DgsData(parentType = "User", field = "orders")
    public List<Order> orders(DgsDataFetchingEnvironment dfe) {
        User user = dfe.getSource();
        return orderService.getOrdersByUserId(user.getId());
    }
}
