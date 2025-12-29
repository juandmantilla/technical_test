package co.com.bancolombia.api.dtos.outs;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchResponseDTO {
    private Long id;
    private Long franchiseId;
    private String name;
    private String address;
    private String phone;
    private LocalDateTime createdDate;
}
