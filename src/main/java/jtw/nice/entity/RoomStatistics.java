package jtw.nice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("jtw_room_statistics")
public class RoomStatistics {

    @TableId(value = "stat_id", type = IdType.AUTO)
    private Integer statId;

    @TableField("room_type")
    private String roomType;

    @TableField("location")
    private String location;

    @TableField("price")
    private BigDecimal price;

    @TableField("remaining_units")
    private Integer remainingUnits;

    @TableField("trip_id")
    private Integer tripId;
}

