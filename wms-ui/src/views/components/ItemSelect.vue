<template>
  <div class="prod-sku-select-wrapper">
    <div class="search">
      <el-form inline="inline" label-width="64">
        <el-form-item label="Category">
          <div class="w200">
            <treeselect
              v-model="query.itemType"
              :options="deptOptions"
              :show-count="true"
              clearable
              placeholder="Please select Type"
            />
          </div>
        </el-form-item>
        <el-form-item label="Search">
          <el-input
            class="w200"
            v-model="query.search"
            clearable
            placeholder="No.、Goods、ID"
          ></el-input>
        </el-form-item>
        <el-button type="primary" @click="clickQuery">Search</el-button>
      </el-form>
    </div>
    <div class="center">
      <div class="left flex-one">
        <div class="content">
          <table class="common-table" v-if="list.length !== 0">
            <tr>
              <th>
                <el-checkbox
                  :value="leftAllChecked === 1"
                  @input="leftAllChecked = $event"
                  :indeterminate="leftAllChecked === 2"
                  :disabled="editableList.length === 0"
                ></el-checkbox>
              </th>
              <th>Goods No.</th>
              <th>Goods Name</th>
              <th>Category</th>
              <th>Picture</th>
              <th>Stock</th>
            </tr>

            <tr v-for="p in list" :key="p.id">
              <td>
                <div class="text-center">
                  <el-checkbox
                    v-model="p.checked"
                    :disabled="rightListKeySet.has(p.id)"
                  ></el-checkbox>
                </div>
              </td>
              <td>
                <div class="text-center">
                  {{ p.itemNo }}
                </div>
              </td>
              <td>
                <div class="text-center">
                  {{ p.itemName }}
                </div>
              </td>
              <td>
                <div class="text-center">
                  {{ p.itemTypeName }}
                </div>
              </td>
              <td>
                <div class="text-center">
                  <ImagePreview :src="p.pics||''" class="listimage" ></ImagePreview>
                </div>
              </td>
              <td>
                <div class="text-center">
                  {{p.inventory}}
                </div>
              </td>
            </tr>
          </table>
          <el-empty v-else :image-size="64"></el-empty>
        </div>
      </div>
    </div>
    <pagination v-show="total > 0" :total="total" :page.sync="pageReq.pageNum" :limit.sync="pageReq.pageSize"
                @pagination="loadAll"/>

  </div>
</template>

<script>
import {listWmsItemWithCount} from "@/api/wms/item";
import Treeselect from "@riophae/vue-treeselect";
import {itemTypeTreeselect} from "@/api/wms/itemType";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "ItemSelect",
  components: {Treeselect},
  props: ['data'],
  data() {
    return {
      //Item类别
      deptOptions: [],
      query: {
        itemType: null,
        search: null,
      },
      list: [],
      total: 0,
      pageReq: {
        pageNum: 1,
        pageSize: 10,
      },
      rightList: [],
      rightListKeySet: new Set
    };
  },
  computed: {
    leftAllChecked: {
      get() {
        return this.editableList.length > 0 &&
        this.editableList.every((it) => it.checked)? 1 : this.editableList.some((it) => it.checked)? 2: 0;
      },
      set(v) {
        this.editableList.forEach((it) => (it.checked = v));
      },
    },
    editableList() {
      return this.list.filter((it) => !this.rightListKeySet.has(it.id));
    },
    leftCheckedList() {
      return this.list.filter((it) => it.checked);
    }
  },
  watch: {
    rightList: {
      handler(newVal, oldVal) {
        const set = new Set();
        newVal.forEach((it) => set.add(it.id));
        this.rightListKeySet = set;
      },
      deep: true
    }
  },
  created() {
    console.log("this.data:",this.data);
    if (this.data) {
      this.initDetailsList(this.data)
    }
    this.loadAll();
    itemTypeTreeselect().then((response) => {
      this.deptOptions = response.data;
    });
  },
  methods: {
    initDetailsList(list) {
      this.rightList = list.map(it => {
        return it.prod
      })
    },
    loadAll() {
      const pageReq = {...this.pageReq};
      // pageReq.page -= 1;
      console.log(pageReq);
      listWmsItemWithCount(this.query, pageReq).then((res) => {
        console.log("listWmsItemWithCount:",res)
        let content=res.rows||[];
        let totalElements=res.total;
        content.forEach((it) => (it.checked = false));
        this.list = content;
        this.total = totalElements;
      });
    },
    clickQuery() {
      this.pageReq.page = 1;
      this.loadAll();
    },
    getRightList() {
      this.moveRight();
      return this.rightList;
    },
    moveRight() {
      for (let it of this.leftCheckedList) {
        if (!this.rightListKeySet.has(it.id)) {
          it.checked = false;
          this.rightList.push({...it, checked: false});
        }
      }
    },
  },
};
</script>

<style lang="stylus">
.prod-sku-select-wrapper
  .center
    display flex
    align-items stretch

    .left, .right

      > div
        padding 12px

      .statistic
        border-bottom 1px solid $gray-2

      .header
        background-color $gray-1
        padding 12px

      .prods
        height 360px
        overflow-y auto

        > .prod
          padding 12px

        > .prod + .prod
          border-top 1px solid var(--gray-2)

    .ops
      display flex
      flex-direction column
      justify-content center
      margin 0 16px
</style>
