package co.com.bancolombia.api.dtos.outs;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
    private String code;
    private String description;
}
