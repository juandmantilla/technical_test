package co.com.bancolombia.r2dbc.repositories;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.r2dbc.entities.ProductEntity;
import co.com.bancolombia.r2dbc.views.ProductStockView;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Long>, ReactiveQueryByExampleExecutor<ProductEntity> {

    @Query("""
                SELECT
                p.id AS "id",
                p.branch_id,
                p.name AS "name",
                p.price AS "price",
                p.stock AS "stock",
                p.created_date
                FROM product p
                JOIN branch b ON b.id = p.branch_id
                WHERE b.franchise_id = :franchiseId
                ORDER BY p.stock DESC
            """)
    Flux<ProductStockView> findProductsByFranchiseOrderByStockDesc(Long franchiseId);


    Mono<Product> findByName(String name);

}
