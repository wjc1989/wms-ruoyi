package com.cyl.wms.pojo.vo;

import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseAudit;
import lombok.Data;
/**
 * 波次 数据视图对象
 *
 * @author zhangcheng
 */
@Data
public class WaveVO extends BaseAudit {
   /** ID */
    private Long id;
   /** 波次号 */
    @Excel(name = "波次号")
    private String waveNo;
   /** Status */
    @Excel(name = "Status")
    private String status;
   /** Remark */
    @Excel(name = "Remark")
    private String remark;
}
