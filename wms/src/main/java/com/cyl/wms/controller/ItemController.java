package com.cyl.wms.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.FormatException;
import com.google.zxing.WriterException;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import io.github.bluesbruce.BrQrCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ItemController extends BaseController {
     private final ItemService service;

    private final ItemConvert convert;

    private final RedisCache redisCache;

    @ApiOperation("查询Goods 列表")
    @PreAuthorize("@ss.hasPermi('wms:item:list')")
    @PostMapping("/list")
    public ResponseEntity<Page<Item>> list(@RequestBody ItemQuery query, Pageable page) {
        List<Item> items = service.selectList(query, page);
//        List<ItemVO> list = service.toVos(items);

        return ResponseEntity.ok(new PageImpl<>(items, page, ((com.github.pagehelper.Page)items).getTotal()));
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
    @PreAuthorize("@ss.hasPermi('wms:item:query')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ItemVO> getInfo(@PathVariable("id") Long id) {
        Item item = service.selectById(id);
        ItemVO itemVO = service.toVo(item);
        return ResponseEntity.ok(itemVO);
    }

    public static void main(String[] args) {
        String code="2408290000010";
    }
    @ApiOperation("新增Goods ")
    @PreAuthorize("@ss.hasPermi('wms:item:add')")
    @Log(title = "Goods", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody Item item) throws IOException, WriterException, FormatException {
        int count=service.insert(item);
        String codePath=genGoodCode(item);
        item.setCodePath(codePath);
        if(codePath!=null){
            Item update=new Item();
            update.setItemNo(item.getCode());
            update.setId(item.getId());
            update.setCodePath(codePath);
            service.updateEntity(update);
        }
        return ResponseEntity.ok(count);
    }

    private String genGoodCode(Item item) throws IOException, WriterException, FormatException {
        String code=item.getCode();
        String uploadPath= RuoYiConfig.getUploadPath()+"/qrcode";
        String fileName= DateUtils.datePath() + "/" + code + ".jpg";
        File targetFile=new File(uploadPath+"/"+fileName);
        if(!targetFile.exists()){
            BrQrCodeUtil.brEncode(code, BarcodeFormat.EAN_13,targetFile);
        }
        return FileUploadUtils.getPathFileName(uploadPath,fileName);
    }




    @ApiOperation("修改Goods ")
    @PreAuthorize("@ss.hasPermi('wms:item:edit')")
    @Log(title = "Goods", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseEntity<Integer> edit(@RequestBody Item item) throws IOException, WriterException {
//        String codePath=genGoodCode(item);
//        item.setCodePath(codePath);
//        item.setItemNo(item.getCode());
        int count=service.update(item);
        return ResponseEntity.ok(count);
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
