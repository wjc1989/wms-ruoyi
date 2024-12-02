package com.cyl.wms.constant;

public interface ShipmentOrderConstant {

  // 未Out
  int NOT_IN = 11;
  // 部分Out
  int PART_IN = 12;
  // 全部Out
  int ALL_IN = 13;
  // 作废
  int DROP = 14;

  /*订单类型*/
  int SALE = 11;
  int OUTSOURCING = 12;
  int RANSFER = 13;
  //部门领料Out
  int DEPT = 14;
  //盘亏Out
  int CHECK_OUT = 31;
  int FINISHED = 13;
}
