package co.com.bancolombia.api.mappers;

import co.com.bancolombia.api.dtos.in.BranchRequestDTO;
import co.com.bancolombia.api.dtos.in.FranchiseRequestDTO;
import co.com.bancolombia.api.dtos.in.ProductRequestDTO;
import co.com.bancolombia.api.dtos.out.BranchResponseDTO;
import co.com.bancolombia.api.dtos.out.FranchiseResponseDTO;
import co.com.bancolombia.api.dtos.out.ProductResponseDTO;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.product.Product;

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
                .branchId(dto.getBranchId())
                .name(dto.getName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();
    }

    public static ProductResponseDTO branchEntityToDto(Product entity) {
        return ProductResponseDTO.builder()
                .id(entity.getId())
                .branchId(entity.getBranchId())
                .name(entity.getName())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}
