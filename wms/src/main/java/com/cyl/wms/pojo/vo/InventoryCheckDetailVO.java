package com.cyl.wms.pojo.vo;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
/**
 * Quantity盘点单据详情 数据视图对象
 *
 * @author zcc
 */
@Data
public class InventoryCheckDetailVO extends BaseAudit {
   /** ID */
    private Long id;
   /** Quantity盘点单 */
    @Excel(name = "Quantity盘点单")
    private Long inventoryCheckId;
   /** Goods  */
    @Excel(name = "Goods ")
    private Long itemId;
   /** Quantity数量 */
    @Excel(name = "Quantity数量")
    private BigDecimal quantity;
   /** 盘点数量 */
    @Excel(name = "盘点数量")
    private BigDecimal checkQuantity;
   /** Warehouse */
    @Excel(name = "Warehouse")
    private Long warehouseId;
   /** Area */
    @Excel(name = "Area")
    private Long areaId;
   /** 货架 */
    @Excel(name = "货架")
    private Long rackId;
   /** Remark */
    @Excel(name = "Remark")
    private String remark;
    private List<Long> place;
}
