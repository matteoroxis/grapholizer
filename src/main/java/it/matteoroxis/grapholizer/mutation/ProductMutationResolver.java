package it.matteoroxis.grapholizer.mutation;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import it.matteoroxis.grapholizer.model.Product;
import it.matteoroxis.grapholizer.model.dto.CreateProduct;
import it.matteoroxis.grapholizer.service.ProductService;

@DgsComponent
public class ProductMutationResolver {

    private final ProductService productService;

    public ProductMutationResolver(ProductService productService) {
        this.productService = productService;
    }

    @DgsMutation
    public Product createProduct(@InputArgument CreateProduct input) {
        return productService.createProduct(input);
    }
}