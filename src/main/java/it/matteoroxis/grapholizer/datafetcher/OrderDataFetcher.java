package it.matteoroxis.grapholizer.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import it.matteoroxis.grapholizer.model.Order;
import it.matteoroxis.grapholizer.model.Product;
import it.matteoroxis.grapholizer.service.ProductService;

import java.util.List;

@DgsComponent
public class OrderDataFetcher {

    private final ProductService productService;

    public OrderDataFetcher(ProductService productService) {
        this.productService = productService;
    }

    @DgsData(parentType = "Order", field = "products")
    public List<Product> products(DgsDataFetchingEnvironment dfe) {
        Order order = dfe.getSource();
        return productService.getProductsByIds(order.getProductIds());
    }
}