package com.cyl.wms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Goods 对象 wms_item
 *
 * @author zcc
 */
@ApiModel(description = "Goods 对象")
@Data
@TableName("wms_item")
public class Item extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("编号")
    @Excel(name = "编号")
    private String itemNo;

    @ApiModelProperty("名称")
    @Excel(name = "名称")
    private String itemName;

    @ApiModelProperty("分类")
    @Excel(name = "分类")
    private String itemType;

    @ApiModelProperty("单位类别")
    @Excel(name = "单位类别")
    private String unit;

    @ApiModelProperty("所属货架")
    @Excel(name = "所属货架")
    private Long rackId;

    @ApiModelProperty("Area")
    @Excel(name = "Area")
    private Long areaId;

    @ApiModelProperty("Warehouse")
    @Excel(name = "Warehouse")
    private Long warehouseId;

    @ApiModelProperty("安全Quantity")
    @Excel(name = "安全Quantity")
    private BigDecimal quantity;

    @ApiModelProperty("有效期")
    @Excel(name = "有效期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiryDate;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;


    @TableField(exist = false)
    private String itemTypeName;

    @TableField(exist = false)
    private String warehouseName;

    @TableField(exist = false)
    private String areaName;
}
