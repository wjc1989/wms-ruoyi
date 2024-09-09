package com.cyl.wms.pojo.vo;

public interface AreaAndItemInfo extends PlaceAndItem {
    void setItemName(String name);

    void setWarehouseName(String name);

    void setAreaName(String name);

    void setRackName(String name);

    void setItemNo(String name);

    default void setItemDelFlag(Integer delFlag) {

    }

    default void setWarehouseDelFlag(Integer delFlag) {

    }
    void setRemark(String remark);
}
