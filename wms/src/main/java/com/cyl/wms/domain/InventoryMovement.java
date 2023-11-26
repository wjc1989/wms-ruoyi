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
@ApiModel(description="Quantity移动对象")
@Data
@TableName("wms_inventory_movement")
public class InventoryMovement extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("Quantity移动编号")
    @Excel(name = "Quantity移动编号")
    private String inventoryMovementNo;

    @ApiModelProperty("原Shelves")
    @Excel(name = "原Shelves")
    private Long sourceRackId;

    @ApiModelProperty("目标货架")
    @Excel(name = "目标货架")
    private Long targetRackId;

    @ApiModelProperty("状态")
    @Excel(name = "状态")
    private Integer status;

    @ApiModelProperty("审核状态")
    @Excel(name = "审核状态")
    private Integer checkStatus;

    @ApiModelProperty("审核人")
    @Excel(name = "审核人")
    private Long checkUserId;

    @ApiModelProperty("审核时间")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkTime;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

}
