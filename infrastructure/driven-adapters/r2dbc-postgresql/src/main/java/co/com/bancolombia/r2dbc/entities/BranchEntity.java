package co.com.bancolombia.r2dbc.entities;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("branch")
public class BranchEntity {

    @Id
    private Long id;

    @Column("franchise_id")
    private Long franchiseId;

    private String name;

    private String address;

    private String phone;

    @Column("created_date")
    private LocalDateTime createdDate;

}
