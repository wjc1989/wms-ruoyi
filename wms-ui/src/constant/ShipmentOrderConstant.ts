/**
 * @file Outbound Type And Status的常量
 * @description Outbound Type And Status的常量
 * @createdBy Zhang Cheng
 * @createdAt 2023/08/10
 */
export const ShipmentOrderConstant = {
  // Dict wms_shipment_type
  Type: {
    // 销售订
    SALE: 11,
    // 外包订
    OUTSOURCING: 12,
    // 转运订
    TRANSFER: 13,
    // Department订
    DEPT: 14
  },
  // Dict wms_shipment_status
  Status: {
    // 未Outbound
    NOT_IN: 11,
    // 部分Outbound
    PART_IN: 12,
    // AllOutbound
    ALL_IN: 13,
    // 作废
    DROP: 14
  }
}
