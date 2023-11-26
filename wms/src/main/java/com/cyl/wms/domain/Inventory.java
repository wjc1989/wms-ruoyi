package com.cyl.wms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyl.wms.pojo.vo.PlaceAndItem;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Quantity wms_inventory
 *
 * @author zcc
 */
@ApiModel(description = "Quantity")
@Data
@TableName("wms_inventory")
public class Inventory extends BaseAudit implements PlaceAndItem {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("Goods NO.")
    @Excel(name = "Goods NO.")
    private Long itemId;

    @ApiModelProperty("Warehouse")
    @Excel(name = "Warehouse")
    private Long warehouseId;

    @ApiModelProperty("Area")
    @Excel(name = "Area")
    private Long areaId;

    @ApiModelProperty("Shelves")
    @Excel(name = "Shelves")
    private Long rackId;

    @ApiModelProperty("Quantity")
    @Excel(name = "Quantity")
    private BigDecimal quantity;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

    @TableField(exist = false)
    private String itemNo;

    @TableField(exist = false)
    private String itemName;

    @TableField(exist = false)
    private String warehouseName;

    @TableField(exist = false)
    private String areaName;

}
