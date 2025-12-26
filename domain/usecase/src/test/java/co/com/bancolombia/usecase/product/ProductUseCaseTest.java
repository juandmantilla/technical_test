package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {

    @Mock
    ProductGateway productGateway;

    @InjectMocks
    ProductUseCase productUseCase;

    @Test
    void addNewProductToBranchTest() {

        Product product = Product.builder()
                .id(1L)
                .name("Burger")
                .stock(10)
                .build();

        Product productWithDate = product.createdDate();

        when(productGateway.addNewProductToBranch(any(Product.class)))
                .thenReturn(Mono.just(productWithDate));

        StepVerifier.create(productUseCase.addNewProductToBranch(product))
                .assertNext(savedProduct -> {
                    assertNotNull(savedProduct.getCreatedDate());
                    assertEquals("Burger", savedProduct.getName());
                    assertEquals(10, savedProduct.getStock());
                })
                .verifyComplete();

        verify(productGateway).addNewProductToBranch(any(Product.class));
    }

    @Test
    void deleteProductInBranchTest() {

        Product product = Product.builder()
                .id(1L)
                .build();

        when(productGateway.deleteProductInBranch(1L))
                .thenReturn(Mono.empty());


        Mono<Void> result = productUseCase.deleteProductInBranch(product);

        StepVerifier.create(result)
                .verifyComplete();

        verify(productGateway).deleteProductInBranch(1L);
    }

    @Test
    void changeProductStockTest() {

        Product product = Product.builder()
                .id(1L)
                .stock(20)
                .build();

        Product productWithNewStock = product.validateNewStock(20);

        when(productGateway.changeProductStock(any(Product.class)))
                .thenReturn(Mono.just(productWithNewStock));

        StepVerifier.create(productUseCase.changeProductStock(product))
                .assertNext(updatedProduct -> {
                    assertEquals(20, updatedProduct.getStock());
                })
                .verifyComplete();

        verify(productGateway).changeProductStock(any(Product.class));
    }

    @Test
    void getStockProductByBranchTest() {
       Long franchiseId = 1L;

        Product product1 = Product.builder().id(1L).name("Burger").stock(10).build();
        Product product2 = Product.builder().id(2L).name("Pizza").stock(5).build();

        when(productGateway.getStockProductByBranch(franchiseId))
                .thenReturn(Flux.just(product1, product2));

        Flux<Product> result = productUseCase.getStockProductByBranch(franchiseId);

        StepVerifier.create(result)
                .expectNext(product1)
                .expectNext(product2)
                .verifyComplete();

        verify(productGateway).getStockProductByBranch(franchiseId);
    }

}