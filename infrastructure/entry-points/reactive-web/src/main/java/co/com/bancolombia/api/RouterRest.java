package co.com.bancolombia.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final Handler handler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .POST("/api/franchise", handler::addNewFranchise)
                .POST("/api/branch", handler::addBranchToFranchise)
                .POST("/api/branch/{branchId}/stock/{stock}", handler::addNewProductToBranch)
                .DELETE("/api/product/{productId}/branch/{branchId}", handler::deleteProductInBranch)
                .PUT("/api/product/stock", handler::changeProductStock)
                .GET("/api/franchise/{franchiseId}/top-stock", handler::getTopStockProductsByFranchise)
                .build();
    }
}
