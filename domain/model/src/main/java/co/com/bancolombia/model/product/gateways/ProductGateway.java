package co.com.bancolombia.model.product.gateways;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.productstockbybranch.ProductStockByBranch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductGateway {

    Mono<Product> addNewProductToBranch(Product product);

    Flux<ProductStockByBranch> getTopStockProductsByFranchise(Long franchiseId);

}
