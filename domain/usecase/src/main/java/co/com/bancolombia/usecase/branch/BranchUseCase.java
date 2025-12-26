package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase {

    private final BranchGateway branchGateway;

    public Mono<Branch> addBranchToFranchise(Branch branch) {
        return branchGateway.addBranchToFranchise(branch.createdDate());
    }
}
