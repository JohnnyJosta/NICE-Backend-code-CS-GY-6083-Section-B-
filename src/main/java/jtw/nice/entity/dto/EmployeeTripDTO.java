package jtw.nice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTripDTO {
    private Integer tripId;
    private String startPortName;
    private String endPortName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}