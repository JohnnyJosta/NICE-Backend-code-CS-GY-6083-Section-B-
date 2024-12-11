package jtw.nice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("jtw_trip_entertainment")
public class TripEntertainment {
    @TableField("trip_id")
    private Integer tripId;

    @TableField("entertainment_id")
    private Integer entertainmentId;

    @TableField("quantity")
    private Integer quantity;
}
