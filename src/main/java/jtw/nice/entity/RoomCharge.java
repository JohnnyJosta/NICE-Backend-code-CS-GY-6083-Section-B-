package jtw.nice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("jtw_room_charge")
public class RoomCharge {

    @TableId(value = "room_charge_id", type = IdType.AUTO)
    private Integer roomChargeId;

    @TableField("charge_date")
    private LocalDate chargeDate;

    @TableField("room_id")
    private Integer roomId;

    @TableField("trip_id")
    private Integer tripId;

    @TableField("group_id")
    private Integer groupId;
}

