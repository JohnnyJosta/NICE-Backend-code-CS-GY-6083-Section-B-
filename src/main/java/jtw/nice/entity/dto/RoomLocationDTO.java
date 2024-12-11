package jtw.nice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomLocationDTO {
    private String location;
    private Integer remainingUnits;
    private BigDecimal price;
}
