package co.com.bancolombia.r2dbc.repositories;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.productstockbybranch.ProductStockByBranch;
import co.com.bancolombia.r2dbc.entities.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Long>, ReactiveQueryByExampleExecutor<ProductEntity> {

    @Query("""
                SELECT DISTINCT ON (b.id)
                    b.id        AS branch_id,
                    b.name      AS branch_name,
                    p.id        AS product_id,
                    p.name      AS product_name,
                    bp.stock
                FROM branch b
                JOIN branch_product bp ON bp.branch_id = b.id
                JOIN product p ON p.id = bp.product_id
                WHERE b.franchise_id = :franchiseId
                ORDER BY b.id, bp.stock DESC
            """)
    Flux<ProductStockByBranch> findProductsByFranchiseOrderByStockDesc(Long franchiseId);


    Mono<Product> findByName(String name);

}
