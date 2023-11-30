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

    @ApiModelProperty("Goods Type Name 精确匹配")
    private String typeNameLike;

    @ApiModelProperty("Sort 精确匹配")
    private Integer orderNum;

    @ApiModelProperty("Status 精确匹配")
    private String status;

}
