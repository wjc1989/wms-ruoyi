package com.cyl.wms.pojo.vo;

import java.time.LocalDateTime;

import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;

/**
 * Quantity移动 数据视图对象
 *
 * @author zcc
 */
@Data
public class InventoryMovementVO extends BaseAudit {
  /**
   * ID
   */
  private Long id;
  /**
   * Quantity移动编号
   */
  @Excel(name = "Quantity移动编号")
  private String inventoryMovementNo;
  /**
   * 原Shelves
   */
  @Excel(name = "原Shelves")
  private Long sourceRackId;
  /**
   * 目标货架
   */
  @Excel(name = "目标货架")
  private Long targetRackId;
  /**
   * 状态
   */
  @Excel(name = "状态")
  private Integer status;
  /**
   * 审核状态
   */
  @Excel(name = "审核状态")
  private Integer checkStatus;
  /**
   * 审核人
   */
  @Excel(name = "审核人")
  private Long checkUserId;
  /**
   * 审核时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime checkTime;
  /**
   * Remark
   */
  @Excel(name = "Remark")
  private String remark;
  private Integer detailCount;
  private Integer itemCount;
}
