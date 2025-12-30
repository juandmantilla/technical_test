package co.com.bancolombia.usecase.branchproduct;

import co.com.bancolombia.model.branchproduct.BranchProduct;
import co.com.bancolombia.model.branchproduct.gateways.BranchProductGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BranchProductUseCaseTest {

    @Mock
    BranchProductGateway branchProductGateway;

    @InjectMocks
    BranchProductUseCase useCase;

    BranchProduct branchProduct;

    @BeforeEach
    void setUp() {
        branchProduct = BranchProduct.builder()
                .branchId(1L)
                .productId(1L)
                .stock(1)
                .build();
    }

    @Test
    void deleteBranchProductTest() {


        when(branchProductGateway.deleteBranchProduct(any(BranchProduct.class))).thenReturn(Mono.empty());

        StepVerifier.create(useCase.deleteBranchProduct(branchProduct))
                .verifyComplete();

        verify(branchProductGateway, times(1)).deleteBranchProduct(any(BranchProduct.class));
    }

    @Test
    void changeStockTest() {

        when(branchProductGateway.changeStock(any(BranchProduct.class))).thenReturn(Mono.just(branchProduct));

        StepVerifier.create(useCase.changeStock(branchProduct))
                .assertNext(next -> {
                    assertNotNull(next);
                    assertEquals(branchProduct.getBranchId(), next.getBranchId());
                    assertEquals(branchProduct.getProductId(), next.getProductId());
                    assertEquals(branchProduct.getStock(), next.getStock());
                }).verifyComplete();


        verify(branchProductGateway, times(1)).changeStock(any(BranchProduct.class));
    }


}