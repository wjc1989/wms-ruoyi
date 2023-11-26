package com.cyl.wms.pojo.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Quantity 数据视图对象
 *
 * @author zcc
 */
@Data
public class InventoryVO extends BaseAudit implements AreaAndItemInfo {
    /**
     * ID
     */
    private Long id;
    /**
     * Goods ID
     */
    private Long itemId;
    // Goods 编号
    @Excel(name = "Goods No.")
    private String itemNo;
    // Goods 名称
    @Excel(name = "Goods Name")
    private String itemName;
    // Goods 类型
    @Excel(name = "Category")
    private String itemTypeName;
    /**
     * Shelves
     */
    private Long rackId;
    // 货架 名称
    private String rackName;
    private Long warehouseId;
    // 仓库 名称
    @Excel(name = "Wharehouse")
    private String warehouseName;
    private Long areaId;
    // 库区 名称
    @Excel(name = "Area")
    private String areaName;
    /**
     * Quantity
     */
    @Excel(name = "Quantity")
    private BigDecimal quantity;
    /**
     * Remark
     */
    @Excel(name = "Remark")
    private String remark;
    private BigDecimal saftyQuantity;

    /**
     * Goods Delete Flag
     */
    private Integer itemDelFlag;

    /**
     * 仓库Delete Flag
     */
    private Integer warehouseDelFlag;
}
