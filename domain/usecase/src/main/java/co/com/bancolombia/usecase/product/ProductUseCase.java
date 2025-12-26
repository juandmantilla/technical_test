package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {

    private final ProductGateway productGateway;

    public Mono<Product> addNewProductToBranch(Product product) {
        return productGateway.addNewProductToBranch(product.createdDate());
    }

    public Mono<Void> deleteProductInBranch(Product product) {
        return productGateway.deleteProductInBranch(product.getId());
    }

    public Mono<Product> changeProductStock(Product product) {
        return productGateway.changeProductStock(product.validateNewStock(product.getStock()));
    }

    public Flux<Product> getStockProductByBranch(Long franchiseId) {
        return productGateway.getStockProductByBranch(franchiseId);
    }
}
