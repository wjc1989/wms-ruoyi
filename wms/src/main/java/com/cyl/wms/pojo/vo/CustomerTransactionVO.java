package com.cyl.wms.pojo.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
/**
 * 客户账户流水 数据视图对象
 *
 * @author zcc
 */
@Data
public class CustomerTransactionVO  {
   /** id */
    private Integer id;
   /** 交易编号 */
    @Excel(name = "交易编号")
    private String transactionCode;
   /** 用户编号 */
    @Excel(name = "用户编号")
    private String customerId;
   /** 交易类型  1：结款  2：应付  */
    @Excel(name = "交易类型  1：结款  2：应付 ")
    private String transactionType;
   /** 交易Amount */
    @Excel(name = "交易Amount")
    private BigDecimal transactionAmount;
   /** 上期余额 */
    @Excel(name = "上期余额")
    private BigDecimal previousBalance;
   /** 当前余额 */
    @Excel(name = "当前余额")
    private BigDecimal currentBalance;
   /** Shipment Order号 */
    @Excel(name = "Shipment Order号")
    private Long shipmentOrderId;
   /** Remark */
    @Excel(name = "Remark")
    private String remark;
   /** 创建时间 */
    private LocalDateTime createTime;
   /** 更新时间 */
    private LocalDateTime updateTime;
}
