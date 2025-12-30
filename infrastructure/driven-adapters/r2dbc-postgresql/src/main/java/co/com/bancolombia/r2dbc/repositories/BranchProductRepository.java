package co.com.bancolombia.r2dbc.repositories;

import co.com.bancolombia.r2dbc.entities.BranchProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface BranchProductRepository extends ReactiveCrudRepository<BranchProductEntity, Void>, ReactiveQueryByExampleExecutor<BranchProductEntity> {

    @Query("""
            DELETE FROM branch_product bp WHERE bp.branch_id = :branchId AND bp.product_id = :productId
            """)
    Mono<Void> deleteBranchProduct(Long branchId, Long productId);


    @Query("""
            UPDATE branch_product SET stock = :newStock WHERE branch_id = :branchId AND product_id = :productId
            """)
    Mono<Integer> changeStock(@Param("branchId") Long branchId, @Param("productId") Long productId, @Param("newStock") Integer newStock);
}
