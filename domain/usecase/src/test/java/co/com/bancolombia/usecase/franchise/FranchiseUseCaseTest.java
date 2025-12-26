package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseUseCaseTest {

    @Mock
    private FranchiseGateway franchiseGateway;

    @InjectMocks
    private FranchiseUseCase franchiseUseCase;

    @Test
    void addNewFranchiseTest() {
        Franchise franchise = Franchise.builder().build();

        when(franchiseGateway.addNewFranchise(any()))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseUseCase.addNewFranchise(franchise))
                .expectNext(franchise)
                .verifyComplete();
    }

}