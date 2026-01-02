package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.branchproduct.BranchProduct;
import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.r2dbc.entities.BranchProductEntity;
import co.com.bancolombia.r2dbc.repositories.BranchProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchProductRepositoryAdapterTest {


    @Mock
    private BranchProductRepository repository;

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private BranchProductRepositoryAdapter adapter;

    private BranchProduct branchProduct;
    private BranchProductEntity entity;

    @BeforeEach
    void setUp() {
        branchProduct = BranchProduct.builder()
                .branchId(1L)
                .productId(2L)
                .stock(10)
                .build();

        entity = BranchProductEntity.builder()
                .branchId(1L)
                .productId(2L)
                .stock(10)
                .build();
    }

    @Test
    void saveBranchProductTest() {
        when(mapper.map(branchProduct, BranchProductEntity.class)).thenReturn(entity);
        when(repository.save(any(BranchProductEntity.class))).thenReturn(Mono.just(entity));
        when(mapper.map(entity, BranchProduct.class)).thenReturn(branchProduct);

        StepVerifier.create(adapter.saveBranchProduct(branchProduct))
                .expectNext(branchProduct)
                .verifyComplete();
    }

    @Test
    void saveBranchProductErrorTest() {
        when(mapper.map(branchProduct, BranchProductEntity.class)).thenReturn(entity);
        when(repository.save(any(BranchProductEntity.class)))
                .thenReturn(Mono.error(new RuntimeException("DB error")));

        StepVerifier.create(adapter.saveBranchProduct(branchProduct))
                .expectErrorMatches(TechnicalException.class::isInstance)
                .verify();
    }


    @Test
    void deleteBranchProductTest() {
        when(repository.deleteBranchProduct(1L, 2L)).thenReturn(Mono.empty());

        StepVerifier.create(adapter.deleteBranchProduct(branchProduct))
                .verifyComplete();
    }

    @Test
    void deleteBranchProductErrorTest() {
        when(repository.deleteBranchProduct(anyLong(), anyLong()))
                .thenReturn(Mono.error(new RuntimeException("DB error")));

        StepVerifier.create(adapter.deleteBranchProduct(branchProduct))
                .expectErrorMatches(TechnicalException.class::isInstance)
                .verify();
    }

    @Test
    void changeStockTest() {
        when(repository.changeStock(1L, 2L, 10))
                .thenReturn(Mono.just(1));

        StepVerifier.create(adapter.changeStock(branchProduct))
                .expectNext(branchProduct)
                .verifyComplete();
    }

    @Test
    void changeStockErrorTest() {
        when(repository.changeStock(anyLong(), anyLong(), any(Integer.class)))
                .thenReturn(Mono.error(new RuntimeException("DB error")));

        StepVerifier.create(adapter.changeStock(branchProduct))
                .expectErrorMatches(TechnicalException.class::isInstance)
                .verify();
    }
}