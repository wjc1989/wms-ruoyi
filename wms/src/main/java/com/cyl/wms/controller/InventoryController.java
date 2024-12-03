package com.cyl.wms.controller;

import com.cyl.wms.convert.InventoryConvert;
import com.cyl.wms.domain.Inventory;
import com.cyl.wms.pojo.query.InventoryQuery;
import com.cyl.wms.pojo.vo.InventoryVO;
import com.cyl.wms.service.InventoryService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QuantityController
 *
 * @author zcc
 * @date 2022-08-05
 */
@Api(description ="Quantity接口列表")
@RestController
@RequestMapping("/wms/inventory")
public class InventoryController extends BaseController {
    @Autowired
    private InventoryService service;
    @Autowired
    private InventoryConvert convert;

    @ApiOperation("查询Quantity列表")
    //@PreAuthorize("@ss.hasPermi('wms:inventory:list')")
    @PostMapping("/list")
    public ResponseEntity<Page<InventoryVO>> list(@RequestBody InventoryQuery query, Pageable page) {
        return ResponseEntity.ok(service.queryPage(query, page));
    }

    @ApiOperation("查询预警列表")
    //@PreAuthorize("@ss.hasPermi('wms:inventory:list')")
    @PostMapping("/warnList")
    public ResponseEntity<Page<InventoryVO>> list(Pageable page) {
        return ResponseEntity.ok(service.queryWarning(page));
    }

    @ApiOperation("导出Quantity列表")
    //@PreAuthorize("@ss.hasPermi('wms:inventory:export')")
    @Log(title = "Quantity", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public ResponseEntity<String> export(InventoryQuery query) {
        List<Inventory> list = service.selectList(query, null);

        ExcelUtil<InventoryVO> util = new ExcelUtil<>(InventoryVO.class);
        List<InventoryVO> res=convert.dos2vos(list);
        this.service.injectAreaAndItemInfo(res);
        this.service.injectItemType(res);
        return ResponseEntity.ok(util.writeExcel(res, "Inventory Data"));
    }

    @ApiOperation("获取Quantity详细信息")
    //@PreAuthorize("@ss.hasPermi('wms:inventory:query')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Inventory> getInfo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.selectById(id));
    }

    @ApiOperation("新增Quantity")
    //@PreAuthorize("@ss.hasPermi('wms:inventory:add')")
    @Log(title = "Quantity", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(service.insert(inventory));
    }

    @ApiOperation("修改Quantity")
    //@PreAuthorize("@ss.hasPermi('wms:inventory:edit')")
    @Log(title = "Quantity", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseEntity<Integer> edit(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(service.update(inventory));
    }

    @ApiOperation("删除Quantity")
    //@PreAuthorize("@ss.hasPermi('wms:inventory:remove')")
    @Log(title = "Quantity", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResponseEntity<Integer> remove(@PathVariable Long[] ids) {
        return ResponseEntity.ok(service.deleteByIds(ids));
    }
    @ApiOperation("删除Quantity")
    //@PreAuthorize("@ss.hasPermi('wms:inventory:remove')")
    @Log(title = "Quantity", businessType = BusinessType.DELETE)
    @DeleteMapping("/item/{ids}")
    public ResponseEntity<Integer> removeByItem(@PathVariable Long[] ids) {
        return ResponseEntity.ok(service.deleteByItemIds(ids));
    }

    @ApiOperation("删除Quantity")
    //@PreAuthorize("@ss.hasPermi('wms:inventory:remove')")
    @Log(title = "Quantity", businessType = BusinessType.DELETE)
    @DeleteMapping("/warehouse/{ids}")
    public ResponseEntity<Integer> removeByWarehouse(@PathVariable Long[] ids) {
        return ResponseEntity.ok(service.deleteByWarehouseIds(ids));
    }

    @PostMapping("/getGoodsCount/{ids}")
    @ResponseBody
    public ResponseEntity<Map> getGoodsCount(@PathVariable Long[] ids){
        Map<Long,HashMap> map=new HashMap();
        List<Inventory> warehouse_count=this.service.selectWarehouseGoodsCount(ids);
        List<Inventory> area_count=this.service.selectAreaGoodsCount(ids);
        List<Inventory> shelf_count=this.service.selectShelfGoodsCount(ids);
        if(warehouse_count!=null){
            warehouse_count.forEach(x->{
                if(map.get(x.getItemId())==null){
                    map.put(x.getItemId(),new HashMap<>());
                }
                if(x.getQuantity().intValue()>0){
                    map.get(x.getItemId()).put("w_"+x.getWarehouseId(),x.getQuantity());
                }
            });
        }

        if(area_count!=null){
            area_count.forEach(x->{
                if(map.get(x.getItemId())==null){
                    map.put(x.getItemId(),new HashMap<>());
                }
                if(x.getQuantity().intValue()>0) {
                    map.get(x.getItemId()).put("a_" + x.getAreaId(), x.getQuantity());
                }
            });
        }

        if(shelf_count!=null){
            shelf_count.forEach(x->{
                if(map.get(x.getItemId())==null){
                    map.put(x.getItemId(),new HashMap<>());
                }
                if(x.getQuantity().intValue()>0) {
                    map.get(x.getItemId()).put("s_" + x.getRackId(), x.getQuantity());
                }
            });
        }

        return ResponseEntity.ok(map);
    }
}
