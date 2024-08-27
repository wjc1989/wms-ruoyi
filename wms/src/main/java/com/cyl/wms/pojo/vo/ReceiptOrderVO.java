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
    @Excel(name = "In Bround Order No.")
    private String receiptOrderNo;

    private Integer receiptOrderType;
   /** 供应商 */
    @Excel(name = "Supplier")
    private Long supplierId;
   /** 订单号 */
    @Excel(name = "Project")
    private String orderNo;

    private BigDecimal payableAmount;
   /** 入库Status */
    @Excel(name = "Status")
    private Integer receiptOrderStatus;
   /** Remark */
    @Excel(name = "Remark")
    private String remark;
    private Integer detailCount;

    private String waveNo;
    @Excel(name="Contact")
    private String contact;
    @Excel(name="Phone")
    private String phone;
    @Excel(name="Create By")
    private String createName;
}
