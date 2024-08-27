<template>
  <el-table
        ref="multipleTable"
        v-bind="$props"
        @selection-change="handleSelectionChange"
        @row-click="handleRowClick"
      >
      <slot />
  </el-table>
</template>

<script>
export default {
  props: ['data'],
  data() {
    return {
      selectionData: []
    }
  },
  methods: {
    // 向父组件享Data
    handleSelectionChange(val) {
      this.$emit('selection-change', val)
    },

    handleRowClick(row, column, event){
      // selectionData -- 已选的Data
				let index = this.selectionData.findIndex(item => {
					// 判断已选数组中已存在该条Data
					return item.id == row.id
				})
				if (index == -1) {
					// e.g.果未存在，设置已选Status，并在list中Add这条Data
					this.$refs.multipleTable.toggleRowSelection(row, true); //设置复选框选中Status
					this.selectionData.push(row)
				} else {
					// e.g.果已存在，设置未选Status，并在list中Delete这条Data
					this.$refs.multipleTable.toggleRowSelection(row, false); //设置复选框未选Status
					this.selectionData.splice(index, 1)
				}
    }
  }
}
</script>
