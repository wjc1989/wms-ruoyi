package com.cyl.wms.pojo.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 出库单详情 数据视图对象
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
     * 订单号
     */
    @Excel(name = "订单号")
    private String orderNo;
    /**
     * 出库单
     */
    @Excel(name = "出库单")
    private Long shipmentOrderId;
    /**
     * Goods
     */
    @Excel(name = "Goods ")
    private Long itemId;
    /**
     * 计划数量
     */
    @Excel(name = "计划数量")
    private BigDecimal planQuantity;
    /**
     * 实际数量
     */
    @Excel(name = "实际数量")
    private BigDecimal realQuantity;
    /**
     * 货架
     */
    @Excel(name = "货架")
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

    @Excel(name = "金额")
    private BigDecimal money;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;
    /**
     * Area
     */
    @Excel(name = "Area")
    private Long areaId;

    @Excel(name = "出库状态")
    private Integer shipmentOrderStatus;
    private List<Long> place;
}
