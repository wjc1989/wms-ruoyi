package com.cyl.wms.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.cyl.wms.domain.InventoryMovement;

/**
 * Quantity移动Mapper接口
 *
 * @author zcc
 */
public interface InventoryMovementMapper extends BaseMapper<InventoryMovement> {
    /**
     * 查询Quantity移动列表
     *
     * @param inventoryMovement Quantity移动
     * @return Quantity移动集合
     */
    List<InventoryMovement> selectByEntity(InventoryMovement inventoryMovement);

    /**
     * 批量软删除
     * @param ids
     * @return
    */
    int updateDelFlagByIds(@Param("ids") Long[] ids);
}
