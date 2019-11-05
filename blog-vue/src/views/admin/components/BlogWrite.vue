<template>
  <div id="main">
    <el-input v-model="title" class="title" placeholder="博客标题"></el-input>
    <mavon-editor v-model="blog" />
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
export default {
  data() {
    return {};
  },
  created() {},
  methods: {
    ...mapActions(["setTitleAsyns", "SetBlogAsyns"]),
    ...mapMutations(["setTitle"])
  },
  computed: {
    ...mapState(["title"]),
    // ...mapGetters(["money"]),
    title: {
      get() {
        return this.$store.state.title;
      },
      set(value) {
        this.setTitleAsyns(value);
      }
    },
    blog: {
      get() {
        return this.$store.state.blog;
      },
      set(value) {
        this.SetBlogAsyns(value);
      }
    }
  }
};
</script>

<style lang="less" scoped>
#main {
  height: 100%;

  .title {
    margin-bottom: 10px;
  }
  .mavonEditor {
    width: 100%;
    height: 100%;
  }
}
</style>
