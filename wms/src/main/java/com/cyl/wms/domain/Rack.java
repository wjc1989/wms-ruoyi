package com.cyl.wms.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 货架对象 wms_rack
 *
 * @author zcc
 */
@ApiModel(description="货架对象")
@Data
@TableName("wms_rack")
public class Rack extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("货架编号")
    @Excel(name = "货架编号")
    private String rackNo;

    @ApiModelProperty("货架名称")
    @Excel(name = "货架名称")
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
