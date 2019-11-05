<template>
  <div class="footer">
    <el-button type="warning">保存草稿</el-button>
    <el-button type="primary" @click="drawer = true">发布</el-button>
    <el-button type="info">附件库</el-button>

    <!-- 发布的侧边栏 start-->
    <el-drawer
      title="文章设置"
      :visible.sync="drawer"
      :direction="direction"
      :before-close="handleClose"
    >
      <span>我来啦!</span>
    </el-drawer>
    <!-- 发布的侧边栏 end-->
  </div>
</template>

<script>
export default {
  name: "AdminFooter",
  data() {
    return {
      drawer: false,
      direction: "rtl"
    };
  },
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭？").then(() => {
        done();
      });
    }
  }
};
</script>

<style scoped lang="less">
.footer {
  /*float: left;*/
}
</style>
