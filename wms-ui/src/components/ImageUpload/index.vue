<template>
  <div class="component-upload-image">
    <el-upload
      multiple
      :action="uploadImgUrl"
      list-type="picture-card"
      :on-success="handleUploadSuccess"
      :before-upload="handleBeforeUpload"
      :limit="limit"
      :on-error="handleUploadError"
      :on-exceed="handleExceed"
      name="file"
      :on-remove="handleRemove"
      :show-file-list="true"
      :headers="headers"
      :file-list="fileList"
      :on-preview="handlePictureCardPreview"
      :class="{hide: this.fileList.length >= this.limit}"
    >

      <i class="el-icon-plus"></i>
    </el-upload>

    <!-- 上传Info -->
    <div class="el-upload__tip" slot="tip" v-if="showTip">

      <template v-if="fileSize">Maximum upload allowed <b style="color: #f56c6c">{{ fileSize }}MB</b> </template>
    </div>

    <el-dialog
      :visible.sync="dialogVisible"
      title="View"
      width="800"
      append-to-body
    >
      <img
        :src="dialogImageUrl"
        style="display: block; max-width: 100%; margin: 0 auto"
      />
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";

export default {
  props: {
    value: [String, Object, Array],
    // 图片Count限制
    limit: {
      type: Number,
      default: 5,
    },
    // 大小限制(MB)
    fileSize: {
       type: Number,
      default: 100,
    },
    // 文件Type, 例e.g.['png', 'jpg', 'jpeg']
    fileType: {
      type: Array,
      default: () => ["png", "jpg", "jpeg"],
    },
    // ShowInfo
    isShowTip: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      number: 0,
      uploadList: [],
      dialogImageUrl: "",
      dialogVisible: false,
      hideUpload: false,
      baseUrl: process.env.VUE_APP_BASE_API,
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器Address
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      fileList: []
    };
  },
  watch: {
    value: {
      handler(val) {
        if (val) {
          // 首先将值转数组
          const list = Array.isArray(val) ? val : this.value.split(',');
          // 然后将数组转对象数组
          this.fileList = list.map(item => {
            if (typeof item === "string") {
              if (item.indexOf(this.baseUrl) === -1) {
                  item = { name: this.baseUrl + item, url: this.baseUrl + item };
              } else {
                  item = { name: item, url: item };
              }
            }
            return item;
          });
        } else {
          this.fileList = [];
          return [];
        }
      },
      deep: true,
      immediate: true
    }
  },
  computed: {
    // ShowInfo
    showTip() {
      return this.isShowTip && (this.fileType || this.fileSize);
    },
  },
  methods: {
    // Delete图片
    handleRemove(file, fileList) {
      const findex = this.fileList.map(f => f.name).indexOf(file.name);
      if(findex > -1) {
        this.fileList.splice(findex, 1);
        this.$emit("input", this.listToString(this.fileList));
      }
    },
    // 上传 Successful回调
    handleUploadSuccess(res) {
      this.uploadList.push({ name: res.fileName, url: this.baseUrl+res.fileName });
      if (this.uploadList.length === this.number) {
        this.fileList = this.fileList.concat(this.uploadList);
        this.uploadList = [];
        this.number = 0;
        this.$emit("input", this.listToString(this.fileList));
        this.$modal.closeLoading();
      }
    },
    // 上传前loading加载
    handleBeforeUpload(file) {
      let isImg = false;
      if (this.fileType.length) {
        let fileExtension = "";
        if (file.name.lastIndexOf(".") > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
        }
        isImg = this.fileType.some(type => {
          if (file.type.indexOf(type) > -1) return true;
          if (fileExtension && fileExtension.indexOf(type) > -1) return true;
          return false;
        });
      } else {
        isImg = file.type.indexOf("image") > -1;
      }

      if (!isImg) {
        this.$modal.msgError(`Allowed to be uploaded are ${this.fileType.join("/")}!`);
        return false;
      }
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize;
        if (!isLt) {
          this.$modal.msgError(`Maximum upload allowed ${this.fileSize} MB!`);
          return false;
        }
      }
      this.$modal.loading("Uploading...");
      this.number++;
    },
    // 文件个数超出
    handleExceed() {
      this.$modal.msgError(`Maximum number of uploads are ${this.limit} !`);
    },
    // 上传失败
    handleUploadError() {
      this.$modal.msgError("Upload failed");
      this.$modal.closeLoading();
    },
    // 预览
    handlePictureCardPreview(file) {
      this.dialogImageUrl = this.baseUrl+file.url;
      this.dialogVisible = true;
    },
    // 对象转成指定字符串分隔
    listToString(list, separator) {
      let strs = "";
      separator = separator || ",";
      for (let i in list) {
        strs += list[i].url.replace(this.baseUrl, "") + separator;
      }
      return strs != '' ? strs.substr(0, strs.length - 1) : '';
    }
  }
};
</script>
<style scoped lang="scss">
// .el-upload--picture-card 控制加No.部分
::v-deep.hide .el-upload--picture-card {
    display: none;
}
// 去掉动画效果
::v-deep .el-list-enter-active,
::v-deep .el-list-leave-active {
    transition: all 0s;
}

::v-deep .el-list-enter, .el-list-leave-active {
    opacity: 0;
    transform: translateY(0);
}
</style>

