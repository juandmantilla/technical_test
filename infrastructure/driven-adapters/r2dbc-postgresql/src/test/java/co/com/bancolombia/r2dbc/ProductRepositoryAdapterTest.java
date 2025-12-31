package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.exceptions.TechnicalException;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.r2dbc.entities.ProductEntity;
import co.com.bancolombia.r2dbc.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductRepositoryAdapterTest {


    @Mock
    ProductRepository productRepository;

    @Mock
    ObjectMapper mapper;

    @InjectMocks
    ProductRepositoryAdapter adapter;

    Product product;
    ProductEntity productEntity;
    LocalDateTime now;


    @BeforeEach
    void setUp() {

        now = LocalDateTime.now();
        adapter = new ProductRepositoryAdapter(productRepository, mapper);

        product = Product.builder()
                .name("name")
                .id(1L)
                .price(23d)
                .createdDate(now)
                .build();

        productEntity = ProductEntity.builder()
                .name("name")
                .id(1L)
                .price(23d)
                .createdDate(now)
                .build();
    }

    @Test
    void addNewProductToBranchTest() {

        when(mapper.map(product, ProductEntity.class))
                .thenReturn(productEntity);

        when(mapper.map(productEntity, Product.class))
                .thenReturn(product);

        when(productRepository.findByName(anyString())).thenReturn(Mono.empty());

        when(productRepository.save(productEntity)).thenReturn(Mono.just(productEntity));

        StepVerifier.create(adapter.addNewProductToBranch(product))
                .assertNext(next -> {
                    assertNotNull(next);
                    assertEquals(product.getName(), next.getName());
                    assertEquals(product.getId(), next.getId());
                    assertEquals(product.getPrice(), next.getPrice());
                })
                .verifyComplete();
    }


    @Test
    void addNewProductToBranchErrorTest() {

        when(mapper.map(product, ProductEntity.class))
                .thenReturn(productEntity);

        when(productRepository.findByName(anyString())).thenReturn(Mono.error(new RuntimeException("DB ERROR")));
        when(productRepository.save(productEntity)).thenReturn(Mono.just(productEntity));

        StepVerifier.create(adapter.addNewProductToBranch(product))
                .expectErrorMatches(error ->
                        error instanceof TechnicalException
                )
                .verify();
    }


    @Test
    void shouldHandleErrorWhenGettingTopStockProducts() {

        when(productRepository.findProductsByFranchiseOrderByStockDesc(anyLong()))
                .thenReturn(Flux.error(new RuntimeException("ERROR DB")));

        StepVerifier.create(adapter.getTopStockProductsByFranchise(1L))
                .expectErrorMatches(error ->
                        error instanceof TechnicalException
                )
                .verify();
    }

}