package com.cyl.wms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

/**
 * 入库单对象 wms_receipt_order
 *
 * @author zcc
 */
@ApiModel(description="入库单对象")
@Data
@TableName("wms_receipt_order")
public class ReceiptOrder extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("入库单号")
    @Excel(name = "入库单号")
    private String receiptOrderNo;

    @ApiModelProperty("入库类型")
    @Excel(name = "入库类型")
    private Integer receiptOrderType;

    @ApiModelProperty("供应商")
    @Excel(name = "供应商")
    private Long supplierId;

    @ApiModelProperty("订单号")
    @Excel(name = "订单号")
    private String orderNo;

    @ApiModelProperty("应付款合计")
    @Excel(name = "应付款合计")
    private BigDecimal payableAmount;

    @ApiModelProperty("入库Status")
    @Excel(name = "入库Status")
    private Integer receiptOrderStatus;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

    @ApiModelProperty("波次号")
    private String waveNo;

    private String contact;
    private String phone;

    @TableField(exist = false)
    private String createName;

}
