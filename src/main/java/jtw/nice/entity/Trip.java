package jtw.nice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("jtw_trip")
public class Trip {

    @TableId(value = "trip_id", type = IdType.AUTO)
    private Integer tripId;

    @TableField("start_date_time")
    private LocalDateTime startDateTime;

    @TableField("end_date_time")
    private LocalDateTime endDateTime;

    @TableField("total_night")
    private Integer totalNight;

    @TableField("start_port_id")
    private Integer startPortId;

    @TableField("end_port_id")
    private Integer endPortId;
}
