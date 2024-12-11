package jtw.nice.entity.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDTO {
    private Integer tripId;
    private Integer groupId;
    private Integer itemId;
    private LocalDate date;
}
