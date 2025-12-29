package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.r2dbc.entities.ProductEntity;
import co.com.bancolombia.r2dbc.repositories.ProductRepository;
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
class ProductRepositoryAdapterTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ObjectMapper objectMapper;

    private ProductRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new ProductRepositoryAdapter(productRepository, objectMapper);
    }

    @Test
    void addNewProductToBranchTest() {
        Product product = Product.builder()
                .id(1L)
                .branchId(10L)
                .name("name")
                .price(12d)
                .stock(100)
                .build();

        ProductEntity entity = ProductEntity.builder()
                .id(1L)
                .branchId(10L)
                .name("name")
                .price(12d)
                .stock(100)
                .build();

        when(objectMapper.map(product, ProductEntity.class)).thenReturn(entity);
        when(objectMapper.map(entity, Product.class)).thenReturn(product);
        when(productRepository.findByName(anyString())).thenReturn(Mono.empty());
        when(productRepository.save(entity)).thenReturn(Mono.just(entity));

        StepVerifier.create(adapter.addNewProductToBranch(product))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void addNewProductToBranchErrorTest() {

        Product product = Product.builder()
                .id(1L)
                .name("name")
                .build();

        ProductEntity entity = ProductEntity.builder()
                .id(1L)
                .name("name")
                .build();

        when(objectMapper.map(product, ProductEntity.class)).thenReturn(entity);
        when(productRepository.findByName(anyString())).thenReturn(Mono.empty());
        when(productRepository.save(entity))
                .thenReturn(Mono.error(new RuntimeException("error")));

        StepVerifier.create(adapter.addNewProductToBranch(product))
                .expectError(TechnicalException.class)
                .verify();
    }

    @Test
    void deleteProductInBranchTest() {
        when(productRepository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(adapter.deleteProductInBranch(1L))
                .verifyComplete();

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProductInBranchErrorTest() {
        when(productRepository.deleteById(1L))
                .thenReturn(Mono.error(new RuntimeException("error")));

        StepVerifier.create(adapter.deleteProductInBranch(1L))
                .expectError(TechnicalException.class)
                .verify();
    }

    @Test
    void changeProductStockTest() {

        Product found = Product.builder()
                .id(1L)
                .name("name")
                .stock(10)
                .build();

        Product updated = found.toBuilder()
                .stock(20)
                .name("name")
                .build();

        ProductEntity foundEntity = ProductEntity.builder().id(1L).stock(10).build();
        ProductEntity savedEntity = ProductEntity.builder().id(1L).stock(20).build();

        when(productRepository.findById(1L)).thenReturn(Mono.just(foundEntity));
        when(objectMapper.map(foundEntity, Product.class)).thenReturn(found);
        when(objectMapper.map(any(Product.class), eq(ProductEntity.class))).thenReturn(savedEntity);
        when(productRepository.save(savedEntity)).thenReturn(Mono.just(savedEntity));
        when(objectMapper.map(savedEntity, Product.class)).thenReturn(updated);

        StepVerifier.create(adapter.changeProductStock(updated))
                .expectNext(updated)
                .verifyComplete();
    }

    @Test
    void changeProductStockErrorTest() {
        when(productRepository.findById(1L))
                .thenReturn(Mono.error(new RuntimeException("error")));

        StepVerifier.create(adapter.changeProductStock(Product.builder().id(1L).stock(10).build()))
                .expectError(TechnicalException.class)
                .verify();
    }


}