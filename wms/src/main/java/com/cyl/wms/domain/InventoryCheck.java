package com.cyl.wms.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * Quantity盘点单据对象 wms_inventory_check
 *
 * @author zcc
 */
@ApiModel(description="Quantity盘点单据对象")
@Data
@TableName("wms_inventory_check")
public class InventoryCheck extends BaseAudit {
    private static final long serialVersionUID = 1L;

    public static String CREATED = "11";
    public static String FINISH = "22";

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("Quantity盘点单号，系统自动生成")
    @Excel(name = "Quantity盘点单号，系统自动生成")
    private String inventoryCheckNo;

    @ApiModelProperty("Quantity盘点类型")
    @Excel(name = "Quantity盘点类型")
    private Integer inventoryCheckType;

    @ApiModelProperty("Quantity盘点单Status11：盘点中 22：已完成")
    @Excel(name = "Quantity盘点单Status11：盘点中 22：已完成")
    private Integer inventoryCheckStatus;

    @ApiModelProperty("盈亏数")
    @Excel(name = "盈亏数")
    private BigDecimal inventoryCheckTotal;

    @ApiModelProperty("Check Status")
    @Excel(name = "Check Status")
    private Integer checkStatus;

    @ApiModelProperty("Check User")
    @Excel(name = "Check User")
    private Long checkUserId;

    @ApiModelProperty("Check Time")
    @Excel(name = "Check Time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkTime;

    @ApiModelProperty("Warehouse")
    @Excel(name = "Warehouse")
    private Long warehouseId;

    @ApiModelProperty("Area")
    @Excel(name = "Area")
    private Long areaId;

    @ApiModelProperty("Shelf")
    @Excel(name = "Shelf")
    private Long rackId;

    @ApiModelProperty("附件文件")
    @Excel(name = "附件文件")
    private String attachment;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

    @TableField(exist = false)
    private List<Long> place;

}
