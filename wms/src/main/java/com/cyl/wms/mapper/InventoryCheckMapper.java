package com.cyl.wms.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.cyl.wms.domain.InventoryCheck;

/**
 * Quantity盘点单据Mapper接口
 *
 * @author zcc
 */
public interface InventoryCheckMapper extends BaseMapper<InventoryCheck> {
    /**
     * 查询Quantity盘点单据列表
     *
     * @param inventoryCheck Quantity盘点单据
     * @return Quantity盘点单据集合
     */
    List<InventoryCheck> selectByEntity(InventoryCheck inventoryCheck);

    /**
     * 批量软删除
     * @param ids
     * @return
    */
    int updateDelFlagByIds(@Param("ids") Long[] ids);
}
