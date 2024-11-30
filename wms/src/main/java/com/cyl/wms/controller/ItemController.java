package com.cyl.wms.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import io.github.bluesbruce.BrQrCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.enums.BusinessType;
import com.cyl.wms.convert.ItemConvert;
import com.cyl.wms.domain.Item;
import com.cyl.wms.pojo.query.ItemQuery;
import com.cyl.wms.service.ItemService;
import com.cyl.wms.pojo.vo.ItemVO;
import com.ruoyi.common.utils.poi.ExcelUtil;
/**
 * Goods Controller
 *
 * @author zcc
 * @date 2022-08-05
 */
@Api(description ="Goods 接口列表")
@RestController
@RequestMapping("/wms/item")
public class ItemController extends BaseController {
    @Autowired
    private ItemService service;
    @Autowired
    private ItemConvert convert;

    @ApiOperation("查询Goods 列表")
    @PreAuthorize("@ss.hasPermi('wms:item:list')")
    @PostMapping("/list")
    public ResponseEntity<Page<ItemVO>> list(@RequestBody ItemQuery query, Pageable page) {
        List<Item> items = service.selectList(query, page);
        List<ItemVO> list = service.toVos(items);

        return ResponseEntity.ok(new PageImpl<>(list, page, ((com.github.pagehelper.Page)items).getTotal()));
    }
    @ApiOperation("查询Goods 列表")
    @PreAuthorize("@ss.hasPermi('wms:item:list')")
    @PostMapping("/listCount")
    public TableDataInfo listCount(@RequestBody ItemQuery query) {
        startPage();
        List<Item> items = service.selectListWithCount(query);
        return  getDataTable(items);
    }


    @ApiOperation("查询Goods 列表")
    @PreAuthorize("@ss.hasPermi('wms:item:list')")
    @PostMapping("/all")
    public ResponseEntity<List<ItemVO>> all(@RequestBody ItemQuery query) {
        List<Item> items = service.selectList(query, null);
        return ResponseEntity.ok(convert.dos2vos(items));
    }

    @ApiOperation("导出Goods 列表")
    @PreAuthorize("@ss.hasPermi('wms:item:export')")
    @Log(title = "Goods ", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public ResponseEntity<String> export(ItemQuery query) {
        List<Item> list = service.selectList(query, null);
        ExcelUtil<ItemVO> util = new ExcelUtil<>(ItemVO.class);
        return ResponseEntity.ok(util.writeExcel(convert.dos2vos(list), "Goods 数据"));
    }

    @ApiOperation("获取Goods 详细信息")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ItemVO> getInfo(@PathVariable("id") Long id) {
        Item item = service.selectById(id);
        ItemVO itemVO = service.toVo(item);
        return ResponseEntity.ok(itemVO);
    }


    @GetMapping(value = "/searchItemByNo/{itemNo}")
    public ResponseEntity<ItemVO> searchItemByNo(@PathVariable("itemNo") String itemNo) {
        Item item = service.selectByNo(itemNo);
        ItemVO itemVO = service.toVo(item);
        return ResponseEntity.ok(itemVO);
    }
    @ApiOperation("新增Goods ")
    @PreAuthorize("@ss.hasPermi('wms:item:add')")
    @Log(title = "Goods", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody Item item) {
        String fileName= DateUtils.datePath() + "/" + IdUtils.simpleUUID() + ".jpg";
        String uploadPath=RuoYiConfig.getUploadPath()+"/qrcode";
        try {
            BrQrCodeUtil.brEncode(item.getItemNo(), BarcodeFormat.CODE_39, new File(uploadPath+"/"+fileName));
            item.setCodePath(FileUploadUtils.getPathFileName(uploadPath,fileName));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(service.insert(item));
    }

    @ApiOperation("修改Goods ")
    @PreAuthorize("@ss.hasPermi('wms:item:edit')")
    @Log(title = "Goods", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseEntity<Integer> edit(@RequestBody Item item) {

        return ResponseEntity.ok(service.update(item));
    }

    @ApiOperation("删除Goods ")
    @PreAuthorize("@ss.hasPermi('wms:item:remove')")
    @Log(title = "Goods", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResponseEntity<Integer> remove(@PathVariable Long[] ids) {
        return ResponseEntity.ok(service.deleteByIds(ids));
    }

    @ApiOperation("查询过期Goods ")
    @PreAuthorize("@ss.hasPermi('wms:item:list')")
    @PostMapping("/expiryList")
    public ResponseEntity<Page<ItemVO>> list(Pageable page){
        List<Item> items = service.queryExpiry(page);
        List<ItemVO> list = service.toVos(items);
        return ResponseEntity.ok(new PageImpl<>(list, page, ((com.github.pagehelper.Page)items).getTotal()));
    }


}
