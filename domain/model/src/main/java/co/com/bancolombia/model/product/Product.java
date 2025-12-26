package co.com.bancolombia.model.product;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.messages.ResponseMessages;
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
    private Long branchId;
    private String name;
    private Double price;
    private Integer stock;
    private LocalDateTime createdDate;

    public Product createdDate() {
        return this.toBuilder().createdDate(BogotaTimeProvider.now()).build();
    }

    public Product validateNewStock(Integer newStock) {
        if (newStock < 0) {
            throw new BusinessException(
                    ResponseMessages.ERROR_VALIDATE_STOCK.getStatusCode(),
                    ResponseMessages.ERROR_VALIDATE_STOCK.getDescription());
        }

        return this.toBuilder().stock(newStock).build();
    }
}
