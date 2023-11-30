package com.cyl.wms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 出库单详情对象 wms_shipment_order_detail
 *
 * @author zcc
 */
@ApiModel(description = "出库单详情对象")
@Data
@TableName("wms_shipment_order_detail")
public class ShipmentOrderDetail extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;
    /**
     * 订单号
     */
    @Excel(name = "订单号")
    @TableField(exist = false)
    private String orderNo;

    @ApiModelProperty("出库单")
    @Excel(name = "出库单")
    private Long shipmentOrderId;

    @ApiModelProperty("Goods ")
    @Excel(name = "Goods ")
    private Long itemId;

    @ApiModelProperty("计划数量")
    @Excel(name = "计划数量")
    private BigDecimal planQuantity;

    @ApiModelProperty("实际数量")
    @Excel(name = "实际数量")
    private BigDecimal realQuantity;

    @ApiModelProperty("货架")
    @Excel(name = "货架")
    private Long rackId;

    @ApiModelProperty("金额")
    @Excel(name = "金额")
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

    @ApiModelProperty("出库Status")
    @Excel(name = "出库Status")
    private Integer shipmentOrderStatus;

}
