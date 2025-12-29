package co.com.bancolombia.r2dbc.repositories;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.r2dbc.entities.FranchiseEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface FranchiseRepository extends ReactiveCrudRepository<FranchiseEntity, Long>, ReactiveQueryByExampleExecutor<FranchiseEntity> {

    Mono<Franchise> findByName(String name);

}
