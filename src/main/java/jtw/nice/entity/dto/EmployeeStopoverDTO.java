package jtw.nice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeStopoverDTO {
    private Integer stopoverId;
    private String portName;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
}
