package com.cyl.wms.pojo.vo;

import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
/**
 * 货区 数据视图对象
 *
 * @author zcc
 */
@Data
public class AreaVO extends BaseAudit {
   /** ID */
    private Long id;
   /** 货区编号 */
    @Excel(name = "货区编号")
    private String areaNo;
   /** 货区名称 */
    @Excel(name = "货区名称")
    private String areaName;
   /** WarehouseID */
    @Excel(name = "WarehouseID")
    private Long warehouseId;
   /** Remark */
    @Excel(name = "Remark")
    private String remark;
}
