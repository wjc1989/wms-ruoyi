package com.cyl.wms.mapper;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyl.wms.pojo.vo.InventoryMovementVO;
import org.apache.ibatis.annotations.Param;
import com.cyl.wms.domain.InventoryMovementDetail;

/**
 * Quantity移动详情Mapper接口
 *
 * @author zcc
 */
public interface InventoryMovementDetailMapper extends BaseMapper<InventoryMovementDetail> {
  /**
   * 查询Quantity移动详情列表
   *
   * @param inventoryMovementDetail Quantity移动详情
   * @return Quantity移动详情集合
   */
  List<InventoryMovementDetail> selectByEntity(InventoryMovementDetail inventoryMovementDetail);

  /**
   * 批量软Delete
   *
   * @param ids
   * @return
   */
  int updateDelFlagByIds(@Param("ids") Long[] ids);

  List<InventoryMovementVO> countByOrderId(List<Long> ids);

  int batchInsert(List<InventoryMovementDetail> inventoryMovementDetails);
}
