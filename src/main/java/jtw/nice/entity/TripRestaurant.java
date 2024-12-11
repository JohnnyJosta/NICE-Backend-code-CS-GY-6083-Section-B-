package jtw.nice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("jtw_trip_restaurant")
public class TripRestaurant {
    @TableField("trip_id")
    private Integer tripId;

    @TableField("restaurant_id")
    private Integer restaurantId;

    @TableField("quantity")
    private Integer quantity;
}
