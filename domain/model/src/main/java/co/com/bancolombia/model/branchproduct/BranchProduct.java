package co.com.bancolombia.model.branchproduct;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.messages.ResponseMessages;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BranchProduct {

    private Long branchId;

    private Long productId;

    private Integer stock;


    public BranchProduct validateNewStock() {
        if (this.stock < 0) {
            throw new BusinessException(
                    ResponseMessages.ERROR_VALIDATE_STOCK.getStatusCode(),
                    ResponseMessages.ERROR_VALIDATE_STOCK.getDescription());
        }

        return toBuilder().build();
    }
}
