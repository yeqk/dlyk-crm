<script>
import {doDelete, doGet, doPost, doPut} from "../http/httpRequest.js";
import {getTokenName, messageConfirm, messageTip, removeTokens} from "../util/util.js";

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
      userDto: {},
      userIdList: [],
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
      this.userIdList = [];
      selection.forEach(data => {
        let userId = data.id;
        this.userIdList.push(userId);
      });
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

    addNewUser() {
      this.userDialogVisible = true;
      this.userDto = {};
    },

    viewUser(id) {
      this.$router.push("/dashboard/user/" + id);
    },

    editUser(id) {
      this.userDialogVisible = true;
      this.loadUser(id);
    },

    deleteUser(id) {
      messageConfirm("您确定要删除该数据吗？")
        .then(() => {
          doDelete("/api/users/" + id, {}).then(res => {
            if (res.data.code === 200) {
              messageTip("删除成功", "success");
              this.reload();
            } else {
              messageTip("删除失败， 原因：" + res.data.msg, "error");
            }
          });
        })
        .catch(() => {
          messageTip("取消删除", "warning");
        });
    },

    batchDeleteUsers() {
      if (this.userIdList <= 0) {
        messageTip("请选择要删除的数据", "warning");
        return;
      }

      let ids = this.userIdList.join(",");
      messageConfirm("您确定要删除该数据吗？")
          .then(() => {
            doDelete("/api/users", {ids: ids}).then(res => {
              if (res.data.code === 200) {
                messageTip("批量删除成功", "success");
                this.reload();
              } else {
                messageTip("批量删除失败， 原因：" + res.data.msg, "error");
              }
            });
          })
          .catch(() => {
            messageTip("取消批量删除", "warning");
          });
    },

    loadUser(id) {
      doGet("/api/users/" + id).then(res => {
        if (res.data.code === 200) {
          this.userDto = res.data.data;
          this.userDto.loginPwd = "";
        }
      });
    },

    submitUserForm() {
      // Validate form before submitting
      this.$refs.addUserFormRef.validate(isValid => {
        if (isValid) {
          let formData = new FormData();
          for (let field in this.userDto) {
            if (this.userDto[field] !== null && this.userDto[field] !== undefined) {
              formData.append(field, this.userDto[field]);
            }
          }

          if (this.userDto.id > 0) { // Edit
            doPut("/api/users", formData).then(res => {
              if (res.data.code === 200) {
                messageTip("编辑成功", "success");
                this.reload();
              } else {
                messageTip("编辑失败", "error");
              }
            });
          } else {
            doPost("/api/users", formData).then(res => {
              if (res.data.code === 200) {
                messageTip("提交成功", "success");
                this.reload();
              } else {
                messageTip("提交失败", "error");
              }
            });
          }
        }
      });
    }
  }
}
</script>

<template>
  <el-button type="primary" @click="addNewUser">添加用户</el-button>
  <el-button type="danger" @click="batchDeleteUsers">批量删除</el-button>
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

  <el-dialog v-model="userDialogVisible" :title="userDto.id > 0 ? '编辑用户' : '添加用户'" width="55%" center draggable>

    <el-form ref="addUserFormRef" :model="userDto" label-width="110px" :rules="userRules">
      <el-form-item label="账号" prop="loginAct">
        <el-input v-model="userDto.loginAct"/>
      </el-form-item>

      <el-form-item label="密码" v-if="userDto.id > 0"> <!-- No validation if edit -->
        <el-input type="password" v-model="userDto.loginPwd" show-password/>
      </el-form-item>
      <el-form-item label="密码" prop="loginPwd" v-else>
        <el-input type="password" v-model="userDto.loginPwd" show-password/>
      </el-form-item>

      <el-form-item label="姓名" prop="name">
        <el-input v-model="userDto.name"/>
      </el-form-item>

      <el-form-item label="手机" prop="phone">
        <el-input v-model="userDto.phone"/>
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="userDto.email"/>
      </el-form-item>

      <el-form-item label="账号未过期" prop="accountNoExpired">
        <el-select v-model="userDto.accountNoExpired" placeholder="请选择">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="密码未过期" prop="credentialsNoExpired">
        <el-select v-model="userDto.credentialsNoExpired" placeholder="请选择">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="账号未锁定" prop="accountNoLocked">
        <el-select v-model="userDto.accountNoLocked" placeholder="请选择">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="账号是否可用" prop="accountEnabled">
        <el-select v-model="userDto.accountEnabled" placeholder="请选择">
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
        <el-button @click="submitUserForm">提交</el-button>
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