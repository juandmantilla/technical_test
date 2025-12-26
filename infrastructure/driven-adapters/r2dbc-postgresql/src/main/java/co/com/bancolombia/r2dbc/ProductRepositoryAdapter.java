package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.model.messages.ResponseMessages;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductGateway;
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
        return save(product)
                .doOnSubscribe(sub -> log.info("Start to add new product to Branch:  {}", product))
                .onErrorMap(error -> {
                    log.error("Error while trying to save the product", error);
                    return new TechnicalException(
                            ResponseMessages.ERROR_SAVE_PRODUCT.getStatusCode(),
                            ResponseMessages.ERROR_SAVE_PRODUCT.getDescription());
                });
    }

    @Override
    public Mono<Void> deleteProductInBranch(Long id) {
        return repository.deleteById(id)
                .doFirst(() -> log.info("Trying to delete Product in Branch"))
                .onErrorMap(error -> {
                    log.error("Error while delete product in branch", error);
                    return new TechnicalException(
                            ResponseMessages.ERROR_DELETE_PRODUCT.getStatusCode(),
                            ResponseMessages.ERROR_DELETE_PRODUCT.getDescription());
                });
    }

    @Override
    public Mono<Product> changeProductStock(Product product) {
        return findById(product.getId())
                .doOnSubscribe(sub -> log.info("Searching for the product by ID : {}", product.getId()))
                .flatMap(productFound -> {

                    if (productFound.getStock().equals(product.getStock())) {
                        log.info("Stock unchanged for product id {}", product.getId());
                        return Mono.just(productFound);
                    }

                    log.info("Changing stock for product id {} from {} to {}",
                            product.getId(),
                            productFound.getStock(),
                            product.getStock()
                    );

                    return save(
                            productFound.toBuilder()
                                    .stock(product.getStock())
                                    .build()
                    );
                })
                .onErrorMap(error -> {

                    log.error("Error while trying to change Product Stock", error);
                    return new TechnicalException(
                            ResponseMessages.ERROR_CHANGE_STOCK.getStatusCode(),
                            ResponseMessages.ERROR_CHANGE_STOCK.getDescription()
                    );
                });

    }

    @Override
    public Flux<Product> getStockProductByBranch(Long franchiseId) {
        return repository.findProductsByFranchiseOrderByStockDesc(franchiseId)
                .doOnSubscribe(sub -> log.info("Searching for products with the highest stock levels: {}", franchiseId))
                .map(productStock -> Product.builder()
                        .id(productStock.getId())
                        .branchId(productStock.getBranchId())
                        .name(productStock.getName())
                        .price(productStock.getPrice())
                        .stock(productStock.getStock())
                        .createdDate(productStock.getCreatedDate())
                        .build())
                .onErrorMap(error -> {
                    log.error("Error while trying to get highest stock levels", error);

                    return new TechnicalException(
                            ResponseMessages.ERROR_GET_HIGHEST_STOCK.getStatusCode(),
                            ResponseMessages.ERROR_GET_HIGHEST_STOCK.getDescription()
                    );
                })
                ;
    }


}
