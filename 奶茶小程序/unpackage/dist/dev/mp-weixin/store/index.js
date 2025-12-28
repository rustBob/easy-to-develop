"use strict";
var __defProp = Object.defineProperty;
var __defNormalProp = (obj, key, value) => key in obj ? __defProp(obj, key, { enumerable: true, configurable: true, writable: true, value }) : obj[key] = value;
var __publicField = (obj, key, value) => {
  __defNormalProp(obj, typeof key !== "symbol" ? key + "" : key, value);
  return value;
};
const common_vendor = require("../common/vendor.js");
class Store {
  constructor() {
    __publicField(this, "data", null);
    __publicField(this, "GLOBAL_STORE_KEY", "global-store-key");
    this.data = JSON.parse(common_vendor.index.getStorageSync(this.GLOBAL_STORE_KEY) || "{}");
  }
  set(key, value) {
    this.data[key] = value;
    common_vendor.index.setStorageSync(this.GLOBAL_STORE_KEY, JSON.stringify(this.data));
  }
  get(key) {
    return this.data[key] ?? null;
  }
  remove(key) {
    delete this.data[key];
    common_vendor.index.setStorageSync(this.GLOBAL_STORE_KEY, JSON.stringify(this.data));
  }
}
const Store$1 = new Store();
exports.Store = Store$1;
//# sourceMappingURL=../../.sourcemap/mp-weixin/store/index.js.map
