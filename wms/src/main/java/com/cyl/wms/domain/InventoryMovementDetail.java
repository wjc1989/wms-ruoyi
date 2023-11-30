package com.cyl.wms.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * Quantity移动详情对象 wms_inventory_movement_detail
 *
 * @author zcc
 */
@ApiModel(description="Move Quantity")
@Data
@TableName("wms_inventory_movement_detail")
public class InventoryMovementDetail extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("ID")
    @Excel(name = "ID")
    private Long inventoryMovementId;

    @ApiModelProperty("Goods")
    @Excel(name = "Goods")
    private Long itemId;

    @ApiModelProperty("Plan Quantity")
    @Excel(name = "Plan Quantity")
    private BigDecimal planQuantity;

    @ApiModelProperty("Real Quantity")
    @Excel(name = "Real Quantity")
    private BigDecimal realQuantity;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

    @ApiModelProperty("Source Rack")
    @Excel(name = "Source Rack")
    private Long sourceRackId;

    @ApiModelProperty("Source Warehouse")
    @Excel(name = "Source Warehouse")
    private Long sourceWarehouseId;

    @ApiModelProperty("Source Area")
    @Excel(name = "Source Area")
    private Long sourceAreaId;

    @ApiModelProperty("Status")
    @Excel(name = "Status")
    private Integer moveStatus;

    @ApiModelProperty("Target Rack")
    @Excel(name = "Target Rack")
    private Long targetRackId;

    @ApiModelProperty("Target Warehouse")
    @Excel(name = "Target Warehouse")
    private Long targetWarehouseId;

    @ApiModelProperty("Target Area")
    @Excel(name = "Target Area")
    private Long targetAreaId;

}
