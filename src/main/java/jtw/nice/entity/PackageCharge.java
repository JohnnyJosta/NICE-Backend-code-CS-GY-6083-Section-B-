package jtw.nice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("jtw_package_charge")
public class PackageCharge {

    @TableId(value = "package_charge_id", type = IdType.AUTO)
    private Integer packageChargeId;

    @TableField("charge_date")
    private LocalDate chargeDate;

    @TableField("package_id")
    private Integer packageId;

    @TableField("trip_id")
    private Integer tripId;

    @TableField("group_id")
    private Integer groupId;
}

