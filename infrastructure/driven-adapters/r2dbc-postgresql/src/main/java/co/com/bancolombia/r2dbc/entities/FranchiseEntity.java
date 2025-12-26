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
@Table("franchise")
public class FranchiseEntity {

    @Id
    private Long id;

    private String name;

    private String description;

    @Column("created_date")
    private LocalDateTime createdDate;
}
