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
 * Shipment Order详情对象 wms_shipment_order_detail
 *
 * @author zcc
 */
@ApiModel(description = "Shipment Order详情对象")
@Data
@TableName("wms_shipment_order_detail")
public class ShipmentOrderDetail extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;
    /**
     * Order No.
     */
    @Excel(name = "Order No.")
    @TableField(exist = false)
    private String orderNo;

    @ApiModelProperty("Shipment Order")
    @Excel(name = "Shipment Order")
    private Long shipmentOrderId;

    @ApiModelProperty("Goods ")
    @Excel(name = "Goods ")
    private Long itemId;

    @ApiModelProperty("Plan Quantity")
    @Excel(name = "Plan Quantity")
    private BigDecimal planQuantity;

    @ApiModelProperty("Real Quantity")
    @Excel(name = "Real Quantity")
    private BigDecimal realQuantity;

    @ApiModelProperty("Shelf")
    @Excel(name = "Shelf")
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

    @ApiModelProperty("Out Status")
    @Excel(name = "Out Status")
    private Integer shipmentOrderStatus;

}
