// /utils/http.js
import { isInvalid, ubtoa } from "@/util/common.js";
import common from "@/util/common.js";
import Store from "@/store";

const baseURL = import.meta.env.VITE_HTTP_BASE_URL

/**
 * 极简请求封装：
 * - 只管发请求
 * - 自动附带 token 到 header.token
 * - 失败直接 reject，交给调用方处理
 */
const http = (options) => {
  const headers = Object.assign(
    { 'Content-Type': 'application/json' },
    { 'easyToken': Store.get('easyToken') || '' }, // 附带 cookie
  );

  return new Promise((resolve, reject) => {
    const task = uni.request({
      url: `${baseURL}${options.url}`,
      method: options.method || 'GET',
      data: options.data || {},
      timeout: 10000,
      header: headers,
      success: (res) => {
        // 获取 cookie
        res.cookies.forEach(cookie => {
          cookie.split(';').forEach(item => {
            const [key, value] = item.split('=');
            if (key && value) {
              Store.set(key, value);
            }
          })
        });
        resolve(res);
      },
      fail: (err) => reject(err)
    });
    resolve.abort = () => task?.abort?.();
  });
}

export default http

// 拦截器
uni.addInterceptor('request', { 
  invoke(args) { 
    if(args.url.includes('login') || args.url.includes('register')){
      return true;
    }
    
    if(!Store.get('easyToken') || !Store.get('user')){
      uni.navigateTo({
        url: '/pages/login/login'
      });
      return false;
    }
  },
  success(res){
    // 401 跳转登录页面
    if(res.data.code === 401){
      uni.showToast({
        title: '登录过期，请重新登录(2s后自动跳转)',
        icon: 'none',
        duration: 2000
      }).then(() => {
        uni.navigateTo({
          url: '/pages/login/login'
        });
      });
      return false;
    }
  }
});

/**
 * 封装请求方法
 */
http.get = (url, params = {}) =>
  http({ url, data: params, method: 'GET' });

http.post = (url, data = {}) =>
  http({ url, data: data, method: 'POST' }); 

http.delete = (url, params = {}) =>
  http({ url, data: params, method: 'DELETE' });

http.put = (url, data = {}) =>
  http({ url, data, method: 'PUT' });

/**
 * 通用请求处理
 * @param reqPromise
 * @param success
 * @param failure
 * @returns {Promise<null>}
 */
export async function handleRequest(reqPromise, success, failure) {
  let res;
  try {
    res = await reqPromise;
  } catch (err) {
    if (!err.msg) err.msg = err.message;
    if (failure) failure(err);
    return null;
  }
  if (res.statusCode !== 200 || (Object.prototype.hasOwnProperty.call(res.data, "code") && res.data.code !== 200)) {
    if (res.notNotify) return null;

    res.msg = res.data.message || "网络错误，请稍后再试。";
    if (failure) failure(res);
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

/**
 * 构建路径（支持多级资源和参数）
 * @param versionPrefix 版本前缀 /globalApi
 * @param segments 资源分段数组（如 ["activities", "stages", "projects"]）
 * @param method 方法类型（all/add/delete/update/get）
 * @param pathParams 路径参数对象（如 { pk1: 123, pk2: 456 }）
 * @returns {string} 完整路径
 */
export function buildPath(versionPrefix, segments, method, pathParams = {}, suffix = ''){
  let path = versionPrefix;
  const depth = segments.length;

  // 不同方法的参数规则
  const isOptional = method === 'list'
  const includeLastParam = method === 'update' || method === 'get';

  pathParams = common.trimObj(pathParams);  

  for (let i = 0; i < depth; i++) {
    path += `/${segments[i]}`;
    const pkKey = `pk${i + 1}`;
    if (isInvalid(pathParams) || isInvalid(pathParams[pkKey])) {
      continue;
    }
    const pkValue = ubtoa(pathParams[pkKey]);

    // 非最后一段 或 需要包含最后参数（delete/update/get）
    if (i < depth - 1 || includeLastParam) {
      if (isOptional) {
        // 可选参数格式: /segment/[:pk]
        path += pkValue !== undefined ? `/${pkValue}` : `/[:${pkKey}]`;
      } else {
        // 必选参数格式: /segment/:pk
        if (pkValue === undefined) throw new Error(`Missing required param: ${pkKey}`);
        path += `/${pkValue}`;
      }
    }
  }

  suffix && (path += '/' + suffix);
  
  return path;
}

export function generateCommonApis(prefix, resources) {
  const apis = {};

  const defaultFailure = err => uni.showToast({
    title: err.message,
    icon: 'none',
    duration: 2000
  });
  
  resources.forEach(resource => {
    const segments = resource.split('/');

    apis[resource] = {
      // GET /v1/activities/[:pk1]/stages/[:pk2]/projects
      list: async function (pathParams = {}, filterData = null, success = null, failure = defaultFailure, suffix = '') {
                let url = buildPath(prefix, segments, 'list', pathParams, suffix + '/list');
        if (filterData && typeof filterData === 'object') {
          filterData = common.trimObj(filterData);
          const query = Object.entries(filterData)
              .map(([k, v]) => Array.isArray(v) ?
                  v.map(e => `${k}[]=${ubtoa(e)}`).join('&')
                  :
                  `${k}=${ubtoa(v)}`
              ).join('&');
          url += `?${query}`;
        }
        return handleRequest(http.get(url, filterData), success, failure);
      },

      // POST /v1/activities/:pk1/stages/:pk2/projects
      add: async function (pathParams = {}, addData = {}, success = null, failure = defaultFailure, suffix = '') {
        let url = buildPath(prefix, segments, 'add', pathParams, suffix);
        return handleRequest(http.post(url, common.trimObj(addData)), success, failure);
      },

      // DELETE /v1/activities/[:pk1]/stages/[:pk2]/projects/[:pk3]
      delete: async function (pathParams = {}, deleteData = {}, success = null, failure = defaultFailure, suffix = '') {
        let url = buildPath(prefix, segments, 'delete', pathParams, suffix);
        if (deleteData && typeof deleteData === 'object') {
          deleteData = common.trimObj(deleteData);
          const query = Object.entries(deleteData)
              .map(([k, v]) => Array.isArray(v) ?
                  v.map(e => `${k}[]=${ubtoa(e)}`).join('&')
                  :
                  `${k}=${ubtoa(v)}`
              ).join('&');
          url += `?${query}`;
        }
        return handleRequest(http.delete(url), success, failure);
      },

      // PUT /v1/activities/:pk1/stages/:pk2/projects/:pk3
      update: async function (pathParams = {}, updateData = {}, success = null, failure = defaultFailure, suffix = '') {
        let url = buildPath(prefix, segments, 'update', pathParams, suffix);
        return handleRequest(http.put(url, common.trimObj(updateData)), success, failure);
      },

      // GET /v1/activities/:pk1/stages/:pk2/projects/:pk3
      get: async function (pathParams = {}, getParams = {}, success = null, failure = defaultFailure, suffix = '') {
        let url = buildPath(prefix, segments, 'get', pathParams, suffix);
        if (getParams && typeof getParams === 'object') {
          getParams = common.trimObj(getParams);
          const query = Object.entries(getParams)
              .map(([k, v]) => Array.isArray(v) ?
                  v.map(e => `${k}[]=${ubtoa(e)}`).join('&')
                  :
                  `${k}=${ubtoa(v)}`
              ).join('&');
          url += `?${query}`;
        }
        return handleRequest(http.get(url), success, failure);
      }
    };
  });
  return apis;
}
