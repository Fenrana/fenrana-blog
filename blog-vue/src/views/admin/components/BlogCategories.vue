<template>
  <div class="categories">
    <div class="search">
      <div class="search-left">
        <span>搜索：</span>
        <el-input v-model="searchKey" style="width: 300px;"></el-input>
      </div>
      <div class="search-right">
        <el-button type="success">筛选</el-button>
        <el-button type="primary" @click="add()">添加</el-button>
      </div>
    </div>
    <div class="categories-data">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="200"> </el-table-column>
        <el-table-column prop="name" label="名称" width="200">
        </el-table-column>
        <el-table-column prop="description" label="描述" width="400">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button @click="edit(scope.row)" type="primary">编辑</el-button>
            <el-button type="danger" @click="deletea(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 修改弹出框 start-->
    <el-dialog
      title="内容分类修改"
      :visible.sync="dialogFormVisible"
      width="500px"
    >
      <el-form :model="form" style="width:380px;margin: 0 auto;">
        <el-form-item label="分类名称">
          <el-input v-model="form.name" style="width: 300px"></el-input>
        </el-form-item>
        <el-form-item label="分类描述">
          <el-input v-model="form.description" style="width: 300px"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitFrom" :loading="isLoading"
          >确 定</el-button
        >
      </div>
    </el-dialog>
    <!--    修改弹出窗end-->
  </div>
</template>

<script>
export default {
  name: "Categories",
  data() {
    return {
      searchKey: "",
      form: {},
      isLoading: false,
      dialogFormVisible: false, //控制修改的弹出框
      tableData: []
    };
  },
  created() {
    this.init();
  },
  methods: {
    edit(row) {
      window.console.log(row);
      this.dialogFormVisible = true;
      this.form = row;
    },
    add() {
      this.form = {};
      this.dialogFormVisible = true;
    },
    //提交修改的分类
    submitFrom() {
      this.isLoading = true;
      // this.dialogFormVisible = false;
      var that = this;
      this.axios
        .post("http://localhost:8081/admin/addCategory", {
          id: this.form.id,
          name: this.form.name,
          description: this.form.description
        })
        .then(response => {
          if (response.data.code === 200) {
            that.isLoading = false;
            this.init();
            that.dialogFormVisible = false;
            this.$message({
              type: "success",
              message: "添加成功!"
            });
          } else {
            this.$message({
              type: "info",
              message: "添加失败!"
            });
          }
        })
        .catch(error => {
          window.console.log(error);
        });
    },
    /*×数据初始化*/
    init() {
      this.axios
        .get("http://localhost:8081/admin/categorys")
        .then(response => {
          window.console.log(response.data);
          this.tableData = response.data.data;
        })
        .catch(error => {
          window.console.log(error);
        });
    },
    //删除分类名称
    deletea(row) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.axios
            .get("http://localhost:8081/admin/deleteCategory/" + row.id)
            .then(res => {
              this.$message({
                type: "info",
                message: "删除成功"
              });
              this.init();
              window.console.log(res);
            })
            .catch(error => {
              window.console.log(error);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    }
  }
};
</script>

<style scoped lang="less">
.categories {
  .search {
    display: flex;
    justify-content: flex-start;
    .search-right {
      margin-left: 15px;
    }
  }
  .categories-data {
    margin-top: 20px;
  }
}
</style>
