package jtw.nice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@TableName("jtw_invoice")
public class Invoice {

    @TableId(value = "invoice_id", type = IdType.AUTO)
    private Integer invoiceId;

    @TableField("charge_date")
    private LocalDate chargeDate;

    @TableField("charge_amount")
    private BigDecimal chargeAmount;

    @TableField("charge_amount")
    private BigDecimal remainingAmount;

    @TableField("trip_id")
    private Integer tripId;

    @TableField("group_id")
    private Integer groupId;
}

