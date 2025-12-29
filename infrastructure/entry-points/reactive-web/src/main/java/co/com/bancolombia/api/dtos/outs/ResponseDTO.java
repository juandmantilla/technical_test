package co.com.bancolombia.api.dtos.outs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO<T> {
    private String code;
    private String description;
    private T data;
}
