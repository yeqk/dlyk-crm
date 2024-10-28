<script>
import {doGet} from "../http/httpRequest.js";

export default {
  name: "UserView",
  data() {
    return {
      page: {
        totalElements: 0,
        size: 0
      },
      userList: [{}]
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
  }
}
</script>

<template>
  <el-button type="primary">添加用户</el-button>
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
</template>

<style scoped>
.el-table {
  margin-top: 12px;
}

.el-pagination {
  margin-top: 12px;
}

</style>