<script>
import {doDelete, doGet, doPost, doPut} from "../http/httpRequest.js";
import {getTokenName, messageConfirm, messageTip, removeTokens} from "../util/util.js";

export default {
  name: "ActivityView",

  inject: ['reload'],

  data() {
    return {
        page: {
        totalElements: 0,
        size: 0
        },
        activityQury: {
        },
        activityList: [{}],
        activityIdList: []
    }
  },

  mounted() {
    this.getData(0);
  },

  methods: {
    handleSelectionChange(selection) {
      this.activityIdList = [];
      selection.forEach(data => {
        let userId = data.id;
        this.activityIdList.push(userId);
      });
    },

    goToPage(page) {
      this.getData(page - 1);
    },

    getData(current) {
      doGet("/api/activities", {
        current: current
      }).then(res => {
        if (res.data.code === 200) {
          this.activityList = res.data.data.content;
          this.page = res.data.data.page;
        }
      });
    },
  }
}
</script>

<template>
  <el-form :inline="true" :model="activityQury">
    <el-form-item label="负责人">
      <el-select
        v-model="activityQury.ownerId" placeholder="请输入活动名称"
        clearable
      >
        <el-option label="Zone one" value="shanghai" />
        <el-option label="Zone two" value="beijing" />
      </el-select>
    </el-form-item>
    <el-form-item label="活动名称">
      <el-input v-model="activityQury.activityName" placeholder="请输入活动名称" clearable />
    </el-form-item>
    <el-form-item label="活动时间">
        <el-date-picker
        v-model="activityQury.startTime"
        type="datetimerange"
        start-placeholder="开始时间"
        end-placeholder="结束时间"
        format="YYYY-MM-DD HH:mm:ss"
        date-format="MMM DD, YYYY"
        time-format="HH:mm"
      />
    </el-form-item>
    <el-form-item label="活动预算">
      <el-input v-model="activityQury.cost" placeholder="请输入活动预算" clearable />
    </el-form-item>
    <el-form-item label="创建时间">
        <el-date-picker
        v-model="activityQury.createTime"
        type="datetime"
        placeholder="请选择创建时间"
        format="YYYY-MM-DD HH:mm:ss"
        date-format="MMM DD, YYYY"
        time-format="HH:mm"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSearch">搜索</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" plain @click="onReset">重置</el-button>
    </el-form-item>
  </el-form>

  <el-button type="primary" @click="addNewActivity">录入市场活动</el-button>
  <el-button type="danger" @click="batchDeleteActivities">批量删除</el-button>
  <el-table
      :data="activityList"
      style="width: 100%"
      @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" width="55" />
    <el-table-column property="id" label="序号" width="120" />
    <el-table-column property="ownerName" label="负责人" show-overflow-tooltip/>
    <el-table-column property="name" label="活动名称" show-overflow-tooltip/>
    <el-table-column property="startTime" label="开始时间" show-overflow-tooltip />
    <el-table-column property="endTime" label="结束时间" show-overflow-tooltip />
    <el-table-column property="cost" label="活动预算" show-overflow-tooltip />
    <el-table-column property="createTime" label="创建时间" show-overflow-tooltip />
    <el-table-column label="操作" width="230px">
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
.el-form {
    margin-bottom: 20px;
}

.el-table {
  margin-top: 12px;
}

.el-pagination {
  margin-top: 12px;
}

.el-input {
  --el-input-width: 220px;
}

.el-select {
  --el-select-width: 220px;
}
</style>