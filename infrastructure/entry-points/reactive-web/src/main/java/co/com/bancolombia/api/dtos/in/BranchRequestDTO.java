package co.com.bancolombia.api.dtos.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchRequestDTO {
    private String name;
    private Long franchiseId;
    private String address;
    private String phone;
}
