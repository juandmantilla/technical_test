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




}