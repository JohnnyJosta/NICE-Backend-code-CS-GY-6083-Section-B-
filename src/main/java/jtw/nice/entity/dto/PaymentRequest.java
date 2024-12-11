package jtw.nice.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private Integer tripId;
    private BigDecimal paymentAmount;
    private String paymentMethod;
}
