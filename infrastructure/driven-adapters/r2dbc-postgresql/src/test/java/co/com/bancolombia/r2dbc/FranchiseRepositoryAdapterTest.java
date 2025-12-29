package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.r2dbc.entities.FranchiseEntity;
import co.com.bancolombia.r2dbc.repositories.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseRepositoryAdapterTest {

    @Mock
    FranchiseRepository franchiseRepository;

    @Mock
    ObjectMapper objectMapper;

    private FranchiseRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new FranchiseRepositoryAdapter(franchiseRepository, objectMapper);
    }

    @Test
    void addNewFranchiseTest() {

        Franchise franchise = Franchise.builder()
                .id(1L)
                .name("Franchise1")
                .description("description")
                .build();

        FranchiseEntity franchiseEntity = FranchiseEntity.builder()
                .id(1L)
                .name("Franchise2")
                .description("description")
                .build();

        when(objectMapper.map(franchise, FranchiseEntity.class))
                .thenReturn(franchiseEntity);

        when(objectMapper.map(franchiseEntity, Franchise.class))
                .thenReturn(franchise);

        when(franchiseRepository.findByName(anyString())).thenReturn(Mono.empty());

        when(franchiseRepository.save(franchiseEntity)).thenReturn(Mono.just(franchiseEntity));

        StepVerifier.create(adapter.addNewFranchise(franchise))
                .expectNext(franchise)
                .verifyComplete();

        verify(franchiseRepository).save(franchiseEntity);
    }
}