package co.com.bancolombia.r2dbc.entities;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("branch_product")
public class BranchProductEntity {

    @Column("branch_id")
    private Long branchId;

    @Column("product_id")
    private Long productId;

    private Integer stock;
}
