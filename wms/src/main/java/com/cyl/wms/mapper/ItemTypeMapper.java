package com.cyl.wms.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.cyl.wms.domain.ItemType;

/**
 * Goods 类型表Mapper接口
 *
 * @author zcc
 */
public interface ItemTypeMapper extends BaseMapper<ItemType> {
    /**
     * 查询Goods 类型表列表
     *
     * @param itemType Goods 类型表
     * @return Goods 类型表集合
     */
    List<ItemType> selectByEntity(ItemType itemType);

    /**
     * 批量软删除
     * @param ids
     * @return
    */
    int updateDelFlagByIds(@Param("ids") Long[] ids);

    ItemType selectByTypeName(@Param("typeName") String itemTypeName);
}
