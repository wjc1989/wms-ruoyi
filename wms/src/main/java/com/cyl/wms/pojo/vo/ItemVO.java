package com.cyl.wms.pojo.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseAudit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * Goods  数据视图对象
 *
 * @author zcc
 */
@Data
public class ItemVO extends BaseAudit {
   /** ID */
    private Long id;
   /** 编号 */
    @Excel(name = "No.")
    private String itemNo;
   /** 名称 */
    @Excel(name = "Name")
    private String itemName;
   /** 分类 */
    private String itemType;
    /**
     * 所属类别
     */
    @Excel(name = "Category")
    private String itemTypeName;
   /** Unit */
    @Excel(name = "Unit")
    private String unit;
   /** 所属Shelf */
    private Long rackId;
   /** Area */
    private Long areaId;
    /** Warehouse名称 */
    @Excel(name = "Warehouse")
    private String warehouseName;
    /** Area名称 */
    @Excel(name = "Area")
    private String areaName;
   /** Warehouse */
    private Long warehouseId;
   /** Safty Quantity */
    @Excel(name = "Safty Quantity")
    private BigDecimal quantity;
   /** 有效期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "Expiry Date", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiryDate;
   /** Remark */
    @Excel(name = "Remark")
    private String remark;
    private String pics;
    private List<Long> place;
    private Integer delFlag;

    private BigDecimal inventory;
    private String codePath;

}
