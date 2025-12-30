package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.model.messages.ResponseMessages;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductGateway;
import co.com.bancolombia.model.productstockbybranch.ProductStockByBranch;
import co.com.bancolombia.r2dbc.entities.ProductEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class ProductRepositoryAdapter extends ReactiveAdapterOperations<Product, ProductEntity, Long, ProductRepository> implements ProductGateway {

    public ProductRepositoryAdapter(ProductRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
    }

    @Override
    public Mono<Product> addNewProductToBranch(Product product) {
        return repository.findByName(product.getName())
                .doOnSubscribe(sub -> log.info("Start to add new product to Branch:  {}", product))
                .switchIfEmpty(save(product))
                .onErrorMap(error -> {
                    log.error("Error while trying to save the product", error);
                    return new TechnicalException(
                            ResponseMessages.ERROR_SAVE_PRODUCT.getStatusCode(),
                            ResponseMessages.ERROR_SAVE_PRODUCT.getDescription());
                });
    }

    @Override
    public Flux<ProductStockByBranch> getTopStockProductsByFranchise(Long franchiseId) {
        return repository.findProductsByFranchiseOrderByStockDesc(franchiseId)
                .doOnSubscribe(sub -> log.info("Searching for products with the highest stock levels: {}", franchiseId))
                .onErrorMap(error -> {
                    log.error("Error while trying to get highest stock levels", error);

                    return new TechnicalException(
                            ResponseMessages.ERROR_GET_HIGHEST_STOCK.getStatusCode(),
                            ResponseMessages.ERROR_GET_HIGHEST_STOCK.getDescription()
                    );
                });
    }


}
