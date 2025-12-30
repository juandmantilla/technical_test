package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.branchproduct.BranchProduct;
import co.com.bancolombia.model.branchproduct.gateways.BranchProductGateway;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductGateway;
import co.com.bancolombia.model.productstockbybranch.ProductStockByBranch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {

    @Mock
    private ProductGateway productGateway;

    @Mock
    private BranchProductGateway branchProductGateway;

    @InjectMocks
    private ProductUseCase productUseCase;

    private Product product;
    private Product savedProduct;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .name("Laptop")
                .createdDate(LocalDateTime.now())
                .build();

        savedProduct = Product.builder()
                .id(1L)
                .name("Laptop")
                .createdDate(product.getCreatedDate())
                .build();
    }

    @Test
    void shouldAddNewProductToBranchSuccessfully() {
        Long branchId = 10L;
        Integer stock = 50;

        when(productGateway.addNewProductToBranch(any()))
                .thenReturn(Mono.just(savedProduct));

        when(branchProductGateway.saveBranchProduct(any(BranchProduct.class)))
                .thenReturn(Mono.just(BranchProduct.builder().build()));

        StepVerifier.create(
                        productUseCase.addNewProductToBranch(product, branchId, stock)
                )
                .expectNext(savedProduct)
                .verifyComplete();
    }

    @Test
    void shouldReturnTopStockProductsByFranchise() {
        Long franchiseId = 5L;

        var ps1 = ProductStockByBranch.builder()
                .productId(1L)
                .branchId(10L)
                .stock(100)
                .build();

        var ps2 = ProductStockByBranch.builder()
                .productId(2L)
                .branchId(11L)
                .stock(80)
                .build();

        when(productGateway.getTopStockProductsByFranchise(franchiseId))
                .thenReturn(Flux.just(ps1, ps2));

        StepVerifier.create(
                        productUseCase.getTopStockProductsByFranchise(franchiseId)
                )
                .expectNext(ps1)
                .expectNext(ps2)
                .verifyComplete();

    }
}


