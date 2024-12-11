package jtw.nice.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PackageDTO {
    private String name;
    private BigDecimal price;
    private String chargeMethod;
}
