package com.cyl.wms.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyl.wms.pojo.query.ItemQuery;
import org.apache.ibatis.annotations.Param;
import com.cyl.wms.domain.Item;

/**
 * Goods Mapper接口
 *
 * @author zcc
 */
public interface ItemMapper extends BaseMapper<Item> {
    /**
     * 查询Goods 列表
     *
     * @param item Goods
     * @return Goods 集合
     */
    List<Item> selectByEntity(Item item);

    /**
     * 批量软删除
     * @param ids
     * @return
    */
    int updateDelFlagByIds(@Param("ids") Long[] ids);

    List<Item> selectExpiry();

    List<Item> selectListWithCount(ItemQuery query);

    Item selectByNo(String itemNo);
}
