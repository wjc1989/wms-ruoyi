package com.cyl.wms.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 货区对象 wms_area
 *
 * @author zcc
 */
@ApiModel(description="货区对象")
@Data
@TableName("wms_area")
public class Area extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("货区编号")
    @Excel(name = "货区编号")
    private String areaNo;

    @ApiModelProperty("货区名称")
    @Excel(name = "货区名称")
    private String areaName;

    @ApiModelProperty("WarehouseID")
    @Excel(name = "WarehouseID")
    private Long warehouseId;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

}
