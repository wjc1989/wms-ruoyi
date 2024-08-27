import { mergeRecursive } from "@/utils/ruoyi";
import dictConverter from './DictConverter'

export const options = {
  metas: {
    '*': {
      /**
       * Dict请求，方法签名function(dictMeta: DictMeta): Promise
       */
      request: (dictMeta) => {
        return Promise.resolve([])
      },
      /**
       * Dict响应Data转换器，方法签名function(response: Object, dictMeta: DictMeta): DictData
       */
      responseConverter,
      labelField: 'label',
      valueField: 'value',
    },
  },
  /**
   * 默认 Label字段
   */
  DEFAULT_LABEL_FIELDS: ['label', 'name', 'title'],
  /**
   * 默认值字段
   */
  DEFAULT_VALUE_FIELDS: ['value', 'id', 'uid', 'key'],
}

/**
 * 映射Dict
 * @param {Object} response DictData
 * @param {DictMeta} dictMeta Dict元Data
 * @returns {DictData}
 */
function responseConverter(response, dictMeta) {
  const dicts = response.content instanceof Array ? response.content : response
  if (dicts === undefined) {
    console.warn(`no dict data of "${dictMeta.type}" found in the response`)
    return []
  }
  return dicts.map(d => dictConverter(d, dictMeta))
}

export function mergeOptions(src) {
  mergeRecursive(options, src)
}

export default options
