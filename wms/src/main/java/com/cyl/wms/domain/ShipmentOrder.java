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
 * 出库单对象 wms_shipment_order
 *
 * @author zcc
 */
@ApiModel(description = "出库单对象")
@Data
@TableName("wms_shipment_order")
public class ShipmentOrder extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("出库单号")
    @Excel(name = "出库单号")
    private String shipmentOrderNo;

    @ApiModelProperty("出库类型")
    @Excel(name = "出库类型")
    private Integer shipmentOrderType;

    @ApiModelProperty("出库订单")
    @Excel(name = "出库订单")
    private String orderNo;

    @ApiModelProperty("客户")
    @Excel(name = "客户")
    private Long customerId;

    @ApiModelProperty("应收款合计")
    @Excel(name = "应收款合计")
    private BigDecimal receivableAmount;

    @ApiModelProperty("出库单Status")
    @Excel(name = "出库单Status")
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

    @ApiModelProperty("波次号")
    private String waveNo;

}
