<template>
   <img :src="realSrc" width="150px" height="55px" ref="itemPrintRef" hidden />

</template>

<script>

export default {
  name: 'ItemPrint',
  props: {
    codePath: {
      type: String,
      default: ""
    }
  },
  data() {
    return {
      realSrc: null
    };
  },
  methods: {
    start() {
      console.log("codePath:",this.codePath);
      this.realSrc=process.env.VUE_APP_BASE_API +this.codePath;
      console.log("11",this.realSrc)
      this.$nextTick(()=>{
        this.$print(this.$refs.itemPrintRef, {}, '30px 10px')
      })
    },
  }
}
</script>

<style lang="stylus" media="print">
@page {
 size:portrait;
  img{
    transform : rotate(90deg);
  }
}

@media print {


}
</style>
