import { generateCommonApis, buildPath, handleRequest } from '@/api/index.js';
import http from '@/api/index.js';
import common from '@/util/common';

export const globalApi = {
  ...(function () {
    const prefix = "/globalApi";
    const resources = [
      'users',
      'roles',
      'mapNodes',
      'routes',
      'drinks',
      'categories',
      'locations',
      'banners',
      'stores',
      'orders',
      'after-sales'
    ]

    // 动态注册 api 方法
    const apis = generateCommonApis(prefix, resources);

    // 特殊 api 方法
    apis['auth'] =  {
      login: async function (loginData = {}, success = null, failure = null){
        const url = buildPath(prefix, ['auth'], 'post', {}, 'login');
        return handleRequest(http.post(url, common.trimObj(loginData)), success, failure);
      },
      register: async function (registerData = {}, success = null, failure = null){
        const url = buildPath(prefix, ['auth'], 'post', {}, 'register');
        return handleRequest(http.post(url, common.trimObj(registerData)), success, failure);
      }
    }

    return apis;

  })()
}
