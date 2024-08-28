<template>
<el-cascader
  placeholder="Please select Warehouse"
  :options="options"
  v-model="selections"
  :props="{ checkStrictly: true }"
  clearable
  :size="size"
  :disabled="disabled"
  filterable>
    <template slot-scope="{node,data}">
        <span>{{data.label}}</span>
        <span v-if="data.count">({{data.count}})</span>
    </template>
</el-cascader>
</template>

<script>
import { mapGetters } from 'vuex'
import {getGoodsCount} from "@/api/wms/inventory";

export default {
  props: {
    value: {
      type: [String, Number,Array],
      default: null
    },
    size: {
      type: String,
      default: 'small'
    },
    disabled:{
      type: Boolean,
      default: false
    },
    goodsId:{
       type: Number,
       default:null
    }
  },
  data() {
    return {
      options: [],
      goodsCount:{}
    }
  },
  computed: {
    ...mapGetters(['rackList', 'areaList', 'warehouseList']),
    selections: {
      get() {
        return this.value
      },
      set(v) {
        console.log("set:",v)
        this.$emit('input', v)
      }
    }
  },
  created() {
    this.setOptions()
  },
  methods: {

    async setOptions() {
      console.log("this.goodsId:",this.goodsId)
      if(this.goodsId){
        let res=await getGoodsCount(this.goodsId);
        this.goodsCount=res[this.goodsId]||{};
      }
      console.log("goodsCount:",this.goodsCount);
      let areaMap = new Map()
      let warehouseMap = new Map()
      this.rackList.forEach(rack => {
        let children = areaMap.get(rack.areaId)
        if (!children) {
          children = []
          areaMap.set(rack.areaId, children)
        }
        let shelf={ value: rack.id, label: rack.rackName };
        if(this.goodsCount['s_'+rack.id]){
          shelf.count=this.goodsCount['s_'+rack.id];
        }
        children.push(shelf);
      })
      this.areaList.forEach(area => {
        let children = warehouseMap.get(area.warehouseId)
        if (!children) {
          children = []
          warehouseMap.set(area.warehouseId, children)
        }
        let areaInfo={ value: area.id, label: area.areaName, children: areaMap.get(area.id) }
        if(this.goodsCount['a_'+area.id]){
          areaInfo.count=this.goodsCount['a_'+area.id];
        }
        children.push(areaInfo)
      })
      this.options = this.warehouseList.map(warehouse => {
        let warehouseInfo={
          value: warehouse.id,
          label: warehouse.warehouseName,
          children: warehouseMap.get(warehouse.id)
        }
        if(this.goodsCount['w_'+warehouse.id]){
          warehouseInfo.count=this.goodsCount['w_'+warehouse.id];
        }
        return warehouseInfo;
      })
      console.log(this.options);
    }
  }
}
</script>
