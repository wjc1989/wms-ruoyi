package com.cyl.wms.pojo.query;

import java.math.BigDecimal;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Shipment Order详情 查询 对象
 *
 * @author zcc
 */
@ApiModel(description="Shipment Order详情 查询 对象")
@Data
public class ShipmentOrderDetailQuery {
    @ApiModelProperty("Shipment Order 精确匹配")
    private Long shipmentOrderId;

    @ApiModelProperty("Goods  精确匹配")
    private Long itemId;

    @ApiModelProperty("Plan Quantity 精确匹配")
    private BigDecimal planQuantity;

    @ApiModelProperty("Real Quantity 精确匹配")
    private BigDecimal realQuantity;

    @ApiModelProperty("Shelf 精确匹配")
    private Long rackId;

}
