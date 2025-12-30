package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.branchproduct.BranchProduct;
import co.com.bancolombia.model.branchproduct.gateways.BranchProductGateway;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductGateway;
import co.com.bancolombia.model.productstockbybranch.ProductStockByBranch;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {

    private final ProductGateway productGateway;
    private final BranchProductGateway branchProductGateway;

    public Mono<Product> addNewProductToBranch(Product product, Long branchId, Integer stock) {
        return productGateway.addNewProductToBranch(product.createdDate())
                .flatMap(saved ->
                        branchProductGateway.saveBranchProduct(BranchProduct.builder()
                                        .productId(saved.getId())
                                        .branchId(branchId)
                                        .stock(stock)
                                        .build())
                                .thenReturn(saved)
                );

    }


    public Flux<ProductStockByBranch> getTopStockProductsByFranchise(Long franchiseId) {
        return productGateway.getTopStockProductsByFranchise(franchiseId);
    }
}
