package com.cyl.wms.pojo.query;

import java.math.BigDecimal;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Quantity记录 查询 对象
 *
 * @author zcc
 */
@ApiModel(description="Quantity记录 查询 对象")
@Data
public class InventoryHistoryQuery {
    @ApiModelProperty("操作id（Out、入库、Quantity移动表单id） 精确匹配")
    private Long formId;

    @ApiModelProperty("操作类型 精确匹配")
    private Integer formType;

    @ApiModelProperty("Goods ID 精确匹配")
    private Long itemId;

    @ApiModelProperty("Rack 精确匹配")
    private Long warehouseId;
    private Long areaId;
    private Long rackId;

    @ApiModelProperty("Quantity变化 精确匹配")
    private BigDecimal quantity;

}
