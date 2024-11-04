<script>
import {doGet, doPost} from "../http/httpRequest.js";
import {getTokenName, messageTip, removeTokens} from "../util/util.js";

export default {
  name: "UserView",

  inject: ['reload'],

  data() {
    return {
      page: {
        totalElements: 0,
        size: 0
      },
      userList: [{}],
      userDialogVisible: false,
      userQuery: {},
      options:[
        {
          label: "是",
          value: 1,
        },
        {
          label: "否",
          value: 0,
        }
      ],
      userRules: {
        loginAct: [
          { required: true, message: 'Username is required', trigger: 'blur' }
        ],
        loginPwd: [
          {required: true, message: 'Password is required', trigger: 'blur'},
          { min: 6, max: 16, message: 'Length should be 6 to 16', trigger: 'blur' }
        ],
        name: [
          { required: true, message: 'Name is required', trigger: 'blur' },
          { pattern: /^[\u4e00-\u9fa5]+$/, message: 'Name must be in Chinese', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: 'Phone number is required', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: 'Please enter a valid Chinese phone number', trigger: 'blur' }
        ],
        email: [
          { required: true, message: 'Email is required', trigger: 'blur' },
          { type: 'email', message: 'Please enter a valid email address', trigger: 'blur' }
        ],
        accountNoExpired: [
          { required: true, message: 'Field is required', trigger: 'blur' },
        ],
        credentialsNoExpired: [
          { required: true, message: 'Field is required', trigger: 'blur' },
        ],
        accountNoLocked: [
          { required: true, message: 'Field is required', trigger: 'blur' },
        ],
        accountEnabled: [
          { required: true, message: 'Field is required', trigger: 'blur' },
        ],
      }
    }
  },

  mounted() {
    this.getData(0);
  },

  methods: {
    handleSelectionChange(selection) {
      console.log(selection);
    },

    goToPage(page) {
      this.getData(page - 1);
    },

    getData(current) {
      doGet("/api/users", {
        current: current
      }).then(res => {
        if (res.data.code === 200) {
          this.userList = res.data.data.content;
          this.page = res.data.data.page;
        }
      });
    },

    viewUser(id) {
      console.log(id);
      this.$router.push("/dashboard/user/" + id);
    },

    editUser(id) {
      console.log(id);
    },

    deleteUser(id) {
      console.log(id);
    },

    submitAddUserForm() {
      // Validate form before submitting
      this.$refs.addUserFormRef.validate(isValid => {
        if (isValid) {
          let formData = new FormData();
          for (let field in this.userQuery) {
            formData.append(field, this.userQuery[field]);
          }
          doPost("/api/users", formData).then(res => {
            if (res.data.code === 200) {
              messageTip("提交成功", "success");
              this.reload();
            } else {
              messageTip("提交失败", "error");
            }
          });
        }
      });
    }
  }
}
</script>

<template>
  <el-button type="primary" @click="userDialogVisible = true">添加用户</el-button>
  <el-button type="danger">批量删除</el-button>
  <el-table
      :data="userList"
      style="width: 100%"
      @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" width="55" />
    <el-table-column property="id" label="序号" width="120" />
    <el-table-column property="loginAct" label="帐号" show-overflow-tooltip/>
    <el-table-column property="name" label="姓名" show-overflow-tooltip/>
    <el-table-column property="phone" label="手机" show-overflow-tooltip />
    <el-table-column property="email" label="邮箱" show-overflow-tooltip />
    <el-table-column property="createTime" label="创建时间" show-overflow-tooltip />
    <el-table-column property="address" label="操作" show-overflow-tooltip>
      <template #default="scope">
        <el-button type="primary" @click="viewUser(scope.row.id)">详情</el-button>
        <el-button type="success" @click="editUser(scope.row.id)">编辑</el-button>
        <el-button type="danger" @click="deleteUser(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination background layout="prev, pager, next" :total="page.totalElements" :page-size="page.size" @prev-click="goToPage" @next-click="goToPage" @current-change="goToPage" />

  <el-dialog v-model="userDialogVisible" title="添加用户" width="55%" center draggable>

    <el-form ref="addUserFormRef" :model="userQuery" label-width="110px" :rules="userRules">
      <el-form-item label="账号" prop="loginAct">
        <el-input v-model="userQuery.loginAct"/>
      </el-form-item>

      <el-form-item label="密码" prop="loginPwd">
        <el-input type="password" v-model="userQuery.loginPwd" show-password/>
      </el-form-item>

      <el-form-item label="姓名" prop="name">
        <el-input v-model="userQuery.name"/>
      </el-form-item>

      <el-form-item label="手机" prop="phone">
        <el-input v-model="userQuery.phone"/>
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="userQuery.email"/>
      </el-form-item>

      <el-form-item label="账号未过期" prop="accountNoExpired">
        <el-select v-model="userQuery.accountNoExpired" placeholder="请选择">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="密码未过期" prop="credentialsNoExpired">
        <el-select v-model="userQuery.credentialsNoExpired" placeholder="请选择">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="账号未锁定" prop="accountNoLocked">
        <el-select v-model="userQuery.accountNoLocked" placeholder="请选择">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="账号是否可用" prop="accountEnabled">
        <el-select v-model="userQuery.accountEnabled" placeholder="请选择">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="submitAddUserForm">提交</el-button>
        <el-button type="primary" @click="userDialogVisible = false">关闭</el-button>
      </div>
    </template>
  </el-dialog>

</template>

<style scoped>
.el-table {
  margin-top: 12px;
}

.el-pagination {
  margin-top: 12px;
}

</style>