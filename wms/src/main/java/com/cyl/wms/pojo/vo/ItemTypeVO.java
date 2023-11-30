package com.cyl.wms.pojo.vo;

import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
/**
 * Goods 类型表 数据视图对象
 *
 * @author zcc
 */
@Data
public class ItemTypeVO extends BaseAudit {
   /** Goods 类型id */
    private Long itemTypeId;
   /** 父Goods 类型id */
    @Excel(name = "父Goods 类型id")
    private Long parentId;
   /** 祖级列表 */
    @Excel(name = "祖级列表")
    private String ancestors;
   /** Goods Type Name */
    @Excel(name = "Goods Type Name")
    private String typeName;
   /** Sort */
    @Excel(name = "Sort")
    private Integer orderNum;
   /** Status */
    @Excel(name = "Goods 类型Status", readConverterExp = "0=Normal,1=Disabled")
    private String status;
}
