package com.cyl.wms.pojo.query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Quantity盘点单据 查询 对象
 *
 * @author zcc
 */
@ApiModel(description="Quantity盘点单据 查询 对象")
@Data
public class InventoryCheckQuery {
    @ApiModelProperty("Quantity盘点单号，系统自动生成 精确匹配")
    private String inventoryCheckNo;

    @ApiModelProperty("Quantity盘点类型 精确匹配")
    private Integer inventoryCheckType;

    @ApiModelProperty("Quantity盘点单状态11：盘点中 22：已完成 精确匹配")
    private Integer inventoryCheckStatus;

    @ApiModelProperty("盈亏数 精确匹配")
    private BigDecimal inventoryCheckTotal;

    @ApiModelProperty("审核状态 精确匹配")
    private Integer checkStatus;

    @ApiModelProperty("审核人 精确匹配")
    private Long checkUserId;

    @ApiModelProperty("审核时间 精确匹配")
    private LocalDateTime checkTime;

    @ApiModelProperty("Warehouse 精确匹配")
    private Long warehouseId;

    @ApiModelProperty("Area 精确匹配")
    private Long areaId;

    @ApiModelProperty("货架 精确匹配")
    private Long rackId;

    @ApiModelProperty("附件文件 精确匹配")
    private String attachment;

}
