package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BranchUseCaseTest {

    @Mock
    BranchGateway branchGateway;

    @InjectMocks
    BranchUseCase useCase;

    Branch branch;

    @Test
    void addBranchToFranchiseTest() {
        var now = LocalDateTime.now();

        branch = Branch.builder()
                .id(1L)
                .franchiseId(1L)
                .name("name")
                .address("address")
                .phone("123123")
                .createdDate(now)
                .build();

        doReturn(Mono.just(branch)).when(branchGateway).addBranchToFranchise(any(Branch.class));

        StepVerifier.create(useCase.addBranchToFranchise(branch))
                .assertNext(next -> {
                    assertNotNull(next);
                    assertEquals(branch.getId(), next.getId());
                    assertEquals(branch.getFranchiseId(), next.getFranchiseId());
                    assertEquals(branch.getName(), next.getName());
                    assertEquals(branch.getAddress(), next.getAddress());
                    assertEquals(branch.getPhone(), next.getPhone());
                    assertEquals(branch.getCreatedDate(), next.getCreatedDate());
                })
                .verifyComplete();
    }

}