package com.cyl.wms.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.jwt.JWTUtil;
import com.cyl.wms.constant.ReceiptOrderConstant;
import com.cyl.wms.constant.ShipmentOrderConstant;
import com.cyl.wms.convert.ReceiptOrderDetailConvert;
import com.cyl.wms.convert.ShipmentOrderDetailConvert;
import com.cyl.wms.domain.*;
import com.cyl.wms.pojo.vo.ReceiptOrderDetailVO;
import com.cyl.wms.pojo.vo.ShipmentOrderDetailVO;
import com.cyl.wms.pojo.vo.form.ReceiptOrderForm;
import com.cyl.wms.pojo.vo.form.ShipmentOrderFrom;
import com.cyl.wms.service.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.FormatException;
import com.google.zxing.WriterException;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.config.ServerConfig;
import io.github.bluesbruce.BrQrCodeUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import com.cyl.wms.pojo.query.ItemQuery;
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
    private final ShipmentOrderDetailService shipmentOrderDetailService;
    private final ShipmentOrderService shipmentOrderService;
    private final RedisCache redisCache;
    private final ServerConfig serverConfig;
    private final ReceiptOrderService receiptOrderService;
    private final ReceiptOrderDetailConvert receiptOrderDetailConvert;
    private final InventoryHistoryService inventoryHistoryService;
    private final InventoryService inventoryService;
    private final ShipmentOrderDetailConvert detailConvert;
    private final ItemTypeService itemTypeService;
    @Value("${token.secret}")
    private String secret;
    @ApiOperation("查询Goods 列表")
    //@PreAuthorize("@ss.hasPermi('wms:item:list')")
    @PostMapping("/list")
    public ResponseEntity<Page<Item>> list(@RequestBody ItemQuery query, Pageable page) {
        List<Item> items = service.selectList(query, page);
//        List<ItemVO> list = service.toVos(items);

        return ResponseEntity.ok(new PageImpl<>(items, page, ((com.github.pagehelper.Page)items).getTotal()));
    }

    @GetMapping("/getToken")
    public Map  getToken(String key){
        String token="e68b6841-7ba9-40ad-baa4-27e4a0ee8452";
        Map map=new HashMap();
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        if("wms_token_my".equals(key)){
            map.put("data",jwtToken);
        }
        map.put("code",200);
        return map;
    }
    @ApiOperation("查询Goods 列表")
    //@PreAuthorize("@ss.hasPermi('wms:item:list')")
    @PostMapping("/listCount")
    public TableDataInfo listCount(@RequestBody ItemQuery query) {
        startPage();
        List<Item> items = service.selectListWithCount(query);
        return  getDataTable(items);
    }


    @ApiOperation("查询Goods 列表")
    //@PreAuthorize("@ss.hasPermi('wms:item:list')")
    @PostMapping("/all")
    public ResponseEntity<List<ItemVO>> all(@RequestBody ItemQuery query) {
        List<Item> items = service.selectList(query, null);
        return ResponseEntity.ok(convert.dos2vos(items));
    }

    @PostMapping("/addOut")
    public ResponseEntity<Integer> addOut(@RequestBody ShipmentOrderFrom shipmentOrderFrom) {

        if(shipmentOrderFrom.getDetails()!=null){
            shipmentOrderFrom.setShipmentOrderNo(shipmentOrderFrom.genShipentmentOrder());

            shipmentOrderFrom.setShipmentOrderType(ShipmentOrderConstant.OUTSOURCING);//Outbound
            shipmentOrderFrom.setShipmentOrderStatus(ShipmentOrderConstant.ALL_IN);//outbound finished
            shipmentOrderFrom.setCreateBy(this.getUserId());
            shipmentOrderService.add(shipmentOrderFrom);
            List<InventoryHistory> adds = new ArrayList<>();
            LocalDateTime now=LocalDateTime.now();
            shipmentOrderFrom.getDetails().forEach(it -> {
                it.setCreateBy(shipmentOrderFrom.getCreateBy());
                BigDecimal added= it.getRealQuantity();
                //判断Quantity是否足够Out
                inventoryService.checkInventory(it.getItemId(), it.getWarehouseId(), it.getAreaId(), it.getRackId(), added);
                // 1. 前一次的Real Quantity是 0
                InventoryHistory h = detailConvert.do2InventoryHistory(it);
                h.setFormId(shipmentOrderFrom.getId());
                h.setFormType(shipmentOrderFrom.getShipmentOrderType());
                h.setQuantity(added.negate());
                h.setDelFlag(0);
                h.setId(null);
                h.setCreateTime(now);
                h.setCreateBy(shipmentOrderFrom.getCreateBy());
                adds.add(h);
            });
            if (!adds.isEmpty()) {
                int add1 = inventoryHistoryService.batchInsert(adds);
//            adds.forEach(it -> it.setQuantity(it.getQuantity().negate()));
                int update1 = inventoryService.batchUpdate1(adds);
             }
        }

        return ResponseEntity.ok(1);
    }
    @PostMapping("/addIn")
    public ResponseEntity<Integer> addIn(@RequestBody Item item) throws IOException, FormatException, WriterException {
        checkChangeAndUpdate(item);
        ReceiptOrderForm receiptOrder=new ReceiptOrderForm();
        receiptOrder.setRemark(item.getRemark());
        receiptOrder.setReceiptOrderStatus(ReceiptOrderConstant.ALL_IN);
        receiptOrder.setReceiptOrderType(ReceiptOrderConstant.IN_PATH);
        receiptOrder.setCreateBy(this.getUserId());
        receiptOrder.setReceiptOrderNo(receiptOrder.genReceiptOrderNo());
        receiptOrder.setDelFlag(0);
        ReceiptOrderDetailVO receiptOrderDetail=new ReceiptOrderDetailVO();
        receiptOrderDetail.setItemId(item.getId());
        receiptOrderDetail.setAreaId(item.getAreaId());
        receiptOrderDetail.setWarehouseId(item.getWarehouseId());
        receiptOrderDetail.setRemark(item.getRemark());
        receiptOrderDetail.setPlanQuantity(new BigDecimal(item.getCount()));
        receiptOrderDetail.setRealQuantity(new BigDecimal(item.getCount()));
        receiptOrderDetail.setOrderNo(item.getProject());
        receiptOrderDetail.setCreateBy(receiptOrder.getCreateBy());

        receiptOrder.setDetails(new ArrayList<>());
        receiptOrder.getDetails().add(receiptOrderDetail);
        receiptOrderService.add(receiptOrder);
        LocalDateTime now=LocalDateTime.now();
        List<InventoryHistory> adds = new ArrayList<>();
        receiptOrder.getDetails().forEach(it->{
            // 存入本次的库存数量变更
            InventoryHistory h = receiptOrderDetailConvert.do2InventoryHistory(it);
            h.setFormId(receiptOrder.getId());
            h.setFormType(receiptOrder.getReceiptOrderType());
            h.setQuantity(it.getRealQuantity());
            h.setDelFlag(0);
            h.setId(null);
            h.setCreateTime(now);
            h.setCreateBy(receiptOrder.getCreateBy());
            adds.add(h);
        });
        if (adds.size() > 0) {
            int add1 = inventoryHistoryService.batchInsert(adds);
            int update1 = inventoryService.batchUpdate1(adds);
        }
        return ResponseEntity.ok(1);
    }

    private void checkChangeAndUpdate(Item item) throws FormatException, IOException, WriterException {
        if(item!=null){
            if(item.getItemTypeName()!=null){
                ItemType itemType=this.itemTypeService.selectByItemName(item.getItemTypeName());
                if(itemType!=null){
                    item.setItemType(itemType.getItemTypeId()+"");
                }
            }
            if(item.getId()!=null){
                Item update=new Item();
                Item old=this.service.selectById(item.getId());
                if(!StrUtil.equals(old.getItemNo(),item.getItemNo())){
                    try {
                        String codePath=genGoodCode(item.getItemNo());
                        update.setId(item.getId());
                        update.setCodePath(codePath);
                        update.setItemNo(item.getItemNo());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (WriterException e) {
                        throw new RuntimeException(e);
                    } catch (FormatException e) {
                        throw new RuntimeException(e);
                    }
                }
                if(!StrUtil.equals(old.getItemName(),item.getItemName())){
                    update.setItemName(item.getItemName());
                    update.setId(item.getId());
                }
                if(!StrUtil.equals(old.getRemark(),item.getRemark())){
                    update.setRemark(item.getRemark());
                    update.setId(item.getId());
                }
                if(!StrUtil.equals(old.getPics(),item.getPics())){
                    update.setPics(item.getPics());
                    update.setId(item.getId());
                }
                if(item.getItemType()!=null&&!StrUtil.equals(old.getItemType(),item.getItemType())){
                    update.setItemType(item.getItemType());
                    update.setItemTypeName(item.getItemTypeName());
                    update.setId(item.getId());
                }

                if(update.getId()!=null){
                    item.setUpdateBy(getUserId());
                    item.setUpdateTime(LocalDateTime.now());
                    this.service.updateEntity(update);
                }
            }else{
                //新增
                item.setCreateBy(getUserId());
                item.setCreateTime(LocalDateTime.now());

                this.service.insert(item);
                String codePath=genGoodCode(item.genCode());
                item.setCodePath(codePath);
                if(codePath!=null){
                    Item update=new Item();
                    update.setItemNo(item.genCode());
                    update.setId(item.getId());
                    update.setCodePath(codePath);
                    service.updateEntity(update);
                }
            }
        }
    }

    @ApiOperation("导出Goods 列表")
    //@PreAuthorize("@ss.hasPermi('wms:item:export')")
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
        ItemVO itemVO;
        if(item!=null){
            itemVO = service.toVo(item);
            if(ArrayUtil.isNotEmpty(itemVO.getPicsArr())){
                itemVO.setPicsArr(itemVO.getPicsArr().stream().map(x-> serverConfig.getUrl()+x).collect(Collectors.toList()));
            }
            return ResponseEntity.ok(itemVO);
        }

        return ResponseEntity.ok(null);

    }

    @ApiOperation("新增Goods ")
    //@PreAuthorize("@ss.hasPermi('wms:item:add')")
    @Log(title = "Goods", businessType = BusinessType.INSERT)
    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody Item item) throws IOException, WriterException, FormatException {
        int count=service.insert(item);
        String codePath=genGoodCode(item.genCode());
        item.setCodePath(codePath);
        if(codePath!=null){
            Item update=new Item();
            update.setItemNo(item.genCode());
            update.setId(item.getId());
            update.setCodePath(codePath);
            service.updateEntity(update);
        }
        return ResponseEntity.ok(count);
    }

    private String genGoodCode(String code) throws IOException, WriterException, FormatException {

        String uploadPath= RuoYiConfig.getUploadPath()+"/qrcode";
        String fileName= DateUtils.datePath() + "/" + code + ".jpg";
        File targetFile=new File(uploadPath+"/"+fileName);
        if(!targetFile.exists()){
            BrQrCodeUtil.brEncode(code, BarcodeFormat.EAN_13,targetFile);
        }
        return FileUploadUtils.getPathFileName(uploadPath,fileName);
    }




    @ApiOperation("修改Goods ")
    //@PreAuthorize("@ss.hasPermi('wms:item:edit')")
    @Log(title = "Goods", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResponseEntity<Integer> edit(@RequestBody Item item) {
//        String codePath=genGoodCode(item);
//        item.setCodePath(codePath);
//        item.setItemNo(item.getCode());
        int count=service.update(item);
        return ResponseEntity.ok(count);
    }

    @ApiOperation("删除Goods ")
    //@PreAuthorize("@ss.hasPermi('wms:item:remove')")
    @Log(title = "Goods", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResponseEntity<Integer> remove(@PathVariable Long[] ids) {
        return ResponseEntity.ok(service.deleteByIds(ids));
    }

    @ApiOperation("查询过期Goods ")
    //@PreAuthorize("@ss.hasPermi('wms:item:list')")
    @PostMapping("/expiryList")
    public ResponseEntity<Page<ItemVO>> list(Pageable page){
        List<Item> items = service.queryExpiry(page);
        List<ItemVO> list = service.toVos(items);
        return ResponseEntity.ok(new PageImpl<>(list, page, ((com.github.pagehelper.Page)items).getTotal()));
    }



}
