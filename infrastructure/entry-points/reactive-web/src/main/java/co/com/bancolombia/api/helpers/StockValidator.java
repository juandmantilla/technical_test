package co.com.bancolombia.api.helpers;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.messages.ResponseMessages;

public class StockValidator {

    private StockValidator() {
    }

    private Integer validateStock(Integer newStock) {

        if (newStock <= 0) {
            throw new BusinessException(
                    ResponseMessages.ERROR_STOCK_VALIDATION.getStatusCode(),
                    ResponseMessages.ERROR_STOCK_VALIDATION.getDescription());
        }

        return newStock;
    }
}
