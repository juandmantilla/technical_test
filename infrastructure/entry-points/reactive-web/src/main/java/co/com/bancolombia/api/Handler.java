package co.com.bancolombia.api;

import co.com.bancolombia.api.config.ApplicationExceptionHandler;
import co.com.bancolombia.api.dtos.in.BranchProductRequestDTO;
import co.com.bancolombia.api.dtos.in.BranchRequestDTO;
import co.com.bancolombia.api.dtos.in.FranchiseRequestDTO;
import co.com.bancolombia.api.dtos.in.ProductRequestDTO;
import co.com.bancolombia.api.helpers.ResponseBuilder;
import co.com.bancolombia.api.mappers.EntityDtoMapper;
import co.com.bancolombia.model.branchproduct.BranchProduct;
import co.com.bancolombia.model.messages.ResponseMessages;
import co.com.bancolombia.usecase.branch.BranchUseCase;
import co.com.bancolombia.usecase.branchproduct.BranchProductUseCase;
import co.com.bancolombia.usecase.franchise.FranchiseUseCase;
import co.com.bancolombia.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler extends ApplicationExceptionHandler {

    private final FranchiseUseCase franchiseUseCase;
    private final BranchUseCase branchUseCase;
    private final ProductUseCase productUseCase;
    private final BranchProductUseCase branchProductUseCase;

    public Mono<ServerResponse> addNewFranchise(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(FranchiseRequestDTO.class)
                .doFirst(() -> log.info("Start process to add new franchise"))
                .map(EntityDtoMapper::franchiseDtoToEntity)
                .flatMap(franchiseUseCase::addNewFranchise)
                .map(EntityDtoMapper::franchiseEntityToDto)
                .flatMap(response -> ResponseBuilder.buildSuccessResponse(
                        ResponseMessages.FRANCHISE_SAVED.getStatusCode(),
                        ResponseMessages.FRANCHISE_SAVED.getDescription(),
                        response))
                .onErrorResume(this::handleException);

    }

    public Mono<ServerResponse> addBranchToFranchise(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(BranchRequestDTO.class)
                .doFirst(() -> log.info("Start to add Branch to Franchise"))
                .map(EntityDtoMapper::branchDtoToEntity)
                .flatMap(branchUseCase::addBranchToFranchise)
                .map(EntityDtoMapper::branchEntityToDto)
                .flatMap(response -> ResponseBuilder.buildSuccessResponse(
                        ResponseMessages.BRANCH_SAVED.getStatusCode(),
                        ResponseMessages.BRANCH_SAVED.getDescription(),
                        response))
                .onErrorResume(this::handleException);
    }

    public Mono<ServerResponse> addNewProductToBranch(ServerRequest serverRequest) {

        Long branchId = Long.valueOf(serverRequest.pathVariable("branchId"));
        Integer stock = Integer.valueOf(serverRequest.pathVariable("stock"));

        return serverRequest.bodyToMono(ProductRequestDTO.class)
                .doFirst(() -> log.info("Start to add new Product to Branch"))
                .map(EntityDtoMapper::productDtoToEntity)
                .flatMap(product -> productUseCase.addNewProductToBranch(product, branchId, stock))
                .map(saved -> EntityDtoMapper.productBranchEntityToDto(saved, branchId, stock))
                .flatMap(response -> ResponseBuilder.buildSuccessResponse(
                        ResponseMessages.PRODUCT_SAVED.getStatusCode(),
                        ResponseMessages.PRODUCT_SAVED.getDescription(),
                        response))
                .onErrorResume(this::handleException);
    }

    public Mono<ServerResponse> deleteProductInBranch(ServerRequest serverRequest) {

        Long productId = Long.valueOf(serverRequest.pathVariable("productId"));
        Long branchId = Long.valueOf(serverRequest.pathVariable("branchId"));

        return branchProductUseCase.deleteBranchProduct(BranchProduct.builder()
                        .productId(productId)
                        .branchId(branchId)
                        .build())
                .doFirst(() -> log.info("Start to delete Product in Branch"))
                .flatMap(response -> ResponseBuilder.buildSuccessResponse(
                        ResponseMessages.DELETE_BRANCH_PRODUCT.getStatusCode(),
                        ResponseMessages.DELETE_BRANCH_PRODUCT.getDescription(),
                        response))
                .onErrorResume(this::handleException);
    }

    public Mono<ServerResponse> changeProductStock(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(BranchProductRequestDTO.class)
                .doFirst(() -> log.info("Start to change Product Stock"))
                .map(EntityDtoMapper::branchProductDtoToEntity)
                .flatMap(branchProductUseCase::changeStock)
                .map(EntityDtoMapper::branchProductEntityToDto)
                .flatMap(response -> ResponseBuilder.buildSuccessResponse(
                        ResponseMessages.STOCK_CHANGED.getStatusCode(),
                        ResponseMessages.STOCK_CHANGED.getDescription(),
                        response))
                .onErrorResume(this::handleException);
    }

    public Mono<ServerResponse> getTopStockProductsByFranchise(ServerRequest serverRequest) {

        Long franchiseId = Long.valueOf(serverRequest.pathVariable("franchiseId"));

        return productUseCase.getTopStockProductsByFranchise(franchiseId)
                .doFirst(() -> log.info("Searching for products with the highest stock levels by Franchise Id, : {}", franchiseId))
                .collectList()
                .map(EntityDtoMapper::productsEntitiesToDto)
                .flatMap(response -> ResponseBuilder.buildSuccessResponse(
                        ResponseMessages.GET_STOCK.getStatusCode(),
                        ResponseMessages.GET_STOCK.getDescription(),
                        response))
                .onErrorResume(this::handleException);
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}
