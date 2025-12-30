package co.com.bancolombia.api.dtos.outs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStockByBranchResponseDTO {
    private Long branchId;
    private String branchName;
    private Long productId;
    private String productName;
    private Integer stock;
}
