package com.cyl.wms.pojo.query;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Goods 类型表 查询 对象
 *
 * @author zcc
 */
@ApiModel(description="Goods 类型表 查询 对象")
@Data
public class ItemTypeQuery {
    @ApiModelProperty("父Goods 类型id 精确匹配")
    private Long parentId;

    @ApiModelProperty("祖级列表 精确匹配")
    private String ancestors;

    @ApiModelProperty("Goods 类型名称 精确匹配")
    private String typeNameLike;

    @ApiModelProperty("显示顺序 精确匹配")
    private Integer orderNum;

    @ApiModelProperty("Goods 类型状态（0正常 1停用） 精确匹配")
    private String status;

}
