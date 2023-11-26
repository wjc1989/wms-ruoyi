package com.cyl.wms.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * Quantity盘点单据详情对象 wms_inventory_check_detail
 *
 * @author zcc
 */
@ApiModel(description="Quantity盘点单据详情对象")
@Data
@TableName("wms_inventory_check_detail")
public class InventoryCheckDetail extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("Quantity盘点单")
    @Excel(name = "Quantity盘点单")
    private Long inventoryCheckId;

    @ApiModelProperty("Goods ")
    @Excel(name = "Goods ")
    private Long itemId;

    @ApiModelProperty("Quantity数量")
    @Excel(name = "Quantity数量")
    private BigDecimal quantity;

    @ApiModelProperty("盘点数量")
    @Excel(name = "盘点数量")
    private BigDecimal checkQuantity;

    @ApiModelProperty("Warehouse")
    @Excel(name = "Warehouse")
    private Long warehouseId;

    @ApiModelProperty("Area")
    @Excel(name = "Area")
    private Long areaId;

    @ApiModelProperty("货架")
    @Excel(name = "货架")
    private Long rackId;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

}
