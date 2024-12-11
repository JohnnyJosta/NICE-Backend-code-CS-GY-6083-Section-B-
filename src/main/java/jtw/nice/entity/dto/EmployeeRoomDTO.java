package jtw.nice.entity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmployeeRoomDTO {
    private Integer roomChargeId;
    private String roomName;
    private LocalDate chargeDate;
    private BigDecimal price;
}
