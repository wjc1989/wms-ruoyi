package com.cyl.wms.pojo.query;

import java.math.BigDecimal;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Quantity移动详情 查询 对象
 *
 * @author zcc
 */
@ApiModel(description="Quantity移动详情 查询 对象")
@Data
public class InventoryMovementDetailQuery {
    @ApiModelProperty("Quantity移动Id 精确匹配")
    private Long inventoryMovementId;

    @ApiModelProperty("Goods  精确匹配")
    private Long itemId;

    @ApiModelProperty("Plan Quantity 精确匹配")
    private BigDecimal planQuantity;

    @ApiModelProperty("Real Quantity 精确匹配")
    private BigDecimal realQuantity;

    @ApiModelProperty("源Shelf 精确匹配")
    private Long sourceRackId;

    @ApiModelProperty("源仓库 精确匹配")
    private Long sourceWarehouseId;

    @ApiModelProperty("源库区 精确匹配")
    private Long sourceAreaId;

    @ApiModelProperty("移库Status 精确匹配")
    private Integer moveStatus;

    @ApiModelProperty("Target Rack 精确匹配")
    private Long targetRackId;

    @ApiModelProperty("目标仓库 精确匹配")
    private Long targetWarehouseId;

    @ApiModelProperty("目标库区 精确匹配")
    private Long targetAreaId;

}
