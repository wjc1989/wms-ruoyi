package com.cyl.wms.controller;

import com.cyl.wms.convert.ShipmentOrderConvert;
import com.cyl.wms.domain.ShipmentOrder;
import com.cyl.wms.pojo.query.ShipmentOrderQuery;
import com.cyl.wms.pojo.vo.ShipmentOrderVO;
import com.cyl.wms.pojo.vo.form.ShipmentOrderFrom;
import com.cyl.wms.service.ShipmentOrderService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
/**
 * Shipment OrderController
 *
 * @author zcc
 * @date 2022-08-05
 */
@Api(description ="Shipment Order接口列表")
@RestController
@RequestMapping("/wms/shipmentOrder")
public class ShipmentOrderController extends BaseController {
    @Autowired
    private ShipmentOrderService service;
    @Autowired
    private ShipmentOrderConvert convert;

    @ApiOperation("查询Shipment Order列表")
    @PreAuthorize("@ss.hasPermi('wms:shipmentOrder:list')")
    @PostMapping("/list")
    public ResponseEntity<Page<ShipmentOrderVO>> list(@RequestBody ShipmentOrderQuery query, Pageable page) {
        return ResponseEntity.ok(service.selectList(query, page));
    }

    @ApiOperation("导出Shipment Order列表")
    @PreAuthorize("@ss.hasPermi('wms:shipmentOrder:export')")
    @Log(title = "Shipment Order", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public ResponseEntity<String> export(ShipmentOrderQuery query) {
        Page<ShipmentOrderVO> list = service.selectList(query, null);
        ExcelUtil<ShipmentOrderVO> util = new ExcelUtil<>(ShipmentOrderVO.class);
        return ResponseEntity.ok(util.writeExcel(list.getContent(), "Shipment Order数据"));
    }

    @ApiOperation("获取Shipment Order详细信息")
    @PreAuthorize("@ss.hasPermi('wms:shipmentOrder:query')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ShipmentOrderFrom> getInfo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.selectById(id));
    }

    @ApiOperation("新增Shipment Order")
    @PreAuthorize("@ss.hasPermi('wms:shipmentOrder:add')")
    @Log(title = "Shipment Order", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody ShipmentOrder shipmentOrder) {
        return ResponseEntity.ok(service.insert(shipmentOrder));
    }

    @ApiOperation("修改Shipment Order")
    @PreAuthorize("@ss.hasPermi('wms:shipmentOrder:edit')")
    @Log(title = "Shipment Order", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseEntity<Integer> edit(@RequestBody ShipmentOrder shipmentOrder) {
        return ResponseEntity.ok(service.update(shipmentOrder));
    }

    @ApiOperation("删除Shipment Order")
    @PreAuthorize("@ss.hasPermi('wms:shipmentOrder:remove')")
    @Log(title = "Shipment Order", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResponseEntity<Integer> remove(@PathVariable Long[] ids) {
        return ResponseEntity.ok(service.deleteByIds(ids));
    }

    @ApiOperation("新增或更新Shipment Order以及Shipment Order明细")
    @PreAuthorize("@ss.hasPermi('wms:shipmentOrder:add')")
    @Log(title = "Shipment Order", businessType = BusinessType.INSERT)
    @PostMapping("add-or-update")
    public ResponseEntity<Integer> addOrUpdate(@RequestBody ShipmentOrderFrom order) {
        if(order.getId()==null){
            return ResponseEntity.ok(service.add(order));
        }else {
            return ResponseEntity.ok(service.update(order));
        }
    }
    @ApiOperation("单个订单分配仓库")
    @PreAuthorize("@ss.hasPermi('wms:shipmentOrder:edit')")
    @Log(title = "Shipment Order", businessType = BusinessType.UPDATE)
    @PostMapping("allocated")
    public ResponseEntity allocatedInventory(Long id,Integer type) {
        service.allocatedInventory(id,type);
        return ResponseEntity.ok().build();
    }
}
