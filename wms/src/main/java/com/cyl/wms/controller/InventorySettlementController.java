package com.cyl.wms.controller;

import com.cyl.wms.convert.InventorySettlementConvert;
import com.cyl.wms.domain.InventorySettlement;
import com.cyl.wms.pojo.query.InventorySettlementQuery;
import com.cyl.wms.pojo.vo.InventorySettlementVO;
import com.cyl.wms.pojo.vo.form.InventorySettlementFrom;
import com.cyl.wms.service.InventorySettlementService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Quantity结算单Controller
 *
 * @author zcc
 * @date 2023-04-18
 */
@Api(description ="Quantity结算单接口列表")
@RestController
@RequestMapping("/wms/inventorySettlement")
public class InventorySettlementController extends BaseController {
    @Autowired
    private InventorySettlementService service;
    @Autowired
    private InventorySettlementConvert convert;

    @ApiOperation("查询Quantity结算单列表")
    //@PreAuthorize("@ss.hasPermi('wms:inventorySettlement:list')")
    @PostMapping("/list")
    public ResponseEntity<Page<InventorySettlement>> list(@RequestBody InventorySettlementQuery query, Pageable page) {
        List<InventorySettlement> list = service.selectList(query, page);
        return ResponseEntity.ok(new PageImpl<>(list, page, ((com.github.pagehelper.Page)list).getTotal()));
    }

    @ApiOperation("导出Quantity结算单列表")
    //@PreAuthorize("@ss.hasPermi('wms:inventorySettlement:export')")
    @Log(title = "Quantity结算单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public ResponseEntity<String> export(InventorySettlementQuery query) {
        List<InventorySettlement> list = service.selectList(query, null);
        ExcelUtil<InventorySettlementVO> util = new ExcelUtil<>(InventorySettlementVO.class);
        return ResponseEntity.ok(util.writeExcel(convert.dos2vos(list), "Quantity结算单数据"));
    }

    @ApiOperation("获取Quantity结算单详细信息")
    //@PreAuthorize("@ss.hasPermi('wms:inventorySettlement:query')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<InventorySettlementFrom> getInfo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.selectById(id));
    }

    @ApiOperation("新增Quantity结算单")
    //@PreAuthorize("@ss.hasPermi('wms:inventorySettlement:add')")
    @Log(title = "Quantity结算单", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody InventorySettlement inventorySettlement) {
        return ResponseEntity.ok(service.insert(inventorySettlement));
    }

    @ApiOperation("修改Quantity结算单")
    //@PreAuthorize("@ss.hasPermi('wms:inventorySettlement:edit')")
    @Log(title = "Quantity结算单", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseEntity<Integer> edit(@RequestBody InventorySettlement inventorySettlement) {
        return ResponseEntity.ok(service.update(inventorySettlement));
    }

    @ApiOperation("删除Quantity结算单")
    //@PreAuthorize("@ss.hasPermi('wms:inventorySettlement:remove')")
    @Log(title = "Quantity结算单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResponseEntity<Integer> remove(@PathVariable Long[] ids) {
        return ResponseEntity.ok(service.deleteByIds(ids));
    }
    @ApiOperation("新增或更新结算单据以及结算单据明细")
    //@PreAuthorize("@ss.hasPermi('wms:inventorySettlement:edit')")
    @Log(title = "Quantity结算单据", businessType = BusinessType.INSERT)
    @PostMapping("add-or-update")
    public ResponseEntity<Integer> addOrUpdate(@RequestBody InventorySettlementFrom inventorySettlementFrom) {
        return ResponseEntity.ok(service.addOrUpdate(inventorySettlementFrom));
    }
}
