package com.cyl.wms.pojo.query;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Shelf 查询 对象
 *
 * @author zcc
 */
@ApiModel(description="Shelf 查询 对象")
@Data
public class RackQuery {
    @ApiModelProperty("Shelf编号 精确匹配")
    private String rackNo;

    @ApiModelProperty("Shelf名称 精确匹配")
    private String rackNameLike;

    @ApiModelProperty("所属货区 精确匹配")
    private Long areaId;

    @ApiModelProperty("Warehouse 精确匹配")
    private Long warehouseId;

}
