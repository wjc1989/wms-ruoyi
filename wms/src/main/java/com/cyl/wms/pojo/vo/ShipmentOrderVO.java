package com.cyl.wms.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Shipment Order 数据视图对象
 *
 * @author zcc
 */
@Data
public class ShipmentOrderVO extends BaseAudit {
  /**
   * ID
   */
  private Long id;
  /**
   * Shipment Order号，系统自动生成
   */
  @Excel(name = "Shipment Order号，系统自动生成")
  private String shipmentOrderNo;
  /**
   * Out类型
   */
  @Excel(name = "Out类型")
  private Integer shipmentOrderType;
  /**
   * Out订单
   */
  @Excel(name = "Out订单")
  private String orderNo;
  /**
   * 客户
   */
  @Excel(name = "客户")
  private Long customerId;
  /**
   * Shipment OrderStatus
   */
  @Excel(name = "Shipment OrderStatus")
  private Integer shipmentOrderStatus;
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

  @Excel(name = "波次号")
  private String waveNo;

  private Integer detailCount;

}
