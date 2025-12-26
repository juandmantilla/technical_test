package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.r2dbc.entities.BranchEntity;
import co.com.bancolombia.r2dbc.repositories.BranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchRepositoryAdapterTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private ObjectMapper objectMapper;

    private BranchRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new BranchRepositoryAdapter(branchRepository, objectMapper);
    }

    @Test
    void addBranchToFranchiseTest() {

        Branch branch = Branch.builder()
                .id(1L)
                .name("branch")
                .franchiseId(10L)
                .build();

        BranchEntity branchEntity = BranchEntity.builder()
                .id(1L)
                .name("branch")
                .franchiseId(10L)
                .build();

        when(objectMapper.map(branch, BranchEntity.class))
                .thenReturn(branchEntity);

        when(objectMapper.map(branchEntity, Branch.class))
                .thenReturn(branch);

        when(branchRepository.save(branchEntity))
                .thenReturn(Mono.just(branchEntity));

        StepVerifier.create(adapter.addBranchToFranchise(branch))
                .expectNext(branch)
                .verifyComplete();

        verify(branchRepository, times(1)).save(branchEntity);
    }
}