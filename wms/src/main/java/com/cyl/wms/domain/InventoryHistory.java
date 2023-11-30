package com.cyl.wms.domain;

import java.math.BigDecimal;

import com.cyl.wms.pojo.vo.PlaceAndItem;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * Quantity记录对象 wms_inventory_history
 *
 * @author zcc
 */
@ApiModel(description="Quantity记录对象")
@Data
@TableName("wms_inventory_history")
public class InventoryHistory extends BaseAudit implements PlaceAndItem {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("操作id（出库、入库、Quantity移动表单id）")
    @Excel(name = "操作id", readConverterExp = "出库、入库、Quantity移动表单id")
    private Long formId;

    @ApiModelProperty("操作类型")
    @Excel(name = "操作类型")
    private Integer formType;

    @ApiModelProperty("Goods ID")
    @Excel(name = "Goods ID")
    private Long itemId;

    @ApiModelProperty("Warehouse")
    @Excel(name = "Warehouse")
    private Long warehouseId;

    @ApiModelProperty("Area")
    @Excel(name = "Area")
    private Long areaId;

    @ApiModelProperty("Rack")
    @Excel(name = "Rack")
    private Long rackId;

    @ApiModelProperty("Quantity变化")
    @Excel(name = "Quantity变化")
    private BigDecimal quantity;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

}
