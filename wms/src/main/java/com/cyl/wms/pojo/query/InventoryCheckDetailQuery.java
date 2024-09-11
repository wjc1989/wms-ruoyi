package com.cyl.wms.pojo.query;

import java.math.BigDecimal;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Quantity盘点单据详情 查询 对象
 *
 * @author zcc
 */
@ApiModel(description="Quantity盘点单据详情 查询 对象")
@Data
public class InventoryCheckDetailQuery {
    @ApiModelProperty("Quantity盘点单 精确匹配")
    private Long inventoryCheckId;

    @ApiModelProperty("Goods  精确匹配")
    private Long itemId;

    @ApiModelProperty("Quantity数量 精确匹配")
    private BigDecimal quantity;

    @ApiModelProperty("盘点数量 精确匹配")
    private BigDecimal checkQuantity;

    @ApiModelProperty("Warehouse 精确匹配")
    private Long warehouseId;

    @ApiModelProperty("Area 精确匹配")
    private Long areaId;

    @ApiModelProperty("Shelf 精确匹配")
    private Long rackId;

}
