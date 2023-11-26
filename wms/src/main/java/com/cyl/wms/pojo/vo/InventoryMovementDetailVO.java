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
  @Excel(name = "Quantity移动Id")
  private Long inventoryMovementId;
  /**
   * Goods
   */
  @Excel(name = "Goods ")
  private Long itemId;
  /**
   * 计划数量
   */
  @Excel(name = "计划数量")
  private BigDecimal planQuantity;
  /**
   * 实际数量
   */
  @Excel(name = "实际数量")
  private BigDecimal realQuantity;
  /**
   * Remark
   */
  @Excel(name = "Remark")
  private String remark;
  /**
   * 源货架
   */
  @Excel(name = "源货架")
  private Long sourceRackId;
  /**
   * 源仓库
   */
  @Excel(name = "源仓库")
  private Long sourceWarehouseId;
  /**
   * 源库区
   */
  @Excel(name = "源库区")
  private Long sourceAreaId;
  /**
   * 移库状态
   */
  @Excel(name = "移库状态")
  private Integer moveStatus;
  /**
   * 目标货架
   */
  @Excel(name = "目标货架")
  private Long targetRackId;
  /**
   * 目标仓库
   */
  @Excel(name = "目标仓库")
  private Long targetWarehouseId;
  /**
   * 目标库区
   */
  @Excel(name = "目标库区")
  private Long targetAreaId;

  private List<Long> sourcePlace;
  private List<Long> targetPlace;

  private Integer delFlag;
}
