package co.com.bancolombia.api.dtos.outs;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FranchiseResponseDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdDate;
}
