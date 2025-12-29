package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchGateway;
import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.model.messages.ResponseMessages;
import co.com.bancolombia.r2dbc.entities.BranchEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.repositories.BranchRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class BranchRepositoryAdapter extends ReactiveAdapterOperations<Branch, BranchEntity, Long, BranchRepository> implements BranchGateway {

    public BranchRepositoryAdapter(BranchRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Branch.class));
    }

    @Override
    public Mono<Branch> addBranchToFranchise(Branch branch) {
        return repository.findByName(branch.getName())
                .doFirst(() -> log.info("Start to save Branch to Franchise"))
                .switchIfEmpty(save(branch))
                .onErrorMap(error -> {
                    log.error("Error to Add Branch to Franchise", error);
                    return new TechnicalException(
                            ResponseMessages.ERROR_ADD_BRANCH_TO_FRANCHISE.getStatusCode(),
                            ResponseMessages.ERROR_ADD_BRANCH_TO_FRANCHISE.getDescription()
                    );
                });
    }
}
