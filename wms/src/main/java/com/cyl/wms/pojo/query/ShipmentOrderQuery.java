package com.cyl.wms.pojo.query;

import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Shipment Order 查询 对象
 *
 * @author zcc
 */
@ApiModel(description="Shipment Order 查询 对象")
@Data
public class ShipmentOrderQuery {
    @ApiModelProperty("Shipment Order号，系统自动生成 精确匹配")
    private String shipmentOrderNo;

    @ApiModelProperty("Out类型 精确匹配")
    private Integer shipmentOrderType;

    @ApiModelProperty("Out订单 精确匹配")
    private String orderNo;

    @ApiModelProperty("客户 精确匹配")
    private Long customerId;

    @ApiModelProperty("Shipment OrderStatus 精确匹配")
    private Integer shipmentOrderStatus;

}
