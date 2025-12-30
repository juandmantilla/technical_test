package co.com.bancolombia.model.product;

import co.com.bancolombia.model.transversal.time.BogotaTimeProvider;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product {
    private Long id;
    private String name;
    private Double price;
    private LocalDateTime createdDate;

    public Product createdDate() {
        return this.toBuilder().createdDate(BogotaTimeProvider.now()).build();
    }
    
}
