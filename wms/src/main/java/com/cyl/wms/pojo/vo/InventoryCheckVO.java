package com.cyl.wms.pojo.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
/**
 * Quantity盘点单据 数据视图对象
 *
 * @author zcc
 */
@Data
public class InventoryCheckVO extends BaseAudit {
   /** ID */
    private Long id;
   /** Quantity盘点单号，系统自动生成 */
    @Excel(name = "Quantity盘点单号，系统自动生成")
    private String inventoryCheckNo;
   /** Quantity盘点类型 */
    @Excel(name = "Quantity盘点类型")
    private Integer inventoryCheckType;
   /** Quantity盘点单Status11：盘点中 22：已完成 */
    @Excel(name = "Quantity盘点单Status11：盘点中 22：已完成")
    private Integer inventoryCheckStatus;
   /** 盈亏数 */
    @Excel(name = "盈亏数")
    private BigDecimal inventoryCheckTotal;
   /** Check Status */
    @Excel(name = "Check Status")
    private Integer checkStatus;
   /** Check User */
    @Excel(name = "Check User")
    private Long checkUserId;
   /** Check Time */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "Check Time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkTime;
   /** Warehouse */
    @Excel(name = "Warehouse")
    private Long warehouseId;
   /** Area */
    @Excel(name = "Area")
    private Long areaId;
   /** Shelf */
    @Excel(name = "Shelf")
    private Long rackId;
   /** 附件文件 */
    @Excel(name = "附件文件")
    private String attachment;
   /** Remark */
    @Excel(name = "Remark")
    private String remark;
}
