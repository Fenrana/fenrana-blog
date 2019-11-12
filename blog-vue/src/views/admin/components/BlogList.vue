<template>
  <div class="content">
    <div class="search">
      <div>
        <span>关键词:</span>
        <el-input v-model="input" placeholder="请输入内容"></el-input>
      </div>
      <div>
        <span>文章状体:</span>
        <el-input v-model="input" placeholder="请输入内容"></el-input>
      </div>
      <div>
        <span>分类目录:</span>
        <el-input v-model="input" placeholder="请输入内容"></el-input>
      </div>
    </div>
    <div class="list"></div>
  </div>
</template>

<script>
export default {
  name: "List"
};
</script>

<style scoped lang="less">
.content {
  .search {
    height: 40px;
    width: 1000%;
    border: solid 1px red;
  }

  .list {
  }
}
</style>
