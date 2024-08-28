import Dict from './Dict'
import { mergeOptions } from './DictOptions'

/**
 *
 * @param {*} Vue
 * @param {*} options , 插件安装时传入的对象，我们项目里面Yes：
 * metas: {
     '*': {
       labelField: 'dictLabel',
       valueField: 'dictValue',
       request(dictMeta) {
       //调用后端接口获取Data
         return getDicts(dictMeta.type).then(res => res.data)
       },
     },
   },
 */
export default function(Vue, options) {
  //mergeOptions(options)会将DictOptions的options合并
  mergeOptions(options)
  Vue.mixin({
    data() {
      //e.g.果vue没有定义dixts即this.$options.dicts空，则Back{}相当于No 混入
      if (this.$options === undefined || this.$options.dicts === undefined || this.$options.dicts === null) {
        return {}
      }
      //DataDict初始化DictData
      const dict = new Dict()
      dict.owner = this
      return {
        //混入dict
        dict
      }
    },
    created() {
      if (!(this.dict instanceof Dict)) {
        return
      }
      options.onCreated && options.onCreated(this.dict)
      //执行Dict的init方法，将vue页面上定义的dicts数组传进去，组装Data，请求后端，获取对应DictData
      this.dict.init(this.$options.dicts).then(() => {
        options.onReady && options.onReady(this.dict)
        this.$nextTick(() => {
          this.$emit('dictReady', this.dict)
          if (this.$options.methods && this.$options.methods.onDictReady instanceof Function) {
            this.$options.methods.onDictReady.call(this, this.dict)
          }
        })
      })
    },
  })
}
