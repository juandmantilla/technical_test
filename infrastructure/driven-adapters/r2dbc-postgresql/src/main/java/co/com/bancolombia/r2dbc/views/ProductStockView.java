package co.com.bancolombia.r2dbc.views;

import java.time.LocalDateTime;

public interface ProductStockView {

    Long getId();

    Long getBranchId();

    String getName();

    Double getPrice();

    Integer getStock();

    LocalDateTime getCreatedDate();

}
