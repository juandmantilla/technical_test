package co.com.bancolombia.api.dtos.outs;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private Long id;
    private Long branchId;
    private String name;
    private Double price;
    private Integer stock;
    private LocalDateTime createdDate;
}
