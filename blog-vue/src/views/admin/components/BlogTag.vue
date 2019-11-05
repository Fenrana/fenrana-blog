<template>
  <h1>BlogTag</h1>
</template>

<script>
export default {
  data() {
    return {
      value: "#### how to use mavonEditor in nuxt.js",
      title: ""
    };
  },
  created() {
    this.$bus.$on("addBlog", aa => {
      window.console.log("额鹅鹅鹅");
      window.console.log(aa);
    });
  }
};
</script>
