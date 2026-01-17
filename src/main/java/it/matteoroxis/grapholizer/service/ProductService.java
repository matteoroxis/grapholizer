package it.matteoroxis.grapholizer.service;

import it.matteoroxis.grapholizer.model.Product;
import it.matteoroxis.grapholizer.model.dto.CreateProduct;
import it.matteoroxis.grapholizer.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getProductsByIds(List<String> productIds) {
        return productRepository.findByIdIn(productIds);
    }

    public Product createProduct(CreateProduct input) {
        Product product = new Product(input.getName(), input.getPrice());
        return productRepository.save(product);
    }
}
