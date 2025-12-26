package co.com.bancolombia.api.dtos.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    private Long id;
    private String name;
    private Long branchId;
    private Double price;
    private Integer stock;
    private LocalDateTime createdDate;
}
