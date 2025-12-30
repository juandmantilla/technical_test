package co.com.bancolombia.model.productstockbybranch;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStockByBranch {
    private Long branchId;
    private String branchName;
    private Long productId;
    private String productName;
    private Integer stock;
}
