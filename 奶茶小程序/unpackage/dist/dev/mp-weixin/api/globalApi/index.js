"use strict";
const api_index = require("../index.js");
const util_common = require("../../util/common.js");
const globalApi = {
  ...function() {
    const prefix = "/globalApi";
    const resources = [
      "users",
      "roles",
      "mapNodes",
      "routes",
      "drinks",
      "categories",
      "locations",
      "banners",
      "stores",
      "orders",
      "after-sales"
    ];
    const apis = api_index.generateCommonApis(prefix, resources);
    apis["auth"] = {
      login: async function(loginData = {}, success = null, failure = null) {
        const url = api_index.buildPath(prefix, ["auth"], "post", {}, "login");
        return api_index.handleRequest(api_index.http.post(url, util_common.common.trimObj(loginData)), success, failure);
      },
      register: async function(registerData = {}, success = null, failure = null) {
        const url = api_index.buildPath(prefix, ["auth"], "post", {}, "register");
        return api_index.handleRequest(api_index.http.post(url, util_common.common.trimObj(registerData)), success, failure);
      }
    };
    return apis;
  }()
};
exports.globalApi = globalApi;
//# sourceMappingURL=../../../.sourcemap/mp-weixin/api/globalApi/index.js.map
