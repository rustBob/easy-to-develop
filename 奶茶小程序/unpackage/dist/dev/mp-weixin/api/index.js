"use strict";
const common_vendor = require("../common/vendor.js");
const util_common = require("../util/common.js");
const store_index = require("../store/index.js");
const baseURL = "http://localhost:8080";
const http = (options) => {
  const headers = Object.assign(
    { "Content-Type": "application/json" },
    { "easyToken": store_index.Store.get("easyToken") || "" }
    // 附带 cookie
  );
  return new Promise((resolve, reject) => {
    const task = common_vendor.index.request({
      url: `${baseURL}${options.url}`,
      method: options.method || "GET",
      data: options.data || {},
      timeout: 1e4,
      header: headers,
      success: (res) => {
        res.cookies.forEach((cookie) => {
          cookie.split(";").forEach((item) => {
            const [key, value] = item.split("=");
            if (key && value) {
              store_index.Store.set(key, value);
            }
          });
        });
        resolve(res);
      },
      fail: (err) => reject(err)
    });
    resolve.abort = () => {
      var _a;
      return (_a = task == null ? void 0 : task.abort) == null ? void 0 : _a.call(task);
    };
  });
};
common_vendor.index.addInterceptor("request", {
  invoke(args) {
    if (args.url.includes("login") || args.url.includes("register")) {
      return true;
    }
    if (!store_index.Store.get("easyToken") || !store_index.Store.get("user")) {
      common_vendor.index.navigateTo({
        url: "/pages/login/login"
      });
      return false;
    }
  },
  success(res) {
    if (res.data.code === 401) {
      common_vendor.index.showToast({
        title: "登录过期，请重新登录(2s后自动跳转)",
        icon: "none",
        duration: 2e3
      }).then(() => {
        common_vendor.index.navigateTo({
          url: "/pages/login/login"
        });
      });
      return false;
    }
  }
});
http.get = (url, params = {}) => http({ url, data: params, method: "GET" });
http.post = (url, data = {}) => http({ url, data, method: "POST" });
http.delete = (url, params = {}) => http({ url, data: params, method: "DELETE" });
http.put = (url, data = {}) => http({ url, data, method: "PUT" });
async function handleRequest(reqPromise, success, failure) {
  let res;
  try {
    res = await reqPromise;
  } catch (err) {
    if (!err.msg)
      err.msg = err.message;
    if (failure)
      failure(err);
    return null;
  }
  if (res.statusCode !== 200 || Object.prototype.hasOwnProperty.call(res.data, "code") && res.data.code !== 200) {
    if (res.notNotify)
      return null;
    res.msg = res.data.message || "网络错误，请稍后再试。";
    if (failure)
      failure(res);
    return null;
  }
  if (success) {
    if (Object.prototype.hasOwnProperty.call(res.data, "code")) {
      success(res.data.data);
    } else {
      success(res.data);
    }
  }
  return null;
}
function buildPath(versionPrefix, segments, method, pathParams = {}, suffix = "") {
  let path = versionPrefix;
  const depth = segments.length;
  const isOptional = method === "list";
  const includeLastParam = method === "update" || method === "get";
  pathParams = util_common.common.trimObj(pathParams);
  for (let i = 0; i < depth; i++) {
    path += `/${segments[i]}`;
    const pkKey = `pk${i + 1}`;
    if (util_common.isInvalid(pathParams) || util_common.isInvalid(pathParams[pkKey])) {
      continue;
    }
    const pkValue = util_common.ubtoa(pathParams[pkKey]);
    if (i < depth - 1 || includeLastParam) {
      if (isOptional) {
        path += pkValue !== void 0 ? `/${pkValue}` : `/[:${pkKey}]`;
      } else {
        if (pkValue === void 0)
          throw new Error(`Missing required param: ${pkKey}`);
        path += `/${pkValue}`;
      }
    }
  }
  suffix && (path += "/" + suffix);
  return path;
}
function generateCommonApis(prefix, resources) {
  const apis = {};
  const defaultFailure = (err) => common_vendor.index.showToast({
    title: err.message,
    icon: "none",
    duration: 2e3
  });
  resources.forEach((resource) => {
    const segments = resource.split("/");
    apis[resource] = {
      // GET /v1/activities/[:pk1]/stages/[:pk2]/projects
      list: async function(pathParams = {}, filterData = null, success = null, failure = defaultFailure, suffix = "") {
        let url = buildPath(prefix, segments, "list", pathParams, suffix + "/list");
        if (filterData && typeof filterData === "object") {
          filterData = util_common.common.trimObj(filterData);
          const query = Object.entries(filterData).map(
            ([k, v]) => Array.isArray(v) ? v.map((e) => `${k}[]=${util_common.ubtoa(e)}`).join("&") : `${k}=${util_common.ubtoa(v)}`
          ).join("&");
          url += `?${query}`;
        }
        return handleRequest(http.get(url, filterData), success, failure);
      },
      // POST /v1/activities/:pk1/stages/:pk2/projects
      add: async function(pathParams = {}, addData = {}, success = null, failure = defaultFailure, suffix = "") {
        let url = buildPath(prefix, segments, "add", pathParams, suffix);
        return handleRequest(http.post(url, util_common.common.trimObj(addData)), success, failure);
      },
      // DELETE /v1/activities/[:pk1]/stages/[:pk2]/projects/[:pk3]
      delete: async function(pathParams = {}, deleteData = {}, success = null, failure = defaultFailure, suffix = "") {
        let url = buildPath(prefix, segments, "delete", pathParams, suffix);
        if (deleteData && typeof deleteData === "object") {
          deleteData = util_common.common.trimObj(deleteData);
          const query = Object.entries(deleteData).map(
            ([k, v]) => Array.isArray(v) ? v.map((e) => `${k}[]=${util_common.ubtoa(e)}`).join("&") : `${k}=${util_common.ubtoa(v)}`
          ).join("&");
          url += `?${query}`;
        }
        return handleRequest(http.delete(url), success, failure);
      },
      // PUT /v1/activities/:pk1/stages/:pk2/projects/:pk3
      update: async function(pathParams = {}, updateData = {}, success = null, failure = defaultFailure, suffix = "") {
        let url = buildPath(prefix, segments, "update", pathParams, suffix);
        return handleRequest(http.put(url, util_common.common.trimObj(updateData)), success, failure);
      },
      // GET /v1/activities/:pk1/stages/:pk2/projects/:pk3
      get: async function(pathParams = {}, getParams = {}, success = null, failure = defaultFailure, suffix = "") {
        let url = buildPath(prefix, segments, "get", pathParams, suffix);
        if (getParams && typeof getParams === "object") {
          getParams = util_common.common.trimObj(getParams);
          const query = Object.entries(getParams).map(
            ([k, v]) => Array.isArray(v) ? v.map((e) => `${k}[]=${util_common.ubtoa(e)}`).join("&") : `${k}=${util_common.ubtoa(v)}`
          ).join("&");
          url += `?${query}`;
        }
        return handleRequest(http.get(url), success, failure);
      }
    };
  });
  return apis;
}
exports.buildPath = buildPath;
exports.generateCommonApis = generateCommonApis;
exports.handleRequest = handleRequest;
exports.http = http;
//# sourceMappingURL=../../.sourcemap/mp-weixin/api/index.js.map
