package com.cyl.wms.pojo.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
/**
 * Quantity结算单 数据视图对象
 *
 * @author zcc
 */
@Data
public class InventorySettlementVO extends BaseAudit {
   /** ID */
    private Long id;
   /** Quantity结算单Status11：结算中 22：已完成 */
    @Excel(name = "Quantity结算单Status11：结算中 22：已完成")
    private Integer inventorySettlementStatus;
   /** 结算类型,1:月结，2:年结 */
    @Excel(name = "结算类型,1:月结，2:年结")
    private Integer settlementType;
   /** Remark */
    @Excel(name = "Remark")
    private String remark;
   /** Quantity结算单号 */
    @Excel(name = "Quantity结算单号")
    private String inventorySettlementNo;
}
