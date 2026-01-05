package co.com.bancolombia.api.mappers;

import co.com.bancolombia.api.dtos.in.BranchProductRequestDTO;
import co.com.bancolombia.api.dtos.in.BranchRequestDTO;
import co.com.bancolombia.api.dtos.in.FranchiseRequestDTO;
import co.com.bancolombia.api.dtos.in.ProductRequestDTO;
import co.com.bancolombia.api.dtos.outs.*;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branchproduct.BranchProduct;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.productstockbybranch.ProductStockByBranch;

import java.util.List;

public class EntityDtoMapper {

    private EntityDtoMapper() {
    }

    public static Franchise franchiseDtoToEntity(FranchiseRequestDTO dto) {
        return Franchise.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public static FranchiseResponseDTO franchiseEntityToDto(Franchise entity) {
        return FranchiseResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdDate(entity.getCreatedDate())
                .build();
    }

    public static Branch branchDtoToEntity(BranchRequestDTO dto) {
        return Branch.builder()
                .franchiseId(dto.getFranchiseId())
                .name(dto.getName())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .build();
    }

    public static BranchResponseDTO branchEntityToDto(Branch entity) {
        return BranchResponseDTO.builder()
                .id(entity.getId())
                .franchiseId(entity.getFranchiseId())
                .name(entity.getName())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .createdDate(entity.getCreatedDate())
                .build();
    }

    public static Product productDtoToEntity(ProductRequestDTO dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .build();
    }

    public static ProductResponseDTO productEntityToDto(Product entity) {
        return ProductResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .createdDate(entity.getCreatedDate())
                .build();
    }

    public static ProductResponseDTO productBranchEntityToDto(Product entity, Long branchId, Integer stock) {
        var productBranch = productEntityToDto(entity);
        productBranch.setBranchId(branchId);
        productBranch.setStock(stock);
        return productBranch;
    }

    public static List<ProductStockByBranchResponseDTO> productsEntitiesToDto(List<ProductStockByBranch> entities) {
        return entities.stream()
                .map(EntityDtoMapper::productStockBranchEntityToDto)
                .toList();
    }

    public static ProductStockByBranchResponseDTO productStockBranchEntityToDto(ProductStockByBranch entity) {

        return ProductStockByBranchResponseDTO.builder()
                .branchId(entity.getBranchId())
                .branchName(entity.getBranchName())
                .productId(entity.getProductId())
                .productName(entity.getProductName())
                .stock(entity.getStock())
                .build();
    }

    public static BranchProduct branchProductDtoToEntity(BranchProductRequestDTO dto) {
        return BranchProduct.builder()
                .branchId(dto.getBranchId())
                .productId(dto.getProductId())
                .stock(dto.getStock())
                .build();
    }

    public static BranchProductResponseDTO branchProductEntityToDto(BranchProduct entity) {
        return BranchProductResponseDTO.builder()
                .branchId(entity.getBranchId())
                .productId(entity.getProductId())
                .stock(entity.getStock())
                .build();
    }

}
