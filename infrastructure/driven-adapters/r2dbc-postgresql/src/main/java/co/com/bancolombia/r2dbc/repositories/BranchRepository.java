package co.com.bancolombia.r2dbc.repositories;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.r2dbc.entities.BranchEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface BranchRepository extends ReactiveCrudRepository<BranchEntity, Long>, ReactiveQueryByExampleExecutor<BranchEntity> {

    Mono<Branch> findByName(String name);
}
