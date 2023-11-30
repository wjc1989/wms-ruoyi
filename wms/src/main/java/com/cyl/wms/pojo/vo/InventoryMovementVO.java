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
   * Source Shelves
   */
  @Excel(name = "Source Shelves")
  private Long sourceRackId;
  /**
   * Target Rack
   */
  @Excel(name = "Target Rack")
  private Long targetRackId;
  /**
   * Status
   */
  @Excel(name = "Status")
  private Integer status;
  /**
   * Check Status
   */
  @Excel(name = "Check Status")
  private Integer checkStatus;
  /**
   * Check User
   */
  @Excel(name = "Check User")
  private Long checkUserId;
  /**
   * Check Time
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Excel(name = "Check Time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime checkTime;
  /**
   * Remark
   */
  @Excel(name = "Remark")
  private String remark;
  private Integer detailCount;
  private Integer itemCount;
}
