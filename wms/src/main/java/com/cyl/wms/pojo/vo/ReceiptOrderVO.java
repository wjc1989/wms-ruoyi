package com.cyl.wms.pojo.vo;

import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 入库单 数据视图对象
 *
 * @author zcc
 */
@Data
public class ReceiptOrderVO extends BaseAudit {
   /** ID */
    private Long id;
   /** 入库单号 */
    @Excel(name = "入库单号")
    private String receiptOrderNo;
   /** 入库类型 */
    @Excel(name = "入库类型")
    private Integer receiptOrderType;
   /** 供应商 */
    @Excel(name = "供应商")
    private Long supplierId;
   /** 订单号 */
    @Excel(name = "订单号")
    private String orderNo;
    @Excel(name = "应付款合计")
    private BigDecimal payableAmount;
   /** 入库Status */
    @Excel(name = "入库Status")
    private Integer receiptOrderStatus;
   /** Remark */
    @Excel(name = "Remark")
    private String remark;
    private Integer detailCount;

    @Excel(name = "波次号")
    private String waveNo;
}
