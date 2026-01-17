package it.matteoroxis.grapholizer.repository;

import it.matteoroxis.grapholizer.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUserId(String userId);
}
