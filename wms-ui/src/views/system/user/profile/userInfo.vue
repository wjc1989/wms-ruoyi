<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="100px">
    <el-form-item label="Name" prop="nickName">
      <el-input v-model="user.nickName" maxlength="30" />
    </el-form-item>
    <el-form-item label="PhoneNum" prop="phoneNumber">
      <el-input v-model="user.phoneNumber" maxlength="11" />
    </el-form-item>
    <el-form-item label="Email" prop="email">
      <el-input v-model="user.email" maxlength="50" />
    </el-form-item>
    <el-form-item label="Gender">
      <el-radio-group v-model="user.sex">
        <el-radio label="0">Male</el-radio>
        <el-radio label="1">Female</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">Save</el-button>
      <el-button type="danger" size="mini" @click="close">Close</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserProfile } from "@/api/system/user";

export default {
  props: {
    user: {
      type: Object
    }
  },
  data() {
    return {
      // Table单校验
      rules: {
        nickName: [
          { required: true, message: "Name Can not blank", trigger: "blur" }
        ],
        email: [
          { required: true, message: "Email Can not blank", trigger: "blur" },
          {
            type: "email",
            message: "Email verification failed",
            trigger: ["blur", "change"]
          }
        ],
        phoneNumber: [
          { required: true, message: "PhoneNum Can not blank", trigger: "blur" },
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserProfile(this.user).then(response => {
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
