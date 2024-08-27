<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="150px">
    <el-form-item label="Old password" prop="oldPassword">
      <el-input v-model="user.oldPassword" placeholder="Please input Old password" type="password" show-password/>
    </el-form-item>
    <el-form-item label="New Password" prop="newPassword">
      <el-input v-model="user.newPassword" placeholder="Please enter new password" type="password" show-password/>
    </el-form-item>
    <el-form-item label="Confirm password" prop="confirmPassword">
      <el-input v-model="user.confirmPassword" placeholder="Confirm password" type="password" show-password/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">Save</el-button>
      <el-button type="danger" size="mini" @click="close">Close</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserPwd } from "@/api/system/user";

export default {
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.user.newPassword !== value) {
        callback(new Error("两次输入的 Password不一致"));
      } else {
        callback();
      }
    };
    return {
      user: {
        oldPassword: undefined,
        newPassword: undefined,
        confirmPassword: undefined
      },
      // Table单校验
      rules: {
        oldPassword: [
          { required: true, message: "Old password Can not blank", trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: "New Password Can not blank", trigger: "blur" },
          { min: 6, max: 20, message: "6 to 20 characters in length", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: "Confirm password Can not blank", trigger: "blur" },
          { required: true, validator: equalToPassword, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserPwd(this.user.oldPassword, this.user.newPassword).then(response => {
            this.$modal.msgSuccess("Edit Successful");
          });
        }
      });
    },
    close() {
      this.$tab.closePage();
    }
  }
};
</script>
