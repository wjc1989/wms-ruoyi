/**
 * @file InboundType And Status的常量
 * @description InboundType And Status的常量
 * @createdBy Zhang Cheng
 * @createdAt 2023/08/10
 */

export const ReceiptOrderConstant = {
  // Dict wms_receipt_type
  Type: {
    // 采购
    PURCHASE: 1,
    // 外协
    OUTSOURCING: 2,
    // 退货
    RETURN: 3,
    // 盘盈Inbound
    CHECK_IN: 32
  },
  // Dict wms_receipt_status
  Status: {
    // 未Inbound
    NOT_IN: 0,
    // 在途
    IN_PATH: 1,
    // 部分Inbound
    PART_IN: 2,
    // AllInbound
    ALL_IN: 3,
    // 作废
    DROP: 2
  }
}
