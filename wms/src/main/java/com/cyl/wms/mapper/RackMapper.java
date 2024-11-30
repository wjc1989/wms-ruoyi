package com.cyl.wms.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.cyl.wms.domain.Rack;

/**
 * ShelfMapper接口
 *
 * @author zcc
 */
public interface RackMapper extends BaseMapper<Rack> {
    /**
     * 查询Shelf列表
     *
     * @param rack Shelf
     * @return Shelf集合
     */
    List<Rack> selectByEntity(Rack rack);

    /**
     * 批量软删除
     * @param ids
     * @return
    */
    int updateDelFlagByIds(@Param("ids") Long[] ids);
}
