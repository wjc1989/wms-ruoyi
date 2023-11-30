package com.cyl.wms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.ArrayList;

/**
 * 波次对象 wms_wave
 *
 * @author zhangcheng
 */
@ApiModel(description = "波次对象")
@Data
@TableName("wms_wave")
public class Wave extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("波次号")
    @Excel(name = "波次号")
    private String waveNo;

    @ApiModelProperty("Status")
    @Excel(name = "Status")
    private String status;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

    @ApiModelProperty("类型")
    @Excel(name = "类型")
    private Integer type;


    @TableField(exist = false)
    private ArrayList<Long> ids;

}
