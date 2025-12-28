"use strict";
function trimObj(obj) {
  if (!obj) {
    return obj;
  }
  if (Array.isArray(obj)) {
    const newObj = [];
    for (let i = 0, len = obj.length; i < len; i++) {
      newObj.push(trimObj(obj[i]));
    }
    return newObj;
  }
  if (typeof obj === "object") {
    const keys = Object.keys(obj);
    for (let i = 0, len = keys.length; i < len; i++) {
      obj[keys[i]] = trimObj(obj[keys[i]]);
    }
  }
  if (typeof obj === "string") {
    return obj.trim();
  }
  return obj;
}
function isInvalid(obj) {
  return obj === null || obj === void 0;
}
function ubtoa(str) {
  if (!str || typeof str !== "string") {
    return str;
  }
  return encodeURIComponent(str).replace(/\./g, "!*~1").replace(/%2F/g, "!*~2").replace(/%26/g, "!*~3").replace(/%3F/g, "!*~4").replace(/%3D/g, "!*~5");
}
const common = {
  trimObj
};
exports.common = common;
exports.isInvalid = isInvalid;
exports.ubtoa = ubtoa;
//# sourceMappingURL=../../.sourcemap/mp-weixin/util/common.js.map
