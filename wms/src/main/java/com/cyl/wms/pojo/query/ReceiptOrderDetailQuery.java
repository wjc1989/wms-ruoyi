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

    @ApiModelProperty("计划数量 精确匹配")
    private BigDecimal planQuantity;

    @ApiModelProperty("实际数量 精确匹配")
    private BigDecimal realQuantity;

    @ApiModelProperty("所属货架 精确匹配")
    private Long rackId;

    @ApiModelProperty("Warehouse 精确匹配")
    private Long warehouseId;

    @ApiModelProperty("Area 精确匹配")
    private Long areaId;

    @ApiModelProperty("入库Status 精确匹配")
    private Integer receiptOrderStatus;

}
