package com.cyl.wms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Shipment Order对象 wms_shipment_order
 *
 * @author zcc
 */
@ApiModel(description = "Shipment Order对象")
@Data
@TableName("wms_shipment_order")
public class ShipmentOrder extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("ShipmentOrder No")
    @Excel(name = "ShipmentOrder No")
    private String shipmentOrderNo;

    @ApiModelProperty("ShipmentOrder Type")
    @Excel(name = "ShipmentOrder Type")
    private Integer shipmentOrderType;

    @ApiModelProperty("Order No")
    @Excel(name = "Order No")
    private String orderNo;

    @ApiModelProperty("Customer")
    @Excel(name = "Customer")
    private Long customerId;

    @ApiModelProperty("Receivable Amount")
    @Excel(name = "Receivable Amount")
    private BigDecimal receivableAmount;

    @ApiModelProperty("ShipmentOrder Status")
    @Excel(name = "ShipmentOrder Status")
    private Integer shipmentOrderStatus;

    @ApiModelProperty("Check Status")
    @Excel(name = "Check Status")
    private Integer checkStatus;

    @ApiModelProperty("Check User")
    @Excel(name = "Check User")
    private Long checkUserId;

    @ApiModelProperty("Check Time")
    @Excel(name = "Check Time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkTime;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

    @ApiModelProperty("Wave")
    private String waveNo;

}
