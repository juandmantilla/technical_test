package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.branchproduct.BranchProduct;
import co.com.bancolombia.model.branchproduct.gateways.BranchProductGateway;
import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.model.messages.ResponseMessages;
import co.com.bancolombia.r2dbc.entities.BranchProductEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repositories.BranchProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class BranchProductRepositoryAdapter extends ReactiveAdapterOperations<BranchProduct, BranchProductEntity, Void, BranchProductRepository> implements BranchProductGateway {

    public BranchProductRepositoryAdapter(BranchProductRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, BranchProduct.class));
    }

    @Override
    public Mono<BranchProduct> saveBranchProduct(BranchProduct branchProduct) {
        return save(branchProduct)
                .doFirst(() -> log.info("Start to save Branch - Product"))
                .onErrorMap(error -> {
                    log.error("Error while trying to Save Branch - Product", error);
                    return new TechnicalException(
                            ResponseMessages.ERROR_SAVE_BRANCH_PRODUCT.getStatusCode(),
                            ResponseMessages.ERROR_SAVE_BRANCH_PRODUCT.getDescription());
                });
    }

    @Override
    public Mono<Void> deleteBranchProduct(BranchProduct branchProduct) {
        return repository.deleteBranchProduct(branchProduct.getBranchId(), branchProduct.getProductId())
                .doFirst(() ->
                        log.info("Start to delete Branch - Product by BranchId {}, ProductId {}",
                                branchProduct.getBranchId(),
                                branchProduct.getProductId()))
                .onErrorMap(error -> {
                    log.error("Error while to delete Branch - Product", error);
                    return new TechnicalException(
                            ResponseMessages.ERROR_DELETE_BRANCH_PRODUCT.getStatusCode(),
                            ResponseMessages.ERROR_DELETE_BRANCH_PRODUCT.getDescription()
                    );
                })
                .doOnSuccess(subs -> log.info("The process of removing the product from the branch has been completed."))
                .then();
    }

    @Override
    public Mono<BranchProduct> changeStock(BranchProduct branchProduct) {
        return repository.changeStock(branchProduct.getBranchId(), branchProduct.getProductId(), branchProduct.getStock())
                .doFirst(() -> log.info("Start to change stock.  New Stock {}", branchProduct.getStock()))
                .thenReturn(branchProduct)
                .onErrorMap(error -> {
                    log.error("Error while trying to change Stock", error);
                    return new TechnicalException(
                            ResponseMessages.ERROR_CHANGE_STOCK.getStatusCode(),
                            ResponseMessages.ERROR_CHANGE_STOCK.getDescription());
                });
    }


}
