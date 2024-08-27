/**
 * @classdesc DictData
 * @property {String} label  Label
 * @property {*} value  Label
 * @property {Object} raw 原始Data
 */
export default class DictData {
  constructor(label, value, raw) {
    this.label = label
    this.value = value
    this.raw = raw
  }
}
