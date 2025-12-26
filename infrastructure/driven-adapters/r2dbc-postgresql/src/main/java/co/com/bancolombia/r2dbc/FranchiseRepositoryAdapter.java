package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseGateway;
import co.com.bancolombia.model.messages.ResponseMessages;
import co.com.bancolombia.r2dbc.entities.FranchiseEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repositories.FranchiseRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class FranchiseRepositoryAdapter extends ReactiveAdapterOperations<Franchise, FranchiseEntity, Long, FranchiseRepository> implements FranchiseGateway {

    public FranchiseRepositoryAdapter(FranchiseRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Franchise.class));
    }

    @Override
    public Mono<Franchise> addNewFranchise(Franchise franchise) {

        return save(franchise)
                .doFirst(() -> log.info("Start add New Franchise"))
                .onErrorMap(error -> {
                    log.error("Error while trying to Add New Franchise");
                    return new TechnicalException(
                            ResponseMessages.ERROR_SAVE_FRANCHISE.getStatusCode(),
                            ResponseMessages.ERROR_SAVE_FRANCHISE.getDescription());
                });
    }

}
