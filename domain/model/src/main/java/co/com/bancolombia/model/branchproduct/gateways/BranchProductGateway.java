package co.com.bancolombia.model.branchproduct.gateways;

import co.com.bancolombia.model.branchproduct.BranchProduct;
import reactor.core.publisher.Mono;

public interface BranchProductGateway {

    Mono<BranchProduct> saveBranchProduct(BranchProduct branchProduct);

    Mono<Void> deleteBranchProduct(BranchProduct branchProduct);

    Mono<BranchProduct> changeStock(BranchProduct branchProduct);
}
