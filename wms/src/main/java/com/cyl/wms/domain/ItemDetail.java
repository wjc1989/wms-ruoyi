package com.cyl.wms.domain;

import lombok.Data;

@Data
public class ItemDetail  {
    private Long itemId;
    private Long count;
    private Long areaId;
    private Long warehouseId;
    private Long rackId;
}
