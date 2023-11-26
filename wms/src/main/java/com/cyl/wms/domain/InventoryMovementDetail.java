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
@ApiModel(description="Quantity移动详情对象")
@Data
@TableName("wms_inventory_movement_detail")
public class InventoryMovementDetail extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("Quantity移动Id")
    @Excel(name = "Quantity移动Id")
    private Long inventoryMovementId;

    @ApiModelProperty("Goods ")
    @Excel(name = "Goods ")
    private Long itemId;

    @ApiModelProperty("计划数量")
    @Excel(name = "计划数量")
    private BigDecimal planQuantity;

    @ApiModelProperty("实际数量")
    @Excel(name = "实际数量")
    private BigDecimal realQuantity;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

    @ApiModelProperty("源货架")
    @Excel(name = "源货架")
    private Long sourceRackId;

    @ApiModelProperty("源仓库")
    @Excel(name = "源仓库")
    private Long sourceWarehouseId;

    @ApiModelProperty("源库区")
    @Excel(name = "源库区")
    private Long sourceAreaId;

    @ApiModelProperty("移库状态")
    @Excel(name = "移库状态")
    private Integer moveStatus;

    @ApiModelProperty("目标货架")
    @Excel(name = "目标货架")
    private Long targetRackId;

    @ApiModelProperty("目标仓库")
    @Excel(name = "目标仓库")
    private Long targetWarehouseId;

    @ApiModelProperty("目标库区")
    @Excel(name = "目标库区")
    private Long targetAreaId;

}
