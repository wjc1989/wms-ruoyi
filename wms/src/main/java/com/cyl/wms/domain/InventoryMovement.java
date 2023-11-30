package com.cyl.wms.domain;

import java.time.LocalDateTime;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * Quantity移动对象 wms_inventory_movement
 *
 * @author zcc
 */
@ApiModel(description="Inventory Move")
@Data
@TableName("wms_inventory_movement")
public class InventoryMovement extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("No.")
    @Excel(name = "No.")
    private String inventoryMovementNo;

    @ApiModelProperty("Source Rack")
    @Excel(name = "Source Rack")
    private Long sourceRackId;

    @ApiModelProperty("Target Rack")
    @Excel(name = "Target Rack")
    private Long targetRackId;

    @ApiModelProperty("Status")
    @Excel(name = "Status")
    private Integer status;

    @ApiModelProperty("Check Status")
    @Excel(name = "Check Status")
    private Integer checkStatus;

    @ApiModelProperty("Check User")
    @Excel(name = "Check User")
    private Long checkUserId;

    @ApiModelProperty("Check Time")
    @Excel(name = "Check Time", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkTime;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

}
