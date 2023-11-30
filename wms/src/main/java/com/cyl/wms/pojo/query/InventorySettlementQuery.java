package com.cyl.wms.pojo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Quantity结算单 查询 对象
 *
 * @author zcc
 */
@ApiModel(description="Quantity结算单 查询 对象")
@Data
public class InventorySettlementQuery {
    @ApiModelProperty("Quantity结算单Status11：结算中 22：已完成 精确匹配")
    private Integer inventorySettlementStatus;

    @ApiModelProperty("结算类型,1:月结，2:年结 精确匹配")
    private Integer settlementType;

    @ApiModelProperty("Quantity结算单号 精确匹配")
    private String inventorySettlementNo;

}
