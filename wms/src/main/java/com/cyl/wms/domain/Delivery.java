package com.cyl.wms.domain;

import java.time.LocalDateTime;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 发货记录对象 wms_delivery
 *
 * @author zcc
 */
@ApiModel(description="发货记录对象")
@Data
@TableName("wms_delivery")
public class Delivery extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("Shipment Order主表Id")
    @Excel(name = "Shipment Order主表Id")
    private Long shipmentOrderId;

    @ApiModelProperty("承运商Id")
    @Excel(name = "承运商Id")
    private Long carrierId;

    @ApiModelProperty("发货日期")
    @Excel(name = "发货日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deliveryDate;

    @ApiModelProperty("快递单号")
    @Excel(name = "快递单号")
    private String trackingNo;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

}
