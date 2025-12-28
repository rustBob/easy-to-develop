"use strict";
const common_vendor = require("../common/vendor.js");
const api_index = require("./index.js");
const prefix = "/globalApi";
const upload = (files) => {
  return new Promise((resolve, reject) => {
    let response = {
      code: 200,
      data: []
    };
    if (!files || files.length === 0) {
      resolve(response);
      return;
    }
    getUploadUrl(files).then((res) => {
      var _a;
      const resFile = ((_a = res.data) == null ? void 0 : _a.data) || res.data;
      if (!resFile || !resFile.uploadUrl) {
        throw new Error("获取上传URL失败: 返回数据异常");
      }
      uploadFile(resFile.uploadUrl, files[0]).then((uploadRes) => {
        var _a2;
        const statusCode = uploadRes.statusCode || Array.isArray(uploadRes) && ((_a2 = uploadRes[1]) == null ? void 0 : _a2.statusCode);
        if (statusCode === 200) {
          const url = resFile.uploadUrl.split("?")[0];
          uploadCallback(resFile.id, url).then((callbackRes) => {
            var _a3;
            if (callbackRes && (((_a3 = callbackRes.data) == null ? void 0 : _a3.code) === 200 || callbackRes.code === 200)) {
              response.data.push(url);
              resolve(response);
            } else {
              common_vendor.index.__f__("error", "at api/file.js:38", "上传回调失败", callbackRes);
              response.code = 999;
              response.msg = "上传回调失败";
              resolve(response);
            }
          }).catch((err) => {
            common_vendor.index.__f__("error", "at api/file.js:47", "上传回调异常:", err);
            response.code = 999;
            response.msg = "上传回调异常";
            resolve(response);
          });
        } else {
          common_vendor.index.__f__("error", "at api/file.js:53", "文件上传云存储失败:", uploadRes);
          response.code = 999;
          response.msg = `文件上传失败，状态码: ${statusCode}`;
          resolve(response);
        }
      }).catch((err) => {
        common_vendor.index.__f__("error", "at api/file.js:60", "上传文件过程异常:", err);
        response.code = 999;
        response.msg = "上传文件过程异常";
        resolve(response);
      });
    }).catch((err) => {
      common_vendor.index.__f__("error", "at api/file.js:67", "获取上传URL异常:", err);
      response.code = 999;
      response.msg = "获取上传URL异常";
      resolve(response);
    });
  });
};
const getUploadUrl = (files) => {
  const file = files[0];
  const filePath = file.path || file.tempFilePath;
  return api_index.http.post(prefix + "/files/getUploadUrl", {
    name: file.name.split(".")[0],
    type: filePath.split(".").pop(),
    size: file.size,
    extType: filePath.split(".").pop(),
    isPublic: 1
  });
};
const uploadFile = async (url, file) => {
  return common_vendor.index.request({
    url,
    method: "PUT",
    data: await fileToArrayBuffer(file),
    header: {
      "Content-Type": "application/octet-stream"
      // 显式指定流类型，防止默认 json
    }
  });
};
const uploadCallback = (id, url) => {
  return api_index.http.post(prefix + `/files/callback`, { id, url });
};
function fileToArrayBuffer(file) {
  return new Promise((resolve, reject) => {
    if (!file) {
      common_vendor.index.__f__("log", "at api/file.js:111", "未选择文件");
      reject(new Error("未选择文件"));
      return;
    }
    const fs = common_vendor.index.getFileSystemManager();
    fs.readFile({
      filePath: file.path || file.tempFilePath,
      // 兼容不同文件对象的路径属性
      success: (res) => {
        resolve(res.data);
      },
      fail: (err) => {
        common_vendor.index.__f__("error", "at api/file.js:137", "文件读取错误:", err);
        reject(err);
      }
    });
  });
}
exports.upload = upload;
//# sourceMappingURL=../../.sourcemap/mp-weixin/api/file.js.map
