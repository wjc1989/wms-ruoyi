<template>
  <el-dialog :visible="visible" width="600px" title="Set Warehouse" @close="handleCancel">
    <el-form :model="form" ref="form">
      <el-form-item label="Warehouse" prop="place" :rules="rules">
        <WmsWarehouseCascader v-model="form.place" size="small"></WmsWarehouseCascader>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleCancel">Cancel</el-button>
      <el-button type="primary" @click="handleConfirm">OK</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'BatchWarehouseDialog',
  props: {
    visible: {
      type: Boolean,
      required: true
    },
    formData: {
      type: Object,
      required: true
    },
    rules: {
      type: Array,
      default: () => [
        {required: true, message: 'Please select  Warehouse', trigger: 'blur'}
      ]
    }
  },
  computed: {
    form: {
      get() {
        return this.formData;
      },
      set(value) {
        this.$emit('update:formData', value);
      }
    }
  },
  methods: {
    handleCancel() {
      this.$emit('update:visible', false);
    },
    handleConfirm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$emit('confirmed');
        }
      });
    }
  }
};
</script>
