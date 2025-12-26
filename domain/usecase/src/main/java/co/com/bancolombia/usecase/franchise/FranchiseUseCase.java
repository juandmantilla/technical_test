package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class FranchiseUseCase {

    private final FranchiseGateway franchiseGateway;

    public Mono<Franchise> addNewFranchise(Franchise franchise) {
        return franchiseGateway.addNewFranchise(franchise.createdDate());
    }

}
