package com.cyl.wms.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 入库单详情对象 wms_receipt_order_detail
 *
 * @author zcc
 */
@ApiModel(description="入库单详情对象")
@Data
@TableName("wms_receipt_order_detail")
public class ReceiptOrderDetail extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @Excel(name = "Order No.")
    @TableField(exist = false)
    private String orderNo;

    @ApiModelProperty("入库单号")
    @Excel(name = "入库单号")
    private Long receiptOrderId;

    @ApiModelProperty("Goods ")
    @Excel(name = "Goods ")
    private Long itemId;

    @ApiModelProperty("Plan Quantity")
    @Excel(name = "Plan Quantity")
    private BigDecimal planQuantity;

    @ApiModelProperty("Real Quantity")
    @Excel(name = "Real Quantity")
    private BigDecimal realQuantity;

    @ApiModelProperty("所属Shelf")
    @Excel(name = "所属Shelf")
    private Long rackId;

    @ApiModelProperty("Amount")
    @Excel(name = "Amount")
    private BigDecimal money;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

    @ApiModelProperty("Warehouse")
    @Excel(name = "Warehouse")
    private Long warehouseId;

    @ApiModelProperty("Area")
    @Excel(name = "Area")
    private Long areaId;

    @ApiModelProperty("入库Status")
    @Excel(name = "入库Status")
    private Integer receiptOrderStatus;

}
