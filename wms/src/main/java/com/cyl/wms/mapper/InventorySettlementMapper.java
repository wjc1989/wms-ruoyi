package com.cyl.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyl.wms.domain.InventorySettlement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Quantity结算单Mapper接口
 *
 * @author zcc
 */
public interface InventorySettlementMapper extends BaseMapper<InventorySettlement> {
    /**
     * 查询Quantity结算单列表
     *
     * @param inventorySettlement Quantity结算单
     * @return Quantity结算单集合
     */
    List<InventorySettlement> selectByEntity(InventorySettlement inventorySettlement);

    /**
     * 批量软删除
     * @param ids
     * @return
    */
    int updateDelFlagByIds(@Param("ids") Long[] ids);
}
