package it.matteoroxis.grapholizer.service;

import it.matteoroxis.grapholizer.model.Order;
import it.matteoroxis.grapholizer.model.Product;
import it.matteoroxis.grapholizer.model.dto.CreateOrder;
import it.matteoroxis.grapholizer.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order createOrder(CreateOrder input) {
        List<Product> products = productService.getProductsByIds(input.getProductIds());
        Double totalAmount = products.stream()
                .mapToDouble(Product::getPrice)
                .sum();

        Order order = new Order(input.getUserId(), input.getProductIds(), totalAmount);
        return orderRepository.save(order);
    }
}
