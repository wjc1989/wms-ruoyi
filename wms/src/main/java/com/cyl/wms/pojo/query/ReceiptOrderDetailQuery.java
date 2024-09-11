package com.cyl.wms.pojo.query;

import java.math.BigDecimal;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 入库单详情 查询 对象
 *
 * @author zcc
 */
@ApiModel(description="入库单详情 查询 对象")
@Data
public class ReceiptOrderDetailQuery {
    @ApiModelProperty("入库单号 精确匹配")
    private Long receiptOrderId;

    @ApiModelProperty("Goods  精确匹配")
    private Long itemId;

    @ApiModelProperty("Plan Quantity 精确匹配")
    private BigDecimal planQuantity;

    @ApiModelProperty("Real Quantity 精确匹配")
    private BigDecimal realQuantity;

    @ApiModelProperty("所属Shelf 精确匹配")
    private Long rackId;

    @ApiModelProperty("Warehouse 精确匹配")
    private Long warehouseId;

    @ApiModelProperty("Area 精确匹配")
    private Long areaId;

    @ApiModelProperty("入库Status 精确匹配")
    private Integer receiptOrderStatus;

}
