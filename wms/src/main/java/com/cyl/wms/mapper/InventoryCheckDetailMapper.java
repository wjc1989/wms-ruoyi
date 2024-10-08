package com.cyl.wms.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.cyl.wms.domain.InventoryCheckDetail;

/**
 * Quantity盘点单据详情Mapper接口
 *
 * @author zcc
 */
public interface InventoryCheckDetailMapper extends BaseMapper<InventoryCheckDetail> {
    /**
     * 查询Quantity盘点单据详情列表
     *
     * @param inventoryCheckDetail Quantity盘点单据详情
     * @return Quantity盘点单据详情集合
     */
    List<InventoryCheckDetail> selectByEntity(InventoryCheckDetail inventoryCheckDetail);

    /**
     * 批量软删除
     * @param ids
     * @return
    */
    int updateDelFlagByIds(@Param("ids") Long[] ids);

    /**
     * 批量插入
     * @param inventoryCheckDetails Quantity盘点单据详情
     * @return 插入结果
     */
    int batchInsert(List<InventoryCheckDetail> inventoryCheckDetails);
}
