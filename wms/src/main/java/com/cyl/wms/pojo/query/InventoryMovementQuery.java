package com.cyl.wms.pojo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Quantity移动 查询 对象
 *
 * @author zcc
 */
@ApiModel(description="Quantity移动 查询 对象")
@Data
public class InventoryMovementQuery {
    @ApiModelProperty("Quantity移动编号 精确匹配")
    private String inventoryMovementNo;

    @ApiModelProperty("Status 精确匹配")
    private Integer status;

}
