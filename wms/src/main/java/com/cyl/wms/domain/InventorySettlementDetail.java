package com.cyl.wms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
/**
 * Quantity结算明细对象 wms_inventory_settlement_detail
 *
 * @author zcc
 */
@ApiModel(description="Quantity结算明细对象")
@Data
@TableName("wms_inventory_settlement_detail")
public class InventorySettlementDetail extends BaseAudit {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("结算单id")
    @Excel(name = "结算单id")
    private Long settlementId;

    @ApiModelProperty("结算类型,1:月结，2:年结")
    @Excel(name = "结算类型,1:月结，2:年结")
    private Integer settlementType;

    @ApiModelProperty("Goods id")
    @Excel(name = "Goods id")
    private Long itemId;

    @ApiModelProperty("编号")
    @Excel(name = "编号")
    private String itemNo;

    @ApiModelProperty("名称")
    @Excel(name = "名称")
    private String itemName;

    @ApiModelProperty("仓库id")
    @Excel(name = "仓库id")
    private Long warehouseId;

    @ApiModelProperty("仓库编号")
    @Excel(name = "仓库编号")
    private String warehouseNo;

    @ApiModelProperty("仓库名称")
    @Excel(name = "仓库名称")
    private String warehouseName;

    @ApiModelProperty("库区id")
    @Excel(name = "库区id")
    private Long areaId;

    @ApiModelProperty("库区编号")
    @Excel(name = "库区编号")
    private String areaNo;

    @ApiModelProperty("库区名称")
    @Excel(name = "库区名称")
    private String areaName;

    @ApiModelProperty("上期结存")
    @Excel(name = "上期结存")
    private BigDecimal previousBalance;

    @ApiModelProperty("本期入库")
    @Excel(name = "本期入库")
    private BigDecimal currentEnter;

    @ApiModelProperty("本期Out")
    @Excel(name = "本期Out")
    private BigDecimal currentOut;

    @ApiModelProperty("本期盘点")
    @Excel(name = "本期盘点")
    private BigDecimal currentCheck;

    @ApiModelProperty("本期结存")
    @Excel(name = "本期结存")
    private BigDecimal currentBalance;

    @ApiModelProperty("Delete Flag")
    private Integer delFlag;

    @ApiModelProperty("Remark")
    @Excel(name = "Remark")
    private String remark;

}
