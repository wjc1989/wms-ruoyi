package com.cyl.wms.pojo.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Shipment Order详情 数据视图对象
 *
 * @author zcc
 */
@Data
public class ShipmentOrderDetailVO extends BaseAudit {
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private Long id;
    /**
     * Order No.
     */
    @Excel(name = "Order No.")
    private String orderNo;
    /**
     * Shipment Order
     */
    @Excel(name = "Shipment Order")
    private Long shipmentOrderId;
    /**
     * Goods
     */
    @Excel(name = "Goods ")
    private Long itemId;
    /**
     * Plan Quantity
     */
    @Excel(name = "Plan Quantity")
    private BigDecimal planQuantity;
    /**
     * Real Quantity
     */
    @Excel(name = "Real Quantity")
    private BigDecimal realQuantity;
    /**
     * Shelf
     */
    @Excel(name = "Shelf")
    private Long rackId;
    /**
     * Remark
     */
    @Excel(name = "Remark")
    private String remark;

    /**
     * Warehouse
     */
    @Excel(name = "Warehouse")
    private Long warehouseId;

    @Excel(name = "Amount")
    private BigDecimal money;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;
    /**
     * Area
     */
    @Excel(name = "Area")
    private Long areaId;

    @Excel(name = "Out Status")
    private Integer shipmentOrderStatus;
    private List<Long> place;
}
