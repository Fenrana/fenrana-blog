<template>
  <div class="content">
    <div class="search">
      <div>
        <span>关键词:</span>
        <el-input
          v-model="searchKey"
          placeholder="请输入内容"
          style="width: 200px;margin-left: 8px;"
        ></el-input>
      </div>
      <div class="search-item">
        <span>文章状体:</span>
        <el-select
          v-model="state"
          placeholder="请选择"
          style="width: 200px;margin-left: 8px;"
        >
          <el-option
            v-for="item in states"
            :key="item.id"
            :label="item.name"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>

      <!--分类选择 start-->
      <div class="search-item">
        <span>分类：</span>
        <el-select
          v-model="category"
          placeholder="请选择"
          style="width: 200px;margin-left: 8px;"
        >
          <el-option
            v-for="item in categoryOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </div>
      <!--分类选择 end-->
      <div class="search-item">
        <el-button type="primary">筛选</el-button>
        <el-button type="info">重置</el-button>
      </div>
    </div>
    <div class="list">
      <div class="content">
        <el-table
          :data="tableData"
          stripe
          style="width: 100%"
          v-loading="loading"
        >
          <el-table-column prop="title" label="标题" width="150">
          </el-table-column>
          <el-table-column
            prop="state"
            label="状态"
            width="130"
            :formatter="formatterSatte"
          >
          </el-table-column>
          <el-table-column prop="category" label="分类" width="130">
          </el-table-column>
          <el-table-column prop="tag" label="标签">
            <!--            <el-tag type="success" disable-transitions>vim</el-tag>-->
            <template slot-scope="scope">
              <el-tag
                type="success"
                v-for="item in scope.row.tags"
                :key="item.id"
                disable-transitions
                >{{ item.name }}</el-tag
              >
            </template>
          </el-table-column>
          <el-table-column prop="visits" label="访问数"> </el-table-column>
          <el-table-column
            prop="createTime"
            label="发布时间"
            :formatter="formatterDate"
          ></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)"
                >编辑</el-button
              >
              <el-button
                size="mini"
                type="info"
                @click="handleDelete(scope.$index, scope.row)"
                >回收站</el-button
              >
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pagination">
        <!--        <el-pagination-->
        <!--          background-->
        <!--          layout="prev, pager, next"-->
        <!--          :total="total"-->
        <!--          :page-sizes="[10, 20, 30]"-->
        <!--        >-->
        <!--        </el-pagination>-->

        <el-pagination
          @size-change="handleSizeChange"
          background
          :current-page.sync="current"
          @current-change="handleCurrentChange"
          :page-sizes="[10, 20, 30]"
          :page-size="100"
          layout="prev, pager, next,sizes"
          :total="total"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "List",
  data() {
    return {
      state: null,
      searchKey: "",
      states: [
        { id: 1, name: "已发布", value: 0 },
        { id: 2, name: "草稿", value: 1 },
        { id: 3, name: "回收站", value: 2 }
      ],
      categoryOptions: [],
      category: "",
      tableData: [],
      total: 0, //文章的总条数
      current: 1,
      loading: true
    };
  },
  created() {
    this.init();
    this.initData({
      current: this.current,
      size: 10,
      searchKey: null,
      category: null
    });
  },
  methods: {
    //分类的初始化方法
    initCategory() {
      this.axios
        .get("http://localhost:8081/admin/categorys")
        .then(response => {
          window.console.log(response.data);
          this.categoryOptions = response.data.data;
        })
        .catch(error => {
          window.console.log(error);
        });
    },
    //数据的初始化方法
    initData(pageQuery) {
      this.axios
        .post("http://localhost:8081/admin/articles", {
          current: pageQuery.current,
          size: pageQuery.size,
          searchKey: pageQuery.searchKey,
          category: pageQuery.category
        })
        .then(response => {
          window.console.log(response.data);
          if (response.data.code === 200) {
            this.tableData = response.data.data.data;
            this.total = response.data.data.total;
            this.loading = false;
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
    init() {
      this.initCategory();
    },
    handleEdit(index, row) {
      window.console.log(index, row);
    },
    handleDelete(index, row) {
      window.console.log(index, row);
    },
    //文章状态的计算
    formatterSatte(row) {
      window.console.log(row.state);
      if (row.state === "0") {
        return "正常发布";
      } else if (row.state === "1") {
        return "草稿";
      } else if (row.state === "2") {
        return "回收站";
      }
    },
    //日期的转换
    formatterDate(row) {
      var date = new Date(row.createTime);
      return date.toLocaleString();
    },
    handleSizeChange(val) {
      window.console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      this.loading = true;
      this.current = val;
      this.initData({
        current: val,
        size: 10,
        searchKey: null,
        category: null
      });
    }
  }
};
</script>

<style scoped lang="less">
.content {
  height: 100%;
  .search {
    height: 5%;
    width: 1000%;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    align-content: center;
    .search-item {
      margin-left: 40px;
    }
  }

  .list {
    margin-top: 15px;
    .content {
      /*height: 800px;*/
    }
    .pagination {
      display: flex;
      justify-content: center;
      margin-top: 10px;
    }
  }
}
</style>
