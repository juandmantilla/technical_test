package co.com.bancolombia.api.dtos.outs;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchProductResponseDTO {
    private Long branchId;
    private Long productId;
    private Integer stock;
}
