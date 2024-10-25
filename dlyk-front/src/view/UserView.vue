<script>
export default {
  name: "UserView"
}
</script>

<template>
  <el-button type="primary">添加用户</el-button>
  <el-button type="danger">批量删除</el-button>
  <el-table
      ref="multipleTableRef"
      :data="tableData"
      style="width: 100%"
      @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" :selectable="selectable" width="55" />
    <el-table-column label="序号" width="120"/>
    <el-table-column property="name" label="帐号" width="120" />
    <el-table-column property="address" label="姓名" />
    <el-table-column property="address" label="手机" />
  </el-table>
</template>

<style scoped>

</style>