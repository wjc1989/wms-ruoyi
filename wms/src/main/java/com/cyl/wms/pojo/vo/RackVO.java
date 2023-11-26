package com.cyl.wms.pojo.vo;

import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
/**
 * 货架 数据视图对象
 *
 * @author zcc
 */
@Data
public class RackVO extends BaseAudit {
   /** ID */
    private Long id;
   /** 货架编号 */
    @Excel(name = "货架编号")
    private String rackNo;
   /** 货架名称 */
    @Excel(name = "货架名称")
    private String rackName;
   /** 所属货区 */
    @Excel(name = "所属货区")
    private Long areaId;
   /** Warehouse */
    @Excel(name = "Warehouse")
    private Long warehouseId;
   /** Remark */
    @Excel(name = "Remark")
    private String remark;
}
