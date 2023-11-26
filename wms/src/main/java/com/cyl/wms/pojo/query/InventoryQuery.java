package com.cyl.wms.pojo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Quantity 查询 对象
 *
 * @author zcc
 */
@ApiModel(description = "Quantity 查询 对象")
@Data
public class InventoryQuery implements InventoryPanelType {
    @ApiModelProperty("Goods ID")
    private Long itemId;

    @ApiModelProperty("Shelves")
    private Long warehouseId;

    @ApiModelProperty("Dashboard Type")
    private Long panelType;

    @ApiModelProperty("Area ID")
    private Long areaId;

    @ApiModelProperty("Shelves ID")
    private Long rackId;

    @ApiModelProperty("Quantity Begin")
    private BigDecimal quantityStart;
    @ApiModelProperty("Quantity End")
    private BigDecimal quantityEnd;

}
