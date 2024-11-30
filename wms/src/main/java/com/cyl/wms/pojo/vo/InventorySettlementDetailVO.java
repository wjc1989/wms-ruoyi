package com.cyl.wms.pojo.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;

import java.math.BigDecimal;
/**
 * Quantity结算明细 数据视图对象
 *
 * @author zcc
 */
@Data
public class InventorySettlementDetailVO extends BaseAudit {
   /** ID */
    private Long id;
   /** 结算单id */
    @Excel(name = "结算单id")
    private Long settlementId;
   /** 结算类型,1:月结，2:年结 */
    @Excel(name = "结算类型,1:月结，2:年结")
    private Integer settlementType;
   /** Goods id */
    @Excel(name = "Goods id")
    private Long itemId;
   /** 编号 */
    @Excel(name = "编号")
    private String itemNo;
   /** 名称 */
    @Excel(name = "名称")
    private String itemName;
   /** 仓库id */
    @Excel(name = "仓库id")
    private Long warehouseId;
   /** 仓库编号 */
    @Excel(name = "仓库编号")
    private String warehouseNo;
   /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String warehouseName;
   /** 库区id */
    @Excel(name = "库区id")
    private Long areaId;
   /** 库区编号 */
    @Excel(name = "库区编号")
    private String areaNo;
   /** 库区名称 */
    @Excel(name = "库区名称")
    private String areaName;
   /** 上期结存 */
    @Excel(name = "上期结存")
    private BigDecimal previousBalance;
   /** 本期入库 */
    @Excel(name = "本期入库")
    private BigDecimal currentEnter;
   /** 本期Out */
    @Excel(name = "本期Out")
    private BigDecimal currentOut;
   /** 本期盘点 */
    @Excel(name = "本期盘点")
    private BigDecimal currentCheck;
   /** 本期结存 */
    @Excel(name = "本期结存")
    private BigDecimal currentBalance;
   /** Remark */
    @Excel(name = "Remark")
    private String remark;
}
