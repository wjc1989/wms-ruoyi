package com.cyl.wms.pojo.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Quantity记录 数据视图对象
 *
 * @author zcc
 */
@Data
public class InventoryHistoryVO extends BaseAudit implements AreaAndItemInfo {
    /**
     * ID
     */
    @Excel(name = "编号")
    private Long id;
    /**
     * 操作id（Out、入库、Quantity移动表单id）
     */
    private Long formId;
    /**
     * 操作类型
     */
    private Integer formType;
    /**
     * 操作Type Name
     */
    @Excel(name = "操作类型")
    private String formTypeName;
    /**
     * Goods ID
     */
    private Long itemId;
    @Excel(name = "Goods 名称")
    private String itemName;
    @Excel(name = "Goods 编码")
    private String itemNo;
    /**
     * Shelves
     */
    private Long rackId;
    private String rackName;
    private Long warehouseId;
    @Excel(name = "仓库名称")
    private String warehouseName;
    private Long areaId;

    @Excel(name = "库区名称")
    private String areaName;
    /**
     * Quantity变化
     */
    @Excel(name = "Quantity变化")
    private BigDecimal quantity;
    /**
     * Remark
     */
    @Excel(name = "Remark")
    private String remark;
}
