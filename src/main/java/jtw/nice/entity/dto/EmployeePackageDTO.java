package jtw.nice.entity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmployeePackageDTO {
    private Integer packageChargeId;
    private String packageName;
    private LocalDate chargeDate;
    private BigDecimal pricePerPerson;
}
