package co.com.bancolombia.usecase.branchproduct;

import co.com.bancolombia.model.branchproduct.BranchProduct;
import co.com.bancolombia.model.branchproduct.gateways.BranchProductGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchProductUseCase {

    private final BranchProductGateway branchProductGateway;
    
    public Mono<Void> deleteBranchProduct(BranchProduct branchProduct) {
        return branchProductGateway.deleteBranchProduct(branchProduct);
    }

    public Mono<BranchProduct> changeStock(BranchProduct branchProduct) {
        return branchProductGateway.changeStock(branchProduct.validateNewStock());
    }
}
