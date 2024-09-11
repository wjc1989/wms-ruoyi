package com.cyl.wms.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * Shelf对象 wms_rack
 *
 * @author zcc
 */
@ApiModel(description="Shelf对象")
@Data
@TableName("wms_rack")
public class Rack extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("Rack No.")
    @Excel(name = "Rack No.")
    private String rackNo;

    @ApiModelProperty("Rack Name.")
    @Excel(name = "Shelf名称")
    private String rackName;

    @ApiModelProperty("所属货区")
    @Excel(name = "所属货区")
    private Long areaId;

    @ApiModelProperty("Warehouse")
    @Excel(name = "Warehouse")
    private Long warehouseId;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

}
