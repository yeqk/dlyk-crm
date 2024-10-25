<script>

import {doGet} from "../http/httpRequest.js";
import {messageConfirm, messageTip, removeTokens} from "../util/util.js";

export default {
  name: "DashboardView",

  data() {
    return {
      isCollapsed: false,
      user: {}
    };
  },

  methods: {

    showMenu() {
      this.isCollapsed = !this.isCollapsed;
    },

    loadLoginUser() {
      doGet("/api/login/info", {}).then(res => {
        this.user = res.data.data;
      })
    },

    logout() {
      doGet("/api/logout", {}).then(res => {
        if (res.data.code === 200) {
          removeTokens();
          messageTip("退出成功", "success");
          window.location.href = "/";
        } else {
          messageConfirm("退出异常， 是否强制退出？")
              .then(() => {
                removeTokens();
                window.location.href = "/"
              })
              .catch(() => {
                messageTip("取消强制退出", "warning")
              })
        }
      });
    },
  },

  mounted() {
    this.loadLoginUser();
  }
}

</script>

<template>

  <el-container>

    <el-aside :width="isCollapsed ? '64px' : '200px'">

      <div class="menuTitle">@动力云客管理系统</div>

      <el-menu
          active-text-color="#ffd04b"
          background-color="#334157"
          class="el-menu-vertical-demo"
          default-active="2"
          text-color="#fff"
          style="border-right: 0px solid;"
          :unique-opened="true"
          :router="true"
          :collapse="isCollapsed"
          :collapse-transition="false">

        <el-sub-menu index="1">
          <template #title>
            <el-icon><OfficeBuilding /></el-icon>
            <span>市场活动</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon><OfficeBuilding /></el-icon>
            市场活动
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="2">
          <template #title>
            <el-icon><Magnet /></el-icon>
            <span>线索管理</span>
          </template>
          <el-menu-item index="2-1">
            <el-icon><OfficeBuilding /></el-icon>
            线索管理
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="3">
          <template #title>
            <el-icon><User /></el-icon>
            <span>客户管理</span>
          </template>
          <el-menu-item index="3-1">
            <el-icon><OfficeBuilding /></el-icon>
            市场活动
          </el-menu-item>
          <el-menu-item index="3-2">
            <el-icon><OfficeBuilding /></el-icon>
            市场统计
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="4">
          <template #title>
            <el-icon><Wallet /></el-icon>
            <span>交易管理</span>
          </template>
          <el-menu-item index="4-1">
            <el-icon><OfficeBuilding /></el-icon>
            交易管理
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="5">
          <template #title>
            <el-icon><Memo /></el-icon>
            <span>产品管理</span>
          </template>
          <el-menu-item index="5-1">
            <el-icon><OfficeBuilding /></el-icon>
            市场活动
          </el-menu-item>
          <el-menu-item index="5-2">
            <el-icon><OfficeBuilding /></el-icon>
            市场统计
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="6">
          <template #title>
            <el-icon><Reading /></el-icon>
            <span>字典管理</span>
          </template>
          <el-menu-item index="6-1">
            <el-icon><OfficeBuilding /></el-icon>
            市场活动
          </el-menu-item>
          <el-menu-item index="6-2">
            <el-icon><OfficeBuilding /></el-icon>
            市场统计
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="7">
          <template #title>
            <el-icon><Avatar /></el-icon>
            <span>用户管理</span>
          </template>
          <el-menu-item index="dashboard/user">
            <el-icon><OfficeBuilding /></el-icon>
            用户管理
          </el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="8">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>系统管理</span>
          </template>
          <el-menu-item index="8-1">
            <el-icon><OfficeBuilding /></el-icon>
            市场活动
          </el-menu-item>
          <el-menu-item index="8-2">
            <el-icon><OfficeBuilding /></el-icon>
            市场统计
          </el-menu-item>
        </el-sub-menu>

      </el-menu>

    </el-aside>

    <el-container>

      <el-header>
        <el-icon class="foldButton" @click="showMenu"><Fold /></el-icon>
        <el-dropdown :hide-on-click="false">
          <span class="el-dropdown-link">
            {{ user.name }}
            <el-icon class="el-icon--right"><arrow-down /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>我的资料</el-dropdown-item>
              <el-dropdown-item>修改密码</el-dropdown-item>
              <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>

      <el-main>
        <router-view/>
      </el-main>

      <el-footer>@Copyright 2024</el-footer>

    </el-container>

  </el-container>

</template>

<style scoped>
.el-aside {
  background: black;
  height: 100vh;
}

.el-header {
  background: azure;
  height: 35px;
  line-height: 35px;
}

.el-footer {
  display: flex;
  background: aliceblue;
  justify-content: center;  /* Center horizontally */
  align-items: center;      /* Center vertically */
  height: 35px;            /* Set height of footer */
  text-align: center;
}

.menuTitle {
  height: 35px;
  color: white;
  text-align: center;
  line-height: 35px;
}

.foldButton {
  cursor: pointer;
}

.el-dropdown {
  float: right;
  line-height: 35px;
}
</style>