package com.cyl.wms.pojo.vo;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;

/**
 * Quantity移动详情 数据视图对象
 *
 * @author zcc
 */
@Data
public class InventoryMovementDetailVO extends BaseAudit {
  /**
   * ID
   */
  private Long id;
  /**
   * Quantity移动Id
   */
  @Excel(name = "ID")
  private Long inventoryMovementId;
  /**
   * Goods
   */
  @Excel(name = "Goods")
  private Long itemId;
  /**
   * Plan Quantity
   */
  @Excel(name = "Plan Quantity")
  private BigDecimal planQuantity;
  /**
   * Real Quantity
   */
  @Excel(name = "Real Quantity")
  private BigDecimal realQuantity;
  /**
   * Remark
   */
  @Excel(name = "Remark")
  private String remark;
  /**
   * 源Shelf
   */
  @Excel(name = "Source Rack")
  private Long sourceRackId;
  /**
   * 源仓库
   */
  @Excel(name = "Source Warehouse")
  private Long sourceWarehouseId;
  /**
   * 源库区
   */
  @Excel(name = "Source Area")
  private Long sourceAreaId;
  /**
   * 移库Status
   */
  @Excel(name = "Status")
  private Integer moveStatus;
  /**
   * Target Rack
   */
  @Excel(name = "Target Rack")
  private Long targetRackId;
  /**
   * 目标仓库
   */
  @Excel(name = "Target Warehouse")
  private Long targetWarehouseId;
  /**
   * 目标库区
   */
  @Excel(name = "Target Area")
  private Long targetAreaId;

  private List<Long> sourcePlace;
  private List<Long> targetPlace;

  private Integer delFlag;
}
