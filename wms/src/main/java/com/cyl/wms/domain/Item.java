package com.cyl.wms.domain;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import com.ruoyi.common.utils.DateUtils;
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
@ApiModel(description = "Goods")
@Data
@TableName("wms_item")
public class Item extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("No.")
    @Excel(name = "No.")
    private String itemNo;

    @ApiModelProperty("Name")
    @Excel(name = "Name")
    private String itemName;

    @ApiModelProperty("Category")
    @Excel(name = "Category")
    private String itemType;

    @ApiModelProperty("Unit")
    @Excel(name = "Unit")
    private String unit;

    @ApiModelProperty("Rack")
    @Excel(name = "Rack")
    private Long rackId;

    @ApiModelProperty("Area")
    @Excel(name = "Area")
    private Long areaId;

    @ApiModelProperty("Warehouse")
    @Excel(name = "Warehouse")
    private Long warehouseId;

    @ApiModelProperty("Safe Quantity")
    @Excel(name = "Safe Quantity")
    private BigDecimal quantity;

    @ApiModelProperty("Expiry Date")
    @Excel(name = "Expiry Date", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiryDate;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;
    private String pics;
    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

    @TableField(exist = false)
    private String itemTypeName;

    @TableField(exist = false)
    private String warehouseName;

    @TableField(exist = false)
    private String areaName;

    @TableField(exist = false)
    private BigDecimal inventory;

    private String codePath;


    public String getCode() {
        if(id==null){
            return null;
        }
        String dateTimeNow= DateUtils.dateTimeNow("yyMMdd");
        int idLength=(id+"").length();
        String code=null;
        //如果长度>6，拼日期超出13位(一维码最大长度)，就不拼日期了
        if(idLength>6){
            code= StrUtil.fillBefore(id+"",'0',12);
        }else{
            code=dateTimeNow+StrUtil.fillBefore(id+"",'0',6);
        }
        return code;
    }
}
