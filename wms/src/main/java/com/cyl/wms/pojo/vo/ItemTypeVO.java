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
   /** Goods 类型名称 */
    @Excel(name = "Goods 类型名称")
    private String typeName;
   /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer orderNum;
   /** Goods 类型状态（0正常 1停用） */
    @Excel(name = "Goods 类型状态", readConverterExp = "0=正常,1=停用")
    private String status;
}
