package jtw.nice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("jtw_payment")
public class Payment {

    @TableId(value = "payment_id", type = IdType.AUTO)
    private Integer paymentId;

    @TableField("payment_amount")
    private BigDecimal paymentAmount;

    @TableField("payment_time")
    private LocalDateTime paymentTime;

    @TableField("payment_method")
    private String paymentMethod;

    @TableField("invoice_id")
    private Integer invoiceId;

    @TableField("trip_id")
    private Integer tripId;

    @TableField("group_id")
    private Integer groupId;
}


