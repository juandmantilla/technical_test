package co.com.bancolombia.model.branch;

import co.com.bancolombia.model.transversal.time.BogotaTimeProvider;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Branch {
    private Long id;
    private Long franchiseId;
    private String name;
    private String address;
    private String phone;
    private LocalDateTime createdDate;

    public Branch createdDate() {
        return this.toBuilder().createdDate(BogotaTimeProvider.now()).build();
    }
}
