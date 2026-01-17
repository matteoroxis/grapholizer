package it.matteoroxis.grapholizer.repository;

import it.matteoroxis.grapholizer.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByIdIn(List<String> ids);
}