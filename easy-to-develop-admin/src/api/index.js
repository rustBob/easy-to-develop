import axios from 'axios'
import { ElNotification } from 'element-plus';
import {isInvalid, ubtoa} from "@/util/common.js";
import common from '@/util/common.js'
import { clearToken, getToken, checkLogin } from '@/util/token.js'
import router  from '@/router';

// 创建一个axios实例
const ApiClient = axios.create({
  baseURL: import.meta.env.VITE_HTTP_BASE_URL, // 这里替换为你的API基础路径
  timeout: 0, // 请求超时时间，ms
  headers: {
    'Content-Type': 'application/json',
    // 可以在这里添加任何其他需要的头信息
  },
  withCredentials: true,
});
// 添加请求拦截器
ApiClient.interceptors.request.use(function (config) {
  if(config.url.includes('/login') || config.url.includes('/register')){
    return config;
  }

  if (checkLogin()) {
    config.headers['easyToken'] = getToken() || '';
  }else{
    ElNotification({
      message: "请先登录，3秒后跳转至登录页。",
      duration: 3000,
      type: 'error',
      onClose: () => {
        router.push('/login');
      }
    })
  }
  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});

// 响应拦截器
ApiClient.interceptors.response.use(
    res => {
      //如果code为401，则表示token过期，需要重新登录
      if(res.data.code === 401){
        //清除token
        clearToken();

        if(location.pathname == '/login'){
          res.notNotify = true;
          return res;
        }

        ElNotification({
          message: "登录已过期，3秒后跳转至登录页。",
          duration: 3000,
          type: 'error',
          onClose: () => {
            router.push('/login');
          }
        })
      }
      return res;
    },
    error => {
      return Promise.reject(error);
    }
);

export default ApiClient;

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

  suffix && (path += `/${suffix}`);

  return path;
}

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
    else ElNotification({
      type: 'error',
      message: err.msg,
    });
    return null;
  }
  if (res.status !== 200 || (Object.prototype.hasOwnProperty.call(res.data, "code") && res.data.code !== 200)) {
    if (res.notNotify) return null;

    res.msg = res.data.message || "网络错误，请稍后再试。";
    if (failure) failure(res);
    else ElNotification({
      type: 'error',
      message: res.msg,
    });
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
 * 构建通用请求方法
 * @param versionPrefix /globalapi
 * @param resources \["a/b"\]
 * @returns {{}} apis对象
 */
export function generateCommonApis(versionPrefix, resources) {
  const apis = {};
  resources.forEach(resource => {
    const segments = resource.split('/');

    const defaultFailure = err => ElNotification({
      type: 'error',
      message: err.msg,
    });

    apis[resource] = {
      // GET /v1/activities/[:pk1]/stages/[:pk2]/projects
      list: async function (pathParams = {}, filterData = null, success = null, failure = defaultFailure) {        let url = buildPath(versionPrefix, segments, 'list', pathParams, 'list');
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
        return handleRequest(ApiClient.get(url), success, failure);
      },

      // POST /v1/activities/:pk1/stages/:pk2/projects
      add: async function (pathParams = {}, addData = {}, success = null, failure = defaultFailure) {
        const url = buildPath(versionPrefix, segments, 'add', pathParams);
        return handleRequest(ApiClient.post(url, common.trimObj(addData)), success, failure);
      },

      // DELETE /v1/activities/[:pk1]/stages/[:pk2]/projects/[:pk3]
      delete: async function (pathParams = {}, deleteData = {}, success = null, failure = defaultFailure) {
        let url = buildPath(versionPrefix, segments, 'delete', pathParams);
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
        return handleRequest(ApiClient.delete(url), success, failure);
      },

      // PUT /v1/activities/:pk1/stages/:pk2/projects/:pk3
      update: async function (pathParams = {}, updateData = {}, success = null, failure = defaultFailure) {
        const url = buildPath(versionPrefix, segments, 'update', pathParams);
        return handleRequest(ApiClient.put(url, common.trimObj(updateData)), success, failure);
      },

      // GET /v1/activities/:pk1/stages/:pk2/projects/:pk3
      get: async function (pathParams = {}, success = null, failure = defaultFailure) {
        const url = buildPath(versionPrefix, segments, 'get', pathParams);
        return handleRequest(ApiClient.get(url), success, failure);
      }
    };
  });

  return apis;
}
