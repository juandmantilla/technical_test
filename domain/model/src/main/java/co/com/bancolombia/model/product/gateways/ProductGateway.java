package co.com.bancolombia.model.product.gateways;

import co.com.bancolombia.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductGateway {

    Mono<Product> addNewProductToBranch(Product product);

    Mono<Void> deleteProductInBranch(Long id);

    Mono<Product> changeProductStock(Product product);

    Flux<Product> getStockProductByBranch(Long franchiseId);

}
