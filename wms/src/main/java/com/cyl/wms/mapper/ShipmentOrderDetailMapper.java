package com.cyl.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyl.wms.domain.ShipmentOrderDetail;
import com.cyl.wms.pojo.vo.ShipmentOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Shipment Order详情Mapper接口
 *
 * @author zcc
 */
public interface ShipmentOrderDetailMapper extends BaseMapper<ShipmentOrderDetail> {
    /**
     * 查询Shipment Order详情列表
     *
     * @param shipmentOrderDetail Shipment Order详情
     * @return Shipment Order详情集合
     */
    List<ShipmentOrderDetail> selectByEntity(ShipmentOrderDetail shipmentOrderDetail);

    /**
     * 批量软删除
     *
     * @param ids
     * @return
     */
    int updateDelFlagByIds(@Param("ids") Long[] ids);

    List<ShipmentOrderVO> countByOrderId(List<Long> ids);

    int batchInsert(List<ShipmentOrderDetail> shipmentOrderDetails);

    List<ShipmentOrderDetail> selectListGroupByItemId(@Param("id") long ids);

    List<ShipmentOrderDetail> selectDetailByWaveNo(String waveNo);
}
