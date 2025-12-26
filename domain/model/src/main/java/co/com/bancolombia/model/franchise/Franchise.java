package co.com.bancolombia.model.franchise;

import co.com.bancolombia.model.transversal.time.BogotaTimeProvider;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Franchise {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdDate;

    public Franchise createdDate() {
        return this.toBuilder().createdDate(BogotaTimeProvider.now()).build();
    }

}
