package com.cyl.wms.pojo.vo;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
/**
 * 入库单详情 数据视图对象
 *
 * @author zcc
 */
@Data
public class ReceiptOrderDetailVO extends BaseAudit {
   /** ID */
    private Long id;
   /** 入库单号 */
    @Excel(name = "In bround No.")
    private Long receiptOrderId;
    @Excel(name = "Project")
    private String orderNo;
   /** Goods  */
    @Excel(name = "Goods")
    private Long itemId;
   /** 计划数量 */
    @Excel(name = "Plan Quantity")
    private BigDecimal planQuantity;
   /** 实际数量 */
    @Excel(name = "Real Quantity")
    private BigDecimal realQuantity;
   /** 所属货架 */
    @Excel(name = "Shelf ID")
    private Long rackId;
    @Excel(name = "Aumont")
    private BigDecimal money;
   /** Remark */
    @Excel(name = "Remark")
    private String remark;
   /** Warehouse */
    @Excel(name = "Warehouse")
    private Long warehouseId;
   /** Area */
    @Excel(name = "Area")
    private Long areaId;
   /** 入库Status */
    @Excel(name = "Status")
    private Integer receiptOrderStatus;

    private Integer delFlag;

    private String contact;

    private String phone;

    private List<Long> place;
}
